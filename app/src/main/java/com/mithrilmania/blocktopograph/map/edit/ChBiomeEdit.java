package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Biome;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChBiomeEdit implements EditTarget.RandomAccessEdit {

    @Nullable
    private Biome mFrom;

    @NotNull
    private Biome mTo;

    ChBiomeEdit(@Nullable Biome fromBiome, @NotNull Biome toBiome) {
        mFrom = fromBiome;
        mTo = toBiome;
    }

    @Override
    public int edit(Chunk chunk, int x, int y, int z) {
        if (mFrom == null || chunk.getBiome(x, z) == mFrom.id)
            chunk.setBiome(x, z, mTo.id);
        return 0;
    }

}
