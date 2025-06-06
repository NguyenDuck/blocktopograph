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
use std::io::{Read, Result, Seek};

pub struct DataReader<R: Read + Seek> {
    reader: R,
}

impl<R: Read + Seek> DataReader<R> {
    pub fn new(reader: R) -> Self {
        DataReader { reader }
    }

    pub fn into_inner(self) -> R {
        self.reader
    }

    pub fn position(&mut self) -> Result<u64> {
        self.reader.seek(std::io::SeekFrom::Current(0))
    }

    pub fn set_position(&mut self, pos: u64) {
        let _ = self.reader.seek(std::io::SeekFrom::Start(pos));
    }

    pub fn capacity(&mut self) -> Result<u64> {
        let current_pos = self.position()?;
        self.reader.seek(std::io::SeekFrom::End(0))?;
        let end_pos = self.position()?;
        self.reader.seek(std::io::SeekFrom::Start(current_pos))?;
        Ok(end_pos - current_pos)
    }

    pub fn read<T: Sized + Default + Copy>(&mut self) -> Result<T> {
        let size = std::mem::size_of::<T>();
        let mut buf = vec![0u8; size];
        self.reader.read_exact(&mut buf)?;
        let t = unsafe {
            let mut tmp: T = std::mem::zeroed();
            std::ptr::copy_nonoverlapping(buf.as_ptr(), &mut tmp as *mut T as *mut u8, size);
            tmp
        };
        Ok(t)
    }
}
