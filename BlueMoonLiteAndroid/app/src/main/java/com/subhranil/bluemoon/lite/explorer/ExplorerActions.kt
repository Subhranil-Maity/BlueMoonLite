package com.subhranil.bluemoon.lite.explorer

import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.Drive

sealed class ExplorerActions {
    data object Refresh : ExplorerActions()
    data class SelectDrive(val drive: Drive) : ExplorerActions()
    data class OnBack(val navHostController: NavHostController) : ExplorerActions()
}