use eframe::egui::{Color32, ColorImage};
use qrcodegen::{QrCode, QrCodeEcc};

pub fn generate_qr_code(data: &str, scale: usize) -> ColorImage {
    let has_cache = HASH_CASH.lock().unwrap().contains_key(data);
    if has_cache {
        return HASH_CASH.lock().unwrap().get(data).unwrap().clone();
    }
    info("YEss");
    let qr = QrCode::encode_text(data, QrCodeEcc::Medium).unwrap();
    let size = qr.size() as usize;
    let scaled_size = size * scale;

    let mut pixels = Vec::with_capacity(scaled_size * scaled_size);

    for y in 0..size {
        for _sy in 0..scale {
            for x in 0..size {
                let is_black = qr.get_module(x as i32, y as i32);
                let color = if is_black { Color32::BLACK } else { Color32::WHITE };

                // Scale horizontally
                for _sx in 0..scale {
                    pixels.push(color);
                }
            }
        }
    }
    let f = ColorImage {
        size: [scaled_size, scaled_size],
        pixels,
    };
    HASH_CASH.lock().unwrap().insert(
        data.to_string(),
        f.clone()
    );

    f
}



use once_cell::sync::Lazy;
use std::collections::HashMap;
use std::sync::Mutex;
use crate::utils::logging::{info, logg};

static HASH_CASH: Lazy<Mutex<HashMap<String, ColorImage>>> = Lazy::new(|| {
    let mut map:HashMap<String, ColorImage> = HashMap::new();
    Mutex::new(map)
});
