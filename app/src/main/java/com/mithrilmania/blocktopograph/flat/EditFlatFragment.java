package com.mithrilmania.blocktopograph.flat;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragLayersBinding;
import com.mithrilmania.blocktopograph.databinding.ItemWorldLayerBinding;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.woxthebox.draglistview.DragItemAdapter;
import com.woxthebox.draglistview.DragListView;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public final class EditFlatFragment extends Fragment {

    public static final int REQUEST_CODE_EDIT_LAYER = 2013;
    public static final String EXTRA_KEY_LIST_INDEX = "index";
    public static final String EXTRA_KEY_LIST_IS_ADD = "isAdd";
    public static final String EXTRA_KEY_LIST_LAYER = "layer";
    public static final String EXTRA_KEY_LIST_EXISTING_SUM = "existingSum";
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
        switch (requestCode) {
            case REQUEST_CODE_EDIT_LAYER: {
                if (resultCode != Activity.RESULT_OK) return;
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MeowAdapter extends DragItemAdapter<Layer, MeowAdapter.MeowHolder> {

        MeowAdapter() {
            setItemList(new LinkedList<>());
        }

        @Override
        public long getUniqueItemId(int i) {
            Layer layer = mItemList.get(i);
            return (layer.block.id << 16) | (layer.block.subId << 8) | layer.amount;
        }

        void loadDefault() {
            Layer layer = new Layer(Block.B_7_0_BEDROCK, 1);
            addItem(0, layer);
            layer = new Layer(Block.B_3_0_DIRT, 2);
            addItem(0, layer);
            layer = new Layer(Block.B_2_0_GRASS, 1);
            addItem(0, layer);
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemWorldLayerBinding binding = DataBindingUtil.inflate(
                    getLayoutInflater(), R.layout.item_world_layer, mBinding.list, false);
            return new MeowHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            Layer layer = mItemList.get(position);
            holder.binding.setLayer(layer);
            holder.binding.icon.setImageBitmap(layer.block.bitmap);
            holder.binding.add.setOnClickListener(view -> {
                int sum = 0;//Existing height. Total height shall be less then 128 we need to ensure.
                for (Layer l : mItemList) {
                    sum += l.amount;
                }
                onClickAddOrEditLayer(position, new Layer(), true, sum);
            });
        }

        private class MeowHolder extends DragItemAdapter.ViewHolder {

            ItemWorldLayerBinding binding;

            MeowHolder(@NonNull ItemWorldLayerBinding binding) {
                super(binding.getRoot(), R.id.icon, false);
                this.binding = binding;
            }
        }
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
            mWaitDialog = UiUtil.buildWaitDialog(activity);
            mWaitDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Activity activity = thiz.get().getActivity();
                if (activity == null) return null;
                Block.loadBitmaps(activity.getAssets());
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
                thiz.mMeowAdapter.loadDefault();
            } catch (Exception e) {
                Log.d(this, e);
                Activity activity = thiz.getActivity();
                if (activity != null) UiUtil.toastError(activity);
            }
            mWaitDialog.dismiss();
        }
    }
}
