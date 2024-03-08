package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

public interface Tag {
    @NonNull
    String toString();

    @NonNull
    Type getType();

    @NonNull
    String getName();

    @NonNull
    Object getValue();
}
