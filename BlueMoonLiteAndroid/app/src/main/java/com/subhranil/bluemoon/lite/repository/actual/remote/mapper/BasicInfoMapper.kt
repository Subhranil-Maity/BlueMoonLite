package com.subhranil.bluemoon.lite.repository.actual.remote.mapper

import com.subhranil.bluemoon.lite.models.BasicInfo
import com.subhranil.bluemoon.lite.repository.actual.remote.dto.BasicInfoDto

fun BasicInfoDto.toBasicInfo(url: String): BasicInfo{
    return BasicInfo(
        hostName = this.hostName,
        hostVersion = this.hostVersion,
        serverVersion = this.serverVersion,
        hostUrl = url,
        hostOsName = this.hostOsName
    )
}