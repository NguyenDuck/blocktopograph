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

use std::{collections::BTreeMap, io::Error};

#[derive(Debug, Clone)]
pub enum NbtTag {
    End,
    Byte(i8),
    Short(i16),
    Int(i32),
    Long(i64),
    Float(f32),
    Double(f64),
    ByteArray(Vec<u8>),
    String(String),
    List(Vec<NbtTag>),
    Compound(BTreeMap<String, NbtTag>),
    IntArray(Vec<i32>),
    LongArray(Vec<i64>),
}

#[repr(u8)]
#[derive(Debug, Clone, Copy, PartialEq)]
pub enum TagId {
    End = 0,
    Byte = 1,
    Short = 2,
    Int = 3,
    Long = 4,
    Float = 5,
    Double = 6,
    ByteArray = 7,
    String = 8,
    List = 9,
    Compound = 10,
    IntArray = 11,
    LongArray = 12,
}

pub trait Id {
    fn id(self) -> u8;
}

impl Id for TagId {
    fn id(self) -> u8 {
        self as u8
    }
}

impl TagId {
    pub fn from_tag_id(value: usize) -> Option<Self> {
        match value {
            0 => Some(TagId::End),
            1 => Some(TagId::Byte),
            2 => Some(TagId::Short),
            3 => Some(TagId::Int),
            4 => Some(TagId::Long),
            5 => Some(TagId::Float),
            6 => Some(TagId::Double),
            7 => Some(TagId::ByteArray),
            8 => Some(TagId::String),
            9 => Some(TagId::List),
            10 => Some(TagId::Compound),
            11 => Some(TagId::IntArray),
            12 => Some(TagId::LongArray),
            _ => None,
        }
    }
}

impl NbtTag {
    fn tag_id(&self) -> TagId {
        match self {
            NbtTag::End => TagId::End,
            NbtTag::Byte(_) => TagId::Byte,
            NbtTag::Short(_) => TagId::Short,
            NbtTag::Int(_) => TagId::Int,
            NbtTag::Long(_) => TagId::Long,
            NbtTag::Float(_) => TagId::Float,
            NbtTag::Double(_) => TagId::Double,
            NbtTag::ByteArray(_) => TagId::ByteArray,
            NbtTag::String(_) => TagId::String,
            NbtTag::List(_) => TagId::List,
            NbtTag::Compound(_) => TagId::Compound,
            NbtTag::IntArray(_) => TagId::IntArray,
            NbtTag::LongArray(_) => TagId::LongArray,
        }
    }

    pub fn as_byte(&self) -> Result<i8, Error> {
        if let NbtTag::Byte(v) = self {
            Ok(*v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to ByteTag",
            ))
        }
    }

    pub fn as_short(&self) -> Result<i16, Error> {
        if let NbtTag::Short(v) = self {
            Ok(*v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to ShortTag",
            ))
        }
    }

    pub fn as_int(&self) -> Result<i32, Error> {
        if let NbtTag::Int(v) = self {
            Ok(*v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to IntTag",
            ))
        }
    }

    pub fn as_long(&self) -> Result<i64, Error> {
        if let NbtTag::Long(v) = self {
            Ok(*v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to LongTag",
            ))
        }
    }

    pub fn as_float(&self) -> Result<f32, Error> {
        if let NbtTag::Float(v) = self {
            Ok(*v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to FloatTag",
            ))
        }
    }

    pub fn as_double(&self) -> Result<f64, Error> {
        if let NbtTag::Double(v) = self {
            Ok(*v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to DoubleTag",
            ))
        }
    }

    pub fn as_byte_array(&self) -> Result<&Vec<u8>, Error> {
        if let NbtTag::ByteArray(v) = self {
            Ok(v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to ByteArrayTag",
            ))
        }
    }

    pub fn as_string(&self) -> Result<&String, Error> {
        if let NbtTag::String(v) = self {
            Ok(v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to StringTag",
            ))
        }
    }

    pub fn as_list(&self) -> Result<&Vec<NbtTag>, Error> {
        if let NbtTag::List(v) = self {
            Ok(v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to ListTag",
            ))
        }
    }

    pub fn as_compound(&self) -> Result<&BTreeMap<String, NbtTag>, Error> {
        if let NbtTag::Compound(v) = self {
            Ok(v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to CompoundTag",
            ))
        }
    }

    pub fn as_int_array(&self) -> Result<&Vec<i32>, Error> {
        if let NbtTag::IntArray(v) = self {
            Ok(v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to IntArrayTag",
            ))
        }
    }

    pub fn as_long_array(&self) -> Result<&Vec<i64>, Error> {
        if let NbtTag::LongArray(v) = self {
            Ok(v)
        } else {
            Err(Error::new(
                std::io::ErrorKind::InvalidData,
                "Invalid cast to LongArrayTag",
            ))
        }
    }
}
