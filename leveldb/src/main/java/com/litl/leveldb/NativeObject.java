package com.litl.leveldb;

import java.io.Closeable;

abstract class NativeObject implements Closeable {

    protected long mPtr;

    protected NativeObject() {
    }

    protected NativeObject(long ptr) {
        this();

        if (ptr == 0) {
            throw new OutOfMemoryError("Failed to allocate native object");
        }

        mPtr = ptr;
    }

    synchronized protected long getPtr() {
        return mPtr;
    }

    synchronized public boolean isClosed() {
        return getPtr() == 0;
    }

    protected void assertOpen(String message) {
        if (getPtr() == 0) {
            throw new IllegalStateException(message);
        }
    }

    protected abstract void closeNativeObject(long ptr);

    @Override
    public synchronized void close() {
        closeNativeObject(mPtr);
        mPtr = 0;
    }

    @Override
    protected void finalize() throws Throwable {
        if (mPtr != 0) closeNativeObject(mPtr);
        mPtr = 0;

        super.finalize();
    }
}
