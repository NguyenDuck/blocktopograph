package io.vn.nguyenduck.blocktopograph.activity.navigation;

import static io.vn.nguyenduck.blocktopograph.Constants.WORLDS_FOLDER;
import static io.vn.nguyenduck.blocktopograph.utils.Utils.buildMinecraftDataDir;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.piegames.nbt.CompoundTag;
import io.vn.nguyenduck.blocktopograph.R;
import io.vn.nguyenduck.blocktopograph.world.WorldPreLoader;

public class WorldListFragment extends Fragment {

    private static final String[] WORLD_PATHS = new String[]{
            buildMinecraftDataDir(Environment.getExternalStorageDirectory().getPath(), WORLDS_FOLDER)
    };

    private static final Map<String, WorldPreLoader> WORLDS = new HashMap<>();
    private static final ArrayList<String> WORLD_PATH = new ArrayList<>();
    private static final WorldListAdapter ADAPTER = new WorldListAdapter();

    private static ExecutorService EXECUTOR_SERVICE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EXECUTOR_SERVICE = Executors.newWorkStealingPool();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.world_list_fragment, container, false);
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.setOnClickListener(View::requestFocus);
        RecyclerView recyclerView = v.findViewById(R.id.world_list);
        recyclerView.setAdapter(ADAPTER);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        EXECUTOR_SERVICE.submit(this::loadWorlds);
    }

    private void loadWorlds() {
        WORLD_PATH.clear();
        for (String path : WORLD_PATHS) {
            for (File file : Objects.requireNonNull(new File(path).listFiles())) {
                if (!file.isDirectory()) continue;
                String p = file.getPath();
                WORLD_PATH.add(p);
                if (!WORLDS.containsKey(p)) WORLDS.put(p, new WorldPreLoader(p));
                else Objects.requireNonNull(WORLDS.get(p)).update();
            }
        }
    }

    private static class WorldListAdapter extends Adapter<ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.world_item, parent, false);
            return new ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WorldPreLoader world = WORLDS.get(WORLD_PATH.get(position));
            View view = getView(holder);

            assert world != null;
            CompoundTag data = (CompoundTag) world.getData();

            ImageView icon = view.findViewById(R.id.world_item_icon);
            Drawable iconDrawable = world.getIconDrawable();
            if (iconDrawable != null) icon.setImageDrawable(iconDrawable);
            else icon.setImageResource(R.drawable.world_preview_default);

            TextView name = view.findViewById(R.id.world_item_name);
            name.setText(world.getName());

            {
                TextView gamemode = view.findViewById(R.id.world_item_gamemode);
                Integer gamemodeResId = switch (data.getByteValue("ForceGameType").get()) {
                    case 1 -> R.string.gamemode_creative;
                    case 2 -> R.string.gamemode_adventure;
                    case 3 -> R.string.gamemode_spectator;
                    default -> R.string.gamemode_survival;
                };
                gamemode.setText(gamemodeResId);
            }
            {
                TextView experimental = view.findViewById(R.id.world_item_experimental);
                CompoundTag exp = data.getAsCompoundTag("experiments").get();
                if (exp.getValue().values().stream().allMatch(v -> (byte) v.getValue() == 0))
                    experimental.setVisibility(View.GONE);
            }
            {
                TextView lastPlay = view.findViewById(R.id.world_item_last_play);
                Long time = data.getLongValue("LastPlayed").get();
                DateFormat formater = SimpleDateFormat.getDateInstance(2);
                lastPlay.setText(formater.format(new Date(time * 1000)));
            }
//            BOGGER.info(data.toString());
        }

        private static @NonNull View getView(@NonNull ViewHolder holder) {
            View view = holder.itemView;

            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setOnFocusChangeListener((v, f) ->
                    v.findViewById(R.id.toolbar_under_world_item)
                            .setVisibility(f ? View.VISIBLE : View.GONE)
            );

            view.setOnClickListener(v -> {
                if (v.hasFocus()) {
                    v.clearFocus();
                    v.findViewById(R.id.toolbar_under_world_item).setVisibility(View.GONE);
                } else v.requestFocus();
            });
            return view;
        }

        @Override
        public int getItemCount() {
            return WORLDS.size();
        }
    }
}