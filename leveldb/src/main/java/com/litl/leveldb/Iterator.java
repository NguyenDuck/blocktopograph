package com.litl.leveldb;

public class Iterator extends NativeObject {
    Iterator(long iterPtr) {
        super(iterPtr);
    }

    @Override
    protected void closeNativeObject(long ptr) {
        nativeDestroy(ptr);
    }

    public void seekToFirst() {
        assertOpen("Iterator is closed");
        nativeSeekToFirst(mPtr);
    }

    public void seekToLast() {
        assertOpen("Iterator is closed");
        nativeSeekToLast(mPtr);
    }

    public void seek(byte[] target) {
        assertOpen("Iterator is closed");
        if (target == null) {
            throw new IllegalArgumentException();
        }
        nativeSeek(mPtr, target);
    }

    public boolean isValid() {
        assertOpen("Iterator is closed");
        return nativeValid(mPtr);
    }

    public void next() {
        assertOpen("Iterator is closed");
        nativeNext(mPtr);
    }

    public void prev() {
        assertOpen("Iterator is closed");
        nativePrev(mPtr);
    }

    public byte[] getKey() {
        assertOpen("Iterator is closed");
        return nativeKey(mPtr);
    }

    public byte[] getValue() {
        assertOpen("Iterator is closed");
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
