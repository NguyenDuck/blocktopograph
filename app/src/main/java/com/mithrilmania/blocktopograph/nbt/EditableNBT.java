package com.mithrilmania.blocktopograph.nbt;

import com.mithrilmania.blocktopograph.nbt.tags.Tag;

/**
 * Wrapper around NBT data for easy passing and saving without knowing its context.
 */
public abstract class EditableNBT {

    boolean modified = false;

    public boolean enableRootModifications = true;

    public abstract Iterable<? extends Tag> getTags();

    public void setModified(){
        modified = true;
    }

    public boolean isModified(){ return modified; }

    /**
     * Save the NBT data to something.
     *
     * @return if the save was successful
     */
    public abstract boolean save();


    public abstract String getRootTitle();


    public abstract void addRootTag(Tag tag);

    public abstract void removeRootTag(Tag tag);
}
