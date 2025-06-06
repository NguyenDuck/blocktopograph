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
use std::{
    collections::BTreeMap,
    io::{Error, ErrorKind},
};

use crate::server::{
    buffer::DataBufWrite,
    nbt::{NbtTag, TagId},
};

pub struct NbtWriter<W: DataBufWrite> {
    writer: W,
}

impl<W: DataBufWrite> NbtWriter<W> {
    pub fn new(writer: W) -> Self {
        Self { writer }
    }

    pub fn as_ref(&self) -> &W {
        &self.writer
    }

    fn write_end(&mut self) -> Result<(), Error> {
        self.write_unsigned_byte(TagId::End as u8)
    }

    pub fn write_tag(&mut self, tag: NbtTag, name: Option<&str>) -> Result<(), Error> {
        self.write_unsigned_byte(tag.tag_id() as u8)?;

        if tag.tag_id() == TagId::End {
            return Ok(());
        }

        if let Some(n) = name {
            self.write_string(n.into())?;
        }

        self.write_nbt_tag(tag)?;

        Ok(())
    }

    pub fn write_nbt_tag(&mut self, tag: NbtTag) -> Result<(), Error> {
        match tag {
            NbtTag::Byte(v) => self.write_byte(v)?,
            NbtTag::Short(v) => self.write_short(v)?,
            NbtTag::Int(v) => self.write_int(v)?,
            NbtTag::Long(v) => self.write_long(v)?,
            NbtTag::Float(v) => self.write_float(v)?,
            NbtTag::Double(v) => self.write_double(v)?,
            NbtTag::ByteArray(v) => self.write_byte_array(v)?,
            NbtTag::String(v) => self.write_string(v)?,
            NbtTag::List(v) => self.write_list(v)?,
            NbtTag::Compound(v) => self.write_compound(v)?,
            NbtTag::IntArray(v) => self.write_int_array(v)?,
            NbtTag::LongArray(v) => self.write_long_array(v)?,
            _ => {
                return Err(Error::new(
                    ErrorKind::InvalidData,
                    format!("Unknown tag type: {:?}", tag.tag_id()),
                ))
            }
        }

        Ok(())
    }

    pub fn write_byte(&mut self, value: i8) -> Result<(), Error> {
        self.writer.write_i8(value)
    }

    pub fn write_unsigned_byte(&mut self, value: u8) -> Result<(), Error> {
        self.writer.write_u8(value)
    }

    pub fn write_short(&mut self, value: i16) -> Result<(), Error> {
        self.writer.write_i16(value)
    }

    pub fn write_unsigned_short(&mut self, value: u16) -> Result<(), Error> {
        self.writer.write_u16(value)
    }

    pub fn write_int(&mut self, value: i32) -> Result<(), Error> {
        self.writer.write_i32(value)
    }

    pub fn write_unsigned_int(&mut self, value: u32) -> Result<(), Error> {
        self.writer.write_u32(value)
    }

    pub fn write_long(&mut self, value: i64) -> Result<(), Error> {
        self.writer.write_i64(value)
    }

    pub fn write_unsigned_long(&mut self, value: u64) -> Result<(), Error> {
        self.writer.write_u64(value)
    }

    pub fn write_float(&mut self, value: f32) -> Result<(), Error> {
        self.writer.write_f32(value)
    }

    pub fn write_double(&mut self, value: f64) -> Result<(), Error> {
        self.writer.write_f64(value)
    }

    pub fn write_byte_array(&mut self, value: Vec<u8>) -> Result<(), Error> {
        self.write_unsigned_int(value.len() as u32)?;
        self.writer.write(value.as_slice())?;
        Ok(())
    }

    pub fn write_string(&mut self, value: String) -> Result<(), Error> {
        self.write_unsigned_short(value.len() as u16)?;
        self.writer.write(value.as_bytes())?;
        Ok(())
    }

    pub fn write_list(&mut self, value: Vec<NbtTag>) -> Result<(), Error> {
        let list_tag_id = value[0].tag_id() as u8;
        self.write_unsigned_byte(list_tag_id)?;
        self.write_unsigned_int(value.len() as u32)?;
        for tag in value {
            self.write_nbt_tag(tag)?;
        }
        self.write_end()?;
        Ok(())
    }

    pub fn write_compound(&mut self, value: BTreeMap<String, NbtTag>) -> Result<(), Error> {
        for (key, tag) in value {
            self.write_tag(tag, Some(&key))?;
        }
        self.write_end()?;
        Ok(())
    }

    pub fn write_int_array(&mut self, value: Vec<i32>) -> Result<(), Error> {
        self.write_unsigned_int(value.len() as u32)?;
        for &item in value.iter() {
            self.write_int(item)?;
        }

        Ok(())
    }

    pub fn write_long_array(&mut self, value: Vec<i64>) -> Result<(), Error> {
        self.write_unsigned_int(value.len() as u32)?;
        for &item in value.iter() {
            self.write_long(item)?;
        }

        Ok(())
    }
}
