package com.subhranil.bluemoon.lite.screens.select_host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
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
    private val serverRepository: ServerRepository
): ViewModel() {
    private val _state = MutableStateFlow(_root_ide_package_.com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenState())
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
    fun onAction(action: com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenActions) {
        when(action) {
            is com.subhranil.bluemoon.lite.screens.select_host.SelectHostScreenActions.OnConnectButtonPressed -> onConnectButtonPressed(action.navHostController)
        }
    }

    private fun onConnectButtonPressed(navHostController: NavHostController) {
        navHostController.navigate(QrScannerCameraScreenRoute)
    }


}