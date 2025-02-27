package com.subhranil.bluemoon.lite.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.subhranil.bluemoon.lite.R
import com.subhranil.bluemoon.lite.models.EntryType
import com.subhranil.bluemoon.lite.models.FsEntry

@Composable
fun LinearEntry(
    modifier: Modifier = Modifier,
    entry: FsEntry,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .height(50.dp)
            .padding(8.dp, 0.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.height(50.dp),
            imageVector = getIcon(entry.entryType),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = entry.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = bytesToHumanReadable(entry.size),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(0.2f)
        ) {
        }
    }
}

@Composable
fun getIcon(entryType: EntryType): ImageVector {
    return when (entryType) {
        EntryType.FILE -> Icons.Default.Face
        EntryType.DIRECTORY -> ImageVector.vectorResource(R.drawable.folder_icon)
        EntryType.SYMLINK -> Icons.Default.Face
    };
}

@Preview()
@Composable
private fun LinearEntryPreview() {
    val entries = listOf(
        FsEntry(
            name = "name.extension",
            size = 21299,
            entryType = EntryType.FILE,
            symlinkLocation = null,
            absolutePath = "d://some/some/name.extension"
        ), FsEntry(
            name = "Folder",
            size = 0,
            entryType = EntryType.DIRECTORY,
            symlinkLocation = null,
            absolutePath = "d://some/some/Folder"
        )
    )
    Column {
        for (entry in entries){
            LinearEntry(entry = entry)
            HorizontalDivider(thickness = 1.dp)
        }
    }
}

fun bytesToHumanReadable(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var bytes = bytes
    var unit = 0
    while (bytes > 1024) {
        bytes /= 1024
        unit++
    }
    return "$bytes ${units[unit]}"
}