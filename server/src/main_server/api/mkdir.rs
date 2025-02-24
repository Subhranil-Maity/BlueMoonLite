use axum::extract::Query;
use tokio::fs::create_dir_all;
use crate::main_server::query_params::QueryParams;
use crate::utils::my_error::{MyError, MyResult};

pub async fn api_mkdir(
    Query(param): Query<QueryParams>
) -> MyResult<()> {
    let path = param.get_path()?;
    if path.exists() { 
        return Err(MyError::FileOrFolderAlreadyExists);
    }
    let a = create_dir_all(path).await?;
    Ok(())
}