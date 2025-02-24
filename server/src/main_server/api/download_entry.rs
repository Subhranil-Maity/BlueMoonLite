use std::path::Path;
use axum::body::Body;
use axum::extract::Query;
use axum::http::{header, Response, StatusCode};
use axum::response::IntoResponse;
use axum::ServiceExt;
use tokio::fs::File;
use tokio::io::AsyncReadExt;
use tower_http::services::ServeFile;
use crate::main_server::query_params::QueryParams;
use crate::utils::my_error::{MyError, MyResult};

pub async fn api_download_entry(
    Query(params): Query<QueryParams>
) -> MyResult<impl IntoResponse> {
    let file_path = params.get_path()?;
    let file_name = Path::new(&file_path)
        .file_name()
        .and_then(|name| name.to_str())
        .unwrap_or("downloaded_file");

    // Open the file
    let mut file = match File::open(&file_path).await {
        Ok(file) => file,
        Err(_) => return Err(MyError::FileOrFolderNotFound),
    };

    // Read file contents into a buffer
    let mut contents = Vec::new();
    if let Err(_) = file.read_to_end(&mut contents).await {
        return Err(MyError::FailedToReadFile);
    }

    // Create response
    let mut response: Response<Body> = Response::new(contents.into());
    response.headers_mut().insert(
        header::CONTENT_TYPE,
        "application/octet-stream".parse().unwrap(),
    );

    response.headers_mut().insert(
        header::CONTENT_DISPOSITION,
        format!("attachment; filename=\"{}\"", file_name).parse().unwrap(),
    );

    Ok(response)
}

