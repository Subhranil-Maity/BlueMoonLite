use std::collections::HashMap;
use std::io::Error;
use std::iter::Map;
use std::path::Path;
use serde::{Deserialize, Serialize};
use tokio::fs::{DirEntry, File};
use tokio::io::{AsyncReadExt, AsyncWriteExt};

#[derive(Debug, Serialize, Deserialize)]
pub struct FsEntry {
    pub name: String,
    pub size: usize,
    pub entry_type: EntryType,
    pub symlink_location: Option<String>,
    pub absolute_path: String
}

pub type FsEntries = Vec<FsEntry>;


impl FsEntry {
    pub fn new(
        name: String,
        size: usize,
        entry_type: EntryType,
        symlink_location: Option<String>,
        absolute_path: String
    ) -> Self {
        Self {
            name,
            size,
            entry_type,
            symlink_location,
            absolute_path
        }
    }
    
    

}
async fn to_dir_entry(entry: DirEntry) -> Result<FsEntry, Error> {
    let e_type = if entry.metadata().await?.is_dir() {EntryType::Directory}
    else if entry.metadata().await?.is_file() {EntryType::File}
    else {EntryType::Symlink};
    let s_location = match e_type {
        EntryType::Symlink => {
            Some(tokio::fs::read_link(entry.path()).await?.to_string_lossy().to_string())
        }
        _ => {None}
    };
    Ok(FsEntry::new(
        entry.file_name().to_string_lossy().to_string(),
        entry.metadata().await?.len() as usize,
        e_type,
        s_location,
        entry.path().to_string_lossy().to_string()
    ))

}


#[derive(Debug, Serialize, Deserialize)]
pub enum EntryType {
    File,
    Directory,
    Symlink
}




pub async fn get_entries(path: &Path) -> Result<FsEntries, Error> {
    let mut entries = vec![];
    let mut dir = tokio::fs::read_dir(path).await?;
    // let entry = dir.next_entry().await?.unwrap();
    while let Some(entry) = dir.next_entry().await? {
        entries.push(to_dir_entry(entry).await?);
    }
    Ok(entries)
}

pub async fn get_entry_bytes(path: &Path) -> Result<Vec<u8>, Error> {
    let mut file = File::open(path)
        .await?;

    let mut contents = Vec::new();
    file.read_to_end(&mut contents)
        .await?;
    Ok(contents)
}

async fn save_file(path: &Path, data: Vec<u8>) -> Result<(), Error> {
    let mut file = File::create(&path)
        .await?;

    file.write_all(&data)
        .await?;

    Ok(())
}

