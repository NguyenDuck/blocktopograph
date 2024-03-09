package io.vn.nguyenduck.blocktopograph.nbt.tag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static io.vn.nguyenduck.blocktopograph.nbt.Type.COMPOUND;

public class CompoundTag implements Tag {
    private final String name;
    private final ArrayList<Tag> value;

    public CompoundTag(String name, ArrayList<Tag> value) {
        this.name = name != null ? name : "";
        this.value = value;
    }

    @NonNull
    @Override
    public Type getType() {
        return COMPOUND;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public ArrayList<Tag> getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        ArrayList<String> a = new ArrayList<>();
        for (Tag t : value) a.add(t.toString());
        boolean isGreaterThan100 = (String.valueOf(a).length() + (a.size() - 1) * 2) > 100;
        StringJoiner valueString = new StringJoiner(isGreaterThan100 ? ",\n" : ", ");
        a.forEach(valueString::add);
        return (name.isEmpty() ? "{" : ("\"" + name + "\": {")) +
                ((isGreaterThan100 ? "\n" : "") + valueString).replace("\n", "\n\t") +
                (isGreaterThan100 ? "\n}" : "}");
    }
}
