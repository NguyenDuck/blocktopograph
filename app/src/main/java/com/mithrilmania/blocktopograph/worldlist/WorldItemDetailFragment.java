package com.mithrilmania.blocktopograph.worldlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldActivity;
import com.mithrilmania.blocktopograph.util.io.IOUtil;

/**
 * A fragment representing a single WorldItem detail screen.
 * This fragment is either contained in a {@link WorldItemListActivity}
 * in two-pane mode (on tablets) or a {@link WorldItemDetailActivity}
 * on handsets.
 */
public class WorldItemDetailFragment extends Fragment {

    /**
     * The dummy content this fragment is presenting.
     */
    private World world;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorldItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.full_date_format), Locale.ENGLISH);
        sdf.setTimeZone(tz);//set time zone.
        return sdf.format(new Date(time * 1000));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Activity activity = this.getActivity();

        String barTitle;
        if(!getArguments().containsKey(World.ARG_WORLD_SERIALIZED)){
            Snackbar.make(activity.findViewById(R.id.worlditem_detail),
                    R.string.error_could_not_open_world_details_lost_track_world, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            barTitle = activity.getString(R.string.error_could_not_open_world);
        } else {

            world = (World) getArguments().getSerializable(World.ARG_WORLD_SERIALIZED);
            barTitle = world ==null ? activity.getString(R.string.error_could_not_open_world) : world.getWorldDisplayName();
        }

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(barTitle);
        }


        View rootView = inflater.inflate(R.layout.worlditem_detail, container, false);

        try {
            if (world != null && world.getLevel() != null){
                TextView worldName = (TextView) rootView.findViewById(R.id.detail_world_name);
                worldName.setText(world.getWorldDisplayName());
                TextView worldSize = (TextView) rootView.findViewById(R.id.detail_world_size);
                worldSize.setText(IOUtil.getFileSizeInText(world.worldFolder));

                TextView worldGameMode = (TextView) rootView.findViewById(R.id.detail_world_gamemode);
                worldGameMode.setText(WorldListUtil.getWorldGamemodeText(activity, world));

                TextView lastPlayed = (TextView) rootView.findViewById(R.id.detail_last_played);
                lastPlayed.setText(WorldListUtil.getLastPlayedText(activity, world));

                TextView worldSeed = (TextView) rootView.findViewById(R.id.detail_world_seed);
                worldSeed.setText(String.valueOf(world.getWorldSeed()));

                TextView worldPath = (TextView) rootView.findViewById(R.id.detail_world_path);
                worldPath.setText(world.levelFile.getAbsolutePath());
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        FloatingActionButton fabOpenWorld = (FloatingActionButton) rootView.findViewById(R.id.fab_open_world);
        assert fabOpenWorld != null;
        fabOpenWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.loading_world, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, WorldActivity.class);
                intent.putExtra(World.ARG_WORLD_SERIALIZED, world);

                context.startActivity(intent);
            }
        });

        // Debug button to write small worlds in an easily readable format
        // WARNING: DO NOT USE ON LARGE WORLDS (The debug output will get way larger than the world!)
        /*
        FloatingActionButton fabDevMode = (FloatingActionButton) rootView.findViewById(R.id.fab_dev_mode);
        assert fabDevMode != null;
        fabDevMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Outputting world...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                WorldData wData = world.getWorldData();



                File outputFile = new File(world.worldFolder, "debug_hex_out.txt");

                try {

                    wData.load();
                    wData.openDB();
                    Iterator it = wData.db.iterator();

                    BufferedWriter buf = new BufferedWriter(new FileWriter(outputFile, false), 1024);

                    for(it.seekToFirst(); it.isValid(); it.next()){
                        byte[] key = it.getKey();
                        byte[] value = it.getValue();

                        buf.newLine();
                        buf.newLine();
                        buf.write("========================");
                        buf.newLine();
                        buf.write("key: " + new String(key));
                        buf.newLine();
                        buf.write("key in Hex: " + WorldData.bytesToHex(key, 0, key.length));
                        buf.newLine();
                        buf.write("------------------------");
                        buf.newLine();
                        for(int d =0; d < value.length; d += 256){
                            buf.write(WorldData.bytesToHex(value, d, Math.min(d + 256, value.length)));
                            buf.newLine();
                        }

                    }

                    buf.close();

                    it.close();
                    wData.closeDB();

                }
                catch (Exception e) {
                    Log.e("Failed writing debug file to "+outputFile.getAbsolutePath()+"\n" + e.toString());
                }

                Snackbar.make(view, "Done outputting world!!! Path: "+outputFile.getAbsolutePath(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/

        return rootView;
    }
}

