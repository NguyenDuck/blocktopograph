package com.litl.leveldb;

import java.nio.ByteBuffer;

public class WriteBatch extends NativeObject {
    public WriteBatch() {
        super(nativeCreate());
    }

    @Override
    protected void closeNativeObject(long ptr) {
        nativeDestroy(ptr);
    }

    public void delete(ByteBuffer key) {
        assertOpen("WriteBatch is closed");
        if (key == null) {
            throw new NullPointerException("key");
        }

        nativeDelete(mPtr, key);
    }

    public void put(ByteBuffer key, ByteBuffer value) {
        assertOpen("WriteBatch is closed");
        if (key == null) {
            throw new NullPointerException("key");
        }
        if (value == null) {
            throw new NullPointerException("value");
        }

        nativePut(mPtr, key, value);
    }

    public void clear() {
        assertOpen("WriteBatch is closed");
        nativeClear(mPtr);
    }

    private static native long nativeCreate();

    private static native void nativeDestroy(long ptr);

    private static native void nativeDelete(long ptr, ByteBuffer key);

    private static native void nativePut(long ptr, ByteBuffer key, ByteBuffer val);

    private static native void nativeClear(long ptr);
}
