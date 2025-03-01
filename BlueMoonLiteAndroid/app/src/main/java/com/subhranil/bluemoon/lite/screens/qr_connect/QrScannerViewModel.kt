package com.subhranil.bluemoon.lite.screens.qr_connect

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.explorer.ExplorerScreenRoute
import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.repository.ServerRepository
import com.subhranil.bluemoon.lite.utils.Validator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class QrScannerViewModel(
    private val serverRepository: ServerRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(QrScannerScreenState())
    val state = _state.asStateFlow()

    fun checkServerConnection(hostUrl: String, onValid: NavHostController) {
        if (state.value.isBasicInfoLoading || state.value.shouldShowBottomSheet) return
        viewModelScope.launch {

            if (Validator.isUrlValid(hostUrl)) {
                delay(1000)
                val isAlive = serverRepository.isServerReachable(hostUrl)
                if (isAlive) {
                    showBottomSheet(hostUrl)
                }
            }
        }
    }

    fun getBasicInfo() {

    }

    private suspend fun showBottomSheet(hostUrl: String) {
        _state.update {
            it.copy(
                shouldShowBottomSheet = true,
                isBasicInfoLoading = true
            )
        }
        val basicInfo = serverRepository.getBasicInfo(hostUrl)
        when (basicInfo) {
            is com.subhranil.bluemoon.lite.utils.result.MyResult.Error -> _state.update {
                it.copy(
                    isError = basicInfo.error.toString()
                )
            }
            is com.subhranil.bluemoon.lite.utils.result.MyResult.Success -> {
                _state.update {
                    it.copy(
                        basicInfo = basicInfo.data
                    )
                }
            }
        }
        _state.update {
            it.copy(
                isBasicInfoLoading = false
            )
        }

    }

    fun onAction(action: QrScannerAction) {
        when(action) {
            is QrScannerAction.OnBottomSheetDismissed -> onBottomSheetDismissed()
            is QrScannerAction.OnConnectButtonPressed -> onConnectButtonPressed(action.navHostController, action.basicInfo)
        }
    }

    private fun onConnectButtonPressed(navHostController: NavHostController, basicInfo: BasicInfo) {
        savedStateHandle["basicUrl"] = basicInfo.hostUrl
        navHostController.navigate(ExplorerScreenRoute)
    }

    private fun onBottomSheetDismissed() {
        _state.update {
            it.copy(
                shouldShowBottomSheet = false,
                isBasicInfoLoading = false,
                isError = null,
                basicInfo = null
            )
        }
    }
}