package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.DOUBLE;

public class DoubleTag implements Tag {
    private final String name;
    private final Double value;

    public DoubleTag(String name, Double value) {
        this.name = name != null ? name : "";
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return DOUBLE;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Double getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return (name.isEmpty() ? "" : ("\"" + name + "\": ")) + value;
    }
}
