package com.subhranil.bluemoon.lite.explorer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenRoute
import com.subhranil.bluemoon.lite.utils.PathStack
import com.subhranil.bluemoon.lite.utils.result.MyResult
import com.subhranil.bluemoon.lite.utils.result.RootError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExplorerViewModel(
    private val repository: ServerRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ExplorerState())
    val state = _state.asStateFlow()

    init {
        withRefreshing {
            if (savedStateHandle.contains("basicUrl")) {
                val url = savedStateHandle.get<String>("basicUrl")!!
                _state.value = state.value.copy(
                    url = url
                )
                fetchBasicInfo(url)
            } else {
                throw RuntimeException("Basic Info is null")
            }
        }

    }

    fun loadDrives() {
        viewModelScope.launch {
            withRefreshing {
                val drives = repository.getDrives(state.value.basicInfo.hostUrl)
                withHandledError(drives) { data ->
                    _state.update {
                        it.copy(
                            drives = data
                        )
                    }
                }
            }
        }
    }

    private fun fetchBasicInfo(url: String) {
        viewModelScope.launch {
            val info = repository.getBasicInfo(url)
            withHandledError(info) { data ->
                _state.update {
                    it.copy(
                        basicInfo = data
                    )
                }
            }
        }
    }


    fun onAction(action: ExplorerActions) {
        when (action) {
            ExplorerActions.Refresh -> onRefresh()
            is ExplorerActions.SelectDrive -> onSelectDrive(action.drive)
            is ExplorerActions.OnBack -> onBack(action.navHostController)
        }
    }

    private fun onBack(navHostController: NavHostController) {
        withRefreshing {
//            if (state.value.currentPath.isNoWhere()) {
//
//            }
            if (state.value.onWhichExplorer == OnWhichExplorer.FILE_SYSTEM) {
                _state.update {
                    it.copy(
                        onWhichExplorer = OnWhichExplorer.DRIVE
                    )
                }
            }else if(state.value.onWhichExplorer == OnWhichExplorer.DRIVE){
                navHostController.navigate(SelectHostScreenRoute)
                return@withRefreshing
            }
            _state.update {
                it.copy(
                    currentPath = it.currentPath.apply {
                        goBack()
                    }
                )
            }
        }
    }

    private fun onSelectDrive(drive: Drive) {
        viewModelScope.launch {
            withRefreshing {
                _state.update {
                    it.copy(
                        onWhichExplorer = OnWhichExplorer.FILE_SYSTEM,
                        currentPath = PathStack().apply {
                            fromAbsolutePath(drive.mountPoint)
                        }
                    )
                }
            }
        }
    }

    private fun onRefresh() {
        loadDrives()
    }

    private inline fun withRefreshing(block: () -> Unit) {
        _state.update {
            it.copy(
                isRefreshing = true
            )
        }
        block()
        _state.update {
            it.copy(
                isRefreshing = false
            )
        }
    }

    private inline fun <D, E : RootError> withHandledError(
        result: MyResult<D, E>,
        block: (data: D) -> Unit
    ) {
        if (result.isError()) {
            _state.update {
                it.copy(
                    errorString = result.getErrorOrNull().toString()
                )
            }
        } else {
            block(result.successOrNull()!!)
        }
    }
}
