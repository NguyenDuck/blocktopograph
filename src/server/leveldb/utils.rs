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
    error::Error,
    fmt::{Debug, Display},
};

#[derive(Hash, Eq, PartialEq, Debug, Clone)]
pub enum KeyType {
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
}

#[derive(Debug)]
pub struct InvalidKeyError {
    pub key: Vec<u8>,
}

impl InvalidKeyError {
    pub fn new(key: &[u8]) -> Self {
        Self {
            key: key.to_owned(),
        }
    }
}

impl Display for InvalidKeyError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(
            f,
            "Invalid key, Raw: {:?}, String: {:?}",
            self.key,
            String::from_utf8_lossy(&self.key)
        )
    }
}

impl Error for InvalidKeyError {
    fn source(&self) -> Option<&(dyn Error + 'static)> {
        None
    }
}

pub fn try_identify_key(key: &[u8]) -> Result<KeyType, InvalidKeyError> {
    if key.starts_with(b"digp") {
        Ok(KeyType::Digp)
    } else if key.starts_with(b"actorprefix") {
        Ok(KeyType::ActorPrefix)
    } else if key.starts_with(b"mobevents") {
        Ok(KeyType::MobEvents)
    } else if key.starts_with(b"scoreboard") {
        Ok(KeyType::Scoreboard)
    } else if key.starts_with(b"~local_player") {
        Ok(KeyType::LocalPlayer)
    } else if key.starts_with(b"AutonomousEntities") {
        Ok(KeyType::AutonomousEntities)
    } else if key.starts_with(b"VILLAGE") {
        Ok(KeyType::VillageData)
    } else if key.starts_with(b"BiomeData") {
        Ok(KeyType::BiomeData)
    } else if key.starts_with(b"LevelChunkMetaDataDictionary") {
        Ok(KeyType::LevelChunkMetaDataDictionary)
    } else if key.starts_with(b"Overworld") {
        Ok(KeyType::Overworld)
    } else if key.starts_with(b"Nether") {
        Ok(KeyType::Nether)
    } else if key.starts_with(b"RealmsStoriesData") {
        Ok(KeyType::RealmsStoriesData)
    } else if key.starts_with(b"TheEnd") {
        Ok(KeyType::TheEnd)
    } else if key.starts_with(b"portals") {
        Ok(KeyType::Portals)
    } else if key.len() == 9 || key.len() == 10 || key.len() == 13 || key.len() == 14 {
        Ok(KeyType::ChunkData)
    } else {
        Err(InvalidKeyError::new(key))
    }
}
