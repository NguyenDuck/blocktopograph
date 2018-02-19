package com.mithrilmania.blocktopograph.worldlist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mithrilmania.blocktopograph.BuildConfig;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.MenuHelper;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldActivity;
import com.mithrilmania.blocktopograph.util.io.IOUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorldItemListActivity extends AppCompatActivity implements MenuHelper.MenuContext {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private WorldItemRecyclerViewAdapter worldItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        if (findViewById(R.id.worlditem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        FloatingActionButton fabRefreshWorlds = (FloatingActionButton) findViewById(R.id.fab_refresh_worlds);
        assert fabRefreshWorlds != null;
        fabRefreshWorlds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(worldItemAdapter != null){
                    if(worldItemAdapter.reloadWorldList()) {
                        Snackbar.make(view, R.string.reloaded_world_list, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, R.string.could_not_find_worlds, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, R.string.no_read_write_access, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });


        FloatingActionButton fabChooseWorldFile = (FloatingActionButton) findViewById(R.id.fab_choose_worldfile);
        assert fabChooseWorldFile != null;
        fabChooseWorldFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                final EditText pathText = new EditText(WorldItemListActivity.this);
                pathText.setHint(R.string.storage_path_here);

                AlertDialog.Builder alert = new AlertDialog.Builder(WorldItemListActivity.this);
                alert.setTitle(R.string.open_world_custom_path);
                alert.setView(pathText);

                alert.setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //new tag name
                        Editable pathEditable = pathText.getText();
                        String path = (pathEditable == null || pathEditable.toString().equals("")) ? null : pathEditable.toString();
                        if(path == null){
                            return;//no path, no world
                        }

                        String levelDat = "/level.dat";
                        int levelIndex = path.lastIndexOf(levelDat);
                        //if the path ends with /level.dat, remove it!
                        if(levelIndex >= 0 && path.endsWith(levelDat))
                            path = path.substring(0, levelIndex);

                        String defaultPath = Environment.getExternalStorageDirectory().toString()+"/games/com.mojang/minecraftWorlds/";
                        File worldFolder = new File(path);
                        String errTitle = null, errMsg = String.format(getString(R.string.report_path_and_previous_search), path, defaultPath);
                        if(!worldFolder.exists()){
                            errTitle = getString(R.string.no_file_folder_found_at_path);
                        }
                        if(!worldFolder.isDirectory()){
                            errTitle = getString(R.string.worldpath_is_not_directory);
                        }
                        if(!(new File(worldFolder, "level.dat").exists())){
                            errTitle = getString(R.string.no_level_dat_found);
                        }
                        if(errTitle != null) {
                            new AlertDialog.Builder(WorldItemListActivity.this)
                                    .setTitle(errTitle)
                                    .setMessage(errMsg)
                                    .setCancelable(true)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                        } else {

                            try {
                                World world = new World(worldFolder);

                                if (mTwoPane) {
                                    Bundle arguments = new Bundle();
                                    arguments.putSerializable(World.ARG_WORLD_SERIALIZED, world);
                                    WorldItemDetailFragment fragment = new WorldItemDetailFragment();
                                    fragment.setArguments(arguments);
                                    WorldItemListActivity.this.getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.worlditem_detail_container, fragment)
                                            .commit();
                                } else {
                                    Intent intent = new Intent(WorldItemListActivity.this, WorldItemDetailActivity.class);
                                    intent.putExtra(World.ARG_WORLD_SERIALIZED, world);

                                    WorldItemListActivity.this.startActivity(intent);
                                }

                            } catch (Exception e){
                                Snackbar.make(view, R.string.error_opening_world, Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }
                        }

                    }
                });

                alert.setCancelable(true);

                //or alert is cancelled
                alert.setNegativeButton(android.R.string.cancel, null);

                alert.show();

                //TODO: browse for custom located world file, not everybody understands filesystem paths
            }
        });



        if(verifyStoragePermissions(this)){
            //directly open the world list if we already have access
            initWorldList();
        }


    }

    public void initWorldList(){

        View recyclerView = findViewById(R.id.worlditem_list);
        assert recyclerView != null;
        boolean hasWorlds = setupRecyclerView((RecyclerView) recyclerView);
        if(!hasWorlds){
            Snackbar.make(recyclerView, R.string.could_not_find_worlds, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    initWorldList();

                } else {

                    // permission denied, boo! Disable the
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    TextView msg = new TextView(this);
                    float dpi = this.getResources().getDisplayMetrics().density;
                    msg.setPadding((int)(19*dpi), (int)(5*dpi), (int)(14*dpi), (int)(5*dpi));
                    msg.setMaxLines(20);
                    msg.setMovementMethod(LinkMovementMethod.getInstance());
                    msg.setText(R.string.no_sdcard_access);
                    builder.setView(msg)
                            .setTitle(R.string.action_help)
                            .setCancelable(true)
                            .setNeutralButton(android.R.string.ok, null)
                            .show();
                }
            }
        }
    }



    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 4242;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     */
    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        } else return true;
    }


    //returns true if the list of worlds is not empty
    private boolean setupRecyclerView(@NonNull RecyclerView recyclerView) {
        this.worldItemAdapter = new WorldItemRecyclerViewAdapter();
        boolean hasWorlds = this.worldItemAdapter.reloadWorldList();
        recyclerView.setAdapter(this.worldItemAdapter);
        return hasWorlds;
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

    public class WorldItemRecyclerViewAdapter extends RecyclerView.Adapter<WorldItemRecyclerViewAdapter.ViewHolder> {

        private final List<World> mValues;

        private final File savesFolder;

        WorldItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();

            String path = Environment.getExternalStorageDirectory().toString()+"/games/com.mojang/minecraftWorlds/";
            Log.d("minecraftWorlds path: " + path);

            this.savesFolder = new File(path);
        }

        //returns true if it has loaded a new list of worlds, false otherwise
        boolean reloadWorldList(){
            mValues.clear();
            File[] saves = savesFolder.exists() ? savesFolder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return new File(pathname + "/level.dat").exists();
                }
            }) : null;

            if(saves != null) {
                Log.d("Number of minecraft worlds: " + saves.length);

                for (File save : saves) {
                    //debug if we see all worlds
                    Log.d("FileName: " + save.getName());

                    try {
                        mValues.add(new World(save));
                    } catch (World.WorldLoadException e) {
                        e.printStackTrace();
                        Log.d("Skipping world while reloading world list: "
                                + save.getName() + ", loading failed somehow, check stack trace");
                    }
                }
            }

            Collections.sort(mValues, new Comparator<World>() {
                @Override
                public int compare(World a, World b) {
                    try {
                        long tA = WorldListUtil.getLastPlayedTimestamp(a);
                        long tB = WorldListUtil.getLastPlayedTimestamp(b);
                        return tA > tB ? -1 : (tA == tB ? 0 : 1);
                    } catch (Exception e) {
                        return 0;
                    }
                }
            });

            //load data into view
            this.notifyDataSetChanged();

            return mValues.size() > 0;

        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.worlditem_list_content, parent, false);
            return new ViewHolder(view);
        }



        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mWorld = mValues.get(position);
            holder.mWorldNameView.setText(holder.mWorld.getWorldDisplayName());
            holder.mWorldSize.setText(IOUtil.getFileSizeInText(holder.mWorld.worldFolder));
            holder.mWorldGamemode.setText(WorldListUtil.getWorldGamemodeText(WorldItemListActivity.this, holder.mWorld));
            holder.mWorldLastPlayed.setText(WorldListUtil.getLastPlayedText(WorldItemListActivity.this, holder.mWorld));
            holder.mWorldPath.setText(holder.mWorld.levelFile.getAbsolutePath());


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(World.ARG_WORLD_SERIALIZED, holder.mWorld);
                        WorldItemDetailFragment fragment = new WorldItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.worlditem_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, WorldItemDetailActivity.class);
                        intent.putExtra(World.ARG_WORLD_SERIALIZED, holder.mWorld);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView mWorldNameView, mWorldSize, mWorldGamemode, mWorldLastPlayed, mWorldPath;
            World mWorld;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mWorldNameView = (TextView) view.findViewById(R.id.world_name);
                mWorldSize = (TextView) view.findViewById(R.id.world_size);
                mWorldGamemode = (TextView) view.findViewById(R.id.world_gamemode);
                mWorldLastPlayed = (TextView) view.findViewById(R.id.world_last_played);
                mWorldPath = (TextView) view.findViewById(R.id.world_path);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mWorldNameView.getText() + "'";
            }
        }
    }
}

