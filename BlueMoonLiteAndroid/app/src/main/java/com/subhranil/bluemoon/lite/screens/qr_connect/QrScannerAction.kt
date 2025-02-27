package com.subhranil.bluemoon.lite.screens.qr_connect

import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.models.BasicInfo

sealed class QrScannerAction {
    data object  OnBottomSheetDismissed: QrScannerAction()
    data class OnConnectButtonPressed(val navHostController: NavHostController, val basicInfo: BasicInfo): QrScannerAction()

}