package io.vn.nguyenduck.blocktopograph.activity.navigation;

import static io.vn.nguyenduck.blocktopograph.Constants.MINECRAFT_APP_ID;
import static io.vn.nguyenduck.blocktopograph.Constants.WORLDS_FOLDER;
import static io.vn.nguyenduck.blocktopograph.utils.Utils.buildAndroidDataDir;
import static io.vn.nguyenduck.blocktopograph.utils.Utils.buildMinecraftDataDir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.vn.nguyenduck.blocktopograph.R;
import io.vn.nguyenduck.blocktopograph.world.WorldPreLoader;

public class WorldListFragment extends Fragment {

    private static final String[] WORLD_PATHS = new String[]{
            buildMinecraftDataDir(buildAndroidDataDir(MINECRAFT_APP_ID), WORLDS_FOLDER)
    };

    private static final Map<String, WorldPreLoader> WORLDS = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.world_list_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (String path : WORLD_PATHS) {
            if (!WORLDS.containsKey(path)) WORLDS.put(path, new WorldPreLoader(path));
            else Objects.requireNonNull(WORLDS.get(path)).update();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView world_list = requireActivity().findViewById(R.id.world_list);
        world_list.setAdapter(new WorldListAdapter());
    }

    private static class WorldListAdapter extends RecyclerView.Adapter<WorldListAdapter.ViewHolder> {

        @NonNull
        @Override
        public WorldListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull WorldListAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return WORLDS.size();
        }

        private static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}