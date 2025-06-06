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
use std::io::Write;

use crate::server::buffer::DataBufWrite;

pub struct LEDataBufWriter<W: Write> {
    writer: W,
}

impl<W: Write> LEDataBufWriter<W> {
    pub fn new(writer: W) -> Self {
        Self { writer }
    }

    pub fn as_ref(&self) -> &W {
        &self.writer
    }

    pub fn as_mut(&mut self) -> &mut W {
        &mut self.writer
    }

    pub fn write<T: Sized + Default + Copy>(&mut self, value: T) -> &mut Self {
        let size = std::mem::size_of::<T>();
        let bytes = unsafe { std::slice::from_raw_parts(&value as *const T as *const u8, size) };
        self.write_all(bytes);
        self.flush();
        self
    }
}

impl<R: Write> Write for LEDataBufWriter<R> {
    fn write(&mut self, buf: &[u8]) -> std::io::Result<usize> {
        self.writer.write(buf)
    }

    fn flush(&mut self) -> std::io::Result<()> {
        self.writer.flush()
    }
}

impl<R: Write> DataBufWrite for LEDataBufWriter<R> {
    fn write_u8(&mut self, value: u8) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_u16(&mut self, value: u16) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_u32(&mut self, value: u32) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_u64(&mut self, value: u64) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_i8(&mut self, value: i8) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_i16(&mut self, value: i16) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_i32(&mut self, value: i32) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_i64(&mut self, value: i64) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_f32(&mut self, value: f32) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }

    fn write_f64(&mut self, value: f64) -> Result<(), std::io::Error> {
        self.write(value);
        Ok(())
    }
}
