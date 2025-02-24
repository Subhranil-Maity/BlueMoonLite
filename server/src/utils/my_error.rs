use std::io::{Error, ErrorKind};
use axum::extract::multipart::MultipartError;
use axum::http::StatusCode;
use axum::Json;
use axum::response::{IntoResponse, Response};
use crate::utils::logging::error;
use crate::utils::my_error::MyError::KnownUnknownError;

pub type MyResult<T> = Result<T, MyError>;

#[derive(Debug)]
pub enum MyError {
    LoginFail,
    FileOrFolderNotFound,
    FileOrFolderAlreadyExists,
    AccessDeniedBySystem,
    NotADirectory,
    NotAValidPath,
    QueryParamMissingOrNotValid,
    IoError,
    ParseError,
    NetworkError,
    Timeout,
    Unauthorized,
    BadRequest,
    UNKNOWN,
    FailedToReadFile,
    KnownUnknownError(String),
    DeletingFolderWithoutFlag,
}


impl IntoResponse for MyError {
    fn into_response(self) -> Response {
        error(&format!("{:?}", self));
        (StatusCode::INTERNAL_SERVER_ERROR, "Unhandled_client_error").into_response()
    }
}

impl From<Error> for MyError {
    fn from(_error: Error) -> Self {
        match _error.kind() {
            ErrorKind::PermissionDenied => MyError::AccessDeniedBySystem,
            ErrorKind::NotFound => MyError::FileOrFolderNotFound,
            ErrorKind::InvalidInput => MyError::NotAValidPath,
            ErrorKind::TimedOut => MyError::Timeout,
            ErrorKind::AlreadyExists => MyError::FileOrFolderAlreadyExists,
            ErrorKind::NotADirectory => MyError::NotADirectory,
            _ => MyError::UNKNOWN
        }
    }
}

impl From<MultipartError> for MyError{
    fn from(value: MultipartError) -> Self {
        MyError::BadRequest
    }
}

impl From<trash::Error> for MyError{
    fn from(value: trash::Error) -> Self {
        KnownUnknownError(value.to_string())
    }
}


