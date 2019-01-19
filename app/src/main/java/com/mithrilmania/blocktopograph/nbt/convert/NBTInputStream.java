package com.mithrilmania.blocktopograph.nbt.convert;

import com.mithrilmania.blocktopograph.nbt.tags.*;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public final class NBTInputStream
        implements Closeable {
    private final DataInputStream is;
    private final boolean littleEndian;

    private int readCount;

    public NBTInputStream(InputStream is)
            throws IOException {
        this(is, false, true);
    }

    public NBTInputStream(InputStream is, boolean compressed)
            throws IOException {
        this(is, compressed, true);
    }

    public NBTInputStream(InputStream is, boolean compressed, boolean littleEndian)
            throws IOException {
        this.littleEndian = littleEndian;
        this.is = new DataInputStream(compressed ? new GZIPInputStream(is) : is);
        this.readCount = 0;
    }

    public ArrayList<Tag> readTopLevelTags() {
        ArrayList<Tag> list = new ArrayList<>();

        //type(1) + namelength(2) + minimumcontent(type(1)+namelength(2)+content(1)=4) = 8
        try {
            while (this.is.available() > 7) {
                list.add(this.readTag());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Tag readTag()
            throws IOException {
        return readTag(0);
    }

    private Tag readTag(int depth)
            throws IOException {
        int type = this.is.readByte() & 0xFF;
        readCount++;
        String name;
        if (type != NBTConstants.NBTType.END.id) {
            short shLen = this.is.readShort();
            readCount += 2;
            int nameLength = (this.littleEndian ? Short.reverseBytes(shLen) : shLen) & 0xFFFF;
            byte[] nameBytes = new byte[nameLength];
            this.is.readFully(nameBytes);
            readCount += nameLength;
            name = new String(nameBytes, NBTConstants.CHARSET.name());
        } else {
            name = "";
        }
        return readTagPayload(type, name, depth);
    }

    private Tag readTagPayload(int type, String name, int depth)
            throws IOException {

        NBTConstants.NBTType nbtType = NBTConstants.NBTType.typesByID.get(type);

        if (nbtType == null) throw new IOException("Invalid tag type: " + type + ".");

        int length;
        byte[] bytes;
        switch (nbtType) {
            case END:
                if (depth == 0) {
                    throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
                }
                return new EndTag();
            case BYTE:
                readCount++;
                return new ByteTag(name, this.is.readByte());
            case SHORT:
                readCount += 2;
                return new ShortTag(name, this.littleEndian ? Short.reverseBytes(this.is.readShort()) : this.is.readShort());
            case INT:
                readCount += 4;
                return new IntTag(name, this.littleEndian ? Integer.reverseBytes(this.is.readInt()) : this.is.readInt());
            case LONG:
                readCount += 8;
                return new LongTag(name, this.littleEndian ? Long.reverseBytes(this.is.readLong()) : this.is.readLong());
            case FLOAT:
                readCount += 4;
                return new FloatTag(name, this.littleEndian ? Float.intBitsToFloat(Integer.reverseBytes(this.is.readInt())) : this.is.readFloat());
            case DOUBLE:
                readCount += 8;
                return new DoubleTag(name, this.littleEndian ? Double.longBitsToDouble(Long.reverseBytes(this.is.readLong())) : this.is.readDouble());
            case BYTE_ARRAY: {
                length = this.littleEndian ? Integer.reverseBytes(this.is.readInt()) : this.is.readInt();
                readCount += 4;
                bytes = new byte[length];
                this.is.readFully(bytes);
                readCount += length;
                return new ByteArrayTag(name, bytes);
            }
            case STRING: {
                length = this.littleEndian ? Short.reverseBytes(this.is.readShort()) : this.is.readShort();
                readCount+=2;
                bytes = new byte[length];
                this.is.readFully(bytes);
                readCount += length;
                return new StringTag(name, new String(bytes, NBTConstants.CHARSET.name()));
            }
            case LIST: {
                int childType = this.is.readByte();
                readCount++;
                length = this.littleEndian ? Integer.reverseBytes(this.is.readInt()) : this.is.readInt();
                readCount += 4;

                NBTConstants.NBTType childNbtType = NBTConstants.NBTType.typesByID.get(childType);
                if (childNbtType.id == 0) return new ListTag(name, new ArrayList<Tag>());
                Class<? extends Tag> clazz = childNbtType.tagClazz;
                ArrayList<Tag> tagList = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    Tag tag = readTagPayload(childType, "", depth + 1);
                    if ((tag instanceof EndTag)) {
                        throw new IOException("TAG_End not permitted in a list.");
                    }
                    if (!clazz.isInstance(tag)) {
                        throw new IOException("Mixed tag types within a list.");
                    }
                    tagList.add(tag);
                }
                return new ListTag(name, tagList);
            }
            case COMPOUND: {
                ArrayList<Tag> tagMap = new ArrayList<>();
                for (; ; ) {
                    Tag tag = readTag(depth + 1);
                    if ((tag instanceof EndTag)) return new CompoundTag(name, tagMap);
                    else tagMap.add(tag);
                }
            }
            case INT_ARRAY: {
                length = this.littleEndian ? Integer.reverseBytes(this.is.readInt()) : this.is.readInt();
                readCount += 4;
                int[] ints = new int[length];
                readCount += length << 2;
                if (this.littleEndian) for (int i = 0; i < length; i++) {
                    ints[i] = Integer.reverseBytes(this.is.readInt());
                }
                else for (int i = 0; i < length; i++) {
                    ints[i] = this.is.readInt();
                }
                return new IntArrayTag(name, ints);
            }
            case SHORT_ARRAY: {
                length = this.littleEndian ? Integer.reverseBytes(this.is.readInt()) : this.is.readInt();
                readCount += 4;
                short[] shorts = new short[length];
                readCount += length << 1;
                if (this.littleEndian) for (int i = 0; i < length; i++) {
                    shorts[i] = Short.reverseBytes(this.is.readShort());
                }
                else for (int i = 0; i < length; i++) {
                    shorts[i] = this.is.readShort();
                }
                return new ShortArrayTag(name, shorts);
            }
            default: {
                throw new IOException("Unhandled NBT type!!! type: " + type);
            }
        }
    }

    public void close()
            throws IOException {
        this.is.close();
    }

    public int getReadCount() {
        return readCount;
    }

    public void resetReadCount() {
        readCount = 0;
    }

    public boolean isLittleEndian() {
        return this.littleEndian;
    }
}
