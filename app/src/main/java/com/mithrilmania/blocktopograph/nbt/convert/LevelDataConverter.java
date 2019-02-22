package com.mithrilmania.blocktopograph.nbt.convert;

import com.mithrilmania.blocktopograph.nbt.tags.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LevelDataConverter {

    public static final byte[] header = {0x04, 0x00, 0x00, 0x00};

    public static CompoundTag read(InputStream inputStream) throws IOException {
        skip(inputStream, 8);
        // Skip the length? Yeah I know it's redundant but...
        NBTInputStream in = new NBTInputStream(inputStream);
        CompoundTag levelTag = (CompoundTag) in.readTag();
        in.close();
        return levelTag;
    }

    public static CompoundTag read(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream is = new BufferedInputStream(fis);
        skip(is, 8);
        NBTInputStream in = new NBTInputStream(is);
        CompoundTag levelTag = (CompoundTag) in.readTag();
        in.close();
        return levelTag;
    }

    public static void write(CompoundTag levelTag, File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        NBTOutputStream out = new NBTOutputStream(bos);
        out.writeTag(levelTag);
        out.close();
        FileOutputStream os = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        int length = bos.size();
        dos.write(header);
        dos.writeInt(Integer.reverseBytes(length));
        bos.writeTo(dos);
        dos.close();
    }

    /**
     * source: http://stackoverflow.com/questions/14057720/robust-skipping-of-data-in-a-java-io-inputstream-and-its-subtypes
     * <p/>
     * Skips n bytes.
     */
    public static void skip(InputStream is, long n) throws IOException {
        while (n > 0) {
            long n1 = is.skip(n);
            if (n1 > 0) {
                n -= n1;
            } else if (n1 == 0) { // should we retry? lets read one byte
                if (is.read() == -1)  // EOF
                    break;
                else
                    n--;
            } else // negative? this should never happen but...
                throw new IOException("skip() returned a negative value - this should never happen");
        }
    }

}
