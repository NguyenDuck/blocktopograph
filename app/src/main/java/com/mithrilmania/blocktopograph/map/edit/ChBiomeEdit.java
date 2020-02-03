package com.mithrilmania.blocktopograph.map.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Biome;


public class ChBiomeEdit implements EditTarget.RandomAccessEdit {

    @Nullable
    private Biome mFrom;

    @NonNull
    private Biome mTo;

    ChBiomeEdit(@Nullable Biome fromBiome, @NonNull Biome toBiome) {
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
