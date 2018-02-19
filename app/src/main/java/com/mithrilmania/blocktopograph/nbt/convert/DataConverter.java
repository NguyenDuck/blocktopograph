package com.mithrilmania.blocktopograph.nbt.convert;


import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataConverter {

    public static ArrayList<Tag> read(byte[] input) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        NBTInputStream in = new NBTInputStream(bais);
        ArrayList<Tag> tags = in.readTopLevelTags();
        in.close();
        return tags;

    }

    public static byte[] write(List<Tag> tags) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        NBTOutputStream out = new NBTOutputStream(bos);
        for (Tag tag : tags) {
            out.writeTag(tag);
        }
        out.close();
        return bos.toByteArray();
    }
}
