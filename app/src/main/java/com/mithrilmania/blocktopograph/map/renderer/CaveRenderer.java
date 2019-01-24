package com.mithrilmania.blocktopograph.map.renderer;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.Dimension;


public class CaveRenderer implements MapRenderer {
    
    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, Version version, ChunkManager chunkManager) throws Version.VersionException {

        boolean solid, intoSurface;
        int id, meta, cavyness, layers, offset;
        Block block;
        int x, y, z, subChunk, color, i, j, tX, tY, r, g, b;

        //the bottom sub-chunk is sufficient to get heightmap data.
        TerrainChunkData floorData = chunk.getTerrain((byte) 0);
        if (floorData == null || !floorData.load2DData())
            throw new RuntimeException();

        TerrainChunkData data;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {


                solid = false;
                intoSurface = false;
                cavyness = 0;
                layers = 0;
                y = floorData.getHeightMapValue(x, z);
                offset = y % version.subChunkHeight;
                subChunk = y / version.subChunkHeight;

                /*
                while (cavefloor > 0) {
                    caveceil = chunk.getCaveYUnderAt(x, z, cavefloor - 1);
                    cavefloor = chunk.getHighestBlockYUnderAt(x, z, caveceil - 1);

                    //do not accept first cave-layer (most likely trees) as caves
                    if (layers > 1) cavyness += caveceil - cavefloor;

                    layers++;
                }
                */


                r = g = b = 0;

                subChunkLoop:
                for (; subChunk >= 0; subChunk--) {

                    data = chunk.getTerrain((byte) subChunk);
                    if (data == null || !data.loadTerrain()) {
                        //start at the top of the next chunk! (current offset might differ)
                        offset = version.subChunkHeight - 1;
                        continue;
                    }


                    for (y = offset; y >= 0; y--) {

                        id = data.getBlockTypeId(x, y, z) & 0xff;

                        meta = data.getBlockData(x, y, z) & 0xff;
                        block = Block.getBlock(id, meta);

                        //try the default meta value: 0
                        if (block == null) block = Block.getBlock(id, 0);

                        switch (id) {
                            case 0:
                                //count the number of times it goes from solid to air
                                if (solid) layers++;

                                //count the air blocks underground,
                                // but avoid trees by skipping the first layer
                                if (intoSurface) cavyness++;
                                break;
                            case 66://rail
                                if (b < 150) {
                                    b = 150;
                                    r = g = 50;
                                }
                                break;
                            case 5://wooden plank
                                if (b < 100) {
                                    b = 100;
                                    r = g = 100;
                                }
                                break;
                            case 52://monster spawner
                                r = g = b = 255;
                                break subChunkLoop;
                            case 54://chest
                                if (b < 170) {
                                    b = 170;
                                    r = 240;
                                    g = 40;
                                }
                                break;
                            case 98://stone bricks
                                if (b < 145) {
                                    b = 145;
                                    r = g = 120;
                                }
                                break;
                            case 48://moss cobblestone
                            case 4://cobblestone
                                if (b < 140) {
                                    b = 140;
                                    r = g = 100;
                                }
                                break;
                        }
                        r += data.getBlockLightValue(x, y, z);
                        solid = block != null && block.color.alpha == 0xff;
                        intoSurface |= solid && (y < 60 || layers > 0);
                    }
                }

                if (g == 0 && layers > 0) {
                    g = (r + 2) * cavyness;
                    r *= 32 * layers;
                    b = 16 * cavyness * (layers - 1);
                } else r *= r;


                r = r < 0 ? 0 : r > 255 ? 255 : r;
                g = g < 0 ? 0 : g > 255 ? 255 : g;
                //b = b < 0 ? 0 : b > 255 ? 255 : b;


                color = (r << 16) | (g << 8) | b | 0xff000000;

                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);

            }
        }
    }

}

