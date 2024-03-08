package io.vn.nguyenduck.blocktopograph;

import android.os.Bundle;
import io.vn.nguyenduck.blocktopograph.nbt.Type;
import io.vn.nguyenduck.blocktopograph.nbt.tag.CompoundTag;
import io.vn.nguyenduck.blocktopograph.nbt.tag.IntTag;
import io.vn.nguyenduck.blocktopograph.nbt.tag.ListTag;
import io.vn.nguyenduck.blocktopograph.nbt.tag.Tag;

import java.util.ArrayList;

public class NbtUtils {

    public static Bundle toBundle(Tag tag) {
        return toBundle(tag, new Bundle());
    }

    public static Bundle toBundle(Tag tag, Bundle b) {
        Type type = tag.getType();
        String name = tag.getName();
        switch (type) {
            case COMPOUND: {
                if (name.isEmpty()) b = toBundle(((CompoundTag) tag).getValue());
                else b.putBundle(name, toBundle(((CompoundTag) tag).getValue()));
                break;
            }
            case LIST: {
                Type childType = ((ListTag) tag).getChildNbtType();
                switch (childType) {
                    case INT: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        int[] a2 = new int[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((IntTag) a.get(i)).getValue();
                        b.putIntArray(name, a2);
                    }
                    break;
                }
                break;
            }
            case BYTE:
                b.putBoolean(name, (Byte) tag.getValue() == 1);
                break;
            case SHORT:
                b.putShort(name, (Short) tag.getValue());
                break;
            case INT:
                b.putInt(name, (Integer) tag.getValue());
                break;
            case LONG:
                b.putLong(name, (Long) tag.getValue());
                break;
            case FLOAT:
                b.putFloat(name, (Float) tag.getValue());
                break;
            case DOUBLE:
                b.putDouble(name, (Double) tag.getValue());
                break;
            case BYTE_ARRAY:
                b.putByteArray(name, (byte[]) tag.getValue());
                break;
            case STRING:
                b.putString(name, (String) tag.getValue());
                break;
            case INT_ARRAY:
                b.putIntArray(name, (int[]) tag.getValue());
                break;
            case SHORT_ARRAY:
                b.putShortArray(name, (short[]) tag.getValue());
                break;
        }
        return b;
    }

    private static Bundle toBundle(ArrayList<Tag> tag) {
        Bundle b = new Bundle();
        tag.forEach(v -> toBundle(v, b));
        return b;
    }
}
