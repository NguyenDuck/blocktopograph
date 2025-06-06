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
use std::path::Path;

use leveldb::{
    db::Database,
    iterator::Iterable,
    options::{Options, ReadOptions},
};

mod server;
use server::{
    core::chunk::{
        chunk_reader::ChunkReaderImpl,
        chunk_tag::{ChunkTag, ChunkTagType},
    },
    leveldb::utils::{try_identify_key, KeyType},
};
use std::collections::HashMap;

fn main() {
    let db_path = Path::new("./assets/test_world/db");
    if !db_path.exists() {
        println!("Database path does not exist: {}", db_path.display());
        return;
    }

    let mut keytype_counts: HashMap<KeyType, usize> = HashMap::new();

    let db = Database::open(db_path, &Options::new()).unwrap();

    db.iter(&ReadOptions::new())
        .for_each(|(k, v)| match try_identify_key(k.as_slice()) {
            Ok(key_type) => {
                *keytype_counts.entry(key_type.clone()).or_insert(0) += 1;

                if key_type == KeyType::ChunkData {
                    ChunkTag::read(k.as_slice())
                        .map(|tag| match tag.key_type {
                            ChunkTagType::FinalizedState
                            | ChunkTagType::ActorDigestVersion
                            | ChunkTagType::Version
                            | ChunkTagType::BlendingData
                            | ChunkTagType::BlendingBiomeHeight
                            | ChunkTagType::PendingTicks
                            | ChunkTagType::Data3D
                            | ChunkTagType::AABBVolumes
                            | ChunkTagType::BlockEntity
                            | ChunkTagType::RandomTicks => {}
                            ChunkTagType::SubChunkPrefix => {
                                let _ = ChunkReaderImpl::new().read_chunk(tag, &v);
                            }
                            _ => println!(
                                "Chunk Tag - X: {}, Z: {}, Dim: {:?}, Type: {:?}",
                                tag.x, tag.z, tag.dim, tag.key_type,
                            ),
                        })
                        .unwrap_or_else(|e| {
                            println!("Error reading chunk tag: {}", e);
                        });
                }
            }
            Err(_) => {
                println!("Key: {:?}, Raw Key: {:?}", String::from_utf8(k.clone()), k)
            }
        });

    println!("Key Type Counts:");
    for (key_type, count) in keytype_counts {
        println!("{:?}: {}", key_type, count);
    }

    println!("Database loaded successfully from {}", db_path.display());
}
