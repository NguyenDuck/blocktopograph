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

use crate::server::core::chunk::sub_chunk::SubChunk;

use super::chunk_tag::{ChunkTagKey, ChunkTagType};

pub trait ChunkReader {
    fn read_chunk(&self, key: ChunkTagKey, data: &[u8]) -> Result<(), Error>;
}

pub struct ChunkReaderImpl {
    map: HashMap<ChunkTagType, Box<dyn ChunkReader>>,
}

impl ChunkReaderImpl {
    pub fn new() -> Self {
        let mut map: HashMap<ChunkTagType, Box<dyn ChunkReader>> = HashMap::new();
        map.insert(ChunkTagType::SubChunkPrefix, Box::new(SubChunkPrefixReader));

        ChunkReaderImpl { map }
    }

    pub fn read_chunk(&self, tag_key: ChunkTagKey, data: &[u8]) -> Result<(), Error> {
        if let Some(chunk_reader) = self.map.get(&tag_key.key_type) {
            let _ = chunk_reader.read_chunk(tag_key, data);
            Ok(())
        } else {
            Err(Error::new(
                std::io::ErrorKind::Unsupported,
                format!("No reader found for chunk type: {:?}", tag_key.key_type),
            ))
        }
    }
}

pub struct SubChunkPrefixReader;

impl ChunkReader for SubChunkPrefixReader {
    fn read_chunk(&self, tag_key: ChunkTagKey, data: &[u8]) -> Result<(), Error> {
        let index = tag_key.index;

        let mut subchunk = SubChunk::from(data)?;

        // println!("saygex {:?}", subchunk.layers[0].blocks[0].value);
        subchunk.get_block(0, 0, 0);

        let old_buffer = subchunk.raw_data.clone();

        let new_buffer = subchunk.save()?;

        let f = find_first_diff(old_buffer.clone().as_ref(), new_buffer.clone().as_ref());

        match f {
            Some((v, a, b)) => {
                println!(
                    "Found diff of x: {}, z: {}, dim: {:?}, index: {}",
                    tag_key.x, tag_key.z, tag_key.dim, index
                );
                println!(
                    "diff at {}, old: {}, new: {}, old length: {}, new length: {}",
                    v,
                    a,
                    b,
                    old_buffer.len(),
                    new_buffer.len()
                );

                std::fs::write(
                    format!(
                        "./debug/{}",
                        format!(
                            "subchunk_{}_{}_{:?}_{}.bin",
                            tag_key.x, tag_key.z, tag_key.dim, tag_key.index
                        )
                    ),
                    old_buffer.clone().as_slice(),
                )
                .expect("Failed to write debug subchunk file");

                std::fs::write(
                    format!(
                        "./debug/{}",
                        format!(
                            "subchunk_{}_{}_{:?}_{}.2.bin",
                            tag_key.x, tag_key.z, tag_key.dim, tag_key.index
                        )
                    ),
                    new_buffer.clone().as_slice(),
                )
                .expect("Failed to write debug subchunk file");
            }
            None => {}
        }

        Ok(())
    }
}

fn find_first_diff(vec1: &Vec<u8>, vec2: &Vec<u8>) -> Option<(usize, u8, u8)> {
    if vec1.len() != vec2.len() {
        return None;
    }

    for (index, (&byte1, &byte2)) in vec1.iter().zip(vec2.iter()).enumerate() {
        if byte1 != byte2 {
            return Some((index, byte1, byte2));
        }
    }

    None
}
