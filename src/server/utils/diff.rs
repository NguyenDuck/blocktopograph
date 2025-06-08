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
pub fn find_first_diff<T: Sized + PartialEq + Copy>(
    vec1: Vec<T>,
    vec2: Vec<T>,
) -> Option<(usize, T, T)> {
    if vec1.len() != vec2.len() {
        return None;
    }

    for (index, (&byte1, &byte2)) in vec1.iter().zip(vec2.iter()).enumerate() {
        if byte1 != byte2 {
            return Some((index, byte1, byte2));
        }
    }

    None
}
