package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.SHORT;

public class ShortTag implements Tag {
    private final String name;
    private final Short value;

    public ShortTag(String name, Short value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return SHORT;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Short getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return name.isEmpty() ? "" : ("\"" + name + "\": ") + value;
    }
}
