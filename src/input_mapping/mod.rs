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
use std::collections::HashMap;

pub struct InputMappingPlugin;

#[derive(Resource, Default)]
pub struct InputMapping {
    key_map: HashMap<Vec<KeyCode>, String>,
    ordered_key_map: HashMap<Vec<KeyCode>, String>,
    action_callbacks: HashMap<String, Box<dyn Fn() + Send + Sync>>,
}

impl Plugin for InputMappingPlugin {
    fn build(&self, app: &mut App) {
        app.insert_resource(InputMapping::default());
    }
}

impl InputMapping {
    pub fn add_custom_binding(&mut self, key_codes: Vec<KeyCode>, action: String) {
        let mut sorted_key_codes = key_codes.clone();
        sorted_key_codes.sort();

        if self.ordered_key_map.contains_key(&sorted_key_codes) {
            self.key_map.insert(key_codes, action);
        } else {
            self.ordered_key_map.insert(sorted_key_codes, action);
        }
    }

    pub fn register_action_callback<F>(&mut self, action: String, callback: F)
    where
        F: Fn() + Send + Sync + 'static,
    {
        self.action_callbacks.insert(action, Box::new(callback));
    }

    pub fn rebind_action(&mut self, action: String, new_keys: Vec<KeyCode>) {
        // Xóa binding cũ
        self.key_map.retain(|_, v| v != &action);
        self.ordered_key_map.retain(|_, v| v != &action);

        // Thêm binding mới
        self.add_custom_binding(new_keys, action);
    }

    pub fn get_action(&self, key_codes: &[KeyCode]) -> Option<&String> {
        let mut sorted_key_codes = key_codes.to_vec();
        sorted_key_codes.sort_unstable();

        self.key_map
            .get(key_codes)
            .or_else(|| self.ordered_key_map.get(&sorted_key_codes))
    }

    fn execute_action(&self, action: &str) {
        if let Some(callback) = self.action_callbacks.get(action) {
            callback();
        }
    }
}

pub fn input_system(keyboard_input: Res<ButtonInput<KeyCode>>, input_mapping: Res<InputMapping>) {
    let pressed_keys: Vec<KeyCode> = keyboard_input.get_pressed().cloned().collect();

    if pressed_keys.is_empty() {
        return;
    }

    // Kiểm tra xem tổ hợp phím có trong keymap không
    if let Some(action) = input_mapping.get_action(&pressed_keys) {
        input_mapping.execute_action(action);
        return;
    }

    // Nếu không có tổ hợp, kiểm tra từng phím đơn lẻ
    for key in &pressed_keys {
        if let Some(action) = input_mapping.get_action(&[*key]) {
            input_mapping.execute_action(action);
        }
    }
}
