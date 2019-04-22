package com.mithrilmania.blocktopograph.map.edit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbvincey.nestedradiobutton.NestedRadioGroupManager;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragSerachAndReplaceBinding;
import com.mithrilmania.blocktopograph.databinding.IncludeBlockBinding;
import com.mithrilmania.blocktopograph.flat.PickBlockActivity;
import com.mithrilmania.blocktopograph.map.KnownBlock;
import com.mithrilmania.blocktopograph.map.selection.SelectionMenuFragment;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;

public class SearchAndReplaceFragment extends DialogFragment {

    public static final String CONFIG = "config";
    private static final int REQUEST_CODE = 2012;
    private static final int[] REQ_OFFSET_IDS = {
            R.id.search_block_any, R.id.search_block_bg,
            R.id.search_block_fg, R.id.replace_block_any,
            R.id.replace_block_bg, R.id.replace_block_fg
    };
    private FragSerachAndReplaceBinding mBinding;
    private SelectionMenuFragment.EditFunctionEntry mEntry;
    private ToolTipsManager mToolTipsManager;

    public static SearchAndReplaceFragment newInstance(SelectionMenuFragment.EditFunctionEntry entry) {
        SearchAndReplaceFragment fragment = new SearchAndReplaceFragment();
        fragment.mEntry = entry;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_serach_and_replace, container, false);

        // Saved instance has highest priority to be recovered.
        Serializable ser;
        if (savedInstanceState != null && (ser = savedInstanceState.getSerializable(CONFIG)) instanceof SnrConfig) {
            SnrConfig cfg = (SnrConfig) ser;
            recoverSearchIn(cfg.searchMode);
            recoverPlaceIn(cfg.placeMode);
            switch (cfg.searchMode) {
                case 1:
                case 2:
                case 3:
                    recoverBlock(mBinding.searchBlockAny, cfg.searchBlockMain);
                    break;
                case 4:
                    recoverBlock(mBinding.searchBlockBg, cfg.searchBlockSub);
                    recoverBlock(mBinding.searchBlockFg, cfg.searchBlockMain);
                    break;
            }
            switch (cfg.placeMode) {
                case 1:
                case 2:
                    recoverBlock(mBinding.replaceBlockAny, cfg.placeBlockMain);
                    break;
                case 3:
                    recoverBlock(mBinding.replaceBlockBg, cfg.placeBlockSub);
                    recoverBlock(mBinding.replaceBlockFg, cfg.placeBlockMain);
            }
            mBinding.cbIgsub.setChecked(cfg.ignoreSubId);
        } else {
            setBlockToItem(mBinding.searchBlockAny, KnownBlock.B_2_0_GRASS);
            setBlockToItem(mBinding.searchBlockBg, KnownBlock.B_0_0_AIR);
            setBlockToItem(mBinding.searchBlockFg, KnownBlock.B_2_0_GRASS);
            setBlockToItem(mBinding.replaceBlockAny, KnownBlock.B_20_0_GLASS);
            setBlockToItem(mBinding.replaceBlockFg, KnownBlock.B_20_0_GLASS);
            setBlockToItem(mBinding.replaceBlockBg, KnownBlock.B_9_0_WATER);
        }

        mBinding.searchIn.setOnCheckedChangeListener(this::onCheckedChanged);
        mBinding.placeIn.setOnCheckedChangeListener(this::onCheckedChanged);
        mBinding.searchBlockAny.getRoot().setOnClickListener(this::onBlockItemClick);
        mBinding.searchBlockBg.getRoot().setOnClickListener(this::onBlockItemClick);
        mBinding.searchBlockFg.getRoot().setOnClickListener(this::onBlockItemClick);
        mBinding.replaceBlockAny.getRoot().setOnClickListener(this::onBlockItemClick);
        mBinding.replaceBlockBg.getRoot().setOnClickListener(this::onBlockItemClick);
        mBinding.replaceBlockFg.getRoot().setOnClickListener(this::onBlockItemClick);
        mBinding.ok.setOnClickListener(this::onClickOk);
        mBinding.help.setOnClickListener(this::onClickHelpMain);
        mToolTipsManager = new ToolTipsManager();
        View root = mBinding.getRoot();
        Dialog dialog = getDialog();
        if (dialog instanceof AlertDialog) {
            ((AlertDialog) dialog).setView(root);
        }
        return root;
    }

    private void onClickHelpMain(@NotNull View view) {
        if (mToolTipsManager.findAndDismiss(view)) return;
        ToolTip.Builder builder = new ToolTip.Builder(
                view.getContext(), view, mBinding.frameMain,
                getString(R.string.map_edit_snr_help_background), ToolTip.POSITION_BELOW)
                .setAlign(ToolTip.ALIGN_LEFT);
        mToolTipsManager.show(builder.build());
    }

    private int getSearchInCode() {
        if (mBinding.rbSearchBg.isChecked()) return 1;
        if (mBinding.rbSearchFg.isChecked()) return 2;
        if (mBinding.rbSearchOr.isChecked()) return 3;
        if (mBinding.rbSearchBoth.isChecked()) return 4;
        return 0;
    }

    private int getPlaceInCode() {
        if (mBinding.rbPlaceBg.isChecked()) return 1;
        if (mBinding.rbPlaceFg.isChecked()) return 2;
        if (mBinding.rbPlaceBoth.isChecked()) return 3;
        return 0;
    }

    private void recoverSearchIn(int code) {
        switch (code) {
            case 1:
                mBinding.rbSearchBg.setChecked(true);
                break;
            case 2:
                mBinding.rbSearchFg.setChecked(true);
                break;
            case 3:
                mBinding.rbSearchOr.setChecked(true);
                break;
            case 4:
                mBinding.rbSearchBoth.setChecked(true);
                break;
        }
    }

    private void recoverPlaceIn(int code) {
        switch (code) {
            case 1:
                mBinding.rbPlaceBg.setChecked(true);
                break;
            case 2:
                mBinding.rbPlaceFg.setChecked(true);
                break;
            case 3:
                mBinding.rbPlaceBoth.setChecked(true);
                break;
        }
    }

    private void recoverBlock(@NotNull IncludeBlockBinding item, @Nullable Serializable data) {
        setBlockToItem(item, data instanceof KnownBlock ? (KnownBlock) data : KnownBlock.B_0_0_AIR);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        writeToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    private void writeToBundle(@NotNull Bundle bundle) {
        SnrConfig cfg = new SnrConfig();
        cfg.searchMode = getSearchInCode();
        cfg.placeMode = getPlaceInCode();
        switch (cfg.searchMode) {
            case 1:
            case 2:
            case 3:
                cfg.searchBlockMain = mBinding.searchBlockAny.getBlock();
                break;
            case 4:
                cfg.searchBlockMain = mBinding.searchBlockFg.getBlock();
                cfg.searchBlockSub = mBinding.searchBlockBg.getBlock();
                break;
        }
        switch (cfg.placeMode) {
            case 1:
            case 2:
                cfg.placeBlockMain = mBinding.replaceBlockAny.getBlock();
                break;
            case 3:
                cfg.placeBlockMain = mBinding.replaceBlockFg.getBlock();
                cfg.placeBlockSub = mBinding.replaceBlockBg.getBlock();
                break;
        }
        cfg.ignoreSubId = mBinding.cbIgsub.isChecked();
        bundle.putSerializable(CONFIG, cfg);
    }

    private void onBlockItemClick(@NotNull View view) {
        int req = REQUEST_CODE;
        int id = view.getId();
        for (int i = 0; i < REQ_OFFSET_IDS.length; i++) {
            if (REQ_OFFSET_IDS[i] == id) {
                req += i;
                break;
            }
        }
        startActivityForResult(
                new Intent(view.getContext(), PickBlockActivity.class), req
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            KnownBlock block = (KnownBlock) data.getSerializableExtra(PickBlockActivity.EXTRA_KEY_BLOCK);
            switch (requestCode - REQUEST_CODE) {
                case 0:
                    setBlockToItem(mBinding.searchBlockAny, block);
                    break;
                case 1:
                    setBlockToItem(mBinding.searchBlockBg, block);
                    break;
                case 2:
                    setBlockToItem(mBinding.searchBlockFg, block);
                    break;
                case 3:
                    setBlockToItem(mBinding.replaceBlockAny, block);
                    break;
                case 4:
                    setBlockToItem(mBinding.replaceBlockBg, block);
                    break;
                case 5:
                    setBlockToItem(mBinding.replaceBlockFg, block);
                    break;
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setBlockToItem(@NotNull IncludeBlockBinding item, @NotNull KnownBlock block) {
        item.icon.setImageBitmap(block.getBitmap());
        UiUtil.blendBlockColor(item.getRoot(), block);
        item.setBlock(block);
    }

    private void onClickOk(View view) {
        Bundle bundle = new Bundle();
        writeToBundle(bundle);
        mEntry.invokeEditFunction(EditFunction.SNR, bundle);
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = requireContext();
        return new AlertDialog.Builder(context)
                .setTitle(R.string.map_edit_func_snr)
                .create();
    }

    private void onCheckedChanged(@NotNull NestedRadioGroupManager group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_search_both:
                mBinding.frameSearchTwo.setVisibility(View.VISIBLE);
                mBinding.frameSearchOne.setVisibility(View.GONE);
                break;
            case R.id.rb_search_bg:
            case R.id.rb_search_fg:
            case R.id.rb_search_or:
                mBinding.frameSearchTwo.setVisibility(View.GONE);
                mBinding.frameSearchOne.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_place_both:
                mBinding.framePlaceTwo.setVisibility(View.VISIBLE);
                mBinding.framePlaceOne.setVisibility(View.GONE);
                break;
            case R.id.rb_place_bg:
            case R.id.rb_place_fg:
                mBinding.framePlaceTwo.setVisibility(View.GONE);
                mBinding.framePlaceOne.setVisibility(View.VISIBLE);
                break;
        }
    }
}
