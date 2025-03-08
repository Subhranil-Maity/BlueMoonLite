package com.subhranil.bluemoon.lite.repository.remote.mapper

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.repository.remote.dto.BasicInfoDto

fun BasicInfoDto.toBasicInfo(url: String): BasicInfo{
    return BasicInfo(
        hostName = this.hostName,
        hostVersion = this.hostVersion,
        serverVersion = "Unknown",
        hostUrl = url
    )
}