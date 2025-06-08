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

use blocktopograph::server::{
    core::chunk::{
        chunk_tag::{ChunkTag, ChunkTagKey, ChunkTagType},
        reader::ChunkReaderManager,
    },
    leveldb::utils::{try_identify_key, KeyType},
};
use leveldb::{db::Database, iterator::Iterable};

#[test]
fn chunk_reader_test() -> Result<(), Box<dyn std::error::Error>> {
    let worlds_path = Path::new("./tests/assets/tests_worlds/worlds").read_dir()?;
    for world_path in worlds_path {
        let path = world_path?;
        println!("World path: {}", path.path().display());
        if path.file_name().as_encoded_bytes().ends_with(b".json") {
            continue;
        } else {
            let p_str = format!("{}/db", path.path().to_str().unwrap());
            let db_path = Path::new(p_str.as_str());

            if !db_path.exists() {
                println!("Database path does not exist: {}", db_path.display());
                continue;
            }

            let db = Database::open(db_path, &leveldb::options::Options::new())?;

            db.iter(&leveldb::options::ReadOptions::new())
                .try_for_each(read_db_key)?;
        }
    }
    Ok(())
}

fn read_db_key((k, v): (Vec<u8>, Vec<u8>)) -> Result<(), Box<dyn std::error::Error>> {
    match try_identify_key(k.as_slice()) {
        Ok(key_type) => {
            if key_type == KeyType::ChunkData {
                let tag = ChunkTag::read(k.as_slice())?;
                if !is_testing_chunk_tag(tag) {
                    return Ok(());
                }
                read_chunk_data(tag, v)?;
            }
            Ok(())
        }
        Err(e) => Err(Box::new(e)),
    }
}

fn is_testing_chunk_tag(key: ChunkTagKey) -> bool {
    match key.key_type {
        ChunkTagType::FinalizedState
        | ChunkTagType::ActorDigestVersion
        | ChunkTagType::Version
        | ChunkTagType::BlendingData
        | ChunkTagType::BlendingBiomeHeight
        | ChunkTagType::PendingTicks
        | ChunkTagType::Data3D
        | ChunkTagType::AABBVolumes
        | ChunkTagType::BlockEntity
        | ChunkTagType::RandomTicks => false,
        ChunkTagType::SubChunkPrefix => true,
        _ => false,
    }
}

fn read_chunk_data(key: ChunkTagKey, value: Vec<u8>) -> Result<(), Box<dyn std::error::Error>> {
    ChunkReaderManager::new().read_chunk(key, &value)?;
    Ok(())
}
