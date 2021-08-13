package com.mithrilmania.blocktopograph.block;

import com.mithrilmania.blocktopograph.block.icon.BlockIcon;
import com.mithrilmania.blocktopograph.block.icon.NoBlockIcon;
import com.mithrilmania.blocktopograph.block.icon.TexPathBlockIcon;

import java.io.Serializable;

public class BlockTemplate implements Serializable {

    private final String subName;

    private final Block block;

    private final BlockIcon icon;

    private final int color;

    private final boolean hasBiomeShading;

    public BlockTemplate(String subName, Block block, BlockIcon icon, int color, boolean hasBiomeShading) {
        this.subName = subName;
        this.block = block;
        this.icon = icon;
        this.color = color;
        this.hasBiomeShading = hasBiomeShading;
    }

    public String getSubName() {
        return subName;
    }

    public Block getBlock() {
        return block;
    }

    public BlockIcon getIcon() {
        return icon;
    }

    public int getColor() {
        return color;
    }

    public boolean isHasBiomeShading() {
        return hasBiomeShading;
    }
}
