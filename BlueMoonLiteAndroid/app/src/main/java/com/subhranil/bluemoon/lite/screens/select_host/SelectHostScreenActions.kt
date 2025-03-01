package com.subhranil.bluemoon.lite.screens.select_host

import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.models.BasicInfo


sealed class SelectHostScreenActions() {
    data class OnConnectButtonPressed(val navHostController: NavHostController): SelectHostScreenActions()
    data class ConnectToHost(val info: BasicInfo, val navHostController: NavHostController) : SelectHostScreenActions()

}
