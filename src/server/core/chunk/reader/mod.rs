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
use std::{collections::HashMap, io::Error};

use crate::server::core::chunk::reader::{
    data3d::Data3DReader, subchunk_prefix::SubChunkPrefixReader,
};

use super::chunk_tag::{ChunkTagKey, ChunkTagType};

pub mod data3d;
pub mod subchunk_prefix;

pub trait ChunkReader {
    fn read_chunk(&self, key: ChunkTagKey, data: &[u8]) -> Result<(), Error>;
}

pub struct ChunkReaderManager {
    map: HashMap<ChunkTagType, Box<dyn ChunkReader>>,
}

impl ChunkReaderManager {
    pub fn new() -> Self {
        let mut map: HashMap<ChunkTagType, Box<dyn ChunkReader>> = HashMap::new();
        map.insert(ChunkTagType::SubChunkPrefix, Box::new(SubChunkPrefixReader));
        map.insert(ChunkTagType::Data3D, Box::new(Data3DReader));

        Self { map }
    }

    pub fn read_chunk(
        &self,
        tag_key: ChunkTagKey,
        data: &[u8],
    ) -> Result<(), impl std::error::Error> {
        if let Some(chunk_reader) = self.map.get(&tag_key.key_type) {
            chunk_reader.read_chunk(tag_key, data)
        } else {
            Err(Error::new(
                std::io::ErrorKind::Unsupported,
                format!("No reader found for chunk type: {:?}", tag_key.key_type),
            ))
        }
    }
}
