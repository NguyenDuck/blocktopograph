package com.mithrilmania.blocktopograph.map.locator;

import androidx.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.databinding.FragLocatorPlayersBinding;
import com.mithrilmania.blocktopograph.databinding.ItemLocatorPlayerBinding;
import com.mithrilmania.blocktopograph.map.Player;
import com.mithrilmania.blocktopograph.util.math.DimensionVector3;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public final class LocatorPlayersFragment extends LocatorPageFragment {

    private FragLocatorPlayersBinding mBinding;
    private World mWorld;

    public static LocatorPlayersFragment create(World world) {
        LocatorPlayersFragment ret = new LocatorPlayersFragment();
        ret.mWorld = world;
        return ret;
    }

    @NotNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.frag_locator_players, container, false);
        new LoadingTask(this).execute(mWorld);
        return mBinding.getRoot();
    }

    private static class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MeowHolder> {

        private final WeakReference<LocatorPageFragment> owner;
        private final Player[] players;

        PlayersAdapter(WeakReference<LocatorPageFragment> owner, Player[] players) {
            this.owner = owner;
            this.players = players;
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LocatorPageFragment owner = this.owner.get();
            assert owner != null;
            ItemLocatorPlayerBinding binding = DataBindingUtil.inflate(
                    owner.getLayoutInflater(), R.layout.item_locator_player,
                    viewGroup, false
            );
            MeowHolder meowHolder = new MeowHolder(binding.getRoot());
            meowHolder.binding = binding;
            return meowHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder meowHolder, int i) {
            Player player = players[i];
            meowHolder.binding.setPlayer(player);
        }

        @Override
        public int getItemCount() {
            return players.length;
        }

        class MeowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ItemLocatorPlayerBinding binding;

            MeowHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                LocatorPageFragment owner = PlayersAdapter.this.owner.get();
                if (owner == null) return;
                if (owner.mCameraMoveCallback != null) {
                    int index = getAdapterPosition();
                    DimensionVector3<Float> pos = players[index].getPosition();
                    owner.mCameraMoveCallback.moveCamera(pos.x, pos.z);
                }
            }
        }

    }

    private static class LoadingTask extends AsyncTask<World, Void, Player[]> {

        private final WeakReference<LocatorPlayersFragment> owner;

        private LoadingTask(LocatorPlayersFragment owner) {
            this.owner = new WeakReference<>(owner);
        }

        @Override
        protected Player[] doInBackground(World... worlds) {
            assert worlds.length == 1;
            World world = worlds[0];
            WorldData worldData = world.getWorldData();
            try {
                worldData.openDB();
            } catch (WorldData.WorldDBException e) {
                return null;
            }
            String[] mlst = worldData.getNetworkPlayerNames();
            Player[] players = new Player[mlst.length + 1];
            try {
                players[0] = Player.localPlayer();
                players[0].setPosition(world.getPlayerPos());
            } catch (Exception e) {
                return null;
            }
            for (int i = 0; i < mlst.length; i++) {
                Player player = Player.networkPlayer(mlst[i]);
                try {
                    player.setPosition(world.getMultiPlayerPos(mlst[i]));
                } catch (Exception ignore) {
                }
                players[i + 1] = player;
            }
            return players;
        }

        @Override
        protected void onPostExecute(Player[] players) {
            LocatorPlayersFragment owner = this.owner.get();
            if (owner == null) return;
            if (players == null) {
                owner.mBinding.recycleView.setVisibility(View.GONE);
            } else {
                owner.mBinding.recycleView.setLayoutManager(
                        new LinearLayoutManager(owner.getActivity()));
                owner.mBinding.recycleView.setAdapter(
                        new PlayersAdapter(new WeakReference<>(owner), players));
                owner.mBinding.emptyView.setVisibility(View.GONE);
            }
        }
    }

}
