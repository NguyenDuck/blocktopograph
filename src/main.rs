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
use bevy::{
    asset::{Assets, RenderAssetUsages},
    color::palettes::css::{GREEN, WHITE},
    math::Vec3,
    pbr::{DirectionalLightShadowMap, StandardMaterial},
    prelude::*,
    render::mesh::{Indices, PrimitiveTopology},
    utils::default,
    DefaultPlugins,
};
use bevy_atmosphere::{
    model::AtmosphereModel,
    plugin::{AtmosphereCamera, AtmospherePlugin},
    prelude::Nishita,
};
use bevy_rapier3d::prelude::*;
use blocktopograph::{
    camera::camera_control_system,
    input_mapping::{input_system, InputMapping, InputMappingPlugin},
    l18n::{L18n, L18nPlugin},
};
use std::collections::HashMap;

fn main() {
    App::new()
        .insert_resource(ClearColor(Color::srgb(0.0, 0.0, 0.0)))
        .add_plugins((
            DefaultPlugins,
            AtmospherePlugin,
            InputMappingPlugin,
            L18nPlugin,
        ))
        .insert_resource(AmbientLight {
            brightness: 250.,
            ..default()
        })
        .insert_resource(DirectionalLightShadowMap { size: 4096 })
        .add_systems(PostStartup, setup)
        .add_systems(PreUpdate, (camera_control_system, input_system))
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
    mut windows: Query<&mut Window>,
    mut input_mapping: ResMut<InputMapping>,
    localization: Res<L18n>,
) {
    let mut window = windows.single_mut();
    window.title = localization.translate(vec!["application", "title"]);

    commands.insert_resource(AtmosphereModel::new(Nishita { ..default() }));
    commands.spawn((Camera3d::default(), AtmosphereCamera::default()));

    commands.spawn((
        Mesh3d(meshes.add(create_cube())),
        MeshMaterial3d(materials.add(StandardMaterial {
            base_color: GREEN.into(),
            ..default()
        })),
        Transform::from_xyz(0., 1., 0.),
    ));

    commands.spawn((
        Mesh3d(meshes.add(Plane3d::new(Vec3::Y, Vec2::ONE * 1000.))),
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

    let key_bindings: HashMap<&str, Vec<KeyCode>> = HashMap::from([
        ("move_forward", vec![KeyCode::KeyW]),
        ("move_backward", vec![KeyCode::KeyS]),
        ("move_left", vec![KeyCode::KeyA]),
        ("move_right", vec![KeyCode::KeyD]),
        ("jump", vec![KeyCode::Space]),
        ("crouch", vec![KeyCode::ShiftLeft]),
        ("sprint", vec![KeyCode::ControlLeft]),
    ]);

    for (action, keys) in key_bindings {
        input_mapping.add_custom_binding(keys, action.to_string());
    }
}
