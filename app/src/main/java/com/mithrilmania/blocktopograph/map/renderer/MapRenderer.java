package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public interface MapRenderer {

    /*
    I (@mithrilmania) hate this setup with CPU rendering, but pushing all data to the GPU
     will just make things worse on some (most?) phones.
    So I guess we have to do some weird patterns to make this efficient...
    Currently trying to keep tile sizes constant, for bitmap recycling.
    Makes zooming look sharp too.
     */

    /**
     * Render a single chunk to provided bitmap (bm)
     *
     * @param chunk        ChunkManager, provides chunks, which provide chunk-data
     * @param canvas        Bitmap to render to
     * @param dimension Mapped dimension
     * @param chunkX    X chunk coordinate (x-block coord / Chunk.WIDTH)
     * @param chunkZ    Z chunk coordinate (z-block coord / Chunk.LENGTH)
     * @param pX        texture X pixel coord to start rendering to
     * @param pY        texture Y pixel coord to start rendering to
     * @param pW        width (X) of one block in pixels
     * @param pL        length (Z) of one block in pixels
     * @param paint
     * @param version
     * @param chunkManager
     * @return bm is returned back
     * @throws Version.VersionException when the version of the chunk is unsupported.
     */
    void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, Version version, ChunkManager chunkManager) throws Version.VersionException;

}
