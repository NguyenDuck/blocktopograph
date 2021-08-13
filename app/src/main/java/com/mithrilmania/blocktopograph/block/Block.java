package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.block.blockproperty.BlockProperty;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Block implements Serializable {

    private final String name;

    private final BlockType type;

    private final Object[] knownProperties;

    private final Map<String, Object> customProperties;

    Block(String name, Map<String, Object> customProperties) {
        this.name = name;
        this.type = null;
        this.knownProperties = null;
        this.customProperties = customProperties;
    }

    Block(BlockType type, Object[] knownProperties, Map<String, Object> customProperties) {
        this.name = type.getName();
        this.type = type;
        this.knownProperties = knownProperties;
        this.customProperties = customProperties;
    }

    public String getName() {
        return name;
    }

    public BlockType getType() {
        return type;
    }

    public Object getProperty(String name) {
        int index = getKnownPropertyIndex(name, type);
        if (index >= 0) return knownProperties[index];
        else return customProperties.get(name);
    }

    public Object[] getKnownProperties() {
        return knownProperties;
    }

    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }

    private static int getKnownPropertyIndex(@NonNull String name, @Nullable BlockType type) {
        if (type != null) {
            BlockProperty[] properties = type.getKnownProperties();
            for (int i = 0, propertiesLength = properties.length; i < propertiesLength; i++) {
                BlockProperty prop = properties[i];
                if (name.equals(prop.getName())) return i;
            }
        }
        return -1;
    }

    public static class Builder {

        private final String name;

        private final BlockType type;

        private final Object[] knownProperties;

        private final Map<String, Object> customProperties = new HashMap<>();

        public Builder(@NonNull String name) {
            this.name = name;
            this.type = null;
            this.knownProperties = null;
        }

        public Builder(@NonNull BlockType type) {
            this.name = null;
            this.type = type;
            this.knownProperties = new Object[type.getKnownProperties().length];
        }

        public Builder setProperty(@NonNull String name, Object val) {
            int index = getKnownPropertyIndex(name, type);
            if (index >= 0) knownProperties[index] = val;
            else customProperties.put(name, val);
            return this;
        }

        public Builder setProperty(@NonNull Tag<?> tag) {
            return setProperty(tag.getName(), tag.getValue());
        }

        public Block build() {
            if (type == null) return new Block(name, customProperties);
            return new Block(type, knownProperties, customProperties);
        }
    }
}
