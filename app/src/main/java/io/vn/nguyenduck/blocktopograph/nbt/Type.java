package io.vn.nguyenduck.blocktopograph.nbt;

public enum Type {
    END(0),
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    SHORT_ARRAY(12);

    private final int value;

    Type(int value) {
        this.value = value;
    }

    public static Type fromInt(int type) {
        return values()[type];
    }

    public byte toByte() {
        return (byte) this.value;
    }
}
