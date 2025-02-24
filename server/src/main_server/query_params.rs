use std::path::Path;
use serde::{Deserialize, Serialize};
use crate::utils::my_error::{MyError, MyResult};

#[derive(Debug, Deserialize, Serialize)]
pub struct QueryParams {
    path: Option<String>,
    should_do: Option<String>
}

impl QueryParams {
    pub fn get_path(&self) -> MyResult<&Path> {
        match &self.path {
            Some(p) => {
                let path = Path::new(p);
                Ok(path)
            }
            None => Err(MyError::QueryParamMissingOrNotValid),
        }
    }
    pub fn get_should_do(&self) -> bool {
        match &self.should_do {
            Some(s) => {
                if s == "true" {
                    true
                } else {
                    false
                }
            }
            None => false
        }
    }
}