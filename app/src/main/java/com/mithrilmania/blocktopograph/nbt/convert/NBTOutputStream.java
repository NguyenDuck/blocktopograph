package com.mithrilmania.blocktopograph.nbt.convert;

import com.mithrilmania.blocktopograph.nbt.tags.*;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class NBTOutputStream
        implements Closeable {
    private final DataOutputStream os;
    private final boolean littleEndian;

    public NBTOutputStream(OutputStream os)
            throws IOException {
        this(os, false, true);
    }

    public NBTOutputStream(OutputStream os, boolean compressed)
            throws IOException {
        this(os, compressed, true);
    }

    public NBTOutputStream(OutputStream os, boolean compressed, boolean littleEndian)
            throws IOException {
        this.littleEndian = littleEndian;
        this.os = new DataOutputStream(compressed ? new GZIPOutputStream(os) : os);
    }

    public void writeTag(Tag tag)
            throws IOException {

        int type = tag.getType().id;
        String name = tag.getName();
        byte[] nameBytes = name.getBytes(NBTConstants.CHARSET.name());

        this.os.writeByte(type);
        this.os.writeShort(this.littleEndian ? Short.reverseBytes((short) nameBytes.length) : nameBytes.length);
        this.os.write(nameBytes);
        if (type == 0) {
            throw new IOException("Named TAG_End not permitted.");
        }
        writeTagPayload(tag);
    }

    private void writeTagPayload(Tag tag)
            throws IOException {

        switch (tag.getType()) {
            case END:
                writeEndTagPayload();
                break;
            case BYTE:
                writeByteTagPayload((ByteTag) tag);
                break;
            case SHORT:
                writeShortTagPayload((ShortTag) tag);
                break;
            case INT:
                writeIntTagPayload((IntTag) tag);
                break;
            case LONG:
                writeLongTagPayload((LongTag) tag);
                break;
            case FLOAT:
                writeFloatTagPayload((FloatTag) tag);
                break;
            case DOUBLE:
                writeDoubleTagPayload((DoubleTag) tag);
                break;
            case BYTE_ARRAY:
                writeByteArrayTagPayload((ByteArrayTag) tag);
                break;
            case STRING:
                writeStringTagPayload((StringTag) tag);
                break;
            case LIST:
                writeListTagPayload((ListTag) tag);
                break;
            case COMPOUND:
                writeCompoundTagPayload((CompoundTag) tag);
                break;
            case INT_ARRAY:
                writeIntArrayTagPayload((IntArrayTag) tag);
                break;
            case SHORT_ARRAY:
                writeShortArrayTagPayload((ShortArrayTag) tag);
                break;
            default:
                throw new IOException("Invalid tag type: " + tag.getType() + ".");
        }
    }

    private void writeByteTagPayload(ByteTag tag)
            throws IOException {
        this.os.writeByte(tag.getValue());
    }

    private void writeByteArrayTagPayload(ByteArrayTag tag)
            throws IOException {
        byte[] bytes = tag.getValue();
        this.os.writeInt(this.littleEndian ? Integer.reverseBytes(bytes.length) : bytes.length);
        this.os.write(bytes);
    }

    private void writeIntArrayTagPayload(IntArrayTag tag)
            throws IOException {
        int[] ints = tag.getValue();
        this.os.writeInt(this.littleEndian ? Integer.reverseBytes(ints.length) : ints.length);

        if(littleEndian) for(int v : ints){
            this.os.writeInt(Integer.reverseBytes(v));
        } else for(int v : ints){
            this.os.writeInt(v);
        }
    }

    private void writeShortArrayTagPayload(ShortArrayTag tag)
            throws IOException {
        short[] shorts = tag.getValue();
        this.os.writeInt(this.littleEndian ? Integer.reverseBytes(shorts.length) : shorts.length);

        if(littleEndian) for(short v : shorts){
            this.os.writeShort(Short.reverseBytes(v));
        } else for(short v : shorts){
            this.os.writeShort(v);
        }
    }

    private void writeCompoundTagPayload(CompoundTag tag)
            throws IOException {
        List<Tag> tags = tag.getValue();
        for (Tag childTag : tags) {
            writeTag(childTag);
        }
        this.os.writeByte(NBTConstants.NBTType.END.id);
    }

    private void writeListTagPayload(ListTag tag)
            throws IOException {
        List<Tag> tags = tag.getValue();
        int size = tags.size();
        Tag first = size > 0 ? tags.get(0) : null;
        //empty lists have END(0) as listType
        byte listType = (byte) (first == null ? NBTConstants.NBTType.END.id : first.getType().id);
        this.os.writeByte(listType);
        //length of the list
        this.os.writeInt(this.littleEndian ? Integer.reverseBytes(size) : size);
        for (int i = 0; i < size; i++) {
            //write only the payload, the tags have no name and no type (listType is the type of all tags in the list)
            writeTagPayload(tags.get(i));
        }
    }

    private void writeStringTagPayload(StringTag tag)
            throws IOException {
        byte[] bytes = tag.getValue().getBytes(NBTConstants.CHARSET.name());
        this.os.writeShort(this.littleEndian ? Short.reverseBytes((short) bytes.length) : bytes.length);
        this.os.write(bytes);
    }

    private void writeDoubleTagPayload(DoubleTag tag)
            throws IOException {
        if (this.littleEndian) {
            this.os.writeLong(Long.reverseBytes(Double.doubleToLongBits(tag.getValue())));
        } else {
            this.os.writeDouble(tag.getValue());
        }
    }

    private void writeFloatTagPayload(FloatTag tag)
            throws IOException {
        if (this.littleEndian) {
            this.os.writeInt(Integer.reverseBytes(Float.floatToIntBits(tag.getValue())));
        } else {
            this.os.writeFloat(tag.getValue());
        }
    }

    private void writeLongTagPayload(LongTag tag)
            throws IOException {
        this.os.writeLong(this.littleEndian ? Long.reverseBytes(tag.getValue()) : tag.getValue());
    }

    private void writeIntTagPayload(IntTag tag)
            throws IOException {
        this.os.writeInt(this.littleEndian ? Integer.reverseBytes(tag.getValue()) : tag.getValue());
    }

    private void writeShortTagPayload(ShortTag tag)
            throws IOException {
        this.os.writeShort(this.littleEndian ? Short.reverseBytes(tag.getValue()) : tag.getValue());
    }


    private void writeEndTagPayload() throws IOException {
        //no payload, no name, just one byte: 0 (see compound tag)
    }

    public void close()
            throws IOException {
        this.os.close();
    }

    public boolean isLittleEndian() {
        return this.littleEndian;
    }
}
