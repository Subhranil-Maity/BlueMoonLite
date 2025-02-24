use std::net::SocketAddr;
use axum::Router;
use axum::routing::get;
use crate::main_server::api::handler;

pub async fn start_server(){
    let app = Router::new().route(
        "/",
        get(root)
    ).merge(handler());

    // info(format!("Starting server at {}:{}", "0.0.0.0", 3000));
    let addr = SocketAddr::from(([0, 0, 0, 0], 3000));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn root() -> &'static str {
    "Hello, World!"
}