package com.subhranil.bluemoon.lite.explorer

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.models.EntryType
import com.subhranil.bluemoon.lite.models.FsEntry
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenRoute
import com.subhranil.bluemoon.lite.utils.PathStack
import com.subhranil.bluemoon.lite.utils.result.MyResult
import com.subhranil.bluemoon.lite.utils.result.RootError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.get

class ExplorerViewModel(
    private val repository: ServerRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ExplorerState())
    val state = _state.asStateFlow()

    init {
        if (savedStateHandle.contains("basicUrl")) {
            val url = savedStateHandle.get<String>("basicUrl")!!
            Log.d("ExplorerViewModel", "Basic Info URL: $url")
            _state.value = state.value.copy(
                url = url
            )
            fetchBasicInfo(url)
        } else {
            throw RuntimeException("Basic Info is null")
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
        runBlocking {
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
            is ExplorerActions.OnSelectedEntry -> onSelectedEntry(action.fsEntry)
        }
    }

    private fun onSelectedEntry(fsEntry: FsEntry) {
        if (fsEntry.entryType == EntryType.DIRECTORY) {
            _state.update {
                it.copy(
                    currentPath = PathStack.fromAbsolutePath(fsEntry.absolutePath)
                )
            }
//            loadCurrentPath()
        } else {
            Toast.makeText(get().get(), "Not Implemented", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onBack(navHostController: NavHostController) {
        if (state.value.onWhichExplorer == OnWhichExplorer.DRIVE) {
            navHostController.navigate(SelectHostScreenRoute)
            return
        }
        if (state.value.onWhichExplorer == OnWhichExplorer.FILE_SYSTEM) {
            if (_state.value.currentPath.isNoWhere()) {
                goToDriveScreen()
            }else{
                _state.update {
                    it.copy(
                        currentPath = it.currentPath.getExceptLast()
                    )
                }
            }
        }
    }

    private fun onSelectDrive(drive: Drive) {
        viewModelScope.launch {
            withRefreshing {
                _state.update {
                    it.copy(
                        onWhichExplorer = OnWhichExplorer.FILE_SYSTEM,
                        currentPath = PathStack.fromAbsolutePath(drive.mountPoint)
                    )
                }
            }
        }
    }

    private fun onRefresh() {
        when (state.value.onWhichExplorer) {
            OnWhichExplorer.DRIVE -> loadDrives()
            OnWhichExplorer.FILE_SYSTEM -> loadCurrentPath()
        }
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
    private fun goToDriveScreen() {
        _state.update {
            it.copy(
                onWhichExplorer = OnWhichExplorer.DRIVE
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

    fun loadCurrentPath() {
        viewModelScope.launch {
            withRefreshing {
                val entries = repository.getEntries(
                    state.value.basicInfo.hostUrl,
                    state.value.currentPath.getAbsolutePath()
                )
                withHandledError(entries) { data ->
                    _state.update { it ->
                        it.copy(
                            currentPathEntries = data.sortedByDescending { d -> d.entryType }
                        )
                    }
                }
            }
        }
    }

    fun consumeError(): String {
        val error = state.value.errorString ?: "No Errors"
        Log.d("ExplorerViewModel", "Error: $error")
        _state.update {
            it.copy(
                errorString = null
            )
        }
        return error
    }
}
