package com.subhranil.bluemoon.lite.models


data class Drive(
    val name: String,
    val usedSize: Long,
    val totalSize: Long,
    val diskType: DiskType,
    val mountPoint: String,
    val fsType: String
)

enum class DiskType {
    HDD, SSD, NVME, REMOVABLE, UNKNOWN
}

