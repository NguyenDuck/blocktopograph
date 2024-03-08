package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import java.util.Arrays;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.INT_ARRAY;

public class IntArrayTag implements Tag {
    private final String name;
    private final Integer[] value;

    public IntArrayTag(String name, Integer[] value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return INT_ARRAY;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Integer[] getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return name.isEmpty() ? "" : ("\"" + name + "\": ") + Arrays.toString(value);
    }
}
