package com.subhranil.bluemoon.lite.repository.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 *#[derive(Debug, Clone, Serialize, Deserialize)]
 * pub struct Drive {
 *     pub name : String,
 *     pub used_size : usize,
 *     pub total_size : usize,
 *     pub disk_type : DriveType,
 *     pub mount_point : String,
 *     pub fs_type : String,
 * }
 *
 * #[derive(Debug, Clone, Serialize, Deserialize)]
 * pub enum DriveType {
 *     HDD,
 *     SSD, NVME, REMOVABLE
 * }
 */
@Serializable
data class DriveDto(
    val name: String,
    @SerialName("used_size")
    val usedSize: Long,
    @SerialName("total_size")
    val totalSize: Long,
    @SerialName("disk_type")
    val diskType: String,
    @SerialName("mount_point")
    val mountPoint: String,
    @SerialName("fs_type")
    val fsType: String
)