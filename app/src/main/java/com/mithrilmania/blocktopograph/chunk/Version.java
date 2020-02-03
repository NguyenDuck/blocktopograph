package com.mithrilmania.blocktopograph.chunk;


import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public enum Version {

    ERROR("ERROR", "failed to retrieve version number", -2, 16, 16),
    NULL("NULL", "no data", -1, 16, 16),
    OLD_LIMITED("v0.2.0", "classic mcpe, 16x16x16x16x18 world, level.dat; introduced in v0.2.0", 1, 128, 1),
    v0_9("v0.9.0", "infinite xz, zlib leveldb; introduced in v0.9.0", 2, 128, 1),
    V1_0("v1.0.0", "Stacked sub-chunks, 256 world-height, 16 high sub-chunks; introduced in alpha v1.0.0 (v0.17)", 3, 16, 16),
    V1_1("v1.1.0", "KnownBlockRepr-light is not stored anymore", 4, 16, 16),
    V1_2_PLUS("v1.2.0.13", "Global numeric id replaced with string id and per-chunk numeric id", 7, 16, 16);

    public static final int LATEST_SUPPORTED_VERSION = V1_2_PLUS.id;

    public final String displayName, description;
    public final int id, subChunkHeight, subChunks;


    Version(String displayName, String description, int id, int subChunkHeight, int subChunks) {
        this.displayName = displayName;
        this.description = description;
        this.id = id;
        this.subChunkHeight = subChunkHeight;
        this.subChunks = subChunks;
    }

    private static final SparseArray<Version> versionMap;

    static {
        versionMap = new SparseArray<>();
        for (Version b : Version.values()) {
            versionMap.put(b.id, b);
        }
    }

    @NonNull
    public static Version getVersion(@Nullable byte[] data) {
        //Log.d("Data version: "+ ConvertUtil.bytesToHexStr(data));

        //`data` is supposed to be one byte,
        // but it might grow to contain more data later on, or larger version ids.
        // Looking up the first byte is sufficient for now.
        if (data == null || data.length <= 0) {
            return NULL;
        } else {
            int versionNumber = data[0] & 0xff;

            //fallback version
            //You can't just do this...
            if (versionNumber > LATEST_SUPPORTED_VERSION) {
                versionNumber = LATEST_SUPPORTED_VERSION;
            }

            Version version = versionMap.get(versionNumber);
            //check if the returned version exists, fallback on ERROR otherwise.
            return version == null ? ERROR : version;
        }
    }

    @Nullable
    public NBTChunkData createEntityChunkData(Chunk chunk) throws VersionException {
        switch (this) {
            case ERROR:
            case NULL:
                return null;
            case OLD_LIMITED:
                throw new VersionException("Handling terrain chunk data is NOT supported for this version!", this);
            default:
                //use the latest version, like nothing will ever happen...
                return new NBTChunkData(chunk, ChunkTag.ENTITY);
        }
    }

    @Nullable
    public NBTChunkData createBlockEntityChunkData(Chunk chunk) throws VersionException {
        switch (this) {
            case ERROR:
            case NULL:
                return null;
            case OLD_LIMITED:
                throw new VersionException("Handling terrain chunk data is NOT supported for this version!", this);
            default:
                //use the latest version, like nothing will ever happen...
                return new NBTChunkData(chunk, ChunkTag.BLOCK_ENTITY);
        }
    }

    @NonNull

    @Override
    public String toString() {
        return "[MCPE version \"" + displayName + "\" (version-code: " + id + ")]";
    }

    public static class VersionException extends Exception {

        VersionException(String msg, @NonNull Version version) {
            super(msg + " " + version.toString());
        }
    }
}
