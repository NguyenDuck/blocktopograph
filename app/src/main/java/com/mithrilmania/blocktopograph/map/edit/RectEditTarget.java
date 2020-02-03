package com.mithrilmania.blocktopograph.map.edit;

import android.annotation.SuppressLint;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class RectEditTarget extends EditTarget {

    @NonNull
    private final Rect mArea;

    private final int yLowest;

    private final int yHighest;

    @NonNull
    private final Dimension dimension;

    public RectEditTarget(@NonNull WorldData worldData, @NonNull Rect area, @NonNull Dimension dimension) {
        super(true, worldData);
        mArea = new Rect(area);
        mArea.right--;
        mArea.bottom--;
        yLowest = 0;
        yHighest = 255;
        this.dimension = dimension;
    }

    @Override
    public EditResultCode forEachXyz(RandomAccessEdit edit) {
        return forEach(false, false, null, edit);
    }

    @Override
    public EditResultCode forEachXz(RandomAccessEdit edit) {
        return forEach(false, true, null, edit);
    }

    @Override
    public EditResultCode forEachChunk(ChunkBasedEdit edit) {
        return forEach(true, false, edit, null);
    }

    @SuppressLint("DefaultLocale")
    private EditResultCode forEach(boolean chunkBased, boolean is2d,
                                   @Nullable ChunkBasedEdit chunkBasedEdit,
                                   @Nullable RandomAccessEdit randomAccessEdit) {

        int exceptionCount = 0;

        int chunkMinX = mArea.left >> 4;
        int chunkMaxX = mArea.right >> 4;
        int chunkMinZ = mArea.top >> 4;
        int chunkMaxZ = mArea.bottom >> 4;

        // Cache should not be used till end.

        for (int chunkX = chunkMinX; chunkX <= chunkMaxX; chunkX++) {
            int innerMinX = (chunkX == chunkMinX) ? (mArea.left & 0xf) : 0;
            int innerMaxX = (chunkX == chunkMaxX) ? (mArea.right & 0xf) : 15;

            for (int chunkZ = chunkMinZ; chunkZ <= chunkMaxZ; chunkZ++) {
                int innerMinZ = (chunkZ == chunkMinZ) ? (mArea.top & 0xf) : 0;
                int innerMaxZ = (chunkZ == chunkMaxZ) ? (mArea.bottom & 0xf) : 15;

                Chunk chunk = mWorldData.getChunkStreaming(chunkX, chunkZ, dimension, false, Version.V1_2_PLUS);

                if (chunkBased) {
                    int result = chunkBasedEdit.edit(chunk, innerMinX, innerMaxX, yLowest, yHighest, innerMinZ, innerMaxZ);
                    if (result != 0) {
                        if (exceptionCount < 5 || exceptionCount > mMaxError) {
                            Log.d(this, String.format(
                                    "Failed with chunk (%d,%d), code %d",
                                    chunkX, chunkZ, result));
                            if (exceptionCount > mMaxError)
                                return EditResultCode.QUIT_TOO_MANY_ERROR;
                            exceptionCount++;
                        }
                    }
                } else {
                    boolean supportHightMap = chunk.supportsHeightMap();

                    for (int innerX = innerMinX; innerX <= innerMaxX; innerX++) {
                        for (int innerZ = innerMinZ; innerZ <= innerMaxZ; innerZ++) {

                            if (is2d) {
                                int result = randomAccessEdit.edit(chunk, innerX, 0, innerZ);
                                if (result != 0) {
                                    if (exceptionCount < 5 || exceptionCount > mMaxError) {
                                        Log.d(this, String.format(
                                                "Failed with chunk (%d,%d), rel (%d,%d), code %d",
                                                chunkX, chunkZ, innerX, innerZ, result));
                                        if (exceptionCount > mMaxError)
                                            return EditResultCode.QUIT_TOO_MANY_ERROR;
                                        exceptionCount++;
                                    }
                                }
                            } else {

                                //Math.min(yHighest, chunk.getHeightMapValue(innerX, innerZ) - 1)
                                //: yHighest;
                                for (int y = yLowest; y <= yHighest; y++) {
                                    int result = randomAccessEdit.edit(chunk, innerX, y, innerZ);
                                    if (result != 0) {
                                        if (exceptionCount < 5 || exceptionCount > mMaxError) {
                                            Log.d(this, String.format(
                                                    "Failed with chunk (%d,%d), rel (%d,%d,%d), code %d",
                                                    chunkX, chunkZ, innerX, y, innerZ, result));
                                            if (exceptionCount > mMaxError)
                                                return EditResultCode.QUIT_TOO_MANY_ERROR;
                                            exceptionCount++;
                                        }
                                    }
                                }// End for y

                            }
                        }// End for innerZ
                    }// End for innerX
                }

                try {
                    chunk.save();
                } catch (Exception e) {
                    if (exceptionCount < 5 || exceptionCount > mMaxError) {
                        Log.d(this, e);
                        if (exceptionCount > mMaxError) return EditResultCode.QUIT_TOO_MANY_ERROR;
                        exceptionCount++;
                    }
                }
            }// End for ChunkZ
        }// End for ChunkX

        mWorldData.resetCache();
        return exceptionCount > 0 ? EditResultCode.PARTIALLY_FAILED : EditResultCode.SUCCESS;
    }
}
