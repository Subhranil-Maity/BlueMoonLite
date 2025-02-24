use std::net::IpAddr;
use std::path::Path;
use std::sync::Arc;
use eframe::{egui, Frame};
use eframe::egui::Context;
use local_ip_address::{local_ip, Error};
use tokio::sync::Mutex;
use server::gui::{gui, MyApp};
use server::main_server::drive::get_drives;
use server::main_server::fs_entry::get_entries;
use server::main_server::server::start_server;
use server::main_server::server_info::ServerInfo;
use server::utils::logging::{logg, LogLevel};



#[tokio::main]
async fn main() {
    let server_info = ServerInfo::new(
        "0.0.0.0".to_string(),
        "3000".to_string()
    );
    
    let app = MyApp::new(
        "Quick Sync Lite".to_string(),
        server_info
    );
    tokio::spawn(async {
        logg(LogLevel::INFO, "Starting server");
        start_server().await;
    });
    gui(app);
    

}
