package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import java.util.Arrays;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.SHORT_ARRAY;

public class ShortArrayTag implements Tag {
    private final String name;
    private final Short[] value;

    public ShortArrayTag(String name, Short[] value) {
        this.name = name != null ? name : "";
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return SHORT_ARRAY;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Short[] getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return name.isEmpty() ? "" : ("\"" + name + "\": ") + Arrays.toString(value);
    }
}
