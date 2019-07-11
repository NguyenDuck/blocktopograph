package com.mithrilmania.blocktopograph.flat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragLayersBinding;
import com.mithrilmania.blocktopograph.databinding.ItemWorldLayerBinding;
import com.mithrilmania.blocktopograph.map.KnownBlock;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.woxthebox.draglistview.DragItemAdapter;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public final class EditFlatFragment extends Fragment {

    static final String EXTRA_KEY_LIST_INDEX = "index";
    static final String EXTRA_KEY_LIST_IS_ADD = "isAdd";
    static final String EXTRA_KEY_LIST_LAYER = "layer";
    static final String EXTRA_KEY_LIST_EXISTING_SUM = "existingSum";
    private static final int REQUEST_CODE_EDIT_LAYER = 2013;
    private FragLayersBinding mBinding;
    private MeowAdapter mMeowAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_layers, container, false);
        new LoadTask(this).execute();
        return mBinding.getRoot();
    }

    private void onClickAddOrEditLayer(int index, Layer layer, boolean isAdd, int existingSum) {
        Activity activity = getActivity();
        if (activity == null) return;
        startActivityForResult(
                new Intent(activity, EditLayerDialog.class)
                        .putExtra(EXTRA_KEY_LIST_INDEX, index)
                        .putExtra(EXTRA_KEY_LIST_LAYER, layer)
                        .putExtra(EXTRA_KEY_LIST_IS_ADD, isAdd)
                        .putExtra(EXTRA_KEY_LIST_EXISTING_SUM, existingSum),
                REQUEST_CODE_EDIT_LAYER
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT_LAYER) {
            if (resultCode != Activity.RESULT_OK) return;
            int index = data.getIntExtra(EXTRA_KEY_LIST_INDEX, 0);
            Serializable ser = data.getSerializableExtra(EXTRA_KEY_LIST_LAYER);
            Layer layer = (Layer) ser;
            if (data.getBooleanExtra(EXTRA_KEY_LIST_IS_ADD, true)) {
                mMeowAdapter.insert(index + 1, layer);
            } else {
                mMeowAdapter.change(index, layer);
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public List<Layer> getResultLayers() {
        return mMeowAdapter.getItemList();
    }

    private static class LoadTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<EditFlatFragment> thiz;
        private AlertDialog mWaitDialog;

        private LoadTask(EditFlatFragment thiz) {
            this.thiz = new WeakReference<>(thiz);
        }

        @Override
        protected void onPreExecute() {
            Activity activity = thiz.get().getActivity();
            if (activity == null) return;
            mWaitDialog = UiUtil.buildProgressWaitDialog(activity, 0, null);
            mWaitDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Activity activity = thiz.get().getActivity();
                if (activity == null) return null;
                KnownBlock.loadBitmaps(activity.getAssets());
            } catch (Exception e) {
                Log.d(this, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            EditFlatFragment thiz = this.thiz.get();
            //If the activity quit nothing needs to be done.
            if (thiz == null) return;
            try {
                DragListView listView = thiz.mBinding.list;
                thiz.mMeowAdapter = thiz.new MeowAdapter();
                listView.setLayoutManager(new LinearLayoutManager(thiz.getActivity()));
                listView.setCanDragHorizontally(false);
                listView.setAdapter(thiz.mMeowAdapter, false);
                listView.setSwipeListener(thiz.mMeowAdapter);
                thiz.mMeowAdapter.loadDefault();
            } catch (Exception e) {
                Log.d(this, e);
                Activity activity = thiz.getActivity();
                if (activity != null) UiUtil.toastError(activity);
            }
            mWaitDialog.dismiss();
        }
    }

    private class MeowAdapter extends DragItemAdapter<Layer, MeowAdapter.MeowHolder> implements ListSwipeHelper.OnSwipeListener {

        MeowAdapter() {
            setItemList(new LinkedList<>());
        }

        @Override
        public long getUniqueItemId(int i) {
            Layer layer = mItemList.get(i);
            //return (((long) i) << 32) | (layer.block.id << 16) | (layer.block.subId << 8) | layer.amount;
            return layer.uid;
        }

        void loadDefault() {
            Layer layer = new Layer(KnownBlock.B_7_0_BEDROCK, 1);
            addItem(0, layer);
            layer = new Layer(KnownBlock.B_3_0_DIRT, 29);
            addItem(0, layer);
            layer = new Layer(KnownBlock.B_2_0_GRASS, 1);
            addItem(0, layer);
            layer = new Layer(KnownBlock.B_31_2_TALLGRASS_GRASS, 1);
            addItem(0, layer);
        }

        void insert(int index, Layer layer) {
            mItemList.add(index, layer);
            notifyItemInserted(index);
        }

        void change(int index, Layer layer) {
            mItemList.set(index, layer);
            notifyItemChanged(index, layer);
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int in) {
            ItemWorldLayerBinding binding = DataBindingUtil.inflate(
                    getLayoutInflater(), R.layout.item_world_layer, mBinding.list, false);
            MeowHolder holder = new MeowHolder(binding);
            holder.binding.add.setOnClickListener(view -> {
                int sum = 0;//Existing height. Total height shall be less then 128 we need to ensure.
                for (Layer l : mItemList) {
                    sum += l.amount;
                }
                onClickAddOrEditLayer(holder.getLayoutPosition(), new Layer(), true, sum);
            });
            holder.binding.root.setOnClickListener(view -> {
                int index = holder.getLayoutPosition();
                int sum = 0;//Existing height. Total height shall be less then 128 we need to ensure.
                for (int i = 0, mItemListSize = mItemList.size(); i < mItemListSize; i++) {
                    if (i == index) continue;
                    Layer l = mItemList.get(i);
                    sum += l.amount;
                }
                onClickAddOrEditLayer(index, mItemList.get(index), false, sum);
            });
            holder.binding.root.setTag(new WeakReference<>(holder));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            Layer layer = mItemList.get(position);
            holder.binding.setLayer(layer);
            holder.binding.icon.setImageBitmap(layer.block.bitmap);
        }

        @Override
        public void onItemSwipeStarted(ListSwipeItem item) {
        }

        @Nullable
        private MeowHolder getHolderFromTag(@NonNull ListSwipeItem view) {
            Object tag = view.getTag();
            if (!(tag instanceof WeakReference)) return null;
            WeakReference ref = (WeakReference) tag;
            Object mho = ref.get();
            return (MeowHolder) mho;
        }

        @Override
        public void onItemSwipeEnded(ListSwipeItem item, ListSwipeItem.SwipeDirection swipedDirection) {
            if (swipedDirection != ListSwipeItem.SwipeDirection.LEFT) return;
            MeowHolder mh = getHolderFromTag(item);
            if (mh == null) return;
            int position = mh.getAdapterPosition();
            int size = mItemList.size();
            if (size > 1) {
                mItemList.remove(position);
                notifyItemRemoved(position);
            } else if (size == 1) {
                mItemList.set(0, new Layer());
                Snackbar.make(mBinding.list, R.string.edit_flat_atleast, Snackbar.LENGTH_SHORT).show();
                notifyItemChanged(0);
            }
        }

        @Override
        public void onItemSwiping(ListSwipeItem item, float swipedDistanceX) {
            float alpha = -swipedDistanceX / item.getMeasuredWidth() * 1.2f;
            MeowHolder mh = getHolderFromTag(item);
            if (mh == null) return;
            mh.binding.itemRight.setAlpha(alpha);
        }

        private class MeowHolder extends DragItemAdapter.ViewHolder {

            ItemWorldLayerBinding binding;

            MeowHolder(@NonNull ItemWorldLayerBinding binding) {
                super(binding.getRoot(), R.id.icon, false);
                this.binding = binding;
                binding.root.setSupportedSwipeDirection(ListSwipeItem.SwipeDirection.LEFT);
            }
        }
    }
}
