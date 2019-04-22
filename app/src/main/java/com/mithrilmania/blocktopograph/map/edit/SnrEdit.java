package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.KnownBlock;
import com.mithrilmania.blocktopograph.map.UnknownBlock;

import org.jetbrains.annotations.NotNull;

public class SnrEdit implements EditTarget.RandomAccessEdit {

    private SnrConfig config;
    private KnownBlock b1;
    private KnownBlock b2;
    private KnownBlock b3;
    private KnownBlock b4;

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
        if (b1 instanceof UnknownBlock || b2 instanceof UnknownBlock)
            return b1.equals(b2);
        return ((KnownBlock) b1).id == ((KnownBlock) b2).id;
    }

    @Override
    public int edit(Chunk chunk, int x, int y, int z) {
        if (
                (config.searchMode == 1 &&
                        (config.ignoreSubId ?
                                equalsIgnoreSubtype(chunk.getBlock(x, y, z, 1), b1)
                                : (b1 == chunk.getBlock(x, y, z, 1))
                        )
                ) || (config.searchMode == 2 &&
                        (config.ignoreSubId ?
                                equalsIgnoreSubtype(chunk.getBlock(x, y, z), b1)
                                : (b1 == chunk.getBlock(x, y, z))
                        )
                ) || (config.searchMode == 3 &&
                        (config.ignoreSubId ?
                                equalsIgnoreSubtype(chunk.getBlock(x, y, z), b1)
                                        || equalsIgnoreSubtype(chunk.getBlock(x, y, z, 1), b1)
                                : (
                                (b1 == chunk.getBlock(x, y, z))
                                        || (b1 == chunk.getBlock(x, y, z, 1)))
                        )
                ) || (config.searchMode == 4 &&
                        (config.ignoreSubId ?
                                (equalsIgnoreSubtype(chunk.getBlock(x, y, z), b1)
                                        && equalsIgnoreSubtype(chunk.getBlock(x, y, z, 1), b2))
                                : (
                                (b1 == chunk.getBlock(x, y, z))
                                        && (b2 == chunk.getBlock(x, y, z, 1)))
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
