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
use crate::server::core::chunk::{
    biome::Biome, chunk_tag::ChunkTagKey, data3d::Data3D, reader::ChunkReaderTrait,
};
use byteorder::ReadBytesExt;
use byteorder::LE;
use bytes::Buf;
use bytes::Bytes;
use std::io::Cursor;

pub struct Data3DReader;

impl Data3DReader {
    fn read_height(
        &self,
        data3d: &mut Data3D,
        _: ChunkTagKey,
        reader: &mut Bytes,
    ) -> Result<(), std::io::Error> {
        for i in 0..256 {
            data3d.height[i] = reader.get_u16_le();
        }

        Ok(())
    }

    fn read_biome(
        &self,
        data3d: &mut Data3D,
        _: ChunkTagKey,
        reader: &mut Bytes,
    ) -> Result<(), std::io::Error> {
        let mut offset = 512;

        loop {
            let header = reader.get_i8();
            offset += 1;

            println!("Header: {:?}", header);

            if header == 0 {
                return Err(std::io::Error::new(
                    std::io::ErrorKind::InvalidData,
                    "Invalid biome header",
                ));
            }

            if header == -1 {
                data3d.biome.fill(Biome::default().numeric_id() as u8);
                continue;
            }

            let bits_per_block = header >> 1;
            let blocks_per_word = (32.0 / bits_per_block as f32).floor() as i32;
            let word_count = (4096.0 / blocks_per_word as f32).ceil() as i32;

            println!(
                "word_count: {}, word_count * 4: {}, palette_offset: {}",
                word_count,
                word_count * 4,
                offset + word_count * 4
            );

            if bits_per_block == 0 {
                let biome_id = reader.get_i32_le();
                let biome = Biome::from_id(biome_id)?;

                data3d.biome.fill(biome.numeric_id() as u8);
                break;
            }

            let palette_length = reader.get_i32_le();

            let mut palette = Vec::with_capacity(palette_length as usize);

            for _ in 0..palette_length {
                palette.push(reader.get_i32_le() as u8);
            }

            println!("Palette: {:?}", palette)

            // for cy in 0..16 {
            //     for cx in 0..16 {
            //         for cz in 0..16 {
            //             result[cx << 4 | cz] = palette[]
            //         }
            //     }
            // }
        }
        Ok(())
    }
}

impl ChunkReaderTrait<Data3D> for Data3DReader {
    fn read(&self, tag_key: ChunkTagKey, data: Vec<u8>) -> Result<Data3D, std::io::Error> {
        let mut data3d = Data3D::default();

        let mut reader = Bytes::from(data);

        self.read_height(&mut data3d, tag_key, &mut reader)?;
        self.read_biome(&mut data3d, tag_key, &mut reader)?;

        Ok(data3d)
    }
}
