use axum::http::StatusCode;
use axum::Json;
use crate::main_server::drive::{get_drives, Drive, Drives};
use crate::utils::my_error::MyResult;

// pub async fn api_get_drives() -> Result<Json<Drives>, (StatusCode, String)>{
pub async fn api_get_drives() -> MyResult<Json<Drives>>{
    Ok(Json(get_drives().await))
}