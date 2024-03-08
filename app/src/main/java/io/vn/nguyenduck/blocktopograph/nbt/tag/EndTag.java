package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.END;

public class EndTag implements Tag {
    @NonNull
    @Override
    public Type getType() {
        return END;
    }

    @NonNull
    @Override
    public String getName() {
        return "";
    }

    @NonNull
    @Override
    public Object getValue() {
        return "";
    }

    @NonNull
    @Override
    public String toString() {
        return "";
    }
}
