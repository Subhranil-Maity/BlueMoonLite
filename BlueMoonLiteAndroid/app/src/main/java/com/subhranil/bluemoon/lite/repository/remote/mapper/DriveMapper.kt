package com.subhranil.bluemoon.lite.repository.remote.mapper

import com.subhranil.bluemoon.lite.models.DiskType
import com.subhranil.bluemoon.lite.models.Drive
import com.subhranil.bluemoon.lite.repository.remote.dto.DriveDto


fun DriveDto.toDrive(): Drive {
    val disktype = when (this.diskType) {
        "HDD" -> DiskType.HDD
        "SSD" -> DiskType.SSD
        "NVME" -> DiskType.NVME
        "REMOVABLE" -> DiskType.REMOVABLE
        else -> DiskType.UNKNOWN
    }
    return Drive(
        name = this.name,
        usedSize = this.usedSize,
        totalSize = this.totalSize,
        diskType = disktype,
        mountPoint = this.mountPoint,
        fsType = this.fsType
    )
}