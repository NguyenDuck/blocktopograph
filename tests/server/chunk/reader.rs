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
use std::{fs, io::Result, path::Path};

use blocktopograph::server::core::chunk::{
    chunk_tag::{try_identify_key, ChunkTag, ChunkTagKey, ChunkTagType, KeyType},
    reader::{subchunk_prefix::SubChunkPrefixReader, ChunkReaderTrait},
};
use mojang_leveldb::{CompressionType, Options, ReadOptions, DB};

#[test]
fn chunk_reader_test() {
    let a = run();

    println!("{:?}", a);
}

fn run() -> Result<()> {
    let worlds_path = match fs::read_dir(Path::new("./tests/assets/tests_worlds/worlds")) {
        Ok(path) => path,
        Err(e) => {
            println!("Error when copy test worlds to temp dir: {}", e);
            return Ok(());
        }
    };
    for world_path in worlds_path {
        let path = world_path.unwrap();
        if path.file_type().unwrap().is_dir() {
            println!("World path: {}", path.path().display());
            let p_str = format!("{}/db", path.path().to_str().unwrap());
            let db_path = Path::new(p_str.as_str());

            if !db_path.exists() {
                println!("Database path does not exist: {}", db_path.display());
                continue;
            }

            let db = match DB::open(
                db_path.to_str().to_owned().unwrap(),
                Options {
                    compression: CompressionType::None,
                    create_if_missing: false,
                },
            ) {
                Ok(db) => db,
                Err(e) => {
                    println!("Error opening database: {}", e);
                    continue;
                }
            };

            let mut iter = db.iter(ReadOptions {
                fill_cache: true,
                verify_checksums: false,
            });

            while let Some((key, value)) = iter.next() {
                // println!("Key: {:?}, Value Length: {}", key.get(), value.len());
                read_db_key((key.get().to_vec(), value.get().to_vec()))?;
            }
        }
    }
    Ok(())
}

fn read_db_key((k, v): (Vec<u8>, Vec<u8>)) -> Result<()> {
    // println!(
    //     "Key: {:?}, Value Length: {}",
    //     String::from_utf8_lossy(k.as_slice()),
    //     v.len()
    // );
    let key_type = try_identify_key(k.clone());
    if key_type.is_err() {
        println!("{:?}", key_type);
    }

    // if key_type == KeyType::ChunkData {
    //     let tag = ChunkTag::read(k).unwrap();
    //     if !is_testing_chunk_tag(tag) {
    //         return Ok(());
    //     }
    //     let result = read_chunk_data(tag, v.clone());

    //     if let Err(_) = result {
    //         write_suchunk_error(tag.x, tag.z, &v)?;
    //     }
    //     result?;
    // }
    Ok(())
}

fn write_suchunk_error(x: i32, z: i32, data: &[u8]) -> Result<()> {
    std::fs::write(format!("./debug/suchunk_{}_{}.bin", x, z), data)
}

fn is_testing_chunk_tag(key: ChunkTagKey) -> bool {
    match key.key_type {
        ChunkTagType::SubChunkPrefix => true,
        _ => false,
    }
}

fn read_chunk_data(key: ChunkTagKey, value: Vec<u8>) -> Result<()> {
    let reader = SubChunkPrefixReader {};
    reader.read(key, value)?;
    Ok(())
}
