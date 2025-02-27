package com.subhranil.bluemoon.lite.models

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
data class Drive(
    val name: String,
    val usedSize: Long,
    val totalSize: Long,
    val diskType: DiskType,
    val mountPoint: String,
    val fsType: String
)

enum class DiskType {
    HDD, SSD, NVME, REMOVABLE
}

