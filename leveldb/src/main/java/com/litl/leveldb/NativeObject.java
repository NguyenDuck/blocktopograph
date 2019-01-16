package com.litl.leveldb;

import java.io.Closeable;

import android.text.TextUtils;
import android.util.Log;

abstract class NativeObject implements Closeable {
    private static final String TAG = NativeObject.class.getSimpleName();
    protected long mPtr;
    private int mRefCount = 0;

    protected NativeObject() {
        // The Java wrapper counts as one reference, will
        // be released when closed
        ref();
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

    synchronized public boolean isClosed(){
        return getPtr() == 0;
    }

    protected void assertOpen(String message) {
        if (getPtr() == 0) {
            throw new IllegalStateException(message);
        }
    }

    synchronized void ref() {
        mRefCount++;
    }

    synchronized void unref() {
        if (mRefCount <= 0) {
            throw new IllegalStateException("Reference count is already 0");
        }

        mRefCount--;

        if (mRefCount == 0) {
            closeNativeObject(mPtr);
            mPtr = 0;
        }
    }

    protected abstract void closeNativeObject(long ptr);

    @Override
    public synchronized void close() {
        if (mPtr != 0) {
            unref();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (mPtr != 0) {
            Class<?> clazz = getClass();
            String name = clazz.getSimpleName();
            while (TextUtils.isEmpty(name)) {
                clazz = clazz.getSuperclass();
                name = clazz.getSimpleName();
            }

            Log.w(TAG, "NativeObject " + name + " refcount: " + mRefCount
                    + " id: " + System.identityHashCode(this)
                    + " was finalized before native resource was closed, did you forget to call close()?");
        }

        super.finalize();
    }
}
