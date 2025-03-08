package com.subhranil.bluemoon.lite.explorer

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.models.FsEntry
import com.subhranil.bluemoon.lite.utils.PathStack

data class ExplorerState (
    val currentPath: PathStack = PathStack(),
    val basicInfo: BasicInfo = BasicInfo("", "", "", ""),
    var errorString: String? = null,
    val url: String = "",
    val isRefreshing: Boolean = false,
    val drives: List<Drive> = emptyList(),
    val title: String = "Explorer",
    val onWhichExplorer: OnWhichExplorer = OnWhichExplorer.DRIVE,
    val currentPathEntries: List<FsEntry> = emptyList()
)

enum class OnWhichExplorer {
    DRIVE,
    FILE_SYSTEM
}
