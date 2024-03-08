package io.vn.nguyenduck.blocktopograph.nbt;

import io.vn.nguyenduck.blocktopograph.nbt.tag.*;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.END;

public class NbtInputStream implements Closeable {
    private final DataInputStream stream;
    private final boolean isLittleEndian;

    public NbtInputStream(InputStream is) {
        this(is, true);
    }

    public NbtInputStream(DataInputStream is) {
        stream = is;
        isLittleEndian = true;
    }

    private NbtInputStream(InputStream is, boolean isLittleEndian) {
        stream = new DataInputStream(is);
        this.isLittleEndian = isLittleEndian;
    }

    public ArrayList<Tag> readAllTags() throws IOException {
        ArrayList<Tag> tagsArray = new ArrayList<>();
        while (stream.available() >= 8) {
            tagsArray.add(readTag());
        }
        return tagsArray;
    }

    public Tag readTag() throws IOException {
        Type type = Type.fromInt(stream.read());
        String name = "";
        if (type != END) {
            byte[] nameByteArray = new byte[readShort()];
            stream.readFully(nameByteArray);
            name = new String(nameByteArray);
        }
        return readTagValue(type, name);
    }

    private Tag readTagValue(Type type, String name) throws IOException {
        switch (type) {
            case END:
                return new EndTag();
            case BYTE:
                return new ByteTag(name, stream.readByte());
            case SHORT:
                return new ShortTag(name, readShort());
            case INT:
                return new IntTag(name, readInt());
            case LONG:
                return new LongTag(name, readLong());
            case FLOAT:
                return new FloatTag(name, readFloat());
            case DOUBLE:
                return new DoubleTag(name, readDouble());
            case BYTE_ARRAY: {
                Byte[] data = new Byte[readInt()];
                for (int i = 0; i < data.length; i++) data[i] = stream.readByte();
                return new ByteArrayTag(name, data);
            }
            case STRING: {
                byte[] data = new byte[readShort()];
                stream.readFully(data);
                return new StringTag(name, new String(data));
            }
            case LIST: {
                Type childNbtType = Type.fromInt(stream.readByte());
                int length = readInt();
                ArrayList<Tag> data = new ArrayList<>();
                for (int i = 0; i < length; i++) data.add(readTagValue(childNbtType, null));
                return new ListTag(name, data, childNbtType);
            }
            case COMPOUND: {
                ArrayList<Tag> data = new ArrayList<>();
                while (true) {
                    Tag tag = readTag();
                    if (tag.getType() == END) break;
                    else data.add(tag);
                }
                return new CompoundTag(name, data);
            }
            case INT_ARRAY: {
                Integer[] data = new Integer[readInt()];
                for (int i = 0; i < data.length; i++) data[i] = readInt();
                return new IntArrayTag(name, data);
            }
            case SHORT_ARRAY: {
                Short[] data = new Short[readInt()];
                for (int i = 0; i < data.length; i++) data[i] = readShort();
                return new ShortArrayTag(name, data);
            }
        }
        return null;
    }

    private short readShort() throws IOException {
        return isLittleEndian ? Short.reverseBytes(stream.readShort()) : stream.readShort();
    }

    private int readInt() throws IOException {
        return isLittleEndian ? Integer.reverseBytes(stream.readInt()) : stream.readInt();
    }

    private long readLong() throws IOException {
        return isLittleEndian ? Long.reverseBytes(stream.readLong()) : stream.readLong();
    }

    private float readFloat() throws IOException {
        return isLittleEndian ? Float.intBitsToFloat(readInt()) : stream.readFloat();
    }

    private double readDouble() throws IOException {
        return isLittleEndian ? Double.longBitsToDouble(readLong()) : stream.readDouble();
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
