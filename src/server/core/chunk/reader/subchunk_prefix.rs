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
    io::{Cursor, Error, ErrorKind, Read, Result},
    os::raw,
};

use bnbt::{nbt, NBTSerializer, NBTTag};
use byteorder::{ReadBytesExt, LE};
use bytes::{Buf, Bytes};

use crate::server::core::chunk::{
    chunk_tag::ChunkTagKey,
    reader::ChunkReaderTrait,
    sub_chunk::{SubChunk, SubChunkLayer},
};

pub struct SubChunkPrefixReader;

impl SubChunkPrefixReader {
    fn read_version(
        &self,
        sub_chunk: &mut SubChunk,
        _: ChunkTagKey,
        reader: &mut Bytes,
    ) -> Result<u8> {
        let version = reader.get_u8();
        sub_chunk.version = version;

        Ok(version)
    }

    fn read_layer(&self, tag_key: ChunkTagKey, reader: &mut Bytes) -> Result<()> {
        self.read_block_indices(tag_key, reader).unwrap();
        self.read_pallette(tag_key, reader).unwrap();
        Ok(())
    }

    fn read_block_indices(&self, _: ChunkTagKey, reader: &mut Bytes) -> Result<()> {
        let palette_type = reader.get_u8();

        let bits_per_block = palette_type >> 1;
        let blocks_per_word = 32 / bits_per_block;
        let word_count = (4096 + (blocks_per_word as i32 - 1)) / (blocks_per_word as i32);
        let mask = (1 << bits_per_block) - 1;

        let mut pos: usize = 0;
        let mut block_indices = [0u16; 4096];

        for _ in 0..word_count {
            let mut word = reader.get_u32_le();

            for _ in 0..blocks_per_word {
                if pos == 4096 {
                    break;
                }

                block_indices[pos] = (word & mask) as u16;
                word >>= bits_per_block;
                pos += 1;
            }
        }

        Ok(())
    }

    fn read_pallette(&self, _: ChunkTagKey, reader: &mut Bytes) -> Result<()> {
        let palette_count = reader.get_u32_le();
        let mut blocks = Vec::with_capacity(palette_count as usize);

        for _ in 0..palette_count {
            let value = NBTTag::from_bytes(reader);

            blocks.push(value);
        }

        Ok(())
    }
}

impl ChunkReaderTrait<SubChunk> for SubChunkPrefixReader {
    fn read(&self, tag_key: ChunkTagKey, data: Vec<u8>) -> Result<SubChunk> {
        if data.len() == 1 {
            return Ok(SubChunk::default());
        }

        let mut sub_chunk = SubChunk::default();

        let mut reader = Bytes::from(data);

        let version = self
            .read_version(&mut sub_chunk, tag_key, &mut reader)
            .unwrap();

        match version {
            1 => self.read_layer(tag_key, &mut reader).unwrap(),
            8 | 9 => {
                let layer_count = reader.get_u8();
                if sub_chunk.layers.len() != layer_count as usize {
                    sub_chunk
                        .layers
                        .resize(layer_count as usize, SubChunkLayer::default());
                }

                if version == 9 {
                    sub_chunk.index = reader.get_i8();
                }

                for _ in 0..layer_count {
                    self.read_layer(tag_key, &mut reader).unwrap();
                }
            }
            _ => {
                return Err(Error::new(
                    ErrorKind::InvalidData,
                    format!("Invalid subchunk version, found: {}", version),
                ))
            }
        }

        Ok(sub_chunk)
    }
}
