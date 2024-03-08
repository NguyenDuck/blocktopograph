package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.STRING;

public class StringTag implements Tag {
    private final String name;
    private final String value;

    public StringTag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return STRING;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return name.isEmpty() ? "\"" : ("\"" + name + "\": \"") + value + "\"";
    }
}
