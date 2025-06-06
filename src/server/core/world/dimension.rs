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
use std::fmt::Debug;

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum DimensionEnum {
    Overworld,
    Nether,
    TheEnd,
}

impl DimensionEnum {
    pub fn from(name: &str) -> Option<Self> {
        match name.to_lowercase().as_str() {
            "overworld" => Some(DimensionEnum::Overworld),
            "nether" => Some(DimensionEnum::Nether),
            "the_end" => Some(DimensionEnum::TheEnd),
            _ => None,
        }
    }

    pub fn from_id(id: i32) -> Option<Self> {
        match id {
            0 => Some(DimensionEnum::Overworld),
            1 => Some(DimensionEnum::Nether),
            2 => Some(DimensionEnum::TheEnd),
            _ => None,
        }
    }
}
