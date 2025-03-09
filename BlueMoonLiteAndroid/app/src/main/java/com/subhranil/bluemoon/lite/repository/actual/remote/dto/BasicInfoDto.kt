package com.subhranil.bluemoon.lite.repository.actual.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * #[derive(Debug, Serialize, Deserialize)]
 * pub struct BasicInfo {
 *     pub host_name: String,
 *     pub host_version: String,
 *                                                                                                                             pub server_version: String,
 * }
 */

@Serializable
data class BasicInfoDto (
    @SerialName("host_name")
    val hostName: String,
    @SerialName("host_version")
    val hostVersion: String,
)