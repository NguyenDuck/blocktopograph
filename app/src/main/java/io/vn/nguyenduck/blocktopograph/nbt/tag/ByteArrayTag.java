package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import java.util.Arrays;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.BYTE_ARRAY;

public class ByteArrayTag implements Tag {
    private final String name;
    private final Byte[] value;

    public ByteArrayTag(String name, Byte[] value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return BYTE_ARRAY;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Byte[] getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return name.isEmpty() ? "" : ("\"" + name + "\": ") + Arrays.toString(value);
    }
}
