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
    io::{Cursor, Write},
    ops::Deref,
};

use byteorder::{WriteBytesExt, LE};

use crate::server::{
    core::chunk::{
        sub_chunk::{SubChunk, SubChunkLayer},
        writer::ChunkWriterTrait,
    },
    utils::chunk::bits_needed_to_store,
};

pub struct SubChunkPrefixWriter;

impl SubChunkPrefixWriter {
    fn write_version(
        &self,
        writer: &mut Cursor<Vec<u8>>,
        version: u8,
    ) -> Result<(), std::io::Error> {
        writer.write_u8(version)?;
        Ok(())
    }

    fn write_layer(
        &self,
        writer: &mut Cursor<Vec<u8>>,
        layer: &SubChunkLayer,
    ) -> Result<(), std::io::Error> {
        let bits_per_block = bits_needed_to_store(layer.blocks.len());
        let storage_size = bits_per_block << 1;

        writer.write_u8(storage_size as u8)?;

        let mut current_word = 0u32;
        let mut bits_written = 0;

        layer.block_indices.iter().try_for_each(|i| {
            if bits_written + bits_per_block > 32 {
                writer.write_u32::<LE>(current_word)?;
                current_word = 0;
                bits_written = 0;
            }

            current_word += (*i as u32) << bits_written;
            bits_written += bits_per_block;
            Ok::<(), std::io::Error>(())
        })?;

        if bits_written != 0 {
            writer.write_u32::<LE>(current_word)?;
        }

        writer.write_u32::<LE>(layer.blocks.len() as u32)?;

        for tag in layer.blocks.iter() {
            writer.write_all(tag.to_bytes().to_vec().as_slice());
        }

        Ok(())
    }
}

impl ChunkWriterTrait<SubChunk> for SubChunkPrefixWriter {
    fn write(&self, _: i32, _: i32, data: SubChunk) -> Result<Vec<u8>, std::io::Error> {
        let buffer = Vec::<u8>::new();

        let mut writer = Cursor::new(buffer);

        self.write_version(&mut writer, data.version)?;

        match data.version {
            8 | 9 => {
                writer.write_u8(data.layers.len() as u8)?;
                if data.version == 9 {
                    writer.write_i8(data.index)?;
                }

                for layer in data.layers.iter() {
                    self.write_layer(&mut writer, layer)?;
                }
            }
            _ => {
                return Err(std::io::Error::new(
                    std::io::ErrorKind::InvalidData,
                    "Invalid subchunk version",
                ));
            }
        }

        Ok(writer.get_ref().deref().to_vec())
    }
}
