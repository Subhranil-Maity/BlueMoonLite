package com.subhranil.bluemoon.lite.models

import androidx.compose.runtime.Immutable



data class FsEntry (
    val name: String,
    val size: Long,
    val entryType: EntryType,
    val symlinkLocation: String?,
    val absolutePath: String
)

enum class EntryType {
    FILE, DIRECTORY, SYMLINK
}
