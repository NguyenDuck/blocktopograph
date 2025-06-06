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
use std::collections::BTreeMap;
use std::sync::{Arc, Mutex};

/// Represents a snapshot of the database at a point in time.
pub struct Snapshot {
    data: Arc<BTreeMap<Vec<u8>, Vec<u8>>>,
}

impl Snapshot {
    /// Create a new snapshot from the current database state.
    pub fn new(data: Arc<BTreeMap<Vec<u8>, Vec<u8>>>) -> Self {
        Snapshot { data }
    }

    /// Get a value for a given key from the snapshot.
    pub fn get(&self, key: &[u8]) -> Option<Vec<u8>> {
        self.data.get(key).cloned()
    }

    /// Iterate over all key-value pairs in the snapshot.
    pub fn iter(&self) -> impl Iterator<Item = (Vec<u8>, Vec<u8>)> + '_ {
        self.data.iter().map(|(k, v)| (k.clone(), v.clone()))
    }
}

/// Example of how a database might use snapshots.
pub struct Database {
    data: Arc<Mutex<BTreeMap<Vec<u8>, Vec<u8>>>>,
}

impl Database {
    pub fn new() -> Self {
        Database {
            data: Arc::new(Mutex::new(BTreeMap::new())),
        }
    }

    pub fn put(&self, key: Vec<u8>, value: Vec<u8>) {
        let mut db = self.data.lock().unwrap();
        db.insert(key, value);
    }

    pub fn get(&self, key: &[u8]) -> Option<Vec<u8>> {
        let db = self.data.lock().unwrap();
        db.get(key).cloned()
    }

    /// Create a snapshot of the current database state.
    pub fn snapshot(&self) -> Snapshot {
        let db = self.data.lock().unwrap();
        Snapshot::new(Arc::new(db.clone()))
    }
}
