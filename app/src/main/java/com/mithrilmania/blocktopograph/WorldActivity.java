package com.mithrilmania.blocktopograph;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.view.View;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mithrilmania.blocktopograph.chunk.NBTChunkData;
import com.mithrilmania.blocktopograph.map.MapFragment;
import com.mithrilmania.blocktopograph.map.renderer.MapType;
import com.mithrilmania.blocktopograph.nbt.EditableNBT;
import com.mithrilmania.blocktopograph.nbt.EditorFragment;
import com.mithrilmania.blocktopograph.nbt.convert.DataConverter;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

public class WorldActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WorldActivityInterface, MenuHelper.MenuContext {

    private World world;

    private MapFragment mapFragment;

    @Override
    public void showActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.show();
    }

    @Override
    public void hideActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.hide();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // immersive fullscreen for Android Kitkat and higher
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActionBar bar = getSupportActionBar();
            if (bar != null) bar.hide();
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("World activity creating...");

        super.onCreate(savedInstanceState);


        /*
        Retrieve world from previous state or intent
         */
        this.world = (World) (savedInstanceState == null
                ? getIntent().getSerializableExtra(World.ARG_WORLD_SERIALIZED)
                : savedInstanceState.getSerializable(World.ARG_WORLD_SERIALIZED));
        if (world == null) {
            Toast.makeText(this, "cannot open: world == null", Toast.LENGTH_SHORT).show();
            //WTF, try going back to the previous screen by finishing this hopeless activity...
            finish();
            //Finish does not guarantee codes below won't be executed!
            //Shit
            return;
        }


        /*
                Layout
         */
        setContentView(R.layout.activity_world);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        // Main drawer, quick access to different menus, tools and map-types.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        View headerView = navigationView.getHeaderView(0);
        assert headerView != null;

        // Title = world-name
        TextView title = (TextView) headerView.findViewById(R.id.world_drawer_title);
        assert title != null;
        title.setText(this.world.getWorldDisplayName());

        // Subtitle = world-seed (Tap worldseed to choose to copy it)
        TextView subtitle = (TextView) headerView.findViewById(R.id.world_drawer_subtitle);
        assert subtitle != null;

        /*
            World-seed & world-name analytics.

            Send anonymous world data to the Firebase (Google analytics for Android) server.
            This data will be pushed to Google-BigQuery.
            Google-BigQuery will crunch the world-data,
              storing hundreds of thousands world-seeds + names.
            The goal is to automatically create a "Top 1000" popular seeds for every week.
            This "Top 1000" will be published as soon as it gets out of BigQuery,
             keep it for the sake of this greater goal. It barely uses internet bandwidth,
             and this makes any forced intrusive revenue alternatives unnecessary.

            TODO BigQuery is not configured yet,
             @mithrilmania (author of Blocktopograph) is working on it!

             Ahhh good idea anyway... Then why didn't you continue it.

            *link to results will be included here for reference when @mithrilmania is done*
         */
        String worldSeed = String.valueOf(this.world.getWorldSeed());
        subtitle.setText(worldSeed);

        Bundle bundle = new Bundle();
        bundle.putString("seed", worldSeed);
        bundle.putString("name", this.world.getWorldDisplayName());


        // anonymous global counter of opened worlds
        Log.logFirebaseEvent(this, Log.CustomFirebaseEvent.WORLD_OPEN, bundle);


        // Open the world-map as default content
        openWorldMap();


        try {
            //try to load world-data (Opens chunk-database for later usage)
            this.world.getWorldData().load();
        } catch (WorldData.WorldDataLoadException e) {
            finish();
            e.printStackTrace();
        }

        Log.d("World activity created");
    }

    @Override
    public void onStart() {
        Log.d("World activity starting...");
        super.onStart();
        Log.d("World activity started");
    }

    @Override
    public void onResume() {
        Log.d("World activity resuming...");
        super.onResume();

        // anonymous global counter of resumed world-activities
        Log.logFirebaseEvent(this, Log.CustomFirebaseEvent.WORLD_RESUME);

        try {
            this.world.resume();
        } catch (WorldData.WorldDBException e) {
            this.onFatalDBError(e);
        }

        Log.d("World activity resumed");
    }

    @Override
    public void onPause() {
        Log.d("World activity pausing...");
        super.onPause();

        try {
            this.world.pause();
        } catch (WorldData.WorldDBException e) {
            e.printStackTrace();
        }

        Log.d("World activity paused");
    }

    @Override
    public void onStop() {
        Log.d("World activity stopping...");
        super.onStop();
        Log.d("World activity stopped");
    }

    @Override
    public void onDestroy() {
        Log.d("World activity destroying...");
        super.onDestroy();
        Log.d("World activity destroyed...");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        final FragmentManager manager = getFragmentManager();
        int count = manager.getBackStackEntryCount();

        // No opened fragments, so it is using the default fragment
        // Ask the user if he/she wants to close the world.
        if (count == 0) {

            new AlertDialog.Builder(this)
                    .setMessage(R.string.ask_close_world)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            WorldActivity.this.finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();

        } else if (confirmContentClose != null) {
            //An important fragment is opened,
            // something that couldn't be reopened in its current state easily,
            // ask the user if he/she intended to close it.
            new AlertDialog.Builder(this)
                    .setMessage(confirmContentClose)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            manager.popBackStack();
                            confirmContentClose = null;
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            //fragment is open, but it may be closed without warning
            manager.popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuHelper.onOptionsItemSelected(this, item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean propagateOnOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("World activity nav-drawer menu item selected: " + id);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;


        switch (id) {
            case (R.id.nav_world_show_map):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openWorldMap();
                    }
                });
                break;
            case (R.id.nav_world_select):
                //close activity; back to world selection screen
                closeWorldActivity();
                break;
            case (R.id.nav_singleplayer_nbt):
                openPlayerEditor();
                break;
            case (R.id.nav_multiplayer_nbt):
                openMultiplayerEditor();
                break;
            /*case(R.id.nav_inventory):
                //TODO go to inventory editor
                //This feature is planned, but not yet implemented,
                // use the generic NBT editor for now...
                break;*/
            case (R.id.nav_world_nbt):
                openLevelEditor();
                break;
            /*case(R.id.nav_tools):
                //TODO open tools menu (world downloader/importer/exporter maybe?)
                break;*/
            case (R.id.nav_overworld_satellite):
                changeMapType(MapType.OVERWORLD_SATELLITE, Dimension.OVERWORLD);
                break;
            case (R.id.nav_overworld_cave):
                changeMapType(MapType.OVERWORLD_CAVE, Dimension.OVERWORLD);
                break;
            case (R.id.nav_overworld_slime_chunk):
                changeMapType(MapType.OVERWORLD_SLIME_CHUNK, Dimension.OVERWORLD);
                break;
            /*case(R.id.nav_overworld_debug):
                changeMapType(MapType.DEBUG); //for debugging tiles positions, rendering, etc.
                break;*/
            case (R.id.nav_overworld_heightmap):
                changeMapType(MapType.OVERWORLD_HEIGHTMAP, Dimension.OVERWORLD);
                break;
            case (R.id.nav_overworld_biome):
                changeMapType(MapType.OVERWORLD_BIOME, Dimension.OVERWORLD);
                break;
            case (R.id.nav_overworld_grass):
                changeMapType(MapType.OVERWORLD_GRASS, Dimension.OVERWORLD);
                break;
            case (R.id.nav_overworld_xray):
                changeMapType(MapType.OVERWORLD_XRAY, Dimension.OVERWORLD);
                break;
            case (R.id.nav_overworld_block_light):
                changeMapType(MapType.OVERWORLD_BLOCK_LIGHT, Dimension.OVERWORLD);
                break;
            case (R.id.nav_nether_map):
                changeMapType(MapType.NETHER, Dimension.NETHER);
                break;
            case (R.id.nav_nether_xray):
                changeMapType(MapType.NETHER_XRAY, Dimension.NETHER);
                break;
            case (R.id.nav_nether_block_light):
                changeMapType(MapType.NETHER_BLOCK_LIGHT, Dimension.NETHER);
                break;
            case (R.id.nav_end_satellite):
                changeMapType(MapType.END_SATELLITE, Dimension.END);
                break;
            case (R.id.nav_end_heightmap):
                changeMapType(MapType.END_HEIGHTMAP, Dimension.END);
                break;
            case (R.id.nav_end_block_light):
                changeMapType(MapType.END_BLOCK_LIGHT, Dimension.END);
                break;
            case (R.id.nav_map_opt_toggle_grid):
                //toggle the grid
                this.showGrid = !this.showGrid;
                //rerender tiles (tiles will render with toggled grid on it now)
                if (this.mapFragment != null) this.mapFragment.resetTileView();
                break;
            case (R.id.nav_map_opt_filter_markers):
                //toggle the grid
                this.mapFragment.openMarkerFilter();
                break;
            case (R.id.nav_map_opt_toggle_markers):
                //toggle markers
                if (this.mapFragment != null) this.mapFragment.toggleMarkers();
                break;
            case (R.id.nav_biomedata_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.BIOME_DATA);
                    }
                });
                break;
            case (R.id.nav_overworld_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.OVERWORLD);
                    }
                });
                break;
            case (R.id.nav_villages_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.M_VILLAGES);
                    }
                });
                break;
            case (R.id.nav_portals_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.PORTALS);
                    }
                });
                break;
            case (R.id.nav_dimension0_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.DIMENSION_0);
                    }
                });
                break;
            case (R.id.nav_dimension1_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.DIMENSION_1);
                    }
                });
                break;
            case (R.id.nav_dimension2_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.DIMENSION_2);
                    }
                });
                break;
            case (R.id.nav_autonomous_entities_nbt):
                changeContentFragment(new OpenFragmentCallback() {
                    @Override
                    public void onOpen() {
                        openSpecialDBEntry(World.SpecialDBEntryType.AUTONOMOUS_ENTITIES);
                    }
                });
                break;
            case (R.id.nav_open_nbt_by_name): {

                //TODO put this bit in its own method

                final EditText keyEditText = new EditText(WorldActivity.this);
                keyEditText.setEms(16);
                keyEditText.setMaxEms(32);
                keyEditText.setHint(R.string.leveldb_key_here);

                new AlertDialog.Builder(WorldActivity.this)
                        .setTitle(R.string.open_nbt_from_db)
                        .setView(keyEditText)
                        .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changeContentFragment(new OpenFragmentCallback() {
                                    @Override
                                    public void onOpen() {
                                        Editable keyNameEditable = keyEditText.getText();
                                        String keyName = keyNameEditable == null
                                                ? null : keyNameEditable.toString();
                                        if (keyName == null || keyName.equals("")) {
                                            Snackbar.make(drawer,
                                                    R.string.invalid_keyname,
                                                    Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        } else {
                                            try {
                                                EditableNBT dbEntry = openEditableNbtDbEntry(keyName);
                                                if (dbEntry == null) Snackbar.make(drawer,
                                                        R.string.cannot_find_db_entry_with_name,
                                                        Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();//TODO maybe add option to create it?
                                                else openNBTEditor(dbEntry);
                                            } catch (Exception e) {
                                                Snackbar.make(drawer,
                                                        R.string.invalid_keyname,
                                                        Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            }
                                        }
                                    }
                                });
                            }
                        })
                        .setCancelable(true)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();

                break;
            }
            default:
                //Warning, we might have messed with the menu XML!
                Log.w("pressed unknown navigation-item in world-activity-drawer");
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Short-hand for opening special entries with openEditableNbtDbEntry(keyName)
     */
    public EditableNBT openSpecialEditableNbtDbEntry(final World.SpecialDBEntryType entryType)
            throws IOException {
        return openEditableNbtDbEntry(entryType.keyName);
    }

    /**
     * Load NBT data of this key from the database, converting it into structured Java Objects.
     * These objects are wrapped in a nice EditableNBT, ready for viewing and editing.
     *
     * @param keyName Key corresponding with NBT data in the database.
     * @return EditableNBT, NBT wrapper of NBT objects to view or to edit.
     * @throws IOException when database fails.
     */
    public EditableNBT openEditableNbtDbEntry(final String keyName) throws IOException {
        final byte[] keyBytes = keyName.getBytes(NBTConstants.CHARSET);
        WorldData worldData = world.getWorldData();
        try {
            worldData.openDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] entryData = worldData.db.get(keyBytes);
        if (entryData == null) return null;

        final ArrayList<Tag> workCopy = DataConverter.read(entryData);

        return new EditableNBT() {

            @Override
            public Iterable<Tag> getTags() {
                return workCopy;
            }

            @Override
            public boolean save() {
                try {
                    WorldData wData = world.getWorldData();
                    wData.openDB();
                    wData.db.put(keyBytes, DataConverter.write(workCopy));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public String getRootTitle() {
                return keyName;
            }

            @Override
            public void addRootTag(Tag tag) {
                workCopy.add(tag);
            }

            @Override
            public void removeRootTag(Tag tag) {
                workCopy.remove(tag);
            }
        };


    }

    //returns an editableNBT, where getTags() provides a compound tag as item with player-data

    /**
     * Loads local player data "~local-player" or level.dat>"Player" into an EditableNBT.
     *
     * @return EditableNBT, local player NBT data wrapped in a handle to use for saving + metadata
     * @throws Exception
     */
    public EditableNBT getEditablePlayer() throws Exception {

        /*
                Logic path:
                1. try to find the player-data in the db:
                        if found -> return that
                        else -> go to 2
                2. try to find the player-data in the level.dat:
                        if found -> return that
                        else -> go to 3
                3. no player-data available: warn the user
         */

        EditableNBT editableNBT;
        try {
            editableNBT = openSpecialEditableNbtDbEntry(World.SpecialDBEntryType.LOCAL_PLAYER);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to read \"~local_player\" from the database.");
        }

        //check if it is not found in the DB
        if (editableNBT == null) editableNBT = openEditableNbtLevel("Player");

        //check if it is not found in level.dat as well
        if (editableNBT == null)
            throw new Exception("Failed to find \"~local_player\" in DB and \"Player\" in level.dat!");


        return editableNBT;

    }

    /**
     * Open NBT editor fragment for special database entry
     */
    public void openSpecialDBEntry(final World.SpecialDBEntryType entryType) {
        try {
            EditableNBT editableEntry = openSpecialEditableNbtDbEntry(entryType);
            if (editableEntry == null) {
                this.openWorldMap();
                //TODO better handling of db problems
                //throw new Exception("\"" + entryType.keyName + "\" not found in DB.");
            }

            Log.i("Opening NBT editor for \"" + entryType.keyName + "\" from world database.");

            openNBTEditor(editableEntry);

        } catch (Exception e) {
            e.printStackTrace();

            String msg = e.getMessage();
            if (e instanceof IOException)
                msg = String.format(getString(R.string.failed_to_read_x_from_db), entryType.keyName);

            new AlertDialog.Builder(WorldActivity.this)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            changeContentFragment(new OpenFragmentCallback() {
                                @Override
                                public void onOpen() {
                                    openWorldMap();
                                }
                            });
                        }
                    }).show();
        }
    }


    public void openMultiplayerEditor() {


        //takes some time to find all players...
        // TODO make more responsive
        // TODO maybe cache player keys for responsiveness?
        //   Or messes this too much with the first perception of present players?
        final String[] players = getWorld().getWorldData().getPlayers();

        final View content = WorldActivity.this.findViewById(R.id.world_content);
        if (players.length == 0) {
            if (content != null) Snackbar.make(content,
                    R.string.no_multiplayer_data_found,
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        //spinner (drop-out list) of players;
        // just the database keys (loading each name from NBT could take an even longer time!)
        final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, players);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


        //wrap layout in alert
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_player)
                .setView(spinner)
                .setPositiveButton(R.string.open_nbt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //new tag type
                        int spinnerIndex = spinner.getSelectedItemPosition();
                        String playerKey = players[spinnerIndex];

                        try {
                            final EditableNBT editableNBT = WorldActivity.this
                                    .openEditableNbtDbEntry(playerKey);

                            changeContentFragment(new OpenFragmentCallback() {
                                @Override
                                public void onOpen() {
                                    try {
                                        openNBTEditor(editableNBT);
                                    } catch (Exception e) {
                                        new AlertDialog.Builder(WorldActivity.this)
                                                .setMessage(e.getMessage())
                                                .setCancelable(false)
                                                .setNeutralButton(android.R.string.ok,
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog,
                                                                                int id) {
                                                                changeContentFragment(
                                                                        new OpenFragmentCallback() {
                                                                            @Override
                                                                            public void onOpen() {
                                                                                openWorldMap();
                                                                            }
                                                                        });
                                                            }
                                                        }).show();
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Failed to open player entry in DB. key: " + playerKey);
                            if (content != null) Snackbar.make(content,
                                    String.format(getString(R.string.failed_read_player_from_db_with_key_x), playerKey),
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                })
                //or alert is cancelled
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public void openPlayerEditor() {
        changeContentFragment(new OpenFragmentCallback() {
            @Override
            public void onOpen() {

                try {
                    openNBTEditor(getEditablePlayer());
                } catch (Exception e) {
                    new AlertDialog.Builder(WorldActivity.this)
                            .setMessage(e.getMessage())
                            .setCancelable(false)
                            .setNeutralButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            changeContentFragment(new OpenFragmentCallback() {
                                                @Override
                                                public void onOpen() {
                                                    openWorldMap();
                                                }
                                            });
                                        }
                                    }).show();
                }

            }
        });
    }

    /**
     * Opens an editableNBT for just the subTag if it is not null.
     * Opens the whole level.dat if subTag is null.
     **/
    public EditableNBT openEditableNbtLevel(String subTagName) {

        //make a copy first, the user might not want to save changed tags.
        final CompoundTag workCopy = world.level.getDeepCopy();
        final ArrayList<Tag> workCopyContents;
        final String contentTitle;
        if (subTagName == null) {
            workCopyContents = workCopy.getValue();
            contentTitle = "level.dat";
        } else {
            workCopyContents = new ArrayList<>();
            Tag subTag = workCopy.getChildTagByKey(subTagName);
            if (subTag == null) return null;
            workCopyContents.add(subTag);
            contentTitle = "level.dat>" + subTagName;
        }

        EditableNBT editableNBT = new EditableNBT() {

            @Override
            public Iterable<Tag> getTags() {
                return workCopyContents;
            }

            @Override
            public boolean save() {
                try {
                    //write a copy of the workCopy, the workCopy may be edited after saving
                    world.writeLevel(workCopy.getDeepCopy());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public String getRootTitle() {
                return contentTitle;
            }

            @Override
            public void addRootTag(Tag tag) {
                workCopy.getValue().add(tag);
                workCopyContents.add(tag);
            }

            @Override
            public void removeRootTag(Tag tag) {
                workCopy.getValue().remove(tag);
                workCopyContents.remove(tag);
            }
        };

        //if this editable nbt is only a view of a sub-tag, not the actual root
        editableNBT.enableRootModifications = (subTagName != null);

        return editableNBT;
    }

    public void openLevelEditor() {
        changeContentFragment(new OpenFragmentCallback() {
            @Override
            public void onOpen() {
                openNBTEditor(openEditableNbtLevel(null));
            }
        });
    }

    //TODO the dimension should be derived from mapTypes.
    // E.g. split xray into xray-overworld and xray-nether, but still use the same [MapRenderer],
    //  splitting allows to pass more sophisticated use of [MapRenderer]s
    private Dimension dimension = Dimension.OVERWORLD;

    public Dimension getDimension() {
        return this.dimension;
    }

    private MapType mapType = dimension.defaultMapType;

    public MapType getMapType() {
        return this.mapType;
    }


    // TODO grid should be rendered independently of tiles, it could be faster and more responsive.
    // However, it does need to adjust itself to the scale and position of the map,
    //  which is not an easy task.
    public boolean showGrid = true;

    @Override
    public boolean getShowGrid() {
        return showGrid;
    }


    private boolean fatal = false;

    @Override
    public void onFatalDBError(WorldData.WorldDBException worldDBException) {

        Log.d(worldDBException.getMessage());
        worldDBException.printStackTrace();

        //already dead? (happens on multiple onFatalDBError(e) calls)
        if (fatal) return;

        fatal = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.error_cannot_open_world_close_and_try_again)
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WorldActivity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void changeMapType(MapType mapType, Dimension dimension) {
        this.mapType = mapType;
        this.dimension = dimension;
        //don't forget to do a reset-tileview, the mapfragment should know of this change ASAP.
        mapFragment.resetTileView();
    }

    public void closeWorldActivity() {

        //TODO not translation-friendly

        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_close_world)
                .setCancelable(false)
                .setIcon(R.drawable.ic_action_exit)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //finish this activity
                                mapFragment.closeChunks();
                                finish();
                            }
                        })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public interface OpenFragmentCallback {
        void onOpen();
    }

    public String confirmContentClose = null;

    public void changeContentFragment(final OpenFragmentCallback callback) {

        final FragmentManager manager = getFragmentManager();

        // confirmContentClose shouldn't be both used as boolean and as close-message,
        //  this is a bad pattern
        if (confirmContentClose != null) {
            new AlertDialog.Builder(this)
                    .setMessage(confirmContentClose)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    callback.onOpen();
                                }
                            })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            callback.onOpen();
        }

    }

    /**
     * Open NBT editor fragment for the given editableNBT
     */
    public void openNBTEditor(EditableNBT editableNBT) {

        // see changeContentFragment(callback)
        this.confirmContentClose = getString(R.string.confirm_close_nbt_editor);

        EditorFragment editorFragment = new EditorFragment();
        editorFragment.setEditableNBT(editableNBT);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.world_content, editorFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    /**
     * Replace current content fragment with a fresh MapFragment
     */
    public void openWorldMap() {

        //TODO should this use cached world-position etc.?

        this.confirmContentClose = null;
        this.mapFragment = new MapFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.world_content, this.mapFragment);
        transaction.commit();

    }


    @Override
    public World getWorld() {
        return this.world;
    }


    @Override
    public void addMarker(AbstractMarker marker) {
        mapFragment.addMarker(marker);
    }


    /**
     * Open a dialog; user chooses chunk-type -> open editor for this type
     **/
    @Override
    public void openChunkNBTEditor(final int chunkX, final int chunkZ, final NBTChunkData nbtChunkData, final ViewGroup viewGroup) {


        if (nbtChunkData == null) {
            //should never happen
            Log.w("User tried to open null chunkData in the nbt-editor!!!");
            return;
        }


        try {
            nbtChunkData.load();
        } catch (Exception e) {
            Snackbar.make(viewGroup, this.getString(R.string.failed_to_load_x, this.getString(R.string.nbt_chunk_data)), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        final List<Tag> tags = nbtChunkData.tags;
        if (tags == null) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.nbt_editor)
                    .setMessage(R.string.data_does_not_exist_for_chunk_ask_if_create)
                    .setIcon(R.drawable.ic_action_save_b)
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Snackbar.make(viewGroup, R.string.creating_and_saving_chunk_NBT_data, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    nbtChunkData.createEmpty();
                                    try {
                                        nbtChunkData.write();
                                        Snackbar.make(viewGroup, R.string.created_and_saved_chunk_NBT_data, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        WorldActivity.this.openChunkNBTEditor(chunkX, chunkZ, nbtChunkData, viewGroup);
                                    } catch (Exception e) {
                                        Snackbar.make(viewGroup, R.string.failed_to_create_or_save_chunk_NBT_data, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                            })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            return;
        }


        //open nbt editor for entity data
        changeContentFragment(new OpenFragmentCallback() {
            @Override
            public void onOpen() {

                //make a copy first, the user might not want to save changed tags.
                final List<Tag> workCopy = new ArrayList<>();
                for (Tag tag : tags) {
                    workCopy.add(tag.getDeepCopy());
                }

                final EditableNBT editableChunkData = new EditableNBT() {

                    @Override
                    public Iterable<Tag> getTags() {
                        return workCopy;
                    }

                    @Override
                    public boolean save() {
                        try {
                            final List<Tag> saveCopy = new ArrayList<>();
                            for (Tag tag : workCopy) {
                                saveCopy.add(tag.getDeepCopy());
                            }
                            nbtChunkData.tags = saveCopy;
                            nbtChunkData.write();
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    public String getRootTitle() {
                        final String format = "%s (cX:%d;cZ:%d)";
                        switch ((nbtChunkData).dataType) {
                            case ENTITY:
                                return String.format(format, getString(R.string.entity_chunk_data), chunkX, chunkZ);
                            case BLOCK_ENTITY:
                                return String.format(format, getString(R.string.tile_entity_chunk_data), chunkX, chunkZ);
                            default:
                                return String.format(format, getString(R.string.nbt_chunk_data), chunkX, chunkZ);
                        }
                    }

                    @Override
                    public void addRootTag(Tag tag) {
                        workCopy.add(tag);
                    }

                    @Override
                    public void removeRootTag(Tag tag) {
                        workCopy.remove(tag);
                    }
                };

                openNBTEditor(editableChunkData);
            }
        });

    }


}