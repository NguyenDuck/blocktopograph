package com.mithrilmania.blocktopograph.map.locator;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.databinding.FragLocatorPlayersBinding;
import com.mithrilmania.blocktopograph.databinding.ItemLocatorMarkerBinding;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;

import java.lang.ref.WeakReference;
import java.util.Collection;

public final class LocatorMarkersFragment extends LocatorPageFragment {

    private FragLocatorPlayersBinding mBinding;
    private World mWorld;

    public static LocatorMarkersFragment create(World world) {
        LocatorMarkersFragment ret = new LocatorMarkersFragment();
        ret.mWorld = world;
        return ret;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.frag_locator_players, container, false);
        new LoadingTask(this).execute(mWorld);
        return mBinding.getRoot();
    }

    private static class MarkersAdapter extends RecyclerView.Adapter<MarkersAdapter.MeowHolder> {

        @NonNull
        private final WeakReference<LocatorPageFragment> owner;

        @NonNull
        private final AbstractMarker[] markers;

        MarkersAdapter(@NonNull WeakReference<LocatorPageFragment> owner, @NonNull AbstractMarker[] markers) {
            this.owner = owner;
            this.markers = markers;
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LocatorPageFragment owner = this.owner.get();
            LayoutInflater inflater;
            if (owner != null) inflater = owner.getLayoutInflater();
            else inflater = LayoutInflater.from(viewGroup.getContext());

            ItemLocatorMarkerBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.item_locator_marker,
                    viewGroup, false
            );
            MeowHolder meowHolder = new MeowHolder(binding.getRoot());
            meowHolder.binding = binding;
            return meowHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder meowHolder, int i) {
            AbstractMarker marker = markers[i];
            meowHolder.binding.setMarker(marker);
        }

        @Override
        public int getItemCount() {
            return markers.length;
        }

        class MeowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ItemLocatorMarkerBinding binding;

            MeowHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                LocatorPageFragment owner = MarkersAdapter.this.owner.get();
                if (owner == null) return;
                if (owner.mCameraMoveCallback != null) {
                    int index = getAdapterPosition();
                    AbstractMarker marker = markers[index];
                    owner.mCameraMoveCallback.moveCamera(marker.x, marker.z);
                }
            }
        }

    }

    private static class LoadingTask extends AsyncTask<World, Void, AbstractMarker[]> {

        private final WeakReference<LocatorMarkersFragment> owner;

        private LoadingTask(LocatorMarkersFragment owner) {
            this.owner = new WeakReference<>(owner);
        }

        @Nullable
        @Override
        protected AbstractMarker[] doInBackground(World... worlds) {
            try {
                World world;
                if (worlds.length != 1 || (world = worlds[0]) == null) return null;

                Collection<AbstractMarker> markers;
                try {
                    markers = world.getMarkerManager().getMarkers();
                } catch (Exception e) {
                    Log.d(this, e);
                    return null;
                }

                if (markers == null) return null;
                if (markers.isEmpty()) return new AbstractMarker[0];
                try {
                    return markers.toArray(new AbstractMarker[0]);
                } catch (Exception e) {
                    Log.d(this, e);
                    return null;
                }

            } catch (Exception e) {
                Log.d(this, e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(AbstractMarker[] markers) {
            LocatorMarkersFragment owner = this.owner.get();
            Activity activity;
            if (owner == null || (activity = owner.getActivity()) == null) return;
            owner.mBinding.loading.setVisibility(View.GONE);
            if (markers == null) {
                owner.mBinding.empty.setVisibility(View.VISIBLE);
                owner.mBinding.empty.setText(R.string.general_failed);
            } else if (markers.length == 0) {
                owner.mBinding.empty.setVisibility(View.VISIBLE);
                owner.mBinding.empty.setText(R.string.no_custom_markers);
            } else {
                owner.mBinding.list.setLayoutManager(
                        new LinearLayoutManager(activity));
                owner.mBinding.list.setAdapter(
                        new MarkersAdapter(new WeakReference<>(owner), markers));
                owner.mBinding.list.setVisibility(View.VISIBLE);
            }
        }
    }

}
