package io.vn.nguyenduck.blocktopograph.nbt.tag;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.INT;

import androidx.annotation.NonNull;

import io.vn.nguyenduck.blocktopograph.nbt.Type;

public class IntTag implements Tag {
    private final String name;
    private final Integer value;

    public IntTag(String name, Integer value) {
        this.name = name != null ? name : "";
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return INT;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Integer getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return (name.isEmpty() ? "" : ("\"" + name + "\": ")) + value;
    }
}
