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
use std::io::Read;

use crate::server::buffer::DataBufRead;

pub struct LEDataBufReader<R: Read> {
    reader: R,
}

impl<R: Read> LEDataBufReader<R> {
    pub fn new(reader: R) -> Self {
        Self { reader }
    }

    pub fn read<T: Sized + Default + Copy>(&mut self) -> T {
        let size = std::mem::size_of::<T>();
        let mut buf = vec![0u8; size];
        let _ = self.reader.read_exact(&mut buf);
        unsafe {
            let mut tmp: T = std::mem::zeroed();
            std::ptr::copy_nonoverlapping(buf.as_ptr(), &mut tmp as *mut T as *mut u8, size);
            tmp
        }
    }

    fn read_u8(&mut self) -> Result<u8, std::io::Error> {
        Ok(self.read::<u8>())
    }

    fn read_u16(&mut self) -> Result<u16, std::io::Error> {
        Ok(self.read::<u16>())
    }

    fn read_u32(&mut self) -> Result<u32, std::io::Error> {
        Ok(self.read::<u32>())
    }

    fn read_u64(&mut self) -> Result<u64, std::io::Error> {
        Ok(self.read::<u64>())
    }

    fn read_i8(&mut self) -> Result<i8, std::io::Error> {
        Ok(self.read::<i8>())
    }

    fn read_i16(&mut self) -> Result<i16, std::io::Error> {
        Ok(self.read::<i16>())
    }

    fn read_i32(&mut self) -> Result<i32, std::io::Error> {
        Ok(self.read::<i32>())
    }

    fn read_i64(&mut self) -> Result<i64, std::io::Error> {
        Ok(self.read::<i64>())
    }

    fn read_f32(&mut self) -> Result<f32, std::io::Error> {
        Ok(self.read::<f32>())
    }

    fn read_f64(&mut self) -> Result<f64, std::io::Error> {
        Ok(self.read::<f64>())
    }
}

impl<R: Read> Read for LEDataBufReader<R> {
    fn read(&mut self, buf: &mut [u8]) -> std::io::Result<usize> {
        self.reader.read(buf)
    }
}

impl<R: Read> DataBufRead for LEDataBufReader<R> {
    fn read_u8(&mut self) -> Result<u8, std::io::Error> {
        Ok(self.read::<u8>())
    }

    fn read_u16(&mut self) -> Result<u16, std::io::Error> {
        Ok(self.read::<u16>())
    }

    fn read_u32(&mut self) -> Result<u32, std::io::Error> {
        Ok(self.read::<u32>())
    }

    fn read_u64(&mut self) -> Result<u64, std::io::Error> {
        Ok(self.read::<u64>())
    }

    fn read_i8(&mut self) -> Result<i8, std::io::Error> {
        Ok(self.read::<i8>())
    }

    fn read_i16(&mut self) -> Result<i16, std::io::Error> {
        Ok(self.read::<i16>())
    }

    fn read_i32(&mut self) -> Result<i32, std::io::Error> {
        Ok(self.read::<i32>())
    }

    fn read_i64(&mut self) -> Result<i64, std::io::Error> {
        Ok(self.read::<i64>())
    }

    fn read_f32(&mut self) -> Result<f32, std::io::Error> {
        Ok(self.read::<f32>())
    }

    fn read_f64(&mut self) -> Result<f64, std::io::Error> {
        Ok(self.read::<f64>())
    }
}
