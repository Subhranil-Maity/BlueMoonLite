use axum::extract::Query;
use trash::Error;
use crate::main_server::query_params::QueryParams;
use crate::utils::my_error::{MyError, MyResult};

pub async fn api_delete_entry(
    Query(params): Query<QueryParams>
) -> MyResult<()> {
    let path = params.get_path()?;
    let should_delete = params.get_should_do();
    if !path.exists() { 
        return Err(MyError::FileOrFolderNotFound);
    }
    if path.is_file() {
        trash::delete(path)?;
    }
    if path.is_dir() && should_delete{ 
        trash::delete(path)?;
    }else { 
        return Err(MyError::DeletingFolderWithoutFlag);
    }
    Ok(())
}