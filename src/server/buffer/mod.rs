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
pub mod reader;
pub mod writer;

use std::io::{Read, Write};

pub trait DataBufRead: Read {
    fn read_u8(&mut self) -> Result<u8, std::io::Error>;
    fn read_u16(&mut self) -> Result<u16, std::io::Error>;
    fn read_u32(&mut self) -> Result<u32, std::io::Error>;
    fn read_u64(&mut self) -> Result<u64, std::io::Error>;

    fn read_i8(&mut self) -> Result<i8, std::io::Error>;
    fn read_i16(&mut self) -> Result<i16, std::io::Error>;
    fn read_i32(&mut self) -> Result<i32, std::io::Error>;
    fn read_i64(&mut self) -> Result<i64, std::io::Error>;

    fn read_f32(&mut self) -> Result<f32, std::io::Error>;
    fn read_f64(&mut self) -> Result<f64, std::io::Error>;
}

pub trait DataBufWrite: Write {
    fn write_u8(&mut self, value: u8) -> Result<(), std::io::Error>;
    fn write_u16(&mut self, value: u16) -> Result<(), std::io::Error>;
    fn write_u32(&mut self, value: u32) -> Result<(), std::io::Error>;
    fn write_u64(&mut self, value: u64) -> Result<(), std::io::Error>;

    fn write_i8(&mut self, value: i8) -> Result<(), std::io::Error>;
    fn write_i16(&mut self, value: i16) -> Result<(), std::io::Error>;
    fn write_i32(&mut self, value: i32) -> Result<(), std::io::Error>;
    fn write_i64(&mut self, value: i64) -> Result<(), std::io::Error>;

    fn write_f32(&mut self, value: f32) -> Result<(), std::io::Error>;
    fn write_f64(&mut self, value: f64) -> Result<(), std::io::Error>;
}
