package com.mithrilmania.blocktopograph.map.edit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.jbvincey.nestedradiobutton.NestedRadioGroupManager;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockRegistry;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.block.ListingBlock;
import com.mithrilmania.blocktopograph.databinding.FragSerachAndReplaceBinding;
import com.mithrilmania.blocktopograph.databinding.IncludeBlockBinding;
import com.mithrilmania.blocktopograph.flat.PickBlockActivity;
import com.mithrilmania.blocktopograph.map.selection.SelectionMenuFragment;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.io.Serializable;

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
    private BlockRegistry registry;

    public static SearchAndReplaceFragment newInstance(BlockRegistry registry, SelectionMenuFragment.EditFunctionEntry entry) {
        SearchAndReplaceFragment fragment = new SearchAndReplaceFragment();
        fragment.mEntry = entry;
        fragment.registry = registry;
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
            //mBinding.cbIgsub.setChecked(cfg.ignoreSubId);
        } else {
            setBlockToItem(mBinding.searchBlockAny, ListingBlock.B_2_GRASS);
            setBlockToItem(mBinding.searchBlockBg, ListingBlock.B_0_AIR);
            setBlockToItem(mBinding.searchBlockFg, ListingBlock.B_2_GRASS);
            setBlockToItem(mBinding.replaceBlockAny, ListingBlock.B_20_GLASS);
            setBlockToItem(mBinding.replaceBlockFg, ListingBlock.B_20_GLASS);
            setBlockToItem(mBinding.replaceBlockBg, ListingBlock.B_9_WATER);
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

    private void onClickHelpMain(@NonNull View view) {
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

    private void recoverBlock(@NonNull IncludeBlockBinding item, @Nullable Serializable data) {
        setBlockToItem(item, data instanceof ListingBlock ? (ListingBlock) data : ListingBlock.B_0_AIR);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        writeToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    private Block convertListingBlockToBlock(ListingBlock listingBlock) {
        if (listingBlock == null) return registry.createBlock(KnownBlockRepr.B_0_0_AIR);
        return registry.createBlock(listingBlock.getIdentifier());
    }

    private void writeToBundle(@NonNull Bundle bundle) {
        SnrConfig cfg = new SnrConfig();
        cfg.searchMode = getSearchInCode();
        cfg.placeMode = getPlaceInCode();
        switch (cfg.searchMode) {
            case 1:
            case 2:
            case 3:
                cfg.searchBlockMain = new SnrConfig.SearchConditionBlock(mBinding.searchBlockAny.getBlock());
                break;
            case 4:
                cfg.searchBlockMain = new SnrConfig.SearchConditionBlock(mBinding.searchBlockFg.getBlock());
                cfg.searchBlockSub = new SnrConfig.SearchConditionBlock(mBinding.searchBlockBg.getBlock());
                break;
        }
        switch (cfg.placeMode) {
            case 1:
            case 2:
                cfg.placeBlockMain = convertListingBlockToBlock(mBinding.replaceBlockAny.getBlock());
                break;
            case 3:
                cfg.placeBlockMain = convertListingBlockToBlock(mBinding.replaceBlockFg.getBlock());
                cfg.placeBlockSub = convertListingBlockToBlock(mBinding.replaceBlockBg.getBlock());
                break;
        }
        cfg.ignoreSubId = true;// mBinding.cbIgsub.isChecked();
        bundle.putSerializable(CONFIG, cfg);
    }

    private void onBlockItemClick(@NonNull View view) {
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
            ListingBlock block = (ListingBlock) data.getSerializableExtra(PickBlockActivity.EXTRA_KEY_BLOCK);
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

    private void setBlockToItem(@NonNull IncludeBlockBinding item, @NonNull ListingBlock block) {
        item.icon.setImageBitmap(block.getIcon(getResources().getAssets()));
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

    private void onCheckedChanged(@NonNull NestedRadioGroupManager group, @IdRes int checkedId) {
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
