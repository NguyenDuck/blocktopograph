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
use std::io::{self, Error, ErrorKind};

use crate::server::{core::world::dimension::DimensionEnum, utils::data_reader::DataReader};

#[allow(dead_code)]
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum ChunkTagType {
    Invalid = 0xFF,

    Data3D = 0x2B,
    Version = 0x2C,
    Data2D = 0x2D,
    Data2DLegacy = 0x2E,
    SubChunkPrefix = 0x2F,
    LegacyTerrain = 0x30,
    BlockEntity = 0x31,
    Entity = 0x32,
    PendingTicks = 0x33,
    LegacyBlockExtraData = 0x34,
    BiomeState = 0x35,
    FinalizedState = 0x36,
    ConversionData = 0x37,
    HardcodedSpawners = 0x39,
    RandomTicks = 0x3A,
    Checksums = 0x3B,
    MetaDataHash = 0x3D,
    GeneratedPreCavesAndCliffsBlending = 0x3E,
    BlendingBiomeHeight = 0x3F,
    BlendingData = 0x40,
    ActorDigestVersion = 0x41,
    LegacyVersion = 0x76,
    AABBVolumes = 0x77,
}

impl From<u8> for ChunkTagType {
    fn from(tag_type: u8) -> Self {
        match tag_type {
            0x2B => ChunkTagType::Data3D,
            0x2C => ChunkTagType::Version,
            0x2D => ChunkTagType::Data2D,
            0x2E => ChunkTagType::Data2DLegacy,
            0x2F => ChunkTagType::SubChunkPrefix,
            0x30 => ChunkTagType::LegacyTerrain,
            0x31 => ChunkTagType::BlockEntity,
            0x32 => ChunkTagType::Entity,
            0x33 => ChunkTagType::PendingTicks,
            0x34 => ChunkTagType::LegacyBlockExtraData,
            0x35 => ChunkTagType::BiomeState,
            0x36 => ChunkTagType::FinalizedState,
            0x37 => ChunkTagType::ConversionData,
            0x39 => ChunkTagType::HardcodedSpawners,
            0x3A => ChunkTagType::RandomTicks,
            0x3B => ChunkTagType::Checksums,
            0x3D => ChunkTagType::MetaDataHash,
            0x3E => ChunkTagType::GeneratedPreCavesAndCliffsBlending,
            0x3F => ChunkTagType::BlendingBiomeHeight,
            0x40 => ChunkTagType::BlendingData,
            0x41 => ChunkTagType::ActorDigestVersion,
            0x76 => ChunkTagType::LegacyVersion,
            0x77 => ChunkTagType::AABBVolumes,
            _ => ChunkTagType::Invalid,
        }
    }
}

#[derive(Debug)]
pub struct ChunkTagKey {
    pub x: i32,
    pub z: i32,
    pub dim: DimensionEnum,
    pub key_type: ChunkTagType,
    pub index: i8,
}

pub struct ChunkTag;

impl ChunkTag {
    pub fn read(bytes: &[u8]) -> Result<ChunkTagKey, Error> {
        let mut reader = DataReader::new(io::Cursor::new(bytes));

        let x = reader.read::<i32>().unwrap();
        let z = reader.read::<i32>().unwrap();

        let dim = if bytes[8] != ChunkTagType::SubChunkPrefix as u8
            && (bytes.len() == 13 || bytes.len() == 14)
        {
            DimensionEnum::from_id(reader.read::<i32>().unwrap().into())
                .unwrap_or(DimensionEnum::Overworld)
        } else {
            DimensionEnum::Overworld
        };

        let key_type: ChunkTagType = reader.read::<u8>().unwrap().into();
        if key_type == ChunkTagType::Invalid {
            return Err(Error::new(
                ErrorKind::InvalidData,
                format!(
                    "Invalid chunk tag type, {:?}, String: {:?}",
                    bytes,
                    String::from_utf8(bytes.to_vec())
                ),
            ));
        }

        let index = reader.read::<i8>().unwrap_or(0);

        Ok(ChunkTagKey {
            x,
            z,
            dim,
            key_type,
            index,
        })
    }
}
