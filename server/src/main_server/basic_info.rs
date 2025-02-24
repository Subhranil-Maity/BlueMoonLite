use serde::{Deserialize, Serialize};
use sysinfo::System;

#[derive(Debug, Serialize, Deserialize)]
pub struct BasicInfo {
    pub host_name: String,
    pub host_version: String,

    pub server_version: String,
}

impl BasicInfo {
    pub fn new(host_name: String, host_version: String, server_version: String) -> Self {
        Self {
            host_name,
            host_version,
            server_version
        }
    }
    
    pub fn get_basic_info() -> BasicInfo {
        BasicInfo {
            host_name: System::name().unwrap_or("Unknown".to_string()),
            host_version: System::kernel_version().unwrap_or("Unknown".to_string()),
            server_version: env!("CARGO_PKG_VERSION").to_string()
        }
    }
}
