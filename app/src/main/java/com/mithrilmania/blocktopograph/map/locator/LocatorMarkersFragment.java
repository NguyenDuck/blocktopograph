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

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.databinding.FragLocatorMarkersBinding;
import com.mithrilmania.blocktopograph.databinding.ItemLocatorMarkerBinding;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Collection;

public final class LocatorMarkersFragment extends LocatorPageFragment {

    private FragLocatorMarkersBinding mBinding;
    private World mWorld;

    public static LocatorMarkersFragment create(World world) {
        LocatorMarkersFragment ret = new LocatorMarkersFragment();
        ret.mWorld = world;
        return ret;
    }

    @NotNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.frag_locator_markers, container, false);
        new LoadingTask(this).execute(mWorld);
        return mBinding.getRoot();
    }

    private static class MarkersAdapter extends RecyclerView.Adapter<MarkersAdapter.MeowHolder> {

        private final WeakReference<LocatorPageFragment> owner;
        private final AbstractMarker[] markers;

        MarkersAdapter(WeakReference<LocatorPageFragment> owner, AbstractMarker[] markers) {
            this.owner = owner;
            this.markers = markers;
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LocatorPageFragment owner = this.owner.get();
            assert owner != null;
            ItemLocatorMarkerBinding binding = DataBindingUtil.inflate(
                    owner.getLayoutInflater(), R.layout.item_locator_marker,
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
                assert worlds.length == 1;
                World world = worlds[0];
                assert world != null;
                Collection<AbstractMarker> markers = world.getMarkerManager().getMarkers();

                if (markers.isEmpty()) return null;
                return markers.toArray(new AbstractMarker[0]);

//                    markerDialogBuilder.setAdapter(
//                            arrayAdapter,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    AbstractMarker m = arrayAdapter.getItem(which);
//                                    if (m == null) return;
//
//                                    Snackbar.make(tileView,
//                                            activity.getString(R.string.something_at_xyz_dim_int,
//                                                    m.getNamedBitmapProvider().getBitmapDisplayName(),
//                                                    m.x, m.y, m.z, m.dimension.name),
//                                            Snackbar.LENGTH_SHORT)
//                                            .setAction("Action", null).show();
//
//                                    WorldActivityInterface worldProvider = MapFragment.this.worldProvider.get();
//                                    if (m.dimension != worldProvider.getDimension()) {
//                                        worldProvider.changeMapType(m.dimension.defaultMapType, m.dimension);
//                                    }
//
//                                    frameTo((double) m.x, (double) m.z);
//
//                                }
//                            });
//                }

            } catch (Exception e) {
                Log.d(this, e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(AbstractMarker[] markers) {
            LocatorMarkersFragment owner = this.owner.get();
            if (owner == null) return;
            if (markers == null) {
                owner.mBinding.recycleView.setVisibility(View.GONE);
            } else {
                owner.mBinding.recycleView.setLayoutManager(
                        new LinearLayoutManager(owner.getActivity()));
                owner.mBinding.recycleView.setAdapter(
                        new MarkersAdapter(new WeakReference<>(owner), markers));
                owner.mBinding.emptyView.setVisibility(View.GONE);
            }
        }
    }

}
