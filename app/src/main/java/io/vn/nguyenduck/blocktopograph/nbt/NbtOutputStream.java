package io.vn.nguyenduck.blocktopograph.nbt;

import io.vn.nguyenduck.blocktopograph.nbt.tag.*;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class NbtOutputStream implements Closeable {
    private final DataOutputStream stream;
    private final boolean isLittleEndian;

    public NbtOutputStream(OutputStream os) {
        this(os, true);
    }

    public NbtOutputStream(DataOutputStream os) {
        stream = os;
        isLittleEndian = true;
    }

    private NbtOutputStream(OutputStream os, boolean isLittleEndian) {
        stream = new DataOutputStream(os);
        this.isLittleEndian = isLittleEndian;
    }

    public void writeTag(Tag tag) throws IOException {
        Type type = tag.getType();
        String name = tag.getName();
        byte[] nameByteArray = name.getBytes();

        stream.write(type.toByte());
        writeShort((short) nameByteArray.length);
        stream.write(nameByteArray);
        writeTagValue(tag);
    }

    private void writeTagValue(Tag tag) throws IOException {
        Type type = tag.getType();
        switch (type) {
            case END:
                stream.write(0);
                break;
            case BYTE:
                stream.write(((ByteTag) tag).getValue());
                break;
            case SHORT:
                writeShort(((ShortTag) tag).getValue());
                break;
            case INT:
                writeInt(((IntTag) tag).getValue());
                break;
            case LONG:
                writeLong(((LongTag) tag).getValue());
                break;
            case FLOAT:
                writeFloat(((FloatTag) tag).getValue());
                break;
            case DOUBLE:
                writeDouble(((DoubleTag) tag).getValue());
                break;
            case BYTE_ARRAY: {
                Byte[] data = ((ByteArrayTag) tag).getValue();
                writeInt(data.length);
                for (Byte d : data) stream.write(d);
                break;
            }
            case STRING: {
                String data = ((StringTag) tag).getValue();
                writeShort((short) data.length());
                stream.write(data.getBytes());
                break;
            }
            case LIST: {
                ArrayList<Tag> tags = ((ListTag) tag).getValue();
                stream.write(tags.isEmpty() ? 0 : ((ListTag) tag).getChildNbtType().toByte());
                writeInt(tags.size());
                for (Tag t : tags) writeTagValue(t);
                break;
            }
            case COMPOUND: {
                for (Tag t : ((CompoundTag) tag).getValue()) writeTag(t);
                stream.write(0);
                break;
            }
            case INT_ARRAY: {
                Integer[] data = ((IntArrayTag) tag).getValue();
                writeInt(data.length);
                for (Integer d : data) writeInt(d);
                break;
            }
            case SHORT_ARRAY: {
                Short[] data = ((ShortArrayTag) tag).getValue();
                writeInt(data.length);
                for (Short d : data) writeShort(d);
                break;
            }
        }
    }

    private void writeShort(short data) throws IOException {
        stream.writeShort(isLittleEndian ? Short.reverseBytes(data) : data);
    }

    private void writeInt(int data) throws IOException {
        stream.writeInt(isLittleEndian ? Integer.reverseBytes(data) : data);
    }

    private void writeLong(long data) throws IOException {
        stream.writeLong(isLittleEndian ? Long.reverseBytes(data) : data);
    }

    private void writeFloat(float data) throws IOException {
        if (isLittleEndian) {
            stream.writeInt(Integer.reverseBytes(Float.floatToIntBits(data)));
        } else {
            stream.writeFloat(data);
        }
    }

    private void writeDouble(double data) throws IOException {
        if (isLittleEndian) {
            stream.writeLong(Long.reverseBytes(Double.doubleToLongBits(data)));
        } else {
            stream.writeDouble(data);
        }
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
