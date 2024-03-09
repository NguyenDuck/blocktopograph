package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.LONG;

public class LongTag implements Tag {
    private final String name;
    private final Long value;

    public LongTag(String name, Long value) {
        this.name = name != null ? name : "";
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return LONG;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public Long getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return (name.isEmpty() ? "" : ("\"" + name + "\": ")) + value;
    }
}
