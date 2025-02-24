use axum::Json;
use crate::main_server::basic_info::BasicInfo;
use crate::utils::my_error::MyResult;

pub async fn api_get_basic_info() -> MyResult<Json<BasicInfo>>{
    Ok(Json(BasicInfo::get_basic_info()))
}