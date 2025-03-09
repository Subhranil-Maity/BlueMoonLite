package com.subhranil.bluemoon.lite.repository.actual.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * pub struct BasicInfo {
    pub host_name: String,
    pub host_version: String,
    pub host_os_name: String,
    pub server_version: String,
}
 */

@Serializable
data class BasicInfoDto(
    @SerialName("host_name")
    val hostName: String,

    @SerialName("host_version")
    val hostVersion: String,

    @SerialName("host_os_name")
    val hostOsName: String,

    @SerialName("server_version")
    val serverVersion: String
)