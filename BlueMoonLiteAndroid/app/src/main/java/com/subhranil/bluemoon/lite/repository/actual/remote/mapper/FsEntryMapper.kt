package com.subhranil.bluemoon.lite.repository.actual.remote.mapper

import androidx.compose.ui.text.toLowerCase
import com.subhranil.bluemoon.lite.models.EntryType
import com.subhranil.bluemoon.lite.models.FsEntry
import com.subhranil.bluemoon.lite.repository.actual.remote.dto.FsEntryDto
import java.util.Locale

fun FsEntryDto.toFsEntry(): FsEntry{
    val entryType = when(this.entryType.lowercase(Locale.getDefault())){
        "file" -> EntryType.FILE
        "directory" -> EntryType.DIRECTORY
        "symlink" -> EntryType.SYMLINK
        else -> EntryType.FILE
    }
    return FsEntry(
        name = this.name,
        size = this.size,
        entryType = entryType,
        symlinkLocation = this.symlinkLocation,
        absolutePath = this.absolutePath
    )
}