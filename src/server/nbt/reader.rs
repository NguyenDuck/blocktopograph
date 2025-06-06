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
    io::{Error, ErrorKind, Read},
};

use crate::server::{
    buffer::DataBufRead,
    nbt::{NbtTag, TagId},
};

pub struct NbtReader<R: Read> {
    reader: R,
}

impl<R: DataBufRead> NbtReader<R> {
    pub fn new(reader: R) -> Self {
        Self { reader }
    }

    fn read_name(&mut self) -> Result<String, Error> {
        let name_length = self.read_unsigned_short()? as usize;
        let mut name_bytes = vec![0u8; name_length];
        self.reader.read_exact(&mut name_bytes)?;
        String::from_utf8(name_bytes).map_err(|e| Error::new(ErrorKind::InvalidData, e.to_string()))
    }

    pub fn read_tag(&mut self) -> Result<(Option<String>, NbtTag), Error> {
        let tag_id = self.read_unsigned_byte()?;
        if tag_id == TagId::End as u8 {
            return Ok((None, NbtTag::End));
        }

        Ok((
            match self.read_name() {
                Ok(name) => Some(name),
                Err(_) => None,
            },
            match tag_id {
                1 => NbtTag::Byte(self.read_byte()?),
                2 => NbtTag::Short(self.read_short()?),
                3 => NbtTag::Int(self.read_int()?),
                4 => NbtTag::Long(self.read_long()?),
                5 => NbtTag::Float(self.read_float()?),
                6 => NbtTag::Double(self.read_double()?),
                7 => NbtTag::ByteArray(self.read_byte_array()?),
                8 => NbtTag::String(self.read_string()?),
                9 => NbtTag::List(self.read_list()?),
                10 => NbtTag::Compound(self.read_compound()?),
                11 => NbtTag::IntArray(self.read_int_array()?),
                12 => NbtTag::LongArray(self.read_long_array()?),
                _ => {
                    return Err(Error::new(
                        ErrorKind::InvalidData,
                        format!("Unknown tag id: {}", tag_id),
                    ));
                }
            },
        ))
    }

    pub fn read_nbt_tag(&mut self, tag_id: u8) -> Result<NbtTag, Error> {
        Ok(match tag_id {
            1 => NbtTag::Byte(self.read_byte()?),
            2 => NbtTag::Short(self.read_short()?),
            3 => NbtTag::Int(self.read_int()?),
            4 => NbtTag::Long(self.read_long()?),
            5 => NbtTag::Float(self.read_float()?),
            6 => NbtTag::Double(self.read_double()?),
            7 => NbtTag::ByteArray(self.read_byte_array()?),
            8 => NbtTag::String(self.read_string()?),
            9 => NbtTag::List(self.read_list()?),
            10 => NbtTag::Compound(self.read_compound()?),
            11 => NbtTag::IntArray(self.read_int_array()?),
            12 => NbtTag::LongArray(self.read_long_array()?),
            _ => {
                return Err(Error::new(
                    ErrorKind::InvalidData,
                    format!("Unknown tag type: {:?}", tag_id),
                ))
            }
        })
    }

    pub fn read_byte(&mut self) -> Result<i8, Error> {
        self.reader.read_i8()
    }

    pub fn read_unsigned_byte(&mut self) -> Result<u8, Error> {
        self.reader.read_u8()
    }

    pub fn read_short(&mut self) -> Result<i16, Error> {
        self.reader.read_i16()
    }

    pub fn read_unsigned_short(&mut self) -> Result<u16, Error> {
        self.reader.read_u16()
    }

    pub fn read_int(&mut self) -> Result<i32, Error> {
        self.reader.read_i32()
    }

    pub fn read_unsigned_int(&mut self) -> Result<u32, Error> {
        self.reader.read_u32()
    }

    pub fn read_long(&mut self) -> Result<i64, Error> {
        self.reader.read_i64()
    }

    pub fn read_unsigned_long(&mut self) -> Result<u64, Error> {
        self.reader.read_u64()
    }

    pub fn read_float(&mut self) -> Result<f32, Error> {
        self.reader.read_f32()
    }

    pub fn read_double(&mut self) -> Result<f64, Error> {
        self.reader.read_f64()
    }

    pub fn read_byte_array(&mut self) -> Result<Vec<u8>, Error> {
        let length = self.read_unsigned_int()? as usize;
        let mut buf = vec![0u8; length];
        let _ = self.reader.read_exact(&mut buf);
        Ok(buf)
    }

    pub fn read_string(&mut self) -> Result<String, Error> {
        let length = self.read_short()? as usize;
        if length == 0 {
            return Ok(String::new());
        }
        let mut buf = vec![0u8; length];
        let _ = self.reader.read_exact(&mut buf);

        String::from_utf8(buf)
            .map_err(|e| Error::new(ErrorKind::InvalidData, e.utf8_error().to_string()))
    }

    pub fn read_list(&mut self) -> Result<Vec<NbtTag>, Error> {
        let list_tag_id = self.read_unsigned_byte()?;
        let length = self.read_int()?;
        let mut list = Vec::with_capacity(length as usize);

        for _ in 0..length {
            list.push(self.read_nbt_tag(list_tag_id)?);
        }

        Ok(list)
    }

    pub fn read_compound(&mut self) -> Result<BTreeMap<String, NbtTag>, Error> {
        let mut compound = BTreeMap::new();

        loop {
            let tag_id = self.read_unsigned_byte()?;

            if tag_id == TagId::End as u8 {
                break;
            }

            compound.insert(self.read_name()?, self.read_nbt_tag(tag_id)?);
        }

        Ok(compound)
    }

    pub fn read_int_array(&mut self) -> Result<Vec<i32>, Error> {
        let length = self.read_unsigned_int()? as usize;
        let mut buf = vec![0i32; length];
        let _ = self.reader.read_exact(bytemuck::cast_slice_mut(&mut buf));
        Ok(buf)
    }

    pub fn read_long_array(&mut self) -> Result<Vec<i64>, Error> {
        let length = self.read_unsigned_int()? as usize;
        let mut buf = vec![0i64; length];
        let _ = self.reader.read_exact(bytemuck::cast_slice_mut(&mut buf));
        Ok(buf)
    }
}
