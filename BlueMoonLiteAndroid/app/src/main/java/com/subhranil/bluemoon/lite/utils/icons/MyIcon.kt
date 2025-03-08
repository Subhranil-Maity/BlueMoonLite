package com.subhranil.bluemoon.lite.utils.icons


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.subhranil.bluemoon.lite.models.EntryType
import com.subhranil.bluemoon.lite.models.FsEntry

@Composable
fun getIcon(fsEntry: FsEntry): Pair<ImageVector, Color> {
    val cache = remember {
        mutableMapOf<String, Pair<ImageVector, Color>>()
    }
    return cache.getOrPut(fsEntry.name) {
        when (fsEntry.entryType) {
            EntryType.FILE -> getFileIcon(fileName = fsEntry.name)
            EntryType.DIRECTORY -> getDirectoryIcon(folderName = fsEntry.name)
            EntryType.SYMLINK -> getSymlinkIcon()
        }
    }
}
