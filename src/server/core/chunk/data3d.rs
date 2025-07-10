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
#[derive(Debug, Clone, PartialEq)]
pub struct Data3D {
    pub height: Box<[u16; 256]>,
    pub biome: Box<[u8; 256]>,
}

impl Default for Data3D {
    fn default() -> Self {
        Self {
            height: Box::new([0; 256]),
            biome: Box::new([0; 256]),
        }
    }
}
