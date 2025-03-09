package com.subhranil.bluemoon.lite.models

import kotlinx.serialization.Serializable




data class BasicInfo(
    val hostName: String,
    val hostOsName: String,
    val hostVersion: String,
    val serverVersion: String,
    var hostUrl: String,
)
