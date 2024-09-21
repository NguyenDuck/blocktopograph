package com.litl.leveldb;

import android.annotation.NonNull;

public class Iterator extends NativeObject {
    Iterator(long iterPtr) {
        super(iterPtr);
    }

    @Override
    protected void closeNativeObject(long ptr) {
        nativeDestroy(ptr);
    }

    private void assertOpen() {
        assertOpen("Iterator is closed");
    }

    public void seekToFirst() {
        assertOpen();
        nativeSeekToFirst(mPtr);
    }

    public void seekToLast() {
        assertOpen();
        nativeSeekToLast(mPtr);
    }

    public void seek(@NonNull byte[] target) {
        assertOpen();
        nativeSeek(mPtr, target);
    }

    public boolean isValid() {
        assertOpen();
        return nativeValid(mPtr);
    }

    public void next() {
        assertOpen();
        nativeNext(mPtr);
    }

    public void prev() {
        assertOpen();
        nativePrev(mPtr);
    }

    public byte[] getKey() {
        assertOpen();
        return nativeKey(mPtr);
    }

    public byte[] getValue() {
        assertOpen();
        return nativeValue(mPtr);
    }

    private static native void nativeDestroy(long ptr);

    private static native void nativeSeekToFirst(long ptr);

    private static native void nativeSeekToLast(long ptr);

    private static native void nativeSeek(long ptr, byte[] key);

    private static native boolean nativeValid(long ptr);

    private static native void nativeNext(long ptr);

    private static native void nativePrev(long ptr);

    private static native byte[] nativeKey(long dbPtr);

    private static native byte[] nativeValue(long dbPtr);
}