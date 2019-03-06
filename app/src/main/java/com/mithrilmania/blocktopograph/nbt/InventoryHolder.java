package com.mithrilmania.blocktopograph.nbt;

import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.tags.ByteTag;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.ListTag;
import com.mithrilmania.blocktopograph.nbt.tags.ShortTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

public final class InventoryHolder {

    private ListTag mContent;

    public static InventoryHolder readFromPlayer(CompoundTag player) {
        Tag tag = player.getChildTagByKey(Keys.INVENTORY);
        if (!(tag instanceof ListTag)) return null;
        InventoryHolder ret = new InventoryHolder();
        ret.mContent = (ListTag) tag;
        return ret;
    }

    private InventoryHolder() {
    }

    public ListTag getContent() {
        return mContent;
    }

    @Nullable
    public Item getItemOfSlot(short slot) {
        for (Tag tag : mContent.getValue()) {
            if (!(tag instanceof CompoundTag)) continue;
            CompoundTag itemTag = (CompoundTag) tag;
            Tag sub = itemTag.getChildTagByKey(Keys.INV_SLOT);
//            if(sub instanceof ShortTag) {
//                Short sh = ((ShortTag) sub).getValue();
//                if (sh == null) continue;
//                if (slot == sh) return new Item(itemTag);
//            }
//            else
            if (sub instanceof ByteTag) {
                Byte by = ((ByteTag) sub).getValue();
                if (by == null) continue;
                if (slot == by) return new Item(itemTag);
            }
        }
        return null;
    }

    public static class Item {

        private final CompoundTag mContent;

        private Item(CompoundTag content) {
            mContent = content;
        }

//        private Item() {
//            mContent = new CompoundTag(Keys.INVENTORY, new ArrayList<>(5));
//            //mContent.
//        }

        @Nullable
        public String getName() {
            Tag tag = mContent.getChildTagByKey(Keys.INV_NAME);
            if (!(tag instanceof StringTag)) return null;
            StringTag curr = (StringTag) tag;
            return curr.getValue();
        }

        public boolean setName(String name) {
            Tag tag = mContent.getChildTagByKey(Keys.INV_NAME);
            if (!(tag instanceof StringTag)) return false;
            StringTag curr = (StringTag) tag;
            curr.setValue(name);
            return true;
        }

        @Nullable
        public Short getId() {
            Tag tag = mContent.getChildTagByKey(Keys.INV_ID);
            if (!(tag instanceof ShortTag)) return null;
            ShortTag curr = (ShortTag) tag;
            return curr.getValue();
        }

        public boolean setId(short id) {
            Tag tag = mContent.getChildTagByKey(Keys.INV_ID);
            if (!(tag instanceof ShortTag)) return false;
            ShortTag curr = (ShortTag) tag;
            curr.setValue(id);
            return true;
        }

        @Nullable
        public Byte getCount() {
            Tag tag = mContent.getChildTagByKey(Keys.INV_COUNT);
            if (!(tag instanceof ByteTag)) return null;
            ByteTag curr = (ByteTag) tag;
            return curr.getValue();
        }

        public boolean setCount(byte count) {
            Tag tag = mContent.getChildTagByKey(Keys.INV_COUNT);
            if (!(tag instanceof ByteTag)) return false;
            ByteTag curr = (ByteTag) tag;
            curr.setValue(count);
            return true;
        }

        @Nullable
        public Short getDamage() {
            Tag tag = mContent.getChildTagByKey(Keys.INV_DAMAGE);
            if (!(tag instanceof ShortTag)) return null;
            ShortTag curr = (ShortTag) tag;
            return curr.getValue();
        }

        public boolean setDamage(short damage) {
            Tag tag = mContent.getChildTagByKey(Keys.INV_DAMAGE);
            if (!(tag instanceof ShortTag)) return false;
            ShortTag curr = (ShortTag) tag;
            curr.setValue(damage);
            return true;
        }

//        @Nullable
//        public Short getSlot() {
//            Tag tag = mContent.getChildTagByKey(Keys.INV_SLOT);
//            if (!(tag instanceof ShortTag)) return null;
//            ShortTag curr = (ShortTag) tag;
//            return curr.getValue();
//        }
//
//        public boolean setSlot(short slot) {
//            Tag tag = mContent.getChildTagByKey(Keys.INV_SLOT);
//            if (!(tag instanceof ShortTag)) return false;
//            ShortTag curr = (ShortTag) tag;
//            curr.setValue(slot);
//            return true;
//        }

        @Nullable
        public ItemTag getItemTag() {
            Tag tag = mContent.getChildTagByKey(Keys.INV_TAG);
            if (tag == null) {
                ItemTag itemTag = new ItemTag();
                mContent.getValue().add(itemTag.getContent());
                return itemTag;
            }
            if (!(tag instanceof CompoundTag)) return null;
            CompoundTag curr = (CompoundTag) tag;
            return new ItemTag(curr);
        }

    }
}
