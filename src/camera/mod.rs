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

use crate::input_mapping::InputMapping;

use bevy::input::mouse::MouseMotion;
use bevy::window::{CursorGrabMode, Window};

pub fn camera_control_system(
    time: Res<Time>,
    keyboard_input: Res<ButtonInput<KeyCode>>,
    mut mouse_motion_events: EventReader<MouseMotion>,
    mut windows: Query<&mut Window>,
    input_mapping: Res<InputMapping>,
    mut query: Query<&mut Transform, With<Camera>>,
) {
    let mut direction = Vec3::ZERO;
    let sensitivity = 0.005;
    let speed = 5.0; // Tốc độ di chuyển

    // Kiểm tra nếu cửa sổ đang được focus
    let mut window = windows.single_mut();
    if window.focused {
        // Ẩn và lock chuột
        window.cursor_options.visible = false;
        window.cursor_options.grab_mode = CursorGrabMode::Locked;

        for mut transform in query.iter_mut() {
            // Tổng hợp chuyển động chuột
            let mut delta = Vec2::ZERO;
            for event in mouse_motion_events.read() {
                delta += event.delta;
            }

            // Tính toán góc quay dựa trên chuyển động chuột
            let delta_yaw = -delta.x * sensitivity;
            let delta_pitch = -delta.y * sensitivity;

            // Lấy góc Euler hiện tại của camera
            let (yaw, pitch, rot) = transform.rotation.to_euler(EulerRot::YXZ);

            // Cập nhật góc yaw và pitch
            let new_yaw = yaw + delta_yaw;
            let max_pitch = std::f32::consts::FRAC_PI_2;
            let new_pitch = (pitch + delta_pitch).clamp(-max_pitch, max_pitch);

            // Cập nhật rotation của camera
            transform.rotation = Quat::from_euler(EulerRot::YXZ, new_yaw, new_pitch, rot);
        }
    } else {
        // Nếu cửa sổ mất focus, hiện lại chuột và thả chuột
        window.cursor_options.visible = true;
        window.cursor_options.grab_mode = CursorGrabMode::None;
    }

    // Lấy danh sách phím đang nhấn
    let pressed_keys: Vec<KeyCode> = keyboard_input.get_pressed().cloned().collect();

    // Xử lý Input Mapping
    for key in &pressed_keys {
        if let Some(action) = input_mapping.get_action(&[*key]) {
            match action.as_str() {
                "move_forward" => direction -= Vec3::Z,
                "move_backward" => direction += Vec3::Z,
                "move_left" => direction -= Vec3::X,
                "move_right" => direction += Vec3::X,
                "crouch" => direction -= Vec3::Y,
                "jump" => direction += Vec3::Y,
                _ => {}
            }
        }
    }

    // Cập nhật vị trí camera
    for mut transform in query.iter_mut() {
        if direction.length_squared() > 0.0 {
            direction = direction.normalize();
            let movement = transform.rotation * direction * speed * time.delta_secs();
            transform.translation += movement;
        }
    }
}
