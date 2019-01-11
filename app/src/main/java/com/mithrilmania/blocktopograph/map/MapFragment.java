package com.mithrilmania.blocktopograph.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.mithrilmania.blocktopograph.Log;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldActivity;
import com.mithrilmania.blocktopograph.WorldActivityInterface;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.*;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;
import com.mithrilmania.blocktopograph.map.marker.CustomNamedBitmapProvider;
import com.mithrilmania.blocktopograph.map.marker.MarkerImageView;
import com.mithrilmania.blocktopograph.map.renderer.MapType;
import com.mithrilmania.blocktopograph.nbt.EditableNBT;
import com.mithrilmania.blocktopograph.nbt.convert.DataConverter;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.FloatTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.ListTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;
import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;
import com.mithrilmania.blocktopograph.util.ViewID;
import com.mithrilmania.blocktopograph.util.math.DimensionVector3;
import com.qozix.tileview.TileView;
import com.qozix.tileview.detail.DetailLevelManager;
import com.qozix.tileview.markers.MarkerLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public class MapFragment extends Fragment {

    private WorldActivityInterface worldProvider;

    private MCTileProvider minecraftTileProvider;

    private TileView tileView;

    //procedural markers can be iterated while other threads add things to it;
    // iterating performance is not really affected because of the small size;
    public CopyOnWriteArraySet<AbstractMarker> proceduralMarkers = new CopyOnWriteArraySet<>();

    public Set<AbstractMarker> staticMarkers = new HashSet<>();

    public AbstractMarker spawnMarker;
    public AbstractMarker localPlayerMarker;


    @Override
    public void onPause() {
        super.onPause();

        //pause drawing the map
        this.tileView.pause();
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(this.worldProvider.getWorld().getWorldDisplayName());

        worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.MAPFRAGMENT_OPEN);
    }

    @Override
    public void onResume() {
        super.onResume();

        //resume drawing the map
        this.tileView.resume();

        worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.MAPFRAGMENT_RESUME);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public DimensionVector3<Float> getMultiPlayerPos(String dbKey) throws Exception {
        try {
            WorldData wData = worldProvider.getWorld().getWorldData();
            wData.openDB();
            byte[] data = wData.db.get(dbKey.getBytes(NBTConstants.CHARSET));
            if (data == null) throw new Exception("no data!");
            final CompoundTag player = (CompoundTag) DataConverter.read(data).get(0);

            ListTag posVec = (ListTag) player.getChildTagByKey("Pos");
            if (posVec == null || posVec.getValue() == null)
                throw new Exception("No \"Pos\" specified");
            if (posVec.getValue().size() != 3)
                throw new Exception("\"Pos\" value is invalid. value: " + posVec.getValue().toString());

            IntTag dimensionId = (IntTag) player.getChildTagByKey("DimensionId");
            if (dimensionId == null || dimensionId.getValue() == null)
                throw new Exception("No \"DimensionId\" specified");
            Dimension dimension = Dimension.getDimension(dimensionId.getValue());
            if (dimension == null) dimension = Dimension.OVERWORLD;

            return new DimensionVector3<>(
                    (float) posVec.getValue().get(0).getValue(),
                    (float) posVec.getValue().get(1).getValue(),
                    (float) posVec.getValue().get(2).getValue(),
                    dimension);

        } catch (Exception e) {
            Log.e(e.getMessage());

            Exception e2 = new Exception("Could not find " + dbKey);
            e2.setStackTrace(e.getStackTrace());
            throw e2;
        }
    }

    public DimensionVector3<Float> getPlayerPos() throws Exception {
        try {
            WorldData wData = worldProvider.getWorld().getWorldData();
            wData.openDB();
            byte[] data = wData.db.get(World.SpecialDBEntryType.LOCAL_PLAYER.keyBytes);

            final CompoundTag player = data != null
                    ? (CompoundTag) DataConverter.read(data).get(0)
                    : (CompoundTag) worldProvider.getWorld().level.getChildTagByKey("Player");

            ListTag posVec = (ListTag) player.getChildTagByKey("Pos");
            if (posVec == null || posVec.getValue() == null)
                throw new Exception("No \"Pos\" specified");
            if (posVec.getValue().size() != 3)
                throw new Exception("\"Pos\" value is invalid. value: " + posVec.getValue().toString());

            IntTag dimensionId = (IntTag) player.getChildTagByKey("DimensionId");
            if (dimensionId == null || dimensionId.getValue() == null)
                throw new Exception("No \"DimensionId\" specified");
            Dimension dimension = Dimension.getDimension(dimensionId.getValue());
            if (dimension == null) dimension = Dimension.OVERWORLD;

            return new DimensionVector3<>(
                    (float) posVec.getValue().get(0).getValue(),
                    (float) posVec.getValue().get(1).getValue(),
                    (float) posVec.getValue().get(2).getValue(),
                    dimension);

        } catch (Exception e) {
            Log.e(e.toString());

            ///Meow
            return new DimensionVector3<>(0f, 64f, 0f, Dimension.OVERWORLD);
//            Exception e2 = new Exception("Could not find player.");
//            e2.setStackTrace(e.getStackTrace());
//            throw e2;
        }
    }

    public DimensionVector3<Integer> getSpawnPos() throws Exception {
        try {
            CompoundTag level = this.worldProvider.getWorld().level;
            int spawnX = ((IntTag) level.getChildTagByKey("SpawnX")).getValue();
            int spawnY = ((IntTag) level.getChildTagByKey("SpawnY")).getValue();
            int spawnZ = ((IntTag) level.getChildTagByKey("SpawnZ")).getValue();
            if (spawnY == 256) {
                TerrainChunkData data = new ChunkManager(
                        this.worldProvider.getWorld().getWorldData(), Dimension.OVERWORLD)
                        .getChunk(spawnX >> 4, spawnZ >> 4)
                        .getTerrain((byte) 0);
                if (data.load2DData())
                    spawnY = data.getHeightMapValue(spawnX % 16, spawnZ % 16) + 1;
            }
            return new DimensionVector3<>(spawnX, spawnY, spawnZ, Dimension.OVERWORLD);
        } catch (Exception e) {
            ///Meow
            return new DimensionVector3<>(0, 64, 0, Dimension.OVERWORLD);
            //throw new Exception("Could not find spawn");
        }
    }

    public static class MarkerListAdapter extends ArrayAdapter<AbstractMarker> {


        MarkerListAdapter(Context context, List<AbstractMarker> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            RelativeLayout v = (RelativeLayout) convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = (RelativeLayout) vi.inflate(R.layout.marker_list_entry, parent, false);
            }

            AbstractMarker m = getItem(position);

            if (m != null) {
                TextView name = (TextView) v.findViewById(R.id.marker_name);
                TextView xz = (TextView) v.findViewById(R.id.marker_xz);
                ImageView icon = (ImageView) v.findViewById(R.id.marker_icon);

                name.setText(m.getNamedBitmapProvider().getBitmapDisplayName());
                String xzStr = String.format(Locale.ENGLISH, "x: %d, y: %d, z: %d", m.x, m.y, m.z);
                xz.setText(xzStr);

                m.loadIcon(icon);

            }

            return v;
        }

    }

    public enum MarkerTapOption {

        TELEPORT_LOCAL_PLAYER(R.string.teleport_local_player),
        REMOVE_MARKER(R.string.remove_custom_marker);

        public final int stringId;

        MarkerTapOption(int id) {
            this.stringId = id;
        }

    }

    public String[] getMarkerTapOptions() {
        MarkerTapOption[] values = MarkerTapOption.values();
        int len = values.length;
        String[] options = new String[len];
        for (int i = 0; i < len; i++) {
            options[i] = getString(values[i].stringId);
        }
        return options;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //TODO handle savedInstance...


        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        RelativeLayout worldContainer = (RelativeLayout) rootView.findViewById(R.id.world_tileview_container);
        assert worldContainer != null;

        /*
        GPS button: moves camera to player position
         */
        FloatingActionButton fabGPSPlayer = (FloatingActionButton) rootView.findViewById(R.id.fab_menu_gps_player);
        assert fabGPSPlayer != null;
        fabGPSPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (tileView == null) throw new Exception("No map available.");

                    DimensionVector3<Float> playerPos = getPlayerPos();

                    Snackbar.make(tileView,
                            getString(R.string.something_at_xyz_dim_float, getString(R.string.player),
                                    playerPos.x, playerPos.y, playerPos.z, playerPos.dimension.name),
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    if (playerPos.dimension != worldProvider.getDimension()) {
                        worldProvider.changeMapType(playerPos.dimension.defaultMapType, playerPos.dimension);
                    }

                    worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.GPS_PLAYER);

                    frameTo((double) playerPos.x, (double) playerPos.z);

                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(view, R.string.failed_find_player, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        /*
        GPS button: moves camera to spawn
         */
        FloatingActionButton fabGPSSpawn = (FloatingActionButton) rootView.findViewById(R.id.fab_menu_gps_spawn);
        assert fabGPSSpawn != null;
        fabGPSSpawn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DimensionVector3<Integer> spawnPos = getSpawnPos();

                    Snackbar.make(tileView,
                            getString(R.string.something_at_xyz_dim_int, getString(R.string.spawn),
                                    spawnPos.x, spawnPos.y, spawnPos.z, spawnPos.dimension.name),
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    if (spawnPos.dimension != worldProvider.getDimension()) {
                        worldProvider.changeMapType(spawnPos.dimension.defaultMapType, spawnPos.dimension);
                    }

                    worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.GPS_SPAWN);

                    frameTo((double) spawnPos.x, (double) spawnPos.z);

                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(view, R.string.failed_find_spawn, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        final Activity activity = getActivity();

        if (activity == null) {
            new Exception("MapFragment: activity is null, cannot set worldProvider!").printStackTrace();
            return null;
        }
        try {
            //noinspection ConstantConditions
            worldProvider = (WorldActivityInterface) activity;
        } catch (ClassCastException e) {
            new Exception("MapFragment: activity is not an worldprovider, cannot set worldProvider!", e).printStackTrace();
            return null;
        }


        //show the toolbar if the fab menu is opened
        FloatingActionMenu fabMenu = (FloatingActionMenu) rootView.findViewById(R.id.fab_menu);
        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) worldProvider.showActionBar();
                else worldProvider.hideActionBar();
            }
        });


        FloatingActionButton fabGPSMarker = (FloatingActionButton) rootView.findViewById(R.id.fab_menu_gps_marker);
        assert fabGPSMarker != null;
        fabGPSMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Collection<AbstractMarker> markers = worldProvider.getWorld().getMarkerManager().getMarkers();

                    if (markers.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        TextView msg = new TextView(activity);
                        float dpi = activity.getResources().getDisplayMetrics().density;
                        msg.setPadding((int) (19 * dpi), (int) (5 * dpi), (int) (14 * dpi), (int) (5 * dpi));
                        msg.setMaxLines(20);
                        msg.setText(R.string.no_custom_markers);
                        builder.setView(msg)
                                .setTitle(R.string.no_custom_markers_title)
                                .setCancelable(true)
                                .setNeutralButton(android.R.string.ok, null)
                                .show();
                    } else {

                        AlertDialog.Builder markerDialogBuilder = new AlertDialog.Builder(activity);
                        markerDialogBuilder.setIcon(R.drawable.ic_place);
                        markerDialogBuilder.setTitle(R.string.go_to_a_marker_list);

                        final MarkerListAdapter arrayAdapter = new MarkerListAdapter(activity, new ArrayList<>(markers));

                        markerDialogBuilder.setNegativeButton(android.R.string.cancel, null);

                        markerDialogBuilder.setAdapter(
                                arrayAdapter,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AbstractMarker m = arrayAdapter.getItem(which);
                                        if (m == null) return;

                                        Snackbar.make(tileView,
                                                activity.getString(R.string.something_at_xyz_dim_int,
                                                        m.getNamedBitmapProvider().getBitmapDisplayName(),
                                                        m.x, m.y, m.z, m.dimension.name),
                                                Snackbar.LENGTH_SHORT)
                                                .setAction("Action", null).show();

                                        if (m.dimension != worldProvider.getDimension()) {
                                            worldProvider.changeMapType(m.dimension.defaultMapType, m.dimension);
                                        }

                                        frameTo((double) m.x, (double) m.z);

                                        worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.GPS_MARKER);
                                    }
                                });
                        markerDialogBuilder.show();
                    }

                } catch (Exception e) {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        /*
        GPS button: moves camera to player position
         */
        FloatingActionButton fabGPSMultiplayer = (FloatingActionButton) rootView.findViewById(R.id.fab_menu_gps_multiplayer);
        assert fabGPSMultiplayer != null;
        fabGPSMultiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (tileView == null) {
                    Snackbar.make(view, R.string.no_map_available, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                Snackbar.make(tileView,
                        R.string.searching_for_players,
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //this can take some time... ...do it in the background
                (new AsyncTask<Void, Void, String[]>() {

                    @Override
                    protected String[] doInBackground(Void... arg0) {
                        try {
                            return worldProvider.getWorld().getWorldData().getPlayers();
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    protected void onPostExecute(final String[] players) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (players == null) {
                                    Snackbar.make(view, R.string.failed_to_retrieve_player_data, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    return;
                                }

                                if (players.length == 0) {
                                    Snackbar.make(view, R.string.no_multiplayer_data_found, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    return;
                                }


                                //NBT tag type spinner
                                final Spinner spinner = new Spinner(activity);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity,
                                        android.R.layout.simple_spinner_item, players);

                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(spinnerArrayAdapter);


                                //wrap layout in alert
                                new AlertDialog.Builder(activity)
                                        .setTitle(R.string.go_to_player)
                                        .setView(spinner)
                                        .setPositiveButton(R.string.go_loud, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                //new tag type
                                                int spinnerIndex = spinner.getSelectedItemPosition();
                                                String playerKey = players[spinnerIndex];

                                                try {
                                                    DimensionVector3<Float> playerPos = getMultiPlayerPos(playerKey);

                                                    Snackbar.make(tileView,
                                                            getString(R.string.something_at_xyz_dim_float,
                                                                    playerKey,
                                                                    playerPos.x,
                                                                    playerPos.y,
                                                                    playerPos.z,
                                                                    playerPos.dimension.name),
                                                            Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();

                                                    worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.GPS_MULTIPLAYER);

                                                    if (playerPos.dimension != worldProvider.getDimension()) {
                                                        worldProvider.changeMapType(playerPos.dimension.defaultMapType, playerPos.dimension);
                                                    }

                                                    frameTo((double) playerPos.x, (double) playerPos.z);

                                                } catch (Exception e) {
                                                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }

                                            }
                                        })
                                        //or alert is cancelled
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .show();
                            }
                        });
                    }

                }).execute();

            }
        });


        /*
        GPS button: moves camera to player position
         */
        FloatingActionButton fabGPSCoord = (FloatingActionButton) rootView.findViewById(R.id.fab_menu_gps_coord);
        assert fabGPSCoord != null;
        fabGPSCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (tileView == null) throw new Exception("No map available.");

                    View xzForm = LayoutInflater.from(activity).inflate(R.layout.xz_coord_form, null);
                    final EditText xInput = (EditText) xzForm.findViewById(R.id.x_input);
                    xInput.setText("0");
                    final EditText zInput = (EditText) xzForm.findViewById(R.id.z_input);
                    zInput.setText("0");

                    //wrap layout in alert
                    new AlertDialog.Builder(activity)
                            .setTitle(R.string.go_to_coordinate)
                            .setView(xzForm)
                            .setPositiveButton(R.string.go_loud, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    int inX, inZ;
                                    try {
                                        inX = Integer.parseInt(xInput.getText().toString());
                                    } catch (NullPointerException | NumberFormatException e) {
                                        Toast.makeText(activity, R.string.invalid_x_coordinate, Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    try {
                                        inZ = Integer.parseInt(zInput.getText().toString());
                                    } catch (NullPointerException | NumberFormatException e) {
                                        Toast.makeText(activity, R.string.invalid_z_coordinate, Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.GPS_COORD);

                                    frameTo((double) inX, (double) inZ);
                                }
                            })
                            .setCancelable(true)
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();

                } catch (Exception e) {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        try {
            Entity.loadEntityBitmaps(activity.getAssets());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Block.loadBitmaps(activity.getAssets());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            CustomIcon.loadCustomBitmaps(activity.getAssets());
        } catch (IOException e) {
            e.printStackTrace();
        }




        /*
        Create tile view
         */
        this.tileView = new TileView(activity) {

            @Override
            public void onLongPress(MotionEvent event) {

                Dimension dimension = worldProvider.getDimension();

                // 1 chunk per tile on scale 1.0
                int pixelsPerBlockW_unscaled = MCTileProvider.TILESIZE / dimension.chunkW;
                int pixelsPerBlockL_unscaled = MCTileProvider.TILESIZE / dimension.chunkL;

                float pixelsPerBlockScaledW = pixelsPerBlockW_unscaled * this.getScale();
                float pixelsPerBlockScaledL = pixelsPerBlockL_unscaled * this.getScale();


                double worldX = (((this.getScrollX() + event.getX()) / pixelsPerBlockScaledW) - MCTileProvider.HALF_WORLDSIZE) / dimension.dimensionScale;
                double worldZ = (((this.getScrollY() + event.getY()) / pixelsPerBlockScaledL) - MCTileProvider.HALF_WORLDSIZE) / dimension.dimensionScale;

                MapFragment.this.onLongClick(worldX, worldZ);
            }
        };

        this.tileView.setId(ViewID.generateViewId());

        //set the map-type
        tileView.getDetailLevelManager().setLevelType(worldProvider.getMapType());

        /*
        Add tile view to main layout
         */

        //add world view to its container in the main layout
        worldContainer.addView(tileView);


        /*
        Create tile(=bitmap) provider
         */
        this.minecraftTileProvider = new MCTileProvider(worldProvider);



        /*
        Set the bitmap-provider of the tile view
         */
        this.tileView.setBitmapProvider(this.minecraftTileProvider);


        /*
        Change tile view settings
         */

        this.tileView.setBackgroundColor(0xFF494E8E);

        // markers should align to the coordinate along the horizontal center and vertical bottom
        tileView.setMarkerAnchorPoints(-0.5f, -1.0f);

        this.tileView.defineBounds(
                -1.0,
                -1.0,
                1.0,
                1.0
        );

        this.tileView.setSize(MCTileProvider.viewSizeW, MCTileProvider.viewSizeL);

        for (MapType mapType : MapType.values()) {
            this.tileView.addDetailLevel(0.0625f, "0.0625", MCTileProvider.TILESIZE, MCTileProvider.TILESIZE, mapType);// 1/(1/16)=16 chunks per tile
            this.tileView.addDetailLevel(0.125f, "0.125", MCTileProvider.TILESIZE, MCTileProvider.TILESIZE, mapType);
            this.tileView.addDetailLevel(0.25f, "0.25", MCTileProvider.TILESIZE, MCTileProvider.TILESIZE, mapType);
            this.tileView.addDetailLevel(0.5f, "0.5", MCTileProvider.TILESIZE, MCTileProvider.TILESIZE, mapType);
            this.tileView.addDetailLevel(1f, "1", MCTileProvider.TILESIZE, MCTileProvider.TILESIZE, mapType);// 1/1=1 chunk per tile

        }

        this.tileView.setScale(0.5f);


        boolean framedToPlayer = false;

        //TODO multi-thread this
        try {

            DimensionVector3<Float> playerPos = getPlayerPos();
            float x = playerPos.x, y = playerPos.y, z = playerPos.z;
            Log.d("Placed player marker at: " + x + ";" + y + ";" + z + " [" + playerPos.dimension.name + "]");
            localPlayerMarker = new AbstractMarker((int) x, (int) y, (int) z,
                    playerPos.dimension, new CustomNamedBitmapProvider(Entity.PLAYER, "~local_player"), false);
            this.staticMarkers.add(localPlayerMarker);
            addMarker(localPlayerMarker);

            if (localPlayerMarker.dimension != worldProvider.getDimension()) {
                worldProvider.changeMapType(localPlayerMarker.dimension.defaultMapType, localPlayerMarker.dimension);
            }

            frameTo((double) x, (double) z);
            framedToPlayer = true;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Failed to place player marker. " + e.toString());
        }


        try {
            DimensionVector3<Integer> spawnPos = getSpawnPos();

            spawnMarker = new AbstractMarker(spawnPos.x, spawnPos.y, spawnPos.z, spawnPos.dimension,
                    new CustomNamedBitmapProvider(CustomIcon.SPAWN_MARKER, "Spawn"), false);
            this.staticMarkers.add(spawnMarker);
            addMarker(spawnMarker);

            if (!framedToPlayer) {

                if (spawnMarker.dimension != worldProvider.getDimension()) {
                    worldProvider.changeMapType(spawnMarker.dimension.defaultMapType, spawnMarker.dimension);
                }

                frameTo((double) spawnPos.x, (double) spawnPos.z);
            }

        } catch (Exception e) {
            //no spawn defined...
            if (!framedToPlayer) frameTo(0.0, 0.0);

        }


        tileView.getMarkerLayout().setMarkerTapListener(new MarkerLayout.MarkerTapListener() {
            @Override
            public void onMarkerTap(View view, int tapX, int tapY) {
                if (!(view instanceof MarkerImageView)) {
                    Log.d("Markertaplistener found a marker that is not a MarkerImageView! " + view.toString());
                    return;
                }

                final AbstractMarker marker = ((MarkerImageView) view).getMarkerHook();
                if (marker == null) {
                    Log.d("abstract marker is null! " + view.toString());
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder
                        .setTitle(String.format(getString(R.string.marker_info), marker.getNamedBitmapProvider().getBitmapDisplayName(), marker.getNamedBitmapProvider().getBitmapDataName(), marker.x, marker.y, marker.z, marker.dimension))
                        .setItems(getMarkerTapOptions(), new DialogInterface.OnClickListener() {
                            @SuppressWarnings("RedundantCast")
                            @SuppressLint("SetTextI18n")
                            public void onClick(DialogInterface dialog, int which) {

                                final MarkerTapOption chosen = MarkerTapOption.values()[which];

                                switch (chosen) {
                                    case TELEPORT_LOCAL_PLAYER: {
                                        try {
                                            final EditableNBT playerEditable = worldProvider.getEditablePlayer();
                                            if (playerEditable == null)
                                                throw new Exception("Player is null");

                                            Iterator playerIter = playerEditable.getTags().iterator();
                                            if (!playerIter.hasNext())
                                                throw new Exception("Player DB entry is empty!");

                                            //db entry consists of one compound tag
                                            final CompoundTag playerTag = (CompoundTag) playerIter.next();

                                            ListTag posVec = (ListTag) playerTag.getChildTagByKey("Pos");

                                            if (posVec == null)
                                                throw new Exception("No \"Pos\" specified");

                                            final List<Tag> playerPos = posVec.getValue();
                                            if (playerPos == null)
                                                throw new Exception("No \"Pos\" specified");
                                            if (playerPos.size() != 3)
                                                throw new Exception("\"Pos\" value is invalid. value: " + posVec.getValue().toString());

                                            IntTag dimensionId = (IntTag) playerTag.getChildTagByKey("DimensionId");
                                            if (dimensionId == null || dimensionId.getValue() == null)
                                                throw new Exception("No \"DimensionId\" specified");


                                            int newX = marker.x;
                                            int newY = marker.y;
                                            int newZ = marker.z;
                                            Dimension newDimension = marker.dimension;

                                            ((FloatTag) playerPos.get(0)).setValue(((float) newX) + 0.5f);
                                            ((FloatTag) playerPos.get(1)).setValue(((float) newY) + 0.5f);
                                            ((FloatTag) playerPos.get(2)).setValue(((float) newZ) + 0.5f);
                                            dimensionId.setValue(newDimension.id);


                                            if (playerEditable.save()) {

                                                localPlayerMarker = moveMarker(localPlayerMarker, newX, newY, newZ, newDimension);

                                                //TODO could be improved for translation friendliness
                                                Snackbar.make(tileView,
                                                        activity.getString(R.string.teleported_player_to_xyz_dimension) + newX + ";" + newY + ";" + newZ + " [" + newDimension.name + "] (" + marker.getNamedBitmapProvider().getBitmapDisplayName() + ")",
                                                        Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();

                                            } else throw new Exception("Failed saving player");

                                        } catch (Exception e) {
                                            Log.w(e.toString());

                                            Snackbar.make(tileView, R.string.failed_teleporting_player,
                                                    Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                        return;
                                    }
                                    case REMOVE_MARKER: {
                                        if (marker.isCustom) {
                                            MapFragment.this.removeMarker(marker);
                                            MarkerManager mng = MapFragment.this.worldProvider.getWorld().getMarkerManager();
                                            mng.removeMarker(marker, true);

                                            mng.save();

                                        } else {
                                            //only custom markers are meant to be removable
                                            Snackbar.make(tileView, R.string.marker_is_not_removable,
                                                    Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    }
                                }
                            }
                        })
                        .setCancelable(true)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();

            }
        });


        //do not loop the scale
        tileView.setShouldLoopScale(false);

        //prevents flickering
        this.tileView.setTransitionsEnabled(false);

        //more responsive rendering
        this.tileView.setShouldRenderWhilePanning(true);

        this.tileView.setSaveEnabled(true);


        return rootView;
    }

    /**
     * Creates a new marker (looking exactly the same as the old one) on the new position,
     * while removing the old marker.
     *
     * @param marker    Marker to be recreated
     * @param x         new pos X
     * @param y         new pos Y
     * @param z         new pos Z
     * @param dimension new pos Dimension
     * @return the newly created marker, which should replace the old one.
     */
    public AbstractMarker moveMarker(AbstractMarker marker, int x, int y, int z, Dimension dimension) {
        AbstractMarker newMarker = marker.copy(x, y, z, dimension);
        if (staticMarkers.remove(marker)) staticMarkers.add(newMarker);
        this.removeMarker(marker);
        this.addMarker(newMarker);

        if (marker.isCustom) {
            MarkerManager mng = this.worldProvider.getWorld().getMarkerManager();
            mng.removeMarker(marker, true);
            mng.addMarker(newMarker, true);
        }

        return newMarker;
    }

    private final static int MARKER_INTERVAL_CHECK = 50;
    private int proceduralMarkersInterval = 0;
    private volatile AsyncTask shrinkProceduralMarkersTask;


    /**
     * Calculates viewport of tileview, expressed in blocks.
     *
     * @param marginX horizontal viewport-margin, in pixels
     * @param marginZ vertical viewport-margin, in pixels
     * @return minimum_X, maximum_X, minimum_Z, maximum_Z, dimension. (min and max are expressed in blocks!)
     */
    public Object[] calculateViewPort(int marginX, int marginZ) {

        Dimension dimension = this.worldProvider.getDimension();

        // 1 chunk per tile on scale 1.0
        int pixelsPerBlockW_unscaled = MCTileProvider.TILESIZE / dimension.chunkW;
        int pixelsPerBlockL_unscaled = MCTileProvider.TILESIZE / dimension.chunkL;

        float scale = tileView.getScale();

        //scale the amount of pixels, less pixels per block if zoomed out
        double pixelsPerBlockW = pixelsPerBlockW_unscaled * scale;
        double pixelsPerBlockL = pixelsPerBlockL_unscaled * scale;

        long blockX = Math.round((tileView.getScrollX() - marginX) / pixelsPerBlockW) - MCTileProvider.HALF_WORLDSIZE;
        long blockZ = Math.round((tileView.getScrollY() - marginZ) / pixelsPerBlockL) - MCTileProvider.HALF_WORLDSIZE;
        long blockW = Math.round((tileView.getWidth() + marginX + marginX) / pixelsPerBlockW);
        long blockH = Math.round((tileView.getHeight() + marginZ + marginZ) / pixelsPerBlockL);

        return new Object[]{blockX, blockX + blockW, blockZ, blockH, dimension};
    }

    public AsyncTask retainViewPortMarkers(final Runnable callback) {

        DisplayMetrics displayMetrics = this.getActivity().getResources().getDisplayMetrics();
        return new AsyncTask<Object, AbstractMarker, Void>() {
            @Override
            protected Void doInBackground(Object... params) {
                long minX = (long) params[0],
                        maxX = (long) params[1],
                        minY = (long) params[2],
                        maxY = (long) params[3];
                Dimension reqDim = (Dimension) params[4];

                for (AbstractMarker p : MapFragment.this.proceduralMarkers) {

                    // do not remove static markers
                    if (MapFragment.this.staticMarkers.contains(p)) continue;

                    if (p.x < minX || p.x > maxX || p.y < minY || p.y > maxY || p.dimension != reqDim) {
                        this.publishProgress(p);
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(final AbstractMarker... values) {
                for (AbstractMarker v : values) {
                    MapFragment.this.removeMarker(v);
                }
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.run();
            }

        }.execute(this.calculateViewPort(
                displayMetrics.widthPixels / 2,
                displayMetrics.heightPixels / 2)
        );
    }

    /**
     * Important: this method should be run from the UI thread.
     *
     * @param marker The marker to remove from the tile view.
     */
    public void removeMarker(AbstractMarker marker) {
        staticMarkers.remove(marker);
        proceduralMarkers.remove(marker);
        if (marker.view != null) tileView.removeMarker(marker.view);
    }

    /**
     * Important: this method should be run from the UI thread.
     *
     * @param marker The marker to add to the tile view.
     */
    public void addMarker(AbstractMarker marker) {

        if (proceduralMarkers.contains(marker)) return;

        if (shrinkProceduralMarkersTask == null
                && ++proceduralMarkersInterval > MARKER_INTERVAL_CHECK) {
            //shrink set of markers to viewport every so often
            shrinkProceduralMarkersTask = retainViewPortMarkers(new Runnable() {
                @Override
                public void run() {
                    //reset this to start accepting viewport update requests again.
                    shrinkProceduralMarkersTask = null;
                }
            });

            proceduralMarkersInterval = 0;
        }


        proceduralMarkers.add(marker);


        Activity act = getActivity();
        if (act == null) return;
        MarkerImageView markerView = marker.getView(act);

        this.filterMarker(marker);

        if (tileView.getMarkerLayout().indexOfChild(markerView) >= 0) {
            tileView.getMarkerLayout().removeMarker(markerView);
        }


        tileView.addMarker(markerView,
                marker.dimension.dimensionScale * (double) marker.x / (double) MCTileProvider.HALF_WORLDSIZE,
                marker.dimension.dimensionScale * (double) marker.z / (double) MCTileProvider.HALF_WORLDSIZE,
                -0.5f, -0.5f);
    }

    public void toggleMarkers() {
        int visibility = tileView.getMarkerLayout().getVisibility();
        tileView.getMarkerLayout().setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


    public enum LongClickOption {

        TELEPORT_LOCAL_PLAYER(R.string.teleport_local_player, null),
        CREATE_MARKER(R.string.create_custom_marker, null),
        //TODO TELEPORT_MULTI_PLAYER("Teleport other player", null),
        ENTITY(R.string.open_chunk_entity_nbt, ChunkTag.ENTITY),
        TILE_ENTITY(R.string.open_chunk_tile_entity_nbt, ChunkTag.BLOCK_ENTITY);

        public final int stringId;
        public final ChunkTag dataType;

        LongClickOption(int id, ChunkTag dataType) {
            this.stringId = id;
            this.dataType = dataType;
        }

    }

    public String[] getLongClickOptions() {
        LongClickOption[] values = LongClickOption.values();
        int len = values.length;
        String[] options = new String[len];
        for (int i = 0; i < len; i++) {
            options[i] = getString(values[i].stringId);
        }
        return options;
    }


    public void onLongClick(final double worldX, final double worldZ) {


        final Activity activity = MapFragment.this.getActivity();


        final Dimension dim = this.worldProvider.getDimension();

        double chunkX = worldX / dim.chunkW;
        double chunkZ = worldZ / dim.chunkL;

        //negative doubles are rounded up when casting to int; floor them
        final int chunkXint = chunkX < 0 ? (((int) chunkX) - 1) : ((int) chunkX);
        final int chunkZint = chunkZ < 0 ? (((int) chunkZ) - 1) : ((int) chunkZ);


        final View container = activity.findViewById(R.id.world_content);
        if (container == null) {
            Log.w("CANNOT FIND MAIN CONTAINER, WTF");
            return;
        }

        new AlertDialog.Builder(activity)
                .setTitle(getString(R.string.postion_2D_floats_with_chunkpos, worldX, worldZ, chunkXint, chunkZint, dim.name))
                .setItems(getLongClickOptions(), new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(DialogInterface dialog, int which) {

                        final LongClickOption chosen = LongClickOption.values()[which];


                        switch (chosen) {
                            case TELEPORT_LOCAL_PLAYER: {
                                try {

                                    final EditableNBT playerEditable = MapFragment.this.worldProvider.getEditablePlayer();
                                    if (playerEditable == null)
                                        throw new Exception("Player is null");

                                    Iterator playerIter = playerEditable.getTags().iterator();
                                    if (!playerIter.hasNext())
                                        throw new Exception("Player DB entry is empty!");

                                    //db entry consists of one compound tag
                                    final CompoundTag playerTag = (CompoundTag) playerIter.next();

                                    ListTag posVec = (ListTag) playerTag.getChildTagByKey("Pos");

                                    if (posVec == null) throw new Exception("No \"Pos\" specified");

                                    final List<Tag> playerPos = posVec.getValue();
                                    if (playerPos == null)
                                        throw new Exception("No \"Pos\" specified");
                                    if (playerPos.size() != 3)
                                        throw new Exception("\"Pos\" value is invalid. value: " + posVec.getValue().toString());

                                    final IntTag dimensionId = (IntTag) playerTag.getChildTagByKey("DimensionId");
                                    if (dimensionId == null || dimensionId.getValue() == null)
                                        throw new Exception("No \"DimensionId\" specified");


                                    float playerY = (float) playerPos.get(1).getValue();


                                    View yForm = LayoutInflater.from(activity).inflate(R.layout.y_coord_form, null);
                                    final EditText yInput = (EditText) yForm.findViewById(R.id.y_input);
                                    yInput.setText(String.valueOf(playerY));

                                    final float newX = (float) worldX;
                                    final float newZ = (float) worldZ;

                                    new AlertDialog.Builder(activity)
                                            .setTitle(chosen.stringId)
                                            .setView(yForm)
                                            .setPositiveButton(R.string.action_teleport, new DialogInterface.OnClickListener() {
                                                @SuppressWarnings("RedundantCast")
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    float newY;
                                                    Editable value = yInput.getText();
                                                    if (value == null) newY = 64f;
                                                    else {
                                                        try {
                                                            newY = (float) Float.parseFloat(value.toString());//removes excessive precision
                                                        } catch (Exception e) {
                                                            newY = 64f;
                                                        }
                                                    }

                                                    ((FloatTag) playerPos.get(0)).setValue(newX);
                                                    ((FloatTag) playerPos.get(1)).setValue(newY);
                                                    ((FloatTag) playerPos.get(2)).setValue(newZ);
                                                    dimensionId.setValue(dim.id);

                                                    if (playerEditable.save()) {

                                                        MapFragment.this.localPlayerMarker = MapFragment.this.moveMarker(
                                                                MapFragment.this.localPlayerMarker,
                                                                (int) newX, (int) newY, (int) newZ, dim);

                                                        Snackbar.make(container,
                                                                String.format(getString(R.string.teleported_player_to_xyz_dim), newX, newY, newZ, dim.name),
                                                                Snackbar.LENGTH_LONG)
                                                                .setAction("Action", null).show();
                                                    } else {
                                                        Snackbar.make(container,
                                                                R.string.failed_teleporting_player,
                                                                Snackbar.LENGTH_LONG)
                                                                .setAction("Action", null).show();
                                                    }
                                                }
                                            })
                                            .setCancelable(true)
                                            .setNegativeButton(android.R.string.cancel, null)
                                            .show();
                                    return;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Snackbar.make(container, R.string.failed_to_find_or_edit_local_player_data,
                                            Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    return;
                                }
                            }
                            case CREATE_MARKER: {

                                View createMarkerForm = LayoutInflater.from(activity).inflate(R.layout.create_marker_form, null);

                                final EditText markerNameInput = (EditText) createMarkerForm.findViewById(R.id.marker_displayname_input);
                                markerNameInput.setText(R.string.default_custom_marker_name);
                                final EditText markerIconNameInput = (EditText) createMarkerForm.findViewById(R.id.marker_iconname_input);
                                markerIconNameInput.setText("blue_marker");
                                final EditText xInput = (EditText) createMarkerForm.findViewById(R.id.x_input);
                                xInput.setText(String.valueOf((int) worldX));
                                final EditText yInput = (EditText) createMarkerForm.findViewById(R.id.y_input);
                                yInput.setText(String.valueOf(64));
                                final EditText zInput = (EditText) createMarkerForm.findViewById(R.id.z_input);
                                zInput.setText(String.valueOf((int) worldZ));


                                new AlertDialog.Builder(activity)
                                        .setTitle(chosen.stringId)
                                        .setView(createMarkerForm)
                                        .setPositiveButton("Create marker", new DialogInterface.OnClickListener() {

                                            public void failParseSnackbarReport(int msg) {
                                                Snackbar.make(container, msg,
                                                        Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            }

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                try {
                                                    String displayName = markerNameInput.getText().toString();
                                                    if (displayName.equals("") || displayName.contains("\"")) {
                                                        failParseSnackbarReport(R.string.marker_invalid_name);
                                                        return;
                                                    }
                                                    String iconName = markerIconNameInput.getText().toString();
                                                    if (iconName.equals("") || iconName.contains("\"")) {
                                                        failParseSnackbarReport(R.string.invalid_icon_name);
                                                        return;
                                                    }

                                                    int xM, yM, zM;

                                                    String xStr = xInput.getText().toString();
                                                    try {
                                                        xM = Integer.parseInt(xStr);
                                                    } catch (NumberFormatException e) {
                                                        failParseSnackbarReport(R.string.invalid_x_coordinate);
                                                        return;
                                                    }
                                                    String yStr = yInput.getText().toString();
                                                    try {
                                                        yM = Integer.parseInt(yStr);
                                                    } catch (NumberFormatException e) {
                                                        failParseSnackbarReport(R.string.invalid_y_coordinate);
                                                        return;
                                                    }

                                                    String zStr = zInput.getText().toString();
                                                    try {
                                                        zM = Integer.parseInt(zStr);
                                                    } catch (NumberFormatException e) {
                                                        failParseSnackbarReport(R.string.invalid_z_coordinate);
                                                        return;
                                                    }

                                                    AbstractMarker marker = MarkerManager.markerFromData(displayName, iconName, xM, yM, zM, dim);
                                                    MarkerManager mng = MapFragment.this.worldProvider.getWorld().getMarkerManager();
                                                    mng.addMarker(marker, true);

                                                    MapFragment.this.addMarker(marker);

                                                    mng.save();

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    failParseSnackbarReport(R.string.failed_to_create_marker);
                                                }
                                            }
                                        })
                                        .setCancelable(true)
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .show();


                                return;
                            }
                            /* TODO multi player teleporting
                            case TELEPORT_MULTI_PLAYER:{

                                break;
                            }*/
                            case ENTITY:
                            case TILE_ENTITY: {

                                final World world = MapFragment.this.worldProvider.getWorld();
                                final ChunkManager chunkManager = new ChunkManager(world.getWorldData(), dim);
                                final Chunk chunk = chunkManager.getChunk(chunkXint, chunkZint);

                                if (!chunkDataNBT(chunk, chosen == LongClickOption.ENTITY)) {
                                    Snackbar.make(container, String.format(getString(R.string.failed_to_load_x), getString(chosen.stringId)),
                                            Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }


                            }
                        }


                    }
                })
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel, null)
                .show();

    }

    /**
     * Opens the chunk data nbt editor.
     *
     * @param chunk  the chunk to edit
     * @param entity if it is entity data (True) or block-entity data (False)
     * @return false when the chunk data could not be loaded.
     */
    private boolean chunkDataNBT(Chunk chunk, boolean entity) {
        NBTChunkData chunkData;
        try {
            chunkData = entity ? chunk.getEntity() : chunk.getBlockEntity();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //just open the editor if the data is there for us to edit it
        this.worldProvider.openChunkNBTEditor(chunk.x, chunk.z, chunkData, this.tileView);
        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


        tileView.destroy();
        tileView = null;
        minecraftTileProvider = null;

    }

    public static class BitmapChoiceListAdapter extends ArrayAdapter<BitmapChoiceListAdapter.NamedBitmapChoice> {

        public static class NamedBitmapChoice {

            //Using a handle to facilitate bitmap swapping without breaking the filter.
            final NamedBitmapProviderHandle namedBitmap;
            boolean enabledTemp;
            boolean enabled;

            public NamedBitmapChoice(NamedBitmapProviderHandle namedBitmap, boolean enabled) {
                this.namedBitmap = namedBitmap;
                this.enabled = this.enabledTemp = enabled;
            }
        }

        public BitmapChoiceListAdapter(Context context, List<NamedBitmapChoice> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, View v, @NonNull ViewGroup parent) {

            final NamedBitmapChoice m = getItem(position);
            if (m == null) return new RelativeLayout(getContext());

            if (v == null) v = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.img_name_check_list_entry, parent, false);


            ImageView img = (ImageView) v.findViewById(R.id.entry_img);
            TextView text = (TextView) v.findViewById(R.id.entry_text);
            final CheckBox check = (CheckBox) v.findViewById(R.id.entry_check);

            img.setImageBitmap(m.namedBitmap.getNamedBitmapProvider().getBitmap());
            text.setText(m.namedBitmap.getNamedBitmapProvider().getBitmapDisplayName());
            check.setTag(position);
            check.setChecked(m.enabledTemp);
            check.setOnCheckedChangeListener(changeListener);

            return v;
        }

        private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Object tag = compoundButton.getTag();
                if (tag == null) return;
                int position = (int) tag;
                final NamedBitmapChoice m = getItem(position);
                if (m == null) return;
                m.enabledTemp = b;
            }
        };

    }


    //static, remember choice while app is open.
    static Map<NamedBitmapProvider, BitmapChoiceListAdapter.NamedBitmapChoice> markerFilter = new HashMap<>();

    static {
        //entities are enabled by default
        for (Entity v : Entity.values()) {
            //skip things without a bitmap (dropped items etc.)
            //skip entities with placeholder ids (900+)
            if (v.sheetPos < 0 || v.id >= 900) continue;
            markerFilter.put(v.getNamedBitmapProvider(),
                    new BitmapChoiceListAdapter.NamedBitmapChoice(v, true));
        }
        //tile-entities are disabled by default
        for (TileEntity v : TileEntity.values()) {
            markerFilter.put(v.getNamedBitmapProvider(),
                    new BitmapChoiceListAdapter.NamedBitmapChoice(v, false));
        }

    }

    public void openMarkerFilter() {

        final Activity activity = this.getActivity();


        final List<BitmapChoiceListAdapter.NamedBitmapChoice> choices = new ArrayList<>();
        choices.addAll(markerFilter.values());

        //sort on names, nice for the user.
        Collections.sort(choices, new Comparator<BitmapChoiceListAdapter.NamedBitmapChoice>() {
            @Override
            public int compare(BitmapChoiceListAdapter.NamedBitmapChoice a, BitmapChoiceListAdapter.NamedBitmapChoice b) {
                return a.namedBitmap.getNamedBitmapProvider().getBitmapDisplayName().compareTo(b.namedBitmap.getNamedBitmapProvider().getBitmapDisplayName());
            }
        });


        new AlertDialog.Builder(activity)
                .setTitle(R.string.filter_markers)
                .setAdapter(new BitmapChoiceListAdapter(activity, choices), null)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //save all the temporary states.
                        for (BitmapChoiceListAdapter.NamedBitmapChoice choice : choices) {
                            choice.enabled = choice.enabledTemp;
                        }
                        MapFragment.this.updateMarkerFilter();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //reset all the temporary states.
                        for (BitmapChoiceListAdapter.NamedBitmapChoice choice : choices) {
                            choice.enabledTemp = choice.enabled;
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        //reset all the temporary states.
                        for (BitmapChoiceListAdapter.NamedBitmapChoice choice : choices) {
                            choice.enabledTemp = choice.enabled;
                        }
                    }
                })
                .show();
    }

    public void updateMarkerFilter() {
        for (AbstractMarker marker : this.proceduralMarkers) {
            filterMarker(marker);
        }
    }

    public void filterMarker(AbstractMarker marker) {
        BitmapChoiceListAdapter.NamedBitmapChoice choice = markerFilter.get(marker.getNamedBitmapProvider());
        if (choice != null) {
            marker.getView(this.getActivity()).setVisibility(
                    (choice.enabled && marker.dimension == worldProvider.getDimension())
                            ? View.VISIBLE : View.INVISIBLE);
        } else {
            marker.getView(this.getActivity())
                    .setVisibility(marker.dimension == worldProvider.getDimension()
                            ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void resetTileView() {
        if (this.tileView != null) {
            worldProvider.logFirebaseEvent(WorldActivity.CustomFirebaseEvent.MAPFRAGMENT_RESET);

            updateMarkerFilter();

            tileView.getDetailLevelManager().setLevelType(worldProvider.getMapType());

            invalidateTileView();
        }
    }

    public void invalidateTileView() {
        MapType redo = worldProvider.getMapType();
        DetailLevelManager manager = tileView.getDetailLevelManager();
        //just swap mapType twice; it is not rendered, but it invalidates all tiles.
        manager.setLevelType(MapType.CHESS);
        manager.setLevelType(redo);
        //all tiles will now reload as soon as the tileView is drawn (user scrolls -> redraw)
    }


    /**
     * This is a convenience method to scrollToAndCenter after layout (which won't happen if called directly in onCreate
     * see https://github.com/moagrius/TileView/wiki/FAQ
     */
    public void frameTo(final double worldX, final double worldZ) {
        this.tileView.post(new Runnable() {
            @Override
            public void run() {
                Dimension dimension = worldProvider.getDimension();
                if (tileView != null) tileView.scrollToAndCenter(
                        dimension.dimensionScale * worldX / (double) MCTileProvider.HALF_WORLDSIZE,
                        dimension.dimensionScale * worldZ / (double) MCTileProvider.HALF_WORLDSIZE);
            }
        });
    }


}
