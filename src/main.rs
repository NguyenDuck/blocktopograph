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
use bevy::{
    asset::{Assets, RenderAssetUsages},
    color::palettes::css::{GREEN, WHITE},
    input::mouse::MouseMotion,
    math::Vec3,
    pbr::DirectionalLightShadowMap,
    pbr::StandardMaterial,
    prelude::*,
    render::mesh::{Indices, PrimitiveTopology},
    utils::default,
    window::{CursorGrabMode, PrimaryWindow},
    DefaultPlugins,
};

use bevy_atmosphere::{model::AtmosphereModel, plugin::AtmospherePlugin, prelude::Nishita};
use bevy_rapier3d::prelude::*;

fn main() {
    App::new()
        .insert_resource(ClearColor(Color::srgb(0.0, 0.0, 0.0)))
        .add_plugins(DefaultPlugins)
        .insert_resource(AmbientLight {
            brightness: 250.,
            ..default()
        })
        .insert_resource(AtmosphereModel::new(Nishita::default()))
        .add_plugins(AtmospherePlugin)
        .insert_resource(DirectionalLightShadowMap { size: 4096 })
        .add_systems(PreStartup, setup)
        .add_systems(Update, (camera_control_system, cursor_grab_system))
        .insert_resource(blocktopograph::input_mapping::InputMapping::default())
        .run();
}

fn create_cube() -> Mesh {
    Mesh::new(
        PrimitiveTopology::TriangleList,
        RenderAssetUsages::default(),
    )
    .with_inserted_attribute(
        Mesh::ATTRIBUTE_POSITION,
        vec![
            [0., 0., 0.],
            [0., 0., 1.],
            [0., 1., 0.],
            [0., 1., 1.],
            [1., 0., 0.],
            [1., 0., 1.],
            [1., 1., 0.],
            [1., 1., 1.],
        ],
    )
    .with_inserted_indices(Indices::U16(vec![
        1, 5, 3, 3, 5, 7, // North
        0, 2, 4, 4, 2, 6, // South
        0, 1, 2, 2, 1, 3, // East
        6, 7, 4, 4, 7, 5, // West
        2, 3, 6, 6, 3, 7, // Up
        5, 1, 4, 4, 1, 0, // Down
    ]))
    .with_inserted_attribute(Mesh::ATTRIBUTE_NORMAL, vec![[0.0, 1.0, 0.0]; 8])
}

fn setup(
    mut commands: Commands,
    mut meshes: ResMut<Assets<Mesh>>,
    mut materials: ResMut<Assets<StandardMaterial>>,
) {
    commands.spawn((
        Camera3d::default(),
        Transform::from_xyz(-5., 1., 0.).looking_at(Vec3::ZERO, Dir3::Y),
    ));

    commands.spawn((
        Mesh3d(meshes.add(create_cube())),
        MeshMaterial3d(materials.add(StandardMaterial {
            base_color: GREEN.into(),
            ..default()
        })),
        Transform::from_xyz(0., 1., 0.),
    ));

    commands.spawn((
        Mesh3d(meshes.add(Plane3d::new(Vec3::Y, Vec2::ONE * 100.))),
        Collider::halfspace(Vec3::Z).unwrap(),
        MeshMaterial3d(materials.add(StandardMaterial {
            base_color: WHITE.into(),
            ..default()
        })),
        Transform::IDENTITY
            .with_translation(Vec3::ONE.with_y(0.) / 2.)
            .looking_to(Dir3::Z, Dir3::Y),
    ));

    commands.spawn((
        DirectionalLight {
            shadows_enabled: true,
            illuminance: 10000.,
            shadow_depth_bias: 0.02,
            shadow_normal_bias: 0.5,
            ..default()
        },
        Transform::from_xyz(-5., 5., -5.).looking_at(Vec3::ZERO, Dir3::Y),
    ));
}

fn camera_control_system(
    time: Res<Time>,
    mouse_button_input: Res<ButtonInput<MouseButton>>,
    mut mouse_motion_events: EventReader<MouseMotion>,
    keyboard_input: Res<ButtonInput<KeyCode>>,
    mut query: Query<&mut Transform, With<Camera>>,
) {
    // Thiết lập độ nhạy chuột
    let sensitivity = 0.005;
    let speed = 5.0;

    // Chỉ sử dụng chuyển động chuột khi chuột phải đang được nhấn giữ
    if mouse_button_input.pressed(MouseButton::Right) {
        for mut transform in query.iter_mut() {
            // Tổng hợp chuyển động chuột
            let mut delta = Vec2::ZERO;
            for event in mouse_motion_events.read().into_iter() {
                delta += event.delta;
            }

            // Tính toán góc quay dựa trên chuyển động chuột
            let delta_yaw = -delta.x * sensitivity;
            let delta_pitch = -delta.y * sensitivity;

            // Lấy góc Euler hiện tại của camera
            let (yaw, pitch, roll) = transform.rotation.to_euler(EulerRot::YXZ);

            // Cập nhật góc yaw và pitch
            let new_yaw = yaw + delta_yaw;
            let mut new_pitch = pitch + delta_pitch;

            // Giới hạn góc pitch để tránh lật camera
            let max_pitch = std::f32::consts::FRAC_PI_2;
            new_pitch = new_pitch.clamp(-max_pitch, max_pitch);

            // Cập nhật rotation của camera
            transform.rotation = Quat::from_euler(EulerRot::YXZ, new_yaw, new_pitch, roll);
        }
    }

    // Xử lý chuyển động bàn phím
    for mut transform in query.iter_mut() {
        let mut direction = Vec3::ZERO;
        if keyboard_input.pressed(KeyCode::KeyW) {
            direction -= Vec3::Z;
        }
        if keyboard_input.pressed(KeyCode::KeyS) {
            direction += Vec3::Z;
        }
        if keyboard_input.pressed(KeyCode::KeyA) {
            direction -= Vec3::X;
        }
        if keyboard_input.pressed(KeyCode::KeyD) {
            direction += Vec3::X;
        }
        if keyboard_input.pressed(KeyCode::ShiftLeft) {
            direction -= Vec3::Y;
        }
        if keyboard_input.pressed(KeyCode::Space) {
            direction += Vec3::Y;
        }

        if direction.length_squared() > 0.0 {
            direction = direction.normalize();
            let movement = transform.rotation * direction * speed * time.delta_secs();
            transform.translation += movement;
        }
    }
}

fn cursor_grab_system(
    mouse_button_input: Res<ButtonInput<MouseButton>>,
    mut query: Query<&mut Window, With<PrimaryWindow>>,
) {
    let mut window = query.single_mut();
    if mouse_button_input.pressed(MouseButton::Right) {
        window.cursor_options.visible = false;
        window.cursor_options.grab_mode = CursorGrabMode::Locked;
    } else {
        window.cursor_options.visible = true;
        window.cursor_options.grab_mode = CursorGrabMode::None;
    }
}
