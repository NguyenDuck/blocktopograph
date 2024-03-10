package io.vn.nguyenduck.blocktopograph;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import static io.vn.nguyenduck.blocktopograph.Constants.COM_MOJANG_FOLDER;
import static io.vn.nguyenduck.blocktopograph.Constants.WORLDS_FOLDER;
import static io.vn.nguyenduck.blocktopograph.TranslationUtils.translateGamemode;

public class WorldListAdapter extends RecyclerView.Adapter<WorldListAdapter.WorldItem> {
    private DocumentFile rootFolder;

    private final ArrayList<WorldLevelData> worldLevelData = new ArrayList<>();

    public static class WorldItem extends RecyclerView.ViewHolder {
        public final View view;
        public final ImageView icon;
        public final TextView name;
        public final TextView gamemode;
        public final TextView experimental;
        public final TextView last_play;
        public final TextView size;

        public WorldItem(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            icon = view.findViewById(R.id.world_item_icon);
            name = view.findViewById(R.id.world_item_name);
            gamemode = view.findViewById(R.id.world_item_gamemode);
            experimental = view.findViewById(R.id.world_item_experimental);
            last_play = view.findViewById(R.id.world_item_last_play);
            size = view.findViewById(R.id.world_item_size);
        }
    }

    @NonNull
    @Override
    public WorldItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.world_item, viewGroup, false);
        return new WorldItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldItem viewHolder, int i) {
        WorldLevelData data = worldLevelData.get(i);
        Bundle bundle = data.dataBundle;

        String worldName = data.rawWorldName;

        viewHolder.name.setText(worldName);
        viewHolder.gamemode.setText(translateGamemode(bundle.getInt("GameType")));
        viewHolder.size.setText(data.worldSizeFormated);
        viewHolder.last_play.setText(
                DateFormat.getDateFormat(viewHolder.view.getContext())
                        .format(bundle.getLong("LastPlayed") * 1000));

        if (bundle.containsKey("experiments")) {
            Bundle experiments = bundle.getBundle("experiments");
            if (experiments != null) {
                viewHolder.experimental.setVisibility(experiments.getBoolean("experiments_ever_used")
                        ? View.VISIBLE : View.GONE);
            }
        }

        if (data.worldIconUri != null) {
            viewHolder.icon.setImageURI(data.worldIconUri);
        } else {
            viewHolder.icon.setImageResource(
                    viewHolder.experimental.getVisibility() == View.GONE ?
                            R.drawable.world_demo_screen_big :
                            R.drawable.world_demo_screen_big_grayscale
            );
        }
    }

    @Override
    public int getItemCount() {
        return worldLevelData.size();
    }

    public void initAdapter(DocumentFile appDataFolder) {
        initAdapter(appDataFolder, false);
    }

    public void initAdapter(DocumentFile appDataFolder, boolean force) {
        if (rootFolder == null || force) rootFolder = appDataFolder;
        worldLevelData.clear();
        DocumentFile f = DocumentUtils.getFileFromPath(rootFolder, new String[]{
                "files",
                "games",
                COM_MOJANG_FOLDER,
                WORLDS_FOLDER});
        if (f != null) {
            Arrays.stream(f.listFiles()).forEach(file -> worldLevelData.add(new WorldLevelData(file)));
            worldLevelData.sort((a, b) -> Math.toIntExact(b.dataBundle.getLong("LastPlayed") - a.dataBundle.getLong("LastPlayed")));
        }
    }
}
