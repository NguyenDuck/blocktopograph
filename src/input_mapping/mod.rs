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
use std::collections::{HashMap, HashSet};

use bevy::{
    ecs::system::Resource,
    input::keyboard::{Key, KeyCode},
};

type InputCallback = Box<dyn Fn() + Send + Sync + 'static>;

#[derive(Resource, Default)]
pub struct InputMapping {
    unordered_to_ordered: HashMap<HashSet<KeyCode>, Vec<(Vec<KeyCode>, InputCallback)>>,
}

impl InputMapping {
    fn add_mapping(&mut self, keys: Vec<KeyCode>, callback: InputCallback) {
        let unoredered_keys: HashSet<KeyCode> = keys.iter().cloned().collect();


        // self.unordered_to_ordered
        //     .entry(unoredered_keys)
        //     .or_insert_with(Vec::new)
        //     .push((keys, callback))
    }
}
