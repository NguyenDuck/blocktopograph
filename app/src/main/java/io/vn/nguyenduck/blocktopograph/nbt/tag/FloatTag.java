package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.FLOAT;


public class FloatTag implements Tag {
    private final String name;
    private final Float value;

    public FloatTag(String name, Float value) {
        this.name = name != null ? name : "";
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return FLOAT;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Float getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return (name.isEmpty() ? "" : ("\"" + name + "\": ")) + value;
    }
}
