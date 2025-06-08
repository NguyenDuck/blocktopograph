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
    io::{Cursor, Error, Read, Seek, Write},
    ops::Deref,
};

use crate::server::{
    buffer::{reader::LEDataBufReader, writer::LEDataBufWriter, DataBufRead, DataBufWrite},
    core::block::Block,
    nbt::{reader::NbtReader, writer::NbtWriter, NbtTag, TagId},
    utils::{diff::find_first_diff, semver::SemVer},
};

#[derive(Debug, Clone)]
pub struct SubChunk {
    pub raw_data: Vec<u8>,
    pub layers: Vec<SubChunkLayer>,
    pub index: i8,
    pub version: u8,
}

#[derive(Debug, Clone)]
pub struct SubChunkLayer {
    pub block_indices: Box<[u16; 4096]>,
    pub blocks: Vec<(Option<String>, NbtTag)>,
}

impl SubChunk {
    pub fn from(data: impl Read) -> Result<Self, Error> {
        let byte: Vec<u8> = data.bytes().into_iter().map(|f| f.unwrap()).collect();

        let raw_data = byte.clone();

        let mut subchunk = SubChunk {
            raw_data,
            layers: Vec::new(),
            index: 0,
            version: 9,
        };

        subchunk.load(LEDataBufReader::new(byte.as_slice()))?;
        Ok(subchunk)
    }

    pub fn load<R: DataBufRead>(&mut self, data: R) -> Result<(), Error> {
        let mut reader = NbtReader::new(data);

        let version = reader.read_unsigned_byte()?;

        self.version = version;

        match version {
            1 => self.load_layer(&mut reader),
            8 | 9 => {
                let layer_count = reader.read_unsigned_byte()?;
                if version == 9 {
                    let index = reader.read_byte()?;

                    self.index = index;
                }

                for _ in 0..layer_count {
                    self.load_layer(&mut reader)?;
                }
                Ok(())
            }
            _ => Err(Error::new(
                std::io::ErrorKind::InvalidData,
                format!("Unsupported subchunk version {}", version),
            )),
        }
    }

    fn load_layer<R: DataBufRead>(&mut self, reader: &mut NbtReader<R>) -> Result<(), Error> {
        let palette_type = reader.read_unsigned_byte()?;

        let bits_per_block = palette_type >> 1;

        let blocks_per_word = 32 / bits_per_block;
        let word_count = (4096 + (blocks_per_word as i32) - 1) / (blocks_per_word as i32);
        let mask = (1 << bits_per_block) - 1;
        let mut pos: usize = 0;
        let mut block_indices = Box::new([0u16; 4096]);

        for _ in 0..word_count {
            let mut word = reader.read_unsigned_int()?;

            for _ in 0..blocks_per_word {
                if pos == 4096 {
                    break;
                }
                block_indices[pos] = (word & mask) as u16;
                word >>= bits_per_block;
                pos += 1;
            }
        }

        let palette_count = reader.read_unsigned_int()?;
        let mut blocks = Vec::with_capacity(palette_count as usize);

        for _ in 0..palette_count {
            blocks.push(reader.read_tag()?);
        }

        self.layers.push(SubChunkLayer {
            block_indices,
            blocks,
        });

        Ok(())
    }

    pub fn save(&mut self) -> Result<Vec<u8>, Error> {
        let cursor = Cursor::new(Vec::new());
        let mut writer = NbtWriter::new(LEDataBufWriter::new(cursor));
        writer.write_unsigned_byte(self.version)?;
        writer.write_unsigned_byte(self.layers.len() as u8)?;
        if self.version == 9 {
            writer.write_byte(self.index)?;
        }
        self.save_layers(&mut writer)?;

        let a = writer.as_ref().as_ref().get_ref();

        Ok(a.to_vec())
    }

    fn save_layers<R: DataBufWrite>(&mut self, writer: &mut NbtWriter<R>) -> Result<(), Error> {
        for layer in self.layers.iter() {
            self.save_layer(writer, layer)?;
        }
        Ok(())
    }

    fn save_layer<R: DataBufWrite>(
        &self,
        writer: &mut NbtWriter<R>,
        layer: &SubChunkLayer,
    ) -> Result<(), Error> {
        let bits_per_block = bits_needed_to_store(layer.blocks.len());
        let storage_size = bits_per_block << 1;

        writer.write_unsigned_byte(storage_size as u8)?;

        let mut current_word = 0u32;
        let mut bits_written = 0;

        layer.block_indices.iter().try_for_each(|i| {
            if bits_written + bits_per_block > 32 {
                writer.write_unsigned_int(current_word)?;
                current_word = 0;
                bits_written = 0;
            }

            current_word += (*i as u32) << bits_written;
            bits_written += bits_per_block;
            Ok::<(), Error>(())
        })?;

        if bits_written != 0 {
            writer.write_unsigned_int(current_word)?;
        }

        writer.write_unsigned_int(layer.blocks.len() as u32)?;
        layer.blocks.iter().try_for_each(|t| {
            let (name, tag) = t;
            writer.write_tag(tag.to_owned(), name.as_deref())?;
            Ok::<(), Error>(())
        })?;

        Ok(())
    }

    fn get_layer_block(&self, layer_index: usize, block_index: usize) -> Result<NbtTag, Error> {
        let layer = &self.layers[layer_index];

        let block_indices = &layer.block_indices;
        let blocks = &layer.blocks;

        let state_index = block_indices[block_index];

        let (_, block_nbt) = &blocks[state_index as usize];

        Ok(block_nbt.clone())
    }

    pub fn get_block(&self, x: i32, y: i32, z: i32) -> Result<Option<Block>, Error> {
        let index = x << 8 | z << 4 | y;

        if self.layers.len() > 2 {
            return Err(Error::new(
                std::io::ErrorKind::Unsupported,
                format!(
                    "SubChunk layer count is greater than 2, got: {}",
                    self.layers.len()
                ),
            ));
        }

        let block_nbt = self.get_layer_block(0, index as usize)?;
        let block_data = block_nbt.as_compound()?;

        let name = block_data.get("name").unwrap().as_string()?;
        let states = block_data.get("states").unwrap();
        let version = block_data.get("version").unwrap().as_int()? as u32;

        let block_water_logged = self.get_layer_block(1, index as usize)?;
        let block_data = block_water_logged.as_compound()?;
        let block_name = block_data.get("name").unwrap().as_string()?;

        let is_water_logged = block_name == "minecraft:water";

        Ok(Some(Block {
            name: name.clone(),
            states: states.clone(),
            version: version.into(),
            water_logged: is_water_logged,
        }))
    }
}

fn bits_needed_to_store(val: usize) -> usize {
    match val {
        0 => 1,
        v => (v.count_zeros() + v.count_ones() - v.leading_zeros()) as usize,
    }
}
