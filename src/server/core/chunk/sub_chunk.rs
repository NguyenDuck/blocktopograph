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
use crate::server::utils::semver::SemVer;
use bnbt::{nbt, NBTTag};

pub trait SubChunkTrait {}

#[derive(Debug, Clone, PartialEq, Default)]
pub struct SubChunk {
    pub layers: Vec<SubChunkLayer>,
    pub index: i8,
    pub version: u8,
}

#[derive(Debug, Clone, PartialEq)]
pub struct SubChunkLayer {
    pub block_indices: Box<[u16; 4096]>,
    pub blocks: Vec<NBTTag>,
}

impl Default for SubChunkLayer {
    fn default() -> Self {
        let version: i32 = SemVer::default().into();
        let air_block = nbt!("", {
            "name": "minecraft:air",
            "states": {},
            "version": version
        });
        // let hashmap = HashMap::new();
        // let mut air_block = NBTValue::Compound(hashmap);
        // hashmap.insert("name".to_owned(), "minecraft:air".into());
        // hashmap.insert("states", v);

        // air_block.insert("name", "minecraft:air").unwrap();
        // air_block
        //     .insert("states", NBTValue::Compound(HashMap::new()))
        //     .unwrap();

        // air_block.insert("version", version).unwrap();

        Self {
            block_indices: Box::new([0; 4096]),
            blocks: vec![air_block],
        }
    }
}

// impl SubChunk {
//     pub fn from(data: impl Read) -> Result<Self, Error> {
//         let byte: Vec<u8> = data.bytes().into_iter().map(|f| f.unwrap()).collect();

//         let raw_data = byte.clone();

//         let mut subchunk = SubChunk {
//             raw_data,
//             layers: Vec::new(),
//             index: 0,
//             version: 9,
//         };

//         subchunk.load(LEDataBufferReader::new(byte.as_slice()))?;
//         Ok(subchunk)
//     }

//     pub fn save(&mut self) -> Result<Vec<u8>, Error> {
//         let data_writer = LEDataBufferWriter::new(Vec::new());
//         let mut writer = NbtWriter::new(data_writer);

//         writer.write_unsigned_byte(self.version);
//         writer.write_unsigned_byte(self.layers.len() as u8);
//         if self.version == 9 {
//             writer.write_byte(self.index);
//         }
//         self.save_layers(&mut writer);

//         Ok(writer.into_inner().into_inner())
//     }

//     fn save_layers<W: Write>(&mut self, writer: &mut NbtWriter<W>) -> Result<(), Error> {
//         for layer in self.layers.iter() {
//             self.save_layer(writer, layer)?;
//         }
//         Ok(())
//     }

//     fn save_layer(
//         &self,
//         writer: &mut impl NbtWriterTrait,
//         layer: &SubChunkLayer,
//     ) -> Result<(), Error> {
//         let bits_per_block = bits_needed_to_store(layer.blocks.len());
//         let storage_size = bits_per_block << 1;

//         writer.write_unsigned_byte(storage_size as u8);

//         let mut current_word = 0u32;
//         let mut bits_written = 0;

//         layer.block_indices.iter().try_for_each(|i| {
//             if bits_written + bits_per_block > 32 {
//                 writer.write_unsigned_int(current_word);
//                 current_word = 0;
//                 bits_written = 0;
//             }

//             current_word += (*i as u32) << bits_written;
//             bits_written += bits_per_block;
//             Ok::<(), Error>(())
//         })?;

//         if bits_written != 0 {
//             writer.write_unsigned_int(current_word);
//         }

//         writer.write_unsigned_int(layer.blocks.len() as u32);
//         layer.blocks.iter().try_for_each(|t| {
//             let (name, tag) = t;
//             writer.write_tag(tag.to_owned(), name.as_deref());
//             Ok::<(), Error>(())
//         })?;

//         Ok(())
//     }

//     fn get_layer_block(&self, layer_index: usize, block_index: usize) -> Result<NbtTag, Error> {
//         let layer = &self.layers[layer_index];

//         let block_indices = &layer.block_indices;
//         let blocks = &layer.blocks;

//         let state_index = block_indices[block_index];

//         let (_, block_nbt) = &blocks[state_index as usize];

//         Ok(block_nbt.clone())
//     }

//     /// Get block in subchunk using local position
//     pub fn get_block(&self, x: i32, y: i32, z: i32) -> Result<Option<Block>, Error> {
//         // Position safe
//         let x = x & 15;
//         let z = z & 15;
//         let y = y & 15;

//         let index = x << 8 | z << 4 | y;

//         if self.layers.len() > 2 {
//             return Err(Error::new(
//                 std::io::ErrorKind::Unsupported,
//                 format!(
//                     "SubChunk layer count is greater than 2, got: {}",
//                     self.layers.len()
//                 ),
//             ));
//         }

//         let block_nbt = self.get_layer_block(0, index as usize)?;
//         let block_data = block_nbt.as_compound();

//         let name = block_data.get("name").unwrap().as_string();
//         let states = block_data.get("states").unwrap();
//         let version = block_data.get("version").unwrap().as_int()? as u32;

//         let is_water_logged = if self.layers.len() == 2 {
//             let block_water_logged = self.get_layer_block(1, index as usize)?;
//             let block_data = block_water_logged.as_compound()?;
//             let block_name = block_data.get("name").unwrap().as_string()?;

//             block_name == "minecraft:water"
//         } else {
//             false
//         };

//         Ok(Some(Block {
//             name: name.clone(),
//             states: states.clone(),
//             version: version.into(),
//             water_logged: is_water_logged,
//         }))
//     }
// }
