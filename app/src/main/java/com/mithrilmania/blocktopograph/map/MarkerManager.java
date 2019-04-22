package com.mithrilmania.blocktopograph.map;

import android.util.LongSparseArray;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;
import com.mithrilmania.blocktopograph.map.marker.CustomNamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO docs
 */
public class MarkerManager {


        /*

        TODO marker stuff:
        - add more marker-icons/types
        - player markers with name-tags

         */


    // g0: Whole match
    // g1: Format version (int)
    // g2: Marker display name (escaped string)
    // g3: Marker icon name (escaped string)
    // g4: X coordinate (int)
    // g5: Y coordinate (int)
    // g6: Z coordinate (int)
    // g7: Dimension name (escaped string)
    //
    // example:
    // 1 "Test \" marker" "default \" \"_ma 1 rker 1" 1234 64 4321 overworld ;
    // Why bother inventing a format?
    private static final Pattern formatRegex = Pattern.compile("(\\d+)\\s+\"(?:(?:(.+?[^\\\\]))|)\"\\s+\"(?:(?:(.+?[^\\\\]))|)\"\\s+(\\d+?)\\s+(\\d+?)\\s+(\\d+?)\\s+(.+?)\\s*;");


    private final ExecutorService executorService;
    private final File markerFile;

    private Set<AbstractMarker> markers = new HashSet<>();
    private LongSparseArray<Set<AbstractMarker>> chunks = new LongSparseArray<>();

    private boolean dirty;

    public MarkerManager(File markerFile) {
        this.markerFile = markerFile;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void load() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MarkerManager.this.loadFromFile();
            }
        });
    }

    private static long xzToKey(int x, int z) {
        return (((long) x) << 32) | z;
    }

    public void removeMarker(AbstractMarker marker, boolean dirty) {

        long chunkKey = xzToKey(marker.x >> 4, marker.z >> 4);
        Set<AbstractMarker> chunk = chunks.get(chunkKey);
        if (chunk != null) chunk.remove(marker);

        //only set it to dirty if the marker is removed.
        this.dirty |= markers.remove(marker) && dirty;
    }

    public void addMarker(AbstractMarker marker, boolean dirty) {
        markers.add(marker);

        long chunkKey = xzToKey(marker.x >> 4, marker.z >> 4);
        Set<AbstractMarker> chunk = chunks.get(chunkKey);
        if (chunk == null) chunks.put(chunkKey, chunk = new HashSet<>());
        chunk.add(marker);

        this.dirty |= dirty;
    }

    private synchronized Set<AbstractMarker> loadFromFile() {
        markers = new HashSet<>();

        if (this.markerFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(this.markerFile));
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.length() < 6) continue;

                    try {
                        int format = Integer.parseInt(line.split(" ")[0]);
                        switch (format) {
                            //add versions here if it changes
                            default: {
                                Matcher matcher = formatRegex.matcher(line);
                                if (matcher.find()) {
                                    String markerDisplayName = matcher.group(2).replace("\\\"", "\"");
                                    String iconName = matcher.group(3).replace("\\\"", "\"");
                                    int x = Integer.parseInt(matcher.group(4));
                                    int y = Integer.parseInt(matcher.group(5));
                                    int z = Integer.parseInt(matcher.group(6));
                                    String dimensionName = matcher.group(7).replace("\\\"", "\"");
                                    Dimension dimension = Dimension.getDimension(dimensionName);
                                    if (dimension == null) dimension = Dimension.OVERWORLD;
                                    this.addMarker(markerFromData(markerDisplayName, iconName, x, y, z, dimension), false);

                                }
                            }
                        }
                    } catch (Exception e) {
                        //ok, probably a comment or something, just ignore
                        Log.d(this, "Invalid line in marker file: " + line);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.dirty = false;

        return markers;
    }


    /**
     * Saves the current markers, if and only if the markers were changed.
     */
    public void save() {
        if (dirty) executorService.submit(new Runnable() {
            @Override
            public void run() {
                MarkerManager.this.saveToFile();
                dirty = false;
            }
        });
    }

    private synchronized void saveToFile() {
        try {

            if (markerFile.createNewFile())
                Log.d(this, "Created " + this.markerFile.getAbsolutePath());

            //append to file
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.markerFile, false)));

            for (AbstractMarker marker : markers) {
                out.format("1 \"%s\", \"%s\", %d %d %d %s ;\n",
                        marker.getNamedBitmapProvider().getBitmapDisplayName().replace("\"", "\\\""),
                        marker.getNamedBitmapProvider().getBitmapDataName().replace("\"", "\\\""),
                        marker.x, marker.y, marker.z,
                        marker.dimension.dataName.replace("\"", "\\\""));
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<AbstractMarker> getMarkers() {
        return Collections.unmodifiableSet(markers);
    }

    public Collection<AbstractMarker> getMarkersOfChunk(int chunkX, int chunkZ) {
        long key = xzToKey(chunkX, chunkZ);
        Set<AbstractMarker> chunk = chunks.get(key);
        if (chunk == null) chunks.put(key, chunk = new HashSet<>());
        return chunk;
    }

    public static AbstractMarker markerFromData(String displayName, String iconName, int x, int y, int z, Dimension dimension) {

        NamedBitmapProvider nbp = KnownBlock.getByDataName(iconName);
        if (nbp == null || nbp.getBitmap() == null) nbp = Entity.getEntity(iconName);
        if (nbp == null || nbp.getBitmap() == null) nbp = TileEntity.getTileEntity(iconName);
        if (nbp == null || nbp.getBitmap() == null) nbp = CustomIcon.getCustomIcon(iconName);
        if (nbp == null || nbp.getBitmap() == null) nbp = CustomIcon.DEFAULT_MARKER;

        return new AbstractMarker(x, y, z, dimension,
                new CustomNamedBitmapProvider(nbp, displayName), true);

    }
}
