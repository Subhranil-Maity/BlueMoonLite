package com.subhranil.bluemoon.lite.explorer

import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.models.FsEntry

sealed class ExplorerActions {
    data object Refresh : ExplorerActions()
    data class SelectDrive(val drive: Drive) : ExplorerActions()
    data class OnBack(val navHostController: NavHostController) : ExplorerActions()
    data class OnSelectedEntry(val fsEntry: FsEntry) : ExplorerActions()
}