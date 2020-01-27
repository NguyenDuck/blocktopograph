package com.mithrilmania.blocktopograph;

import android.annotation.SuppressLint;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.mithrilmania.blocktopograph.block.BlockRegistry;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around level.dat world spec en levelDB database.
 */
public class WorldData {

    //another method for debugging, makes it easy to print a readable byte array
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public DB db;

    private WeakReference<World> world;
    private LruCache<Key, Chunk> chunks = new ChunkCache(this, 256);
    public final BlockRegistry mBlockRegistry;

    public WorldData(World world) {
        this.world = new WeakReference<>(world);
        this.mBlockRegistry = new BlockRegistry(2048);
    }

    static String bytesToHex(byte[] bytes, int start, int end) {
        char[] hexChars = new char[(end - start) * 2];
        for (int j = start; j < end; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[(j - start) * 2] = hexArray[v >>> 4];
            hexChars[(j - start) * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] getChunkDataKey(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk) {
        if (dimension == Dimension.OVERWORLD) {
            byte[] key = new byte[asSubChunk ? 10 : 9];
            System.arraycopy(getReversedBytes(x), 0, key, 0, 4);
            System.arraycopy(getReversedBytes(z), 0, key, 4, 4);
            key[8] = type.dataID;
            if (asSubChunk) key[9] = subChunk;
            return key;
        } else {
            byte[] key = new byte[asSubChunk ? 14 : 13];
            System.arraycopy(getReversedBytes(x), 0, key, 0, 4);
            System.arraycopy(getReversedBytes(z), 0, key, 4, 4);
            System.arraycopy(getReversedBytes(dimension.id), 0, key, 8, 4);
            key[12] = type.dataID;
            if (asSubChunk) key[13] = subChunk;
            return key;
        }
    }

    private static byte[] getReversedBytes(int i) {
        return new byte[]{
                (byte) i,
                (byte) (i >> 8),
                (byte) (i >> 16),
                (byte) (i >> 24)
        };
    }

    //load db when needed (does not load it!)
    @SuppressLint({"SetWorldReadable", "SetWorldWritable"})
    public void load() throws WorldDataLoadException {

        if (db != null) return;

        World world = this.world.get();

        File dbFile = new File(world.worldFolder, "db");
        if (!dbFile.canRead()) {
            if (!dbFile.setReadable(true, false))
                throw new WorldDataLoadException("World-db folder is not readable! World-db folder: " + dbFile.getAbsolutePath());
        }
        if (!dbFile.canWrite()) {
            if (!dbFile.setWritable(true, false))
                throw new WorldDataLoadException("World-db folder is not writable! World-db folder: " + dbFile.getAbsolutePath());
        }

        Log.d(this, "WorldFolder: " + world.worldFolder.getAbsolutePath());
        Log.d(this, "WorldFolder permissions: read: " + dbFile.canRead() + " write: " + dbFile.canWrite());

        if (dbFile.listFiles() == null)
            throw new WorldDataLoadException("Failed loading world-db: cannot list files in worldfolder");

        for (File dbEntry : dbFile.listFiles()) {
            Log.d(this, "File in db: " + dbEntry.getAbsolutePath());
        }
        this.db = new DB(dbFile);


    }

    //open db to make it available for this app
    public void openDB() throws WorldDBException {
        if (this.db == null)
            throw new WorldDBException("DB is null!!! (db is not loaded probably)");

        if (this.db.isClosed()) {
            try {
                this.db.open();
            } catch (Exception e) {

                throw new WorldDBException("DB could not be opened! " + e.getMessage());
            }
        }

    }

    //close db to make it available for other apps (Minecraft itself!)
    public void closeDB() throws WorldDBException {
        if (this.db == null)
            return;
        //Why bother throw an exception, isn't it good enough being able to skip closing as it's null?
        try {
            this.db.close();
        } catch (Exception e) {
            //db was already closed (probably)
            e.printStackTrace();
        }
    }

    public byte[] getChunkData(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk) throws WorldDBException, WorldDBLoadException {

        //ensure that the db is opened
        this.openDB();

        byte[] chunkKey = getChunkDataKey(x, z, type, dimension, subChunk, asSubChunk);
        //Log.d("Getting cX: "+x+" cZ: "+z+ " with key: "+bytesToHex(chunkKey, 0, chunkKey.length));
        return db.get(chunkKey);
    }

    public void writeChunkData(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk, byte[] chunkData) throws WorldDBException {
        //ensure that the db is opened
        this.openDB();

        db.put(getChunkDataKey(x, z, type, dimension, subChunk, asSubChunk), chunkData);
    }

    public void removeChunkData(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk) throws WorldDBException {
        //ensure that the db is opened
        this.openDB();

        db.delete(getChunkDataKey(x, z, type, dimension, subChunk, asSubChunk));
    }

    public Chunk getChunk(int cX, int cZ, Dimension dimension, boolean createIfMissing, Version createOfVersion) {
        Key key = new Key(cX, cZ, dimension);
        key.createIfMissng = createIfMissing;
        key.createOfVersion = createOfVersion;
        return chunks.get(key);
    }

    public Chunk getChunk(int cX, int cZ, Dimension dimension) {
        Key key = new Key(cX, cZ, dimension);
        return chunks.get(key);
    }

    // Avoid using cache for stream like operations.
    // Caller shall lock cache before operation and invalidate cache afterwards.
    public Chunk getChunkStreaming(int cx, int cz, Dimension dimension, boolean createIfMissing, Version createOfVersion) {
        return Chunk.create(this, cx, cz, dimension, createIfMissing, createOfVersion);
    }

    public void resetCache() {
        this.chunks.evictAll();
    }

    public String[] getNetworkPlayerNames() {
        List<String> players = getDBKeysStartingWith("player_");
        return players.toArray(new String[0]);
    }

    public List<String> getDBKeysStartingWith(String startWith) {
        Iterator it = db.iterator();

        ArrayList<String> items = new ArrayList<>();
        for (it.seekToFirst(); it.isValid(); it.next()) {
            byte[] key = it.getKey();
            if (key == null) continue;
            String keyStr = new String(key);
            if (keyStr.startsWith(startWith)) items.add(keyStr);
        }
        it.close();

        return items;
    }

    private static class ChunkCache extends LruCache<Key, Chunk> {

        private WeakReference<WorldData> worldData;

        ChunkCache(WorldData worldData, int maxSize) {
            super(maxSize);
            this.worldData = new WeakReference<>(worldData);
        }

        @Override
        protected void entryRemoved(boolean evicted, Key key, Chunk oldValue, Chunk newValue) {
            try {
                oldValue.save();
            } catch (Exception e) {
                Log.d(this, e);
            }
        }

        @Nullable
        @Override
        protected Chunk create(Key key) {
            WorldData worldData = this.worldData.get();
            if (worldData == null) return null;
            return Chunk.create(worldData, key.x, key.z, key.dim, key.createIfMissng, key.createOfVersion);
        }
    }

    static class Key {

        public int x, z;
        public Dimension dim;
        public boolean createIfMissng;
        public Version createOfVersion;

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

    public static class WorldDataLoadException extends Exception {
        private static final long serialVersionUID = 659185044124115547L;

        public WorldDataLoadException(String msg) {
            super(msg);
        }
    }

    public static class WorldDBException extends Exception {
        private static final long serialVersionUID = -3299282170140961220L;

        public WorldDBException(String msg) {
            super(msg);
        }
    }

    public static class WorldDBLoadException extends Exception {
        private static final long serialVersionUID = 4412238820886423076L;

        public WorldDBLoadException(String msg) {
            super(msg);
        }
    }

}
