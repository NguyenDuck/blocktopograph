package com.mithrilmania.blocktopograph.nbt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.ListTag;
import com.mithrilmania.blocktopograph.nbt.tags.LongTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.util.ArrayList;

public class ItemTag {

    private final CompoundTag mContent;
    private ListTag mPages;

    ItemTag() {
        ArrayList<Tag> tags = new ArrayList<>();
        mContent = new CompoundTag(Keys.INV_TAG, tags);
    }

    ItemTag(CompoundTag content) {
        mContent = content;
    }

    @Nullable
    private String getStringEntry(@NonNull String key) {
        Tag tag = mContent.getChildTagByKey(key);
        if (!(tag instanceof StringTag)) return null;
        return ((StringTag) tag).getValue();
    }

    private static boolean setStringEntry(@NonNull CompoundTag parent, @NonNull String key, @NonNull String val) {
        Tag tag = parent.getChildTagByKey(key);
        if (tag == null) {
            StringTag curr = new StringTag(key, val);
            parent.getValue().add(curr);
            return true;
        }
        if (!(tag instanceof StringTag)) return false;
        ((StringTag) tag).setValue(val);
        return true;
    }

    @Nullable
    public Long getId() {
        Tag tag = mContent.getChildTagByKey(Keys.I_BOOK_ID);
        if (!(tag instanceof LongTag)) return null;
        return ((LongTag) tag).getValue();
    }

    public boolean setId(long id) {
        Tag tag = mContent.getChildTagByKey(Keys.I_BOOK_ID);
        if (tag == null) {
            LongTag curr = new LongTag(Keys.I_BOOK_ID, id);
            mContent.getValue().add(curr);
            return true;
        }
        if (!(tag instanceof LongTag)) return false;
        ((LongTag) tag).setValue(id);
        return true;
    }

    @Nullable
    public Integer getGeneration() {
        Tag tag = mContent.getChildTagByKey(Keys.I_BOOK_GENERATION);
        if (!(tag instanceof IntTag)) return null;
        return ((IntTag) tag).getValue();
    }

    public boolean setGeneration(int generation) {
        Tag tag = mContent.getChildTagByKey(Keys.I_BOOK_GENERATION);
        if (tag == null) {
            LongTag curr = new LongTag(Keys.I_BOOK_GENERATION, generation);
            mContent.getValue().add(curr);
            return true;
        }
        if (!(tag instanceof IntTag)) return false;
        ((IntTag) tag).setValue(generation);
        return true;
    }

    @Nullable
    public String getAuthor() {
        return getStringEntry(Keys.I_BOOK_AUTHOR);
    }

    public boolean setAuthor(String author) {
        return setStringEntry(mContent, Keys.I_BOOK_AUTHOR, author);
    }

    @Nullable
    public String getTitle() {
        return getStringEntry(Keys.I_BOOK_TITLE);
    }

    public boolean setTitle(String title) {
        return setStringEntry(mContent, Keys.I_BOOK_TITLE, title);
    }

    @Nullable
    public String getXuid() {
        return getStringEntry(Keys.I_BOOK_XUID);
    }

    public boolean setXuid(String xuid) {
        return setStringEntry(mContent, Keys.I_BOOK_XUID, xuid);
    }

    /**
     * Try to load pages of a book item.
     *
     * @return false if the same tag of wrong type exists, otherwise true.
     */
    private boolean preparePages() {
        if (mPages != null) return true;
        Tag tag = mContent.getChildTagByKey(Keys.I_BOOK_PAGES);
        if (tag instanceof ListTag) {
            mPages = (ListTag) tag;
            return true;
        } else return tag == null;
    }

    public int getPagesCount() {
        preparePages();
        if (mPages == null) return -1;
        return mPages.getValue().size();
    }

    private static void addPage(ArrayList<Tag> list) {
        ArrayList<Tag> subs = new ArrayList<>(4);
        CompoundTag tag = new CompoundTag("", subs);
        setStringEntry(tag, Keys.I_BOOK_PAGES_PHOTONAME, "");
        setStringEntry(tag, Keys.I_BOOK_PAGES_TEXT, "");
        list.add(tag);
    }

    @Nullable
    public CompoundTag getPage(int index) {
        if (!preparePages()) return null;
        if (mPages == null) {
            mPages = new ListTag(Keys.I_BOOK_PAGES, new ArrayList<>(Math.max(index, 4)));
            mContent.getValue().add(mPages);
        }
        ArrayList<Tag> pages = mPages.getValue();
        for (int i = pages.size(); i <= index; i++) addPage(pages);
        Tag tag = pages.get(index);
        if (tag instanceof CompoundTag) return (CompoundTag) tag;
        return null;
    }

    public static boolean setPageText(CompoundTag page, String text) {
        return setStringEntry(page, Keys.I_BOOK_PAGES_TEXT, text);
    }

    public static boolean setPageUrl(@NonNull CompoundTag page, @NonNull String url) {
        Tag tag = page.getChildTagByKey(Keys.I_BOOK_PAGES_CLICK);
        CompoundTag event;
        if (tag == null) {
            event = new CompoundTag(Keys.I_BOOK_PAGES_CLICK, new ArrayList<>(2));
            page.getValue().add(event);
        } else {
            if (!(tag instanceof CompoundTag)) return false;
            event = (CompoundTag) tag;
        }
        if (!setStringEntry(event, Keys.I_BOOK_PAGES_CLICK_ACTION, Keys.I_BOOK_PAGES_CLICK_ACTION_URL))
            return false;
        if (!setStringEntry(event, Keys.I_BOOK_PAGES_CLICK_VALUE, url)) return false;
        return true;
    }

    @NonNull
    public CompoundTag getContent() {
        return mContent;
    }
}
