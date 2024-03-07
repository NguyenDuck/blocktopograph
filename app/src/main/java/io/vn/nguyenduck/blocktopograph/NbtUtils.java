package io.vn.nguyenduck.blocktopograph;

import android.os.Bundle;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static io.vn.nguyenduck.blocktopograph.Logger.LOGGER;

public class NbtUtils {

    public static String toString(Object[] obj) {
        return toString(obj, 0);
    }

    public static String toString(Object[] obj, Integer depth) {
        Type type = (Type) obj[0];
        String name = (String) obj[1];
        switch (type) {
            case COMPOUND: {
                String s = toString((ArrayList<Object[]>) obj[2], depth + 1);
                return (name.isEmpty() ? "{" : "\"" + name + "\": {") + (s.isEmpty() ? s : "\n" + s + "\n" + "\t".repeat(depth)) + "}";
            }
            case LIST: {
                StringJoiner s = new StringJoiner(", ");
                ((ArrayList<Object[]>) obj[2]).forEach(v -> s.add(v[2].toString()));
                return "\"" + name + "\": [" + s + "]";
            }
            case STRING:
                return "\"" + name + "\": \"" + obj[2].toString().replace("\n", "\\n") + "\"";
            case BYTE:
                return "\"" + name + "\": " + ((byte) obj[2] == 1);
            case INT:
            case SHORT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return "\"" + name + "\": " + obj[2];
            default:
                LOGGER.info(String.valueOf(type));
        }
        return "";
    }

    private static String toString(ArrayList<Object[]> obj, int depth) {
        StringJoiner s = new StringJoiner(",\n");
        obj.forEach(v -> s.add("\t".repeat(depth) + toString(v, depth)));
        return s.toString();
    }

    public static Bundle toBundle(Object[] obj) {
        return toBundle(obj, new Bundle());
    }

    public static Bundle toBundle(Object[] obj, Bundle b) {
        Type type = (Type) obj[0];
        String name = (String) obj[1];
        switch (type) {
            case COMPOUND:
                if (name.isEmpty()) b = toBundle((ArrayList<Object[]>) obj[2]);
                else b.putBundle(name, toBundle((ArrayList<Object[]>) obj[2]));
                break;
            case LIST: {
                Type childType = (Type) obj[3];
                switch (childType) {
                    case INT: {
                        ArrayList<Object[]> a = (ArrayList<Object[]>) obj[2];
                        int[] a2 = new int[a.size()];
                        for (int i = 0; i < a.size(); i++) {
                            a2[i] = (int) a.get(i)[2];
                        }
                        b.putIntArray(name, a2);
                    }
                    break;
                }
            }
            break;
            case STRING:
                b.putString(name, (String) obj[2]);
                break;
            case BYTE:
                b.putBoolean(name, (Byte) obj[2] == 1);
                break;
            case INT:
                b.putInt(name, (Integer) obj[2]);
                break;
            case DOUBLE:
                b.putDouble(name, (Double) obj[2]);
                break;
            case FLOAT:
                b.putFloat(name, (Float) obj[2]);
                break;
            case LONG:
                b.putLong(name, (Long) obj[2]);
                break;
            case SHORT:
                b.putShort(name, (Short) obj[2]);
                break;
            case INT_ARRAY:
                b.putIntArray(name, (int[]) obj[2]);
                break;
            case BYTE_ARRAY:
                b.putByteArray(name, (byte[]) obj[2]);
                break;
            case SHORT_ARRAY:
                b.putShortArray(name, (short[]) obj[2]);
                break;
        }
        return b;
    }

    private static Bundle toBundle(ArrayList<Object[]> obj) {
        Bundle b = new Bundle();
        obj.forEach(v -> toBundle(v, b));
        return b;
    }
}
