package com.litl.leveldb;

import android.annotation.NonNull;

import java.io.File;
import java.nio.ByteBuffer;

public class DB extends NativeObject {
    public abstract static class Snapshot extends NativeObject {
        Snapshot(long ptr) {
            super(ptr);
        }
    }

    private final String mPath;

    public DB(@NonNull String path) {
        mPath = path;
        open();
    }

    public void open() {
        mPtr = nativeOpen(mPath);
    }

    private void assertOpen() {
        assertOpen("Database is closed");
    }

    @Override
    protected void closeNativeObject(long ptr) {
        nativeClose(ptr);
    }

    public void put(@NonNull byte[] key, @NonNull byte[] value) {
        assertOpen();
        nativePut(mPtr, key, value);
    }

    public byte[] get(byte[] key) {
        return get(null, key);
    }

    public byte[] get(Snapshot snapshot, @NonNull byte[] key) {
        assertOpen();
        return nativeGet(mPtr, snapshot != null ? snapshot.getPtr() : 0, key);
    }

    public byte[] get(ByteBuffer key) {
        return get(null, key);
    }

    public byte[] get(Snapshot snapshot, @NonNull ByteBuffer key) {
        assertOpen();
        return nativeGet(mPtr, snapshot != null ? snapshot.getPtr() : 0, key);
    }

    public void delete(@NonNull byte[] key) {
        assertOpen();
        nativeDelete(mPtr, key);
    }

    public void write(@NonNull WriteBatch batch) {
        assertOpen();
        nativeWrite(mPtr, batch.getPtr());
    }

    public Iterator iterator() {
        return iterator(null);
    }

    public Iterator iterator(final Snapshot snapshot) {
        assertOpen();

        return new Iterator(nativeIterator(mPtr, snapshot != null ? snapshot.getPtr() : 0)) {
            @Override
            protected void closeNativeObject(long ptr) {
                super.closeNativeObject(ptr);
            }
        };
    }

    public Snapshot getSnapshot() {
        assertOpen();
        return new Snapshot(nativeGetSnapshot(mPtr)) {
            protected void closeNativeObject(long ptr) {
                nativeReleaseSnapshot(DB.this.getPtr(), getPtr());
            }
        };
    }

    public static native String fixLdb(String dbpath);

    public static void destroy(File path) {
        nativeDestroy(path.getAbsolutePath());
    }

    private static native long nativeOpen(String dbpath);

    private static native void nativeClose(long dbPtr);

    private static native void nativePut(long dbPtr, byte[] key, byte[] value);

    private static native byte[] nativeGet(long dbPtr, long snapshotPtr, byte[] key);

    private static native byte[] nativeGet(long dbPtr, long snapshotPtr, ByteBuffer key);

    private static native void nativeDelete(long dbPtr, byte[] key);

    private static native void nativeWrite(long dbPtr, long batchPtr);

    private static native void nativeDestroy(String dbpath);

    private static native long nativeIterator(long dbPtr, long snapshotPtr);

    private static native long nativeGetSnapshot(long dbPtr);

    private static native void nativeReleaseSnapshot(long dbPtr, long snapshotPtr);

    {
        System.loadLibrary("leveldbjni");
    }
}