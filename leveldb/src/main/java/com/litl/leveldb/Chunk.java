package com.litl.leveldb;

import java.io.File;
import java.nio.ByteBuffer;

public class Chunk extends NativeObject {

    public Chunk(DB db, int x, int z, int dim) {
        super();
        mPtr = nativeOpen(db.getPtr(), x, z, dim);
    }

    public int getBlock(int x, int y, int z) {
        return nativeGetBlock(mPtr, x, y, z);
    }

    public void close() {
        closeNativeObject(mPtr);
    }

    @Override
    protected void closeNativeObject(long ptr) {
        nativeClose(ptr);
    }

    private static native long nativeOpen(long dbptr, int x, int z, int dim);

    private static native int nativeGetBlock(long ckptr, int x, int y, int z);

    private static native void nativeClose(long ckPtr);

    {
        System.loadLibrary("leveldbjni");
    }
}
