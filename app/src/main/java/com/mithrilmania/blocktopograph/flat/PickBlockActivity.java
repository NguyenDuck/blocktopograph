package com.mithrilmania.blocktopograph.flat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.Streams;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.block.BlockTemplate;
import com.mithrilmania.blocktopograph.block.BlockTemplates;
import com.mithrilmania.blocktopograph.databinding.DialogPickBlockBinding;
import com.mithrilmania.blocktopograph.databinding.ItemPickBlockBinding;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PickBlockActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_BLOCK = "block";
    private DialogPickBlockBinding mBinding;
    private LinearLayoutManager mListManager;
    private MeowAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.dialog_pick_block);

        RecyclerView list = mBinding.list;
        mListManager = new LinearLayoutManager(this);
        list.setLayoutManager(mListManager);
        mAdapter = new MeowAdapter();
        list.setAdapter(mAdapter);
        setResult(RESULT_CANCELED);

        mBinding.text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                new UpdateListTask(PickBlockActivity.this).execute();
            }
        });

        new UpdateListTask(PickBlockActivity.this).execute();
    }

    private static class UpdateListTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<PickBlockActivity> thiz;
        private String keyword;
        private int index1, index2;

        private UpdateListTask(PickBlockActivity thiz) {
            this.thiz = new WeakReference<>(thiz);
        }

        @Override
        protected void onPreExecute() {
            PickBlockActivity activity = thiz.get();
            if (activity == null) return;
            keyword = activity.mBinding.text.getText().toString();

            //Save for restoring.
            index1 = activity.mListManager.findFirstVisibleItemPosition();
            index2 = activity.mListManager.findLastVisibleItemPosition();
            if (index2 <= index1) index2 = index1;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PickBlockActivity activity = thiz.get();
            if (activity == null) return null;
            List<BlockTemplate> list = activity.mAdapter.getListControl();

            //Backup all candidates.
            BlockTemplate[] olds = null;
            if (index1 >= 0) {
                olds = new BlockTemplate[index2 - index1 + 1];
                for (int i = index1, limit = list.size(); i < olds.length && i < limit; i++) {
                    olds[i] = list.get(i);
                }
            }
            //Reset.
            list.clear();

            //Restore.
            String text = keyword;
            int num;
            try {
                num = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                num = -1;
            }
            for (Iterator<BlockTemplate> it = BlockTemplates.getAll().iterator(); it.hasNext(); ) {
                BlockTemplate b = it.next();
                if (
                        (b.getBlock().getName().contains(text)))
                    list.add(b);
            }
            int position = -1;
            if (olds != null) for (BlockTemplate b : olds) {
                int i = list.indexOf(b);
                if (i != -1) {
                    position = i;
                    break;
                }
            }
            index1 = position;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PickBlockActivity activity = thiz.get();
            if (activity == null) return;
            activity.mAdapter.notifyDataSetChanged();
            if (index1 >= 0) activity.mListManager.scrollToPosition(index1);
        }
    }

    private class MeowAdapter extends RecyclerView.Adapter<MeowAdapter.MeowHolder> {

        private final List<BlockTemplate> mBlocks;

        private MeowAdapter() {
            mBlocks = new ArrayList<>(512);
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemPickBlockBinding binding = DataBindingUtil.inflate(
                    getLayoutInflater(), R.layout.item_pick_block, mBinding.list, false);
            View root = binding.getRoot();
            MeowHolder holder = new MeowHolder(root);
            holder.binding = binding;
            root.setOnClickListener(v -> {
                //UiUtil.toast(PickBlockActivity.this,""+i);
                setResult(RESULT_OK, new Intent()
                        .putExtra(EXTRA_KEY_BLOCK, holder.binding.getOldBlock()));//;(KnownBlockRepr) v.getTag()));
                finish();
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder meowHolder, int i) {
            BlockTemplate oldBlock = mBlocks.get(i);
            ItemPickBlockBinding binding = meowHolder.binding;
            binding.setOldBlock(oldBlock);
            //binding.getRoot().setTag(oldBlock);
            binding.icon.setImageBitmap(oldBlock.getIcon().getIcon(PickBlockActivity.this));
            UiUtil.blendBlockColor(binding.getRoot(), oldBlock);
        }

        @Override
        public int getItemCount() {
            return mBlocks.size();
        }

        List<BlockTemplate> getListControl() {
            return mBlocks;
        }

        class MeowHolder extends RecyclerView.ViewHolder {

            ItemPickBlockBinding binding;

            MeowHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

    }
}
