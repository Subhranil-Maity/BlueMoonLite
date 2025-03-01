package com.subhranil.bluemoon.lite.models

import kotlinx.serialization.Serializable

/*
 * #[derive(Debug, Serialize, Deserialize)]
 * pub struct BasicInfo {
 *     pub host_name: String,
 *     pub host_version: String,
 *                                                                                                                             pub server_version: String,
 * }
 */


data class BasicInfo(
    val hostName: String,
    val hostVersion: String,
    val serverVersion: String,
    var hostUrl: String,
)
