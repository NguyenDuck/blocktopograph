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
pub(crate) mod windows;

use raw_window_handle::RawWindowHandle;

#[derive(Debug)]
pub enum PlatformError {
    InitFailed(String),
    UnsupporedOperation,
}

pub enum PlatformEvent {
    CloseRequested,
    Resized(u32, u32),
    Moved(i32, i32),
    KeyDown(u32),
    KeyUp(u32),
}

pub trait PlatformAPI {
    fn create_window(title: &str) -> Result<RawWindowHandle, PlatformError>;
    fn poll_events(&mut self) -> Vec<PlatformEvent>;
    fn get_time_ms(self) -> u64;

    fn sleep(&mut self, ms: u64) {
        std::thread::sleep(std::time::Duration::from_millis(ms));
    }
}
