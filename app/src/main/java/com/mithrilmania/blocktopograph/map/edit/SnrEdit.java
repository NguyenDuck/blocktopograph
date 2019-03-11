package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.chunk.Chunk;

import org.jetbrains.annotations.NotNull;

public class SnrEdit implements EditTarget.RandomAccessEdit {

    private SnrConfig config;
    private int b1;
    private int b2;
    private int b3;
    private int b4;

    SnrEdit(@NotNull SnrConfig cfg) {
        config = cfg;
        switch (cfg.searchMode) {
            case 1:
            case 2:
            case 3:
                b1 = cfg.searchBlockMain.getRuntimeId();
                break;
            case 4:
                b1 = cfg.searchBlockMain.getRuntimeId();
                b2 = cfg.searchBlockSub.getRuntimeId();
                break;
        }
        if (cfg.ignoreSubId) {
            b1 &= 0xffff00;
            b2 &= 0xffff00;
        }
        switch (cfg.placeMode) {
            case 1:
            case 2:
                b3 = cfg.placeBlockMain.getRuntimeId();
                break;
            case 3:
                b3 = cfg.placeBlockMain.getRuntimeId();
                b4 = cfg.placeBlockSub.getRuntimeId();
                break;
        }
    }

    @Override
    public int edit(Chunk chunk, int x, int y, int z) {
        if (
                (config.searchMode == 1 &&
                        (config.ignoreSubId ?
                                (chunk.getBlockRuntimeId(x, y, z, 1) & 0xffff00) == b1
                                : (chunk.getBlockRuntimeId(x, y, z, 1) == b1)
                        )
                ) || (config.searchMode == 2 &&
                        (config.ignoreSubId ?
                                (chunk.getBlockRuntimeId(x, y, z) & 0xffff00) == b1
                                : (chunk.getBlockRuntimeId(x, y, z) == b1)
                        )
                ) || (config.searchMode == 3 &&
                        (config.ignoreSubId ?
                                ((chunk.getBlockRuntimeId(x, y, z) & 0xffff00) == b1
                                        || (chunk.getBlockRuntimeId(x, y, z, 1) & 0xffff00) == b1)
                                : (
                                (chunk.getBlockRuntimeId(x, y, z) == b1)
                                        || (chunk.getBlockRuntimeId(x, y, z, 1) == b1))
                        )
                ) || (config.searchMode == 4 &&
                        (config.ignoreSubId ?
                                ((chunk.getBlockRuntimeId(x, y, z) & 0xffff00) == b1
                                        && (chunk.getBlockRuntimeId(x, y, z, 1) & 0xffff00) == b2)
                                : (
                                (chunk.getBlockRuntimeId(x, y, z) == b1)
                                        && (chunk.getBlockRuntimeId(x, y, z, 1) == b2))
                        )
                )
        ) {
            switch (config.placeMode) {
                case 1:
                    chunk.setBlockRuntimeId(x, y, z, 1, b3);
                    break;
                case 2:
                    chunk.setBlockRuntimeId(x, y, z, 0, b3);
                    break;
                case 3:
                    chunk.setBlockRuntimeId(x, y, z, 0, b3);
                    chunk.setBlockRuntimeId(x, y, z, 1, b4);
                    break;
            }
        }
        return 0;
    }

}
