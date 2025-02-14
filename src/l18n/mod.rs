/**
 * Copyright © 2025 NguyenDuck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
////////////////////////////////////////////////////////////////////////
use bevy::prelude::*;
use serde_yml::Value;
use std::{fs, path::Path};

pub struct L18nPlugin;

impl Plugin for L18nPlugin {
    fn build(&self, app: &mut App) {
        app.add_systems(PreStartup, setup_l18n);
    }
}

#[derive(Resource, Default)]
pub struct L18n {
    global_translations: Value,
    translations: Value,
    current_lang: String,
}

impl L18n {
    fn new() -> Self {
        let global_translations = Self::load_global();

        L18n {
            global_translations,
            translations: Value::Null,
            current_lang: "en_US".to_string(),
        }
    }

    // Hàm để load nội dung từ global.yml
    fn load_global() -> Value {
        let global_path = "resources/lang/global.yml";
        if Path::new(global_path).exists() {
            let global_content = fs::read_to_string(global_path).unwrap_or_default();
            serde_yml::from_str(&global_content).unwrap_or_default()
        } else {
            Value::Null // Nếu không tìm thấy file global.yml, trả về Null
        }
    }

    // Hàm để load ngôn ngữ từ file lang (ví dụ: en_US.yml, vi_VN.yml) và kết hợp với global.yml
    fn load_language(&self, lang: &str) -> Value {
        let path = format!("resources/lang/{}.yml", lang);

        // Nếu không tìm thấy file ngôn ngữ, trả về Null
        if !Path::new(&path).exists() {
            return Value::Null;
        }

        // Đọc nội dung file ngôn ngữ
        let content = fs::read_to_string(&path).unwrap_or_default();
        let mut lang_values: Value = serde_yml::from_str(&content).unwrap_or_default();

        // Kết hợp các giá trị từ global.yml vào lang_values mà không ghi đè các key đã có
        if let Value::Mapping(ref mut lang_map) = lang_values {
            if let Value::Mapping(global_map) = &self.global_translations {
                for (key, value) in global_map {
                    // Chỉ thêm các key từ global.yml nếu chúng chưa có trong lang_values
                    if !lang_map.contains_key(&key) {
                        lang_map.insert(key.clone(), value.clone());
                    }
                }
            }
        }

        lang_values
    }

    pub fn set_language(&mut self, lang: &str) {
        self.translations = self.load_language(lang);
        self.current_lang = lang.to_string();
    }

    pub fn translate(&self, keys: Vec<&str>) -> String {
        if keys.is_empty() {
            // Nếu không có khóa, trả về chuỗi rỗng
            return "".to_string();
        }

        let mut current_value: &Value = &self.global_translations;

        for key in &keys {
            let key_str = key.to_string();
            current_value = match current_value {
                Value::Mapping(map) => map.get(&Value::String(key_str)).unwrap_or(&Value::Null),
                _ => &Value::Null,
            };
        }

        match current_value {
            Value::String(s) => s.clone(),
            _ => "".to_string(),
        }
    }
}

pub fn detect_system_language() -> String {
    std::env::var("LANG")
        .unwrap()
        .split('.')
        .next()
        .unwrap()
        .replace('-', "_")
}

pub fn setup_l18n(mut commands: Commands) {
    let detected_lang = detect_system_language();
    let mut l18n = L18n::new();
    l18n.set_language(&detected_lang);
    commands.insert_resource(l18n);
}
