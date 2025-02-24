use axum::Router;
use axum::routing::{delete, get, post};
use crate::main_server::api::basic_info::api_get_basic_info;
use crate::main_server::api::delete::api_delete_entry;
use crate::main_server::api::download_entry::api_download_entry;
use crate::main_server::api::get_drives::api_get_drives;
use crate::main_server::api::get_entries::api_get_entries;
use crate::main_server::api::mkdir::api_mkdir;
use crate::main_server::api::upload_entry::api_upload_entry;

mod get_drives;
mod get_entries;
mod download_entry;
mod upload_entry;
mod delete;
mod mkdir;
mod basic_info;

pub fn handler() -> Router {
    Router::new().route("/api/get_drives", get(api_get_drives))
        .route("/api/get_entries", get(api_get_entries))
        .route("/api/download_entry", get(api_download_entry))
        .route("/api/upload_entry", post(api_upload_entry))
        .route("/api/delete_entry", delete(api_delete_entry))
        .route("/api/mkdir", post(api_mkdir))
        .route("/api/get_basic_info", get(api_get_basic_info))
    // Router::new()
}

