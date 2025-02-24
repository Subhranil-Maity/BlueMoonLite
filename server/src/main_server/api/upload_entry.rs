use std::fs::File;
use std::io::{ErrorKind, Write};
use axum::extract::{Multipart, Query};
use tokio::fs::remove_file;
use crate::main_server::query_params::QueryParams;
use crate::utils::my_error::{MyError, MyResult};

pub async fn api_upload_entry(
    Query(params): Query<QueryParams>,
    mut multipart: Multipart
) -> MyResult<()>{
    let path = params.get_path()?;
    let should_overwrite = params.get_should_do();
    println!("path: {:?}", path);
    while let Some(field) = multipart.next_field().await? {
        let field_name = field.name().unwrap().to_string();
        let data = field.bytes().await?;
        let exists = path.exists();
        if exists && should_overwrite {
            let _ = trash::delete(path);
        } else if exists {
            return Err(MyError::FileOrFolderAlreadyExists);
        }
        let file = File::create(path)?;
        let mut writer = std::io::BufWriter::new(file);
        writer.write_all(&data)?;
        writer.flush()?
    }
    Ok(())
}