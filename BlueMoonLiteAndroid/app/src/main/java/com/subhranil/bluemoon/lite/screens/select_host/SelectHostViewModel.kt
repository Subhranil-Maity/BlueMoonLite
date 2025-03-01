package com.subhranil.bluemoon.lite.screens.select_host

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.explorer.ExplorerScreen
import com.subhranil.bluemoon.lite.explorer.ExplorerScreenRoute
import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.repository.LocalInfoRepository
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.screens.qr_connect.QrScannerCameraScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectHostViewModel(
    private val localInfoRepository: LocalInfoRepository,
    private val serverRepository: ServerRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(SelectHostScreenState())
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            // TODO: Add Items As it is being Checked
            val aliveHost = mutableListOf<BasicInfo>()
            for (basicInfo in localInfoRepository.getAllPreviousBasicInfo()) {
                val isAlive = serverRepository.isServerReachable(basicInfo.hostUrl)
                if (isAlive) {
                    aliveHost.add(basicInfo)
                }
            }
            _state.update {
                it.copy(
                    activeHosts = aliveHost
                )
            }
        }
    }
    fun onAction(action: SelectHostScreenActions) {
        when(action) {
            is SelectHostScreenActions.OnConnectButtonPressed -> onConnectButtonPressed(action.navHostController)
            is SelectHostScreenActions.ConnectToHost -> onConnectToHost(action.info, action.navHostController)
        }
    }

    private fun onConnectToHost(info: BasicInfo, navHostController: NavHostController) {
        savedStateHandle["basicUrl"] = info.hostUrl
        navHostController.navigate(ExplorerScreenRoute)
    }

    private fun onConnectButtonPressed(navHostController: NavHostController) {
        navHostController.navigate(QrScannerCameraScreenRoute)
    }


}