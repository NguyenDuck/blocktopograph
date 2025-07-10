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
use std::io::{Error, ErrorKind, Result};

use bytes::{Buf, Bytes};

use crate::server::core::world::dimension::DimensionEnum;

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

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub struct ChunkTagKey {
    pub x: i32,
    pub z: i32,
    pub dim: DimensionEnum,
    pub key_type: ChunkTagType,
    pub index: i8,
}

pub struct ChunkTag;

impl ChunkTag {
    pub fn read(data: Vec<u8>) -> Result<ChunkTagKey> {
        let data_cloned_for_error = data.clone();

        let mut reader = Bytes::from(data);

        let x = reader.get_i32_le();
        let z = reader.get_i32_le();

        let dim = if reader.remaining() > 4 {
            let is_sub_chunk_tag = reader.get_u8() == ChunkTagType::SubChunkPrefix as u8;

            println!("is_sub_chunk_tag: {}", is_sub_chunk_tag);

            // if is_sub_chunk_tag {
            //     return DimensionEnum::from_id(reader.get_i32_le())
            //         .unwrap_or(DimensionEnum::Overworld);
            // } else {
            DimensionEnum::Overworld
            // }
        } else {
            DimensionEnum::Overworld
        };

        // let dim = if *reader.get(8).unwrap() != ChunkTagType::SubChunkPrefix as u8
        //     && (data.len() == 13 || data.len() == 14)
        // {
        //     DimensionEnum::from_id(reader.get_i32_le()).unwrap_or(DimensionEnum::Overworld)
        // } else {
        //     DimensionEnum::Overworld
        // };

        let key_type: ChunkTagType = reader.get_u8().into();
        if key_type == ChunkTagType::Invalid {
            return Err(Error::new(
                ErrorKind::InvalidData,
                format!(
                    "Invalid chunk tag type, {:?}, String: {:?}",
                    data_cloned_for_error,
                    String::from_utf8(data_cloned_for_error.to_vec()).unwrap()
                ),
            ));
        }

        let index = if reader.remaining() > 0 {
            reader.get_i8()
        } else {
            0
        };

        Ok(ChunkTagKey {
            x,
            z,
            dim,
            key_type,
            index,
        })
    }
}

#[derive(Hash, Eq, PartialEq, Debug, Clone)]
pub enum KeyType {
    Invalid,
    Digp,
    ActorPrefix,
    MobEvents,
    Scoreboard,
    LocalPlayer,
    ChunkData,
    AutonomousEntities,
    VillageData,
    BiomeData,
    LevelChunkMetaDataDictionary,
    Overworld,
    Nether,
    RealmsStoriesData,
    TheEnd,
    Portals,
    Autonomous,
}

pub fn try_identify_key(key: Vec<u8>) -> Result<KeyType> {
    let key_str = String::from_utf8_lossy(key.as_slice());

    let estimate_tag = {
        if key_str.starts_with("digp") {
            KeyType::Digp
        } else if [9, 10, 13, 14].contains(&key.len()) {
            KeyType::ChunkData
        } else {
            KeyType::Invalid
        }
    };

    if estimate_tag != KeyType::Invalid {
        Ok(estimate_tag)
    } else {
        Err(Error::new(
            ErrorKind::InvalidData,
            format!(
                "Cannot identify key, found: {:?}, String: {:?}",
                key,
                String::from_utf8_lossy(key.as_slice())
            ),
        ))
    }

    // if key.starts_with(b"digp") {
    //     Ok(KeyType::Digp)
    // } else if key.starts_with(b"actorprefix") {
    //     Ok(KeyType::ActorPrefix)
    // } else if key.starts_with(b"mobevents") {
    //     Ok(KeyType::MobEvents)
    // } else if key.starts_with(b"scoreboard") {
    //     Ok(KeyType::Scoreboard)
    // } else if key.starts_with(b"~local_player") {
    //     Ok(KeyType::LocalPlayer)
    // } else if key.starts_with(b"AutonomousEntities") {
    //     Ok(KeyType::AutonomousEntities)
    // } else if key.starts_with(b"VILLAGE") {
    //     Ok(KeyType::VillageData)
    // } else if key.starts_with(b"BiomeData") {
    //     Ok(KeyType::BiomeData)
    // } else if key.starts_with(b"LevelChunkMetaDataDictionary") {
    //     Ok(KeyType::LevelChunkMetaDataDictionary)
    // } else if key.starts_with(b"Overworld") {
    //     Ok(KeyType::Overworld)
    // } else if key.starts_with(b"Nether") {
    //     Ok(KeyType::Nether)
    // } else if key.starts_with(b"RealmsStoriesData") {
    //     Ok(KeyType::RealmsStoriesData)
    // } else if key.starts_with(b"TheEnd") {
    //     Ok(KeyType::TheEnd)
    // } else if key.starts_with(b"portals") {
    //     Ok(KeyType::Portals)
    // } else if key.starts_with(b"Autonomou") {
    //     Ok(KeyType::Autonomous)
    // } else if key.len() == 9 || key.len() == 10 || key.len() == 13 || key.len() == 14 {
    //     Ok(KeyType::ChunkData)
    // } else {

    // }
}
