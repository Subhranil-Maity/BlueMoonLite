use std::net::SocketAddr;
use axum::Router;
use axum::routing::get;
use tower_http::trace::TraceLayer;
use crate::main_server::api::lite_api_handler;

pub async fn start_server(){
    tracing_subscriber::fmt()
        .with_max_level(tracing::Level::DEBUG)
        .init();
    let app = Router::new().route(
        "/",
        get(root)
    ).nest(
        "/lite", lite_api_handler()
    ).layer(TraceLayer::new_for_http());

    // info(format!("Starting server at {}:{}", "0.0.0.0", 3000));
    let addr = SocketAddr::from(([0, 0, 0, 0], 3000));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn root() -> &'static str {
    "Hello, World!"
}