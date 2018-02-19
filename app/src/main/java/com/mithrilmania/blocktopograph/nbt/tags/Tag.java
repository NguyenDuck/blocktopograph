package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

import java.io.Serializable;

public abstract class Tag<T> implements Serializable {

    private static final long serialVersionUID = 7925783664695648371L;

    protected String name;

    protected T value;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue(){
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public abstract NBTConstants.NBTType getType();

    public abstract Tag<T> getDeepCopy();

    public String toString(){
        String name = getName();
        String type = getType().name();
        T value = getValue();
        return (type == null ? "?" : ("TAG_"+type))
                + (name == null ? "(?)" : ("("+name+")"))
                + (value == null ? ":?" : (": "+value.toString()));
    }


}
