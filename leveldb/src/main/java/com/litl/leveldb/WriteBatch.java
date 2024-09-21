package com.litl.leveldb;

import android.annotation.NonNull;

import java.nio.ByteBuffer;

public class WriteBatch extends NativeObject {
    public WriteBatch() {
        super(nativeCreate());
    }

    @Override
    protected void closeNativeObject(long ptr) {
        nativeDestroy(ptr);
    }

    private void assertOpen() {
        assertOpen("WriteBatch is closed");
    }

    public void delete(@NonNull ByteBuffer key) {
        assertOpen();
        nativeDelete(mPtr, key);
    }

    public void put(@NonNull ByteBuffer key, @NonNull ByteBuffer value) {
        assertOpen();
        nativePut(mPtr, key, value);
    }

    public void clear() {
        assertOpen();
        nativeClear(mPtr);
    }

    private static native long nativeCreate();

    private static native void nativeDestroy(long ptr);

    private static native void nativeDelete(long ptr, ByteBuffer key);

    private static native void nativePut(long ptr, ByteBuffer key, ByteBuffer val);

    private static native void nativeClear(long ptr);
}