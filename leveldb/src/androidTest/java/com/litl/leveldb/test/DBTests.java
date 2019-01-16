package com.litl.leveldb.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import android.test.AndroidTestCase;

import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.litl.leveldb.WriteBatch;

public class DBTests extends AndroidTestCase {
    private File mPath;
    private DB mDb;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mPath = new File(getContext().getCacheDir(), "db-tests");
        mDb = new DB(mPath);
        mDb.open();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mDb.close();
        DB.destroy(mPath);
    }

    private byte[] bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void testBasics() {
        mDb.put(bytes("hello"), bytes("world"));
        mDb.put(bytes("bye"), bytes("moon"));

        byte[] val = mDb.get(bytes("hello"));
        assertNotNull(val);
        assertTrue(Arrays.equals(val, bytes("world")));

        val = mDb.get(bytes("bye"));
        assertNotNull(val);
        assertTrue(Arrays.equals(val, bytes("moon")));

        val = mDb.get(bytes("boo"));
        assertNull(val);

        final ByteBuffer bb = ByteBuffer.allocate(20);
        bb.put(bytes("boo hello world"));
        bb.flip();

        // Search for "hello"
        bb.position(4).limit(bb.position() + 5);
        val = mDb.get(bb);
        assertNotNull(val);
        assertTrue(Arrays.equals(val, bytes("world")));

        // Search for "boo"
        bb.position(0).limit(4);
        bb.flip();
        val = mDb.get(bb);
        assertNull(val);
    }

    public void testBatchAndIterator() {
        final String[] keys = { "foo1", "foo2", "foo3", "foo4", "foo5" };
        final String[] vals = { "bar1", "bar2", "bar3", "bar4", "bar5" };

        final WriteBatch batch = new WriteBatch();
        try {
            final ByteBuffer key = ByteBuffer.allocate(10);
            final ByteBuffer val = ByteBuffer.allocate(10);
            for (int i = 0; i < keys.length; i++) {
                key.clear();
                key.put(bytes(keys[i]));
                key.flip();

                val.clear();
                val.put(bytes(vals[i]));
                val.flip();

                batch.put(key, val);
            }

            mDb.write(batch);
        } finally {
            batch.close();
        }

        final Iterator iter = mDb.iterator();
        try {
            int i = 0;
            for (iter.seekToFirst(); iter.isValid(); iter.next()) {
                assertTrue(i < keys.length);

                final byte[] key = iter.getKey();
                final byte[] val = iter.getValue();

                assertTrue(Arrays.equals(bytes(keys[i]), key));
                assertTrue(Arrays.equals(bytes(vals[i]), val));

                i++;
            }
            assertTrue(i == keys.length);
        } finally {
            iter.close();
        }
    }

    public void testSnapshots() {
        mDb.put(bytes("hello"), bytes("one"));
        mDb.put(bytes("bye"), bytes("one"));
        mDb.put(bytes("hi"), bytes("one"));

        final DB.Snapshot snapshot = mDb.getSnapshot();
        final Iterator iter = mDb.iterator(snapshot);
        try {
            mDb.put(bytes("hello"), bytes("two"));
            mDb.delete(bytes("bye"));

            assertTrue(Arrays.equals(mDb.get(snapshot, bytes("hello")), bytes("one")));
            assertTrue(Arrays.equals(mDb.get(snapshot, bytes("bye")), bytes("one")));
            assertTrue(Arrays.equals(mDb.get(snapshot, bytes("hi")), bytes("one")));

            int i = 0;
            for (iter.seekToFirst(); iter.isValid(); iter.next()) {
                i++;
                assertTrue(Arrays.equals(iter.getValue(), bytes("one")));
            }
            assertEquals(3, i);
        } finally {
            iter.close();
            snapshot.close();
        }

        assertTrue(Arrays.equals(mDb.get(bytes("hello")), bytes("two")));
        assertTrue(Arrays.equals(mDb.get(bytes("hi")), bytes("one")));
        assertNull(mDb.get(bytes("bye")));
    }

    public void testSeek() {
        mDb.put(bytes("01"), bytes("foo"));
        mDb.put(bytes("02"), bytes("foo"));
        mDb.put(bytes("11"), bytes("foo"));
        mDb.put(bytes("12"), bytes("foo"));
        mDb.put(bytes("13"), bytes("foo"));
        mDb.put(bytes("21"), bytes("foo"));

        Iterator iter = mDb.iterator();
        try {
            iter.seek(bytes("1"));
            assertTrue(iter.isValid());
            assertTrue(Arrays.equals(bytes("11"), iter.getKey()));

            iter.seek(bytes("2"));
            assertTrue(iter.isValid());
            assertTrue(Arrays.equals(bytes("21"), iter.getKey()));

            iter.seek(bytes("3"));
            assertFalse(iter.isValid());
        } finally {
            iter.close();
        }

        iter = mDb.iterator();
        try {
            // Iterate over all values starting with "1"
            int i = 0;
            final byte[] searchByte = new byte[] { '1' };
            for (iter.seek(searchByte); iter.isValid(); iter.next()) {
                final byte[] key = iter.getKey();
                if (key[0] != searchByte[0]) {
                    break;
                }

                i++;
            }

            assertEquals(3, i);
        } finally {
            iter.close();
        }
    }

    public void testWriteBatch() {
        final ByteBuffer managedBuf = ByteBuffer.allocate(10);
        final ByteBuffer directBuf = ByteBuffer.allocateDirect(10);

        final WriteBatch putBatch = new WriteBatch();
        try {
            directBuf.clear();
            directBuf.put(bytes("hello"));
            directBuf.flip();
            managedBuf.clear();
            managedBuf.put(bytes("world"));
            managedBuf.flip();
            putBatch.put(directBuf, managedBuf);

            managedBuf.clear();
            managedBuf.put(bytes("bye"));
            managedBuf.flip();
            directBuf.clear();
            directBuf.put(bytes("moon"));
            directBuf.flip();
            putBatch.put(managedBuf, directBuf);

            mDb.write(putBatch);
        } finally {
            putBatch.close();
        }

        final Iterator iter1 = mDb.iterator();
        try {
            iter1.seekToFirst();
            assertTrue(iter1.isValid());
            assertTrue(Arrays.equals(bytes("bye"), iter1.getKey()));
            assertTrue(Arrays.equals(bytes("moon"), iter1.getValue()));
            iter1.next();
            assertTrue(iter1.isValid());
            assertTrue(Arrays.equals(bytes("hello"), iter1.getKey()));
            assertTrue(Arrays.equals(bytes("world"), iter1.getValue()));
            iter1.next();
            assertFalse(iter1.isValid());
        } finally {
            iter1.close();
        }

        final WriteBatch deleteBatch = new WriteBatch();
        try {
            managedBuf.clear();
            managedBuf.put(bytes("hello"));
            managedBuf.flip();
            deleteBatch.delete(managedBuf);

            directBuf.clear();
            directBuf.put(bytes("bye"));
            directBuf.flip();
            deleteBatch.delete(directBuf);

            mDb.write(deleteBatch);
        } finally {
            deleteBatch.close();
        }

        final Iterator iter2 = mDb.iterator();
        try {
            iter2.seekToFirst();
            assertFalse(iter2.isValid());
        } finally {
            iter2.close();
        }
    }
}
