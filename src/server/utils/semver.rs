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
pub struct SemVer {
    pub major: u32,
    pub minor: u32,
    pub patch: u32,
}

impl Default for SemVer {
    fn default() -> Self {
        Self {
            major: 1,
            minor: 21,
            patch: Default::default(),
        }
    }
}

impl Into<u32> for SemVer {
    fn into(self) -> u32 {
        self.major << 24 | self.minor << 16 | self.patch << 8
    }
}

impl From<u32> for SemVer {
    fn from(value: u32) -> Self {
        Self {
            major: (value >> 24) & 0xff,
            minor: (value >> 16) & 0xff,
            patch: (value >> 8) & 0xff,
        }
    }
}
