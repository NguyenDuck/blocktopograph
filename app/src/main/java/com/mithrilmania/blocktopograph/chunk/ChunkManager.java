package com.mithrilmania.blocktopograph.chunk;

import android.util.LruCache;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.lang.ref.WeakReference;


public class ChunkManager {

    private LruCache<Key, Chunk> chunks = new LruCache<Key, Chunk>(256) {
        @Override
        protected Chunk create(Key key) {
            return Chunk.create(worldData.get(), key.x, key.z, key.dim);
        }
    };

    private WeakReference<WorldData> worldData;

    public ChunkManager(WorldData worldData) {
        this.worldData = new WeakReference<>(worldData);
    }

    public Chunk getChunk(int cX, int cZ, Dimension dimension) {
        Key key = new Key(cX, cZ, dimension);
        return chunks.get(key);
    }

    public void disposeAll() {
        this.chunks.evictAll();
    }

    static class Key {

        public int x, z;
        Dimension dim;

        Key(int x, int z, Dimension dim) {
            this.x = x;
            this.z = z;
            this.dim = dim;
        }

        @Override
        public int hashCode() {
            return (x * 31 + z) * 31 + dim.id;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) return false;
            Key another = (Key) obj;
            return ((x == another.x) && (z == another.z) && (dim != null)
                    && (another.dim != null) && (dim.id == another.dim.id));
        }
    }

}
