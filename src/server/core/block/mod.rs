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
use bnbt::NBTTag;

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
use crate::server::utils::semver::SemVer;

#[derive(PartialEq, Clone)]
pub struct Block {
    pub name: String,
    pub states: NBTTag,
    pub version: Option<SemVer>,
    pub water_logged: bool,
}

impl std::fmt::Debug for Block {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        let mut debug = f.debug_struct("Block");

        debug
            .field("name", &self.name)
            .field("states", &self.states)
            .field("version", &self.version);

        if self.water_logged {
            debug.field("water_logged", &self.water_logged);
        }

        debug.finish()
    }
}
