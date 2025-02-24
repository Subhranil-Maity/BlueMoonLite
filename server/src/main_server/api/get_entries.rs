use axum::extract::Query;
use axum::Json;
use crate::main_server::fs_entry::{get_entries, FsEntries, FsEntry};
use crate::main_server::query_params::QueryParams;
use crate::utils::my_error::MyResult;

pub async fn api_get_entries(
    Query(params): Query<QueryParams>
) -> MyResult<Json<FsEntries>> {
    let path = params.get_path()?;
    let entries = get_entries(path).await?;
    Ok(Json(entries))
}