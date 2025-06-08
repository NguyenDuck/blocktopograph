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
use std::io::Error;

use crate::server::{
    core::chunk::{chunk_tag::ChunkTagKey, reader::ChunkReader, sub_chunk::SubChunk},
    utils::diff::find_first_diff,
};

pub struct SubChunkPrefixReader;

impl ChunkReader for SubChunkPrefixReader {
    fn read_chunk(&self, tag_key: ChunkTagKey, data: &[u8]) -> Result<(), Error> {
        let index = tag_key.index;

        let mut subchunk = SubChunk::from(data)?;

        let block = subchunk.get_block(0, 0, 0)?;
        println!("{:?}", block);

        let old_buffer = subchunk.raw_data.clone();

        let new_buffer = subchunk.save()?;

        let f = find_first_diff(old_buffer.clone(), new_buffer.clone());

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
