package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

import java.util.ArrayList;
import java.util.List;

public class CompoundTag extends Tag<ArrayList<Tag>> {

    private static final long serialVersionUID = 4540757946052775740L;

    public CompoundTag(String name, ArrayList<Tag> value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.COMPOUND;
    }


    public Tag getChildTagByKey(String key){
        List<Tag> list = getValue();
        if(list == null) return null;
        for(Tag tag : list){
            if(key.equals(tag.getName())) return tag;
        }
        return null;
    }

    public String toString(){
        String name = getName();
        String type = getType().name();
        ArrayList<Tag> value = getValue();
        StringBuilder bldr = new StringBuilder();
        bldr.append(type == null ? "?" : ("TAG_" + type))
            .append(name == null ? "(?)" : ("(" + name + ")"));

        if(value != null) {
            bldr.append(": ")
                .append(value.size())
                .append(" entries\n{\n");
            for (Tag entry : value) {
                //pad every line of the value
                bldr.append("   ")
                    .append(entry.toString().replaceAll("\n", "\n   "))
                    .append("\n");
            }
            bldr.append("}");

        } else bldr.append(":?");

        return bldr.toString();
    }

    @Override
    public CompoundTag getDeepCopy() {
        if(value != null){
            ArrayList<Tag> copy = new ArrayList<>();
            for(Tag tag : value){
                copy.add(tag.getDeepCopy());
            }
            return new CompoundTag(name, copy);
        } else return new CompoundTag(name, null);
    }

}
