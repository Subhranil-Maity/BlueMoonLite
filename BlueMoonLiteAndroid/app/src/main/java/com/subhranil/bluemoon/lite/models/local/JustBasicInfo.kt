package com.subhranil.bluemoon.lite.models.local

import com.subhranil.bluemoon.lite.models.BasicInfo
import kotlinx.serialization.Serializable

@Serializable
data class JustBasicInfo(
    val hostName: String,
    val hostUrl: String
)

fun JustBasicInfo.toBasicInfo(): BasicInfo {
    return BasicInfo(
        hostName = this.hostName,
        hostVersion = "Unknown",
        serverVersion = "Unknown",
        hostUrl = this.hostUrl
    )
}