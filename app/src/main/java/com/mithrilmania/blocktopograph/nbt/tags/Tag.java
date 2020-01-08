package com.mithrilmania.blocktopograph.nbt.tags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class Tag<T> implements Serializable {

    private static final long serialVersionUID = 7925783664695648371L;

    protected String name;

    protected T value;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static boolean areCompoundTagsEqual(@NonNull List<Tag> tags1, @NonNull List<Tag> tags2) {
        if (tags1.size() != tags2.size()) return false;
        boolean[] notCompared = new boolean[tags2.size()];
        for (Tag tagof1 : tags1) {
            boolean found = false;
            for (int i = 0; i < tags2.size(); i++)
                if (!notCompared[i] && tagof1.equals(tags2.get(i))) {
                    found = true;
                    notCompared[i] = true;
                    break;
                }
            if (!found) return false;
        }
        return true;
    }

    public T getValue() {
        return value;
    }

    public abstract NBTConstants.NBTType getType();

    public abstract Tag<T> getDeepCopy();

    public void setValue(T value) {
        this.value = value;
    }

    public String toString() {
        String name = getName();
        String type = getType().name();
        T value = getValue();
        return (type == null ? "?" : ("TAG_" + type))
                + (name == null ? "(?)" : ("(" + name + ")"))
                + (value == null ? ":?" : (": " + value.toString()));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Tag)) return false;
        Tag another = (Tag) obj;
        if (!name.equals(another.name)) return false;
        if (getType() != another.getType()) return false;
        switch (getType()) {
            case COMPOUND:
                return areCompoundTagsEqual(((CompoundTag) this).value,
                        ((CompoundTag) another).value);
            case INT:
            case BYTE:
            case LONG:
            case FLOAT:
            case SHORT:
            case DOUBLE:
            case STRING:
            case LIST:
                return value.equals(another.value);
            case END:
                return true;
            case INT_ARRAY:
                return Arrays.equals(((IntArrayTag) this).value, ((IntArrayTag) another).value);
            case BYTE_ARRAY:
                return Arrays.equals(((ByteArrayTag) this).value, ((ByteArrayTag) another).value);
            case SHORT_ARRAY:
                return Arrays.equals(((ShortArrayTag) this).value, ((ShortArrayTag) another).value);
            default:
                throw new RuntimeException("wtf????");
        }
    }
}
