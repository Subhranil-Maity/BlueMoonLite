package com.subhranil.bluemoon.lite.repository.actual.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * {
 *     "name": "name.extension",
 *     "size": 21299,
 *     "entry_type": "File",
 *     "symlink_location": null,
 *     "absolute_path": "d://some/some/name.extension"
 *   },
 */

@Serializable
data class FsEntryDto (
    val name: String,
    val size: Long,
    @SerialName("entry_type")
    val entryType: String,
    @SerialName("symlink_location")
    val symlinkLocation: String?,
    @SerialName("absolute_path")
    val absolutePath: String
)