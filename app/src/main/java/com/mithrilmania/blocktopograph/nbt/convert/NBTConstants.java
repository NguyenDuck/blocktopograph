package com.mithrilmania.blocktopograph.nbt.convert;

import com.mithrilmania.blocktopograph.nbt.tags.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NBTConstants {

    public enum NBTType {

        END(0, EndTag.class, "EndTag"),
        BYTE(1, ByteTag.class, "ByteTag"),
        SHORT(2, ShortTag.class, "ShortTag"),
        INT(3, IntTag.class, "IntTag"),
        LONG(4, LongTag.class, "LongTag"),
        FLOAT(5, FloatTag.class, "FloatTag"),
        DOUBLE(6, DoubleTag.class, "DoubleTag"),
        BYTE_ARRAY(7, ByteArrayTag.class, "ByteArrayTag"),
        STRING(8, StringTag.class, "StringTag"),
        LIST(9, ListTag.class, "ListTag"),
        COMPOUND(10, CompoundTag.class, "CompoundTag"),
        INT_ARRAY(11, IntArrayTag.class, "IntArrayTag"),

        //Is this one even used?!? Maybe used in mods?
        SHORT_ARRAY(100, ShortArrayTag.class, "ShortArrayTag");

        public final int id;
        public final Class<? extends Tag> tagClazz;
        public final String displayName;

        static public Map<Integer, NBTType> typesByID = new HashMap<>();
        static public Map<Class<? extends Tag>, NBTType> typesByClazz = new HashMap<>();

        NBTType(int id, Class<? extends Tag> tagClazz, String displayName){
            this.id = id;
            this.tagClazz = tagClazz;
            this.displayName = displayName;
        }

        //not all NBT types are meant to be created in an editor, the END tag for example.
        public static String[] editorOptions_asString;
        public static NBTType[] editorOptions_asType = new NBTType[]{
                BYTE,
                SHORT,
                INT,
                LONG,
                FLOAT,
                DOUBLE,
                BYTE_ARRAY,
                STRING,
                LIST,
                COMPOUND,
                INT_ARRAY,
                SHORT_ARRAY
        };

        static {


            int len = editorOptions_asType.length;
            editorOptions_asString = new String[len];
            for(int i = 0; i < len; i++){
                editorOptions_asString[i] = editorOptions_asType[i].displayName;
            }


            //fill maps
            for(NBTType type : NBTType.values()){
                typesByID.put(type.id, type);
                typesByClazz.put(type.tagClazz, type);
            }
        }

        public static Tag newInstance(String tagName, NBTType type){
            switch (type){
                case END: return new EndTag();
                case BYTE: return new ByteTag(tagName, (byte) 0);
                case SHORT: return new ShortTag(tagName, (short) 0);
                case INT: return new IntTag(tagName, 0);
                case LONG: return new LongTag(tagName, 0L);
                case FLOAT: return new FloatTag(tagName, 0f);
                case DOUBLE: return new DoubleTag(tagName, 0.0);
                case BYTE_ARRAY: return new ByteArrayTag(tagName, null);
                case STRING: return new StringTag(tagName, "");
                case LIST: return new ListTag(tagName, new ArrayList<Tag>());
                case COMPOUND: return new CompoundTag(tagName, new ArrayList<Tag>());
                default: return null;
            }
        }

    }

    public static final Charset CHARSET = Charset.forName("UTF-8");

}
