package io.vn.nguyenduck.blocktopograph;

import android.os.Bundle;
import io.vn.nguyenduck.blocktopograph.nbt.Type;
import io.vn.nguyenduck.blocktopograph.nbt.tag.*;

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
                    case BYTE: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        byte[] a2 = new byte[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((ByteTag) a.get(i)).getValue();
                        b.putByteArray(name, a2);
                    }
                    break;
                    case SHORT: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        short[] a2 = new short[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((ShortTag) a.get(i)).getValue();
                        b.putShortArray(name, a2);
                    }
                    break;
                    case INT: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        int[] a2 = new int[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((IntTag) a.get(i)).getValue();
                        b.putIntArray(name, a2);
                    }
                    break;
                    case LONG: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        long[] a2 = new long[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((LongTag) a.get(i)).getValue();
                        b.putLongArray(name, a2);
                    }
                    break;
                    case FLOAT: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        float[] a2 = new float[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((FloatTag) a.get(i)).getValue();
                        b.putFloatArray(name, a2);
                    }
                    break;
                    case DOUBLE: {
                        ArrayList<Tag> a = ((ListTag) tag).getValue();
                        double[] a2 = new double[a.size()];
                        for (int i = 0; i < a.size(); i++) a2[i] = ((DoubleTag) a.get(i)).getValue();
                        b.putDoubleArray(name, a2);
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
