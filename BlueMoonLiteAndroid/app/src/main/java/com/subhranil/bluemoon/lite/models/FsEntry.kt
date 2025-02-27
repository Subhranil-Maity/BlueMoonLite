package com.subhranil.bluemoon.lite.models

/*
 * {
 *     "name": "name.extension",
 *     "size": 21299,
 *     "entry_type": "File",
 *     "symlink_location": null,
 *     "absolute_path": "d://some/some/name.extension"
 *   },
 */


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
