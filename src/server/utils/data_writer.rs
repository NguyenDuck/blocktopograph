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
use std::io::{Seek, Write};

pub struct DataWriter<W: Write + Seek> {
    writer: W,
}

impl<W: Write + Seek> DataWriter<W> {
    pub fn new(writer: W) -> Self {
        DataWriter { writer }
    }

    pub fn into_inner(self) -> W {
        self.writer
    }

    pub fn position(&mut self) -> std::io::Result<u64> {
        self.writer.seek(std::io::SeekFrom::Current(0))
    }

    pub fn set_position(&mut self, pos: u64) -> std::io::Result<()> {
        self.writer.seek(std::io::SeekFrom::Start(pos))?;
        Ok(())
    }

    pub fn capacity(&mut self) -> std::io::Result<u64> {
        let current_pos = self.position()?;
        self.writer.seek(std::io::SeekFrom::End(0))?;
        let end_pos = self.position()?;
        self.writer.seek(std::io::SeekFrom::Start(current_pos))?;
        Ok(end_pos - current_pos)
    }

    pub fn write<T: Sized + Default + Copy>(&mut self, value: T) -> &mut Self {
        let size = std::mem::size_of::<T>();

        let bytes = unsafe { std::slice::from_raw_parts(&value as *const T as *const u8, size) };
        self.writer.write_all(bytes);
        self
    }
}
