package com.subhranil.bluemoon.lite.screens.select_host

import androidx.navigation.NavHostController


sealed class SelectHostScreenActions(){
    data class OnConnectButtonPressed(val navHostController: NavHostController): SelectHostScreenActions()
}
