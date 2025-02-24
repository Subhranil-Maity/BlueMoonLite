use std::net::IpAddr;
use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct ServerInfo{
    pub ip: String,
    pub port: String,
}

impl ServerInfo {
    pub fn new(ip: String, port: String) -> Self {
        Self {
            ip,
            port
        }
    }
    
    fn get_qr_info(&self) -> String {
        format!("http://{}:{}", self.ip, self.port)
    }
    
    pub fn get_qr_info_from_ip_addr(&self, ip: IpAddr) -> String {
        format!("http://{}:{}", ip, self.port)
    }
}