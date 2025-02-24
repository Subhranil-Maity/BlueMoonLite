use crate::gui::qr::generate_qr_code;
use crate::main_server::server_info::ServerInfo;
use eframe::egui::{Context, Ui};
use eframe::{egui, Frame};
use local_ip_address::{local_ip, Error};
use std::net::IpAddr;
use std::ops::Add;

mod qr;

#[derive(Clone)]
pub struct MyApp {
    app_name: String,
    is_server_running: bool,
    server_info: ServerInfo
}
// impl Default for MyApp {
//     fn default() -> Self {
//         // let qr_image = generate_qr_code("Hello, Rust!");
//         Self {
//             app_name: "name".to_string(),
//             is_server_running: false,
// 
//         }
//     }
// }
impl MyApp {
    pub fn new(app_name: String, server_info: ServerInfo) -> Self {
        Self {
            app_name,
            is_server_running: false,
            server_info
        }
    }
}

impl eframe::App for MyApp {
    fn update(&mut self, ctx: &Context, frame: &mut Frame) {
        egui::CentralPanel::default().show(ctx, |ui| {
            // ui.label(format!("Count: {}", self.counter));
            match local_ip() {
                Ok(local_ip_address) => {
                    show_ui(self, ui, local_ip_address, &self.server_info.clone());
                }
                Err(Error::LocalIpAddressNotFound) => {
                    ui.label("Device Not Connected To Any Network");
                }
                Err(e) => {
                    ui.label(format!("Error: {:?}", e));
                }
            }
        });
    }
}

fn show_ui(my_app: &mut MyApp, ui: &mut Ui, ip: IpAddr, server: &ServerInfo) {
    let img = generate_qr_code(server.get_qr_info_from_ip_addr(ip).as_str(), 10);
    let texture = ui.ctx().load_texture("qr_code", img, egui::TextureOptions::default());
    let image_size = texture.size_vec2();
    // ui.ho
    ui.vertical_centered(|ui| {
        ui.add(egui::Image::from_texture(&texture).fit_to_exact_size(image_size));
    });
}


pub fn gui(my_app: MyApp) {
    let options = eframe::NativeOptions {
        vsync: true,
        viewport: egui::ViewportBuilder::default().with_inner_size([400.0, 500.0]),
        ..Default::default()
    };

    eframe::run_native(
        my_app.app_name.as_str(),
        options,
        Box::new(|cc| {
            Ok(Box::<MyApp>::new(my_app.clone()))
        })
    ).unwrap()
}