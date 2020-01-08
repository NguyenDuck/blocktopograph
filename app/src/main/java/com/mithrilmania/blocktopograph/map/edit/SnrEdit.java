package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.chunk.Chunk;

import org.jetbrains.annotations.NotNull;

public class SnrEdit implements EditTarget.RandomAccessEdit {

    private SnrConfig config;
    private Block b1;
    private Block b2;
    private Block b3;
    private Block b4;

    SnrEdit(@NotNull SnrConfig cfg) {
        config = cfg;
        switch (cfg.searchMode) {
            case 1:
            case 2:
            case 3:
                b1 = cfg.searchBlockMain;
                break;
            case 4:
                b1 = cfg.searchBlockMain;
                b2 = cfg.searchBlockSub;
                break;
        }
        switch (cfg.placeMode) {
            case 1:
            case 2:
                b3 = cfg.placeBlockMain;
                break;
            case 3:
                b3 = cfg.placeBlockMain;
                b4 = cfg.placeBlockSub;
                break;
        }
    }

    private static boolean equalsIgnoreSubtype(Block b1, Block b2) {
        return ((Object) b1.getBlockType()) == b2.getBlockType();
    }

    @Override
    public int edit(Chunk chunk, int x, int y, int z) {
        if (
                (config.searchMode == 1 &&
                        (config.ignoreSubId ?
                                equalsIgnoreSubtype(chunk.getBlock(x, y, z, 1), b1)
                                : (b1.equals(chunk.getBlock(x, y, z, 1)))
                        )
                ) || (config.searchMode == 2 &&
                        (config.ignoreSubId ?
                                equalsIgnoreSubtype(chunk.getBlock(x, y, z), b1)
                                : (b1.equals(chunk.getBlock(x, y, z)))
                        )
                ) || (config.searchMode == 3 &&
                        (config.ignoreSubId ?
                                equalsIgnoreSubtype(chunk.getBlock(x, y, z), b1)
                                        || equalsIgnoreSubtype(chunk.getBlock(x, y, z, 1), b1)
                                : (
                                (b1.equals(chunk.getBlock(x, y, z)))
                                        || (b1.equals(chunk.getBlock(x, y, z, 1))))
                        )
                ) || (config.searchMode == 4 &&
                        (config.ignoreSubId ?
                                (equalsIgnoreSubtype(chunk.getBlock(x, y, z), b1)
                                        && equalsIgnoreSubtype(chunk.getBlock(x, y, z, 1), b2))
                                : (
                                (b1.equals(chunk.getBlock(x, y, z)))
                                        && (b2.equals(chunk.getBlock(x, y, z, 1))))
                        )
                )
        ) {
            switch (config.placeMode) {
                case 1:
                    chunk.setBlock(x, y, z, 1, b3);
                    break;
                case 2:
                    chunk.setBlock(x, y, z, 0, b3);
                    break;
                case 3:
                    chunk.setBlock(x, y, z, 0, b3);
                    chunk.setBlock(x, y, z, 1, b4);
                    break;
            }
        }
        return 0;
    }

}
