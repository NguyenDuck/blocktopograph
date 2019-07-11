package com.mithrilmania.blocktopograph.worldlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldActivity;
import com.mithrilmania.blocktopograph.databinding.WorlditemDetailBinding;
import com.mithrilmania.blocktopograph.test.MainTestActivity;
import com.mithrilmania.blocktopograph.util.IoUtil;

import java.util.Arrays;

/**
 * A fragment representing a single WorldItem detail screen.
 * This fragment is either contained in a {@link WorldItemListActivity}
 * in two-pane mode (on tablets) or a {@link WorldItemDetailActivity}
 * on handsets.
 */
public class WorldItemDetailFragment extends Fragment implements View.OnClickListener {

    /**
     * The dummy content this fragment is presenting.
     */
    private static final byte[] SEQ_TEST = {0, 1, 1, 0};

    private World mWorld;
    private byte[] mSequence;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorldItemDetailFragment() {
    }

//    private String getDate(long time) {
//        Calendar cal = Calendar.getInstance();
//        TimeZone tz = cal.getTimeZone();//get your local time zone.
//        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.full_date_format), Locale.ENGLISH);
//        sdf.setTimeZone(tz);//set time zone.
//        return sdf.format(new Date(time * 1000));
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WorlditemDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.worlditem_detail, container, false);

        Activity activity = this.getActivity();
        assert activity != null;

        String barTitle;
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey(World.ARG_WORLD_SERIALIZED)) {
            Snackbar.make(binding.worlditemDetail,
                    R.string.error_could_not_open_world_details_lost_track_world, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            barTitle = activity.getString(R.string.error_could_not_open_world);
        } else {
            mWorld = (World) arguments.getSerializable(World.ARG_WORLD_SERIALIZED);
            barTitle = mWorld == null ? activity.getString(R.string.error_could_not_open_world) : mWorld.getWorldDisplayName();
        }

        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(barTitle);
        }

        try {
            if (mWorld != null && mWorld.getLevel() != null) {
                binding.setName(mWorld.getWorldDisplayName());
                binding.setSize(IoUtil.getFileSizeInText(mWorld.worldFolder));
                binding.setMode(WorldListUtil.getWorldGamemodeText(activity, mWorld));
                binding.setTime(WorldListUtil.getLastPlayedText(activity, mWorld));
                binding.setSeed(String.valueOf(mWorld.getWorldSeed()));
                binding.setPath(mWorld.levelFile.getAbsolutePath());
            }
        } catch (Exception e) {
            Log.d(this, e);
        }

        binding.fabOpenWorld.setOnClickListener(view -> {
            Snackbar.make(view, R.string.loading_world, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            Context context = view.getContext();
            Intent intent = new Intent(context, WorldActivity.class);
            intent.putExtra(World.ARG_WORLD_SERIALIZED, mWorld);

            context.startActivity(intent);
        });

        mSequence = new byte[]{-1, -1, -1, -1};
        binding.buttonLeft.setOnClickListener(this);
        binding.buttonRight.setOnClickListener(this);

        // Debug button to write small worlds in an easily readable format
        // WARNING: DO NOT USE ON LARGE WORLDS (The debug output will get way larger than the mWorld!)
        /*
        FloatingActionButton fabDevMode = (FloatingActionButton) rootView.findViewById(R.id.fab_dev_mode);
        assert fabDevMode != null;
        fabDevMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Outputting mWorld...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                WorldData wData = mWorld.getWorldData();



                File outputFile = new File(mWorld.worldFolder, "debug_hex_out.txt");

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

                Snackbar.make(view, "Done outputting mWorld!!! Path: "+outputFile.getAbsolutePath(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/

        return binding.getRoot();
    }

    private void sequence(byte code) {
        int pos = mSequence.length - 1;
        System.arraycopy(mSequence, 1, mSequence, 0, pos);
        mSequence[pos] = code;
        if (Arrays.equals(mSequence, SEQ_TEST)) {
            startActivity(
                    new Intent(getActivity(), MainTestActivity.class)
                            .putExtra(World.ARG_WORLD_SERIALIZED, mWorld)
            );
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left:
                sequence((byte) 0);
                break;
            case R.id.button_right:
                sequence((byte) 1);
                break;
        }
    }
}

