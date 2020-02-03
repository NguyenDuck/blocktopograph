package com.mithrilmania.blocktopograph.map.edit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.mithrilmania.blocktopograph.BiomeSelectDialog;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragChBiomeBinding;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.map.selection.SelectionMenuFragment;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.io.Serializable;

import static android.app.Activity.RESULT_OK;

public final class ChBiomeFragment extends DialogFragment {

    public static final String KEY_FROM = "from";
    public static final String KEY_TO = "to";
    private static final int REQUEST_CODE_FOR = 2012;
    private static final int REQUEST_CODE_TO = 2013;
    private FragChBiomeBinding mBinding;
    private SelectionMenuFragment.EditFunctionEntry mEntry;

    public static ChBiomeFragment newInstance(SelectionMenuFragment.EditFunctionEntry entry) {
        ChBiomeFragment fragment = new ChBiomeFragment();
        fragment.mEntry = entry;
        return fragment;
    }

    private void onChangeForCheckedChanged(@NonNull RadioGroup group, @IdRes int checkedId) {
        mBinding.biomeView.getRoot().setVisibility(
                checkedId == R.id.biome_for_specific ? View.VISIBLE : View.GONE);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_ch_biome, container, false);
        mBinding.changeFor.setOnCheckedChangeListener(this::onChangeForCheckedChanged);
        mBinding.biomeView.getRoot().setOnClickListener(v ->
                startActivityForResult(new Intent(getActivity(), BiomeSelectDialog.class), REQUEST_CODE_FOR));
        mBinding.biomeReplace.getRoot().setOnClickListener(v ->
                startActivityForResult(new Intent(getActivity(), BiomeSelectDialog.class), REQUEST_CODE_TO));
        UiUtil.blendBlockColor(mBinding.biomeView.getRoot(), Biome.PLAINS);
        mBinding.biomeView.setBiome(Biome.PLAINS);
        mBinding.biomeView.getRoot().setVisibility(View.GONE);
        UiUtil.blendBlockColor(mBinding.biomeReplace.getRoot(), Biome.JUNGLE);
        mBinding.biomeReplace.setBiome(Biome.JUNGLE);
        View root = mBinding.getRoot();
        Dialog dialog = getDialog();
        if (dialog instanceof AlertDialog) {
            ((AlertDialog) dialog).setView(root);
        }
        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = requireContext();
        return new AlertDialog.Builder(context)
                .setTitle(R.string.map_edit_func_chbiome)
                .setPositiveButton(android.R.string.ok, this::onClickOk)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FOR:
            case REQUEST_CODE_TO:
                if (resultCode == RESULT_OK && data != null) {
                    Serializable ser = data.getSerializableExtra(BiomeSelectDialog.KEY_BIOME);
                    if (ser instanceof Biome) {
                        Biome biome = (Biome) ser;
                        switch (requestCode) {
                            case REQUEST_CODE_FOR:
                                UiUtil.blendBlockColor(mBinding.biomeView.getRoot(), biome);
                                mBinding.biomeView.setBiome(biome);
                                break;
                            case REQUEST_CODE_TO:
                                UiUtil.blendBlockColor(mBinding.biomeReplace.getRoot(), biome);
                                mBinding.biomeReplace.setBiome(biome);
                                break;
                        }
                    }
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onClickOk(DialogInterface dia, int i) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FROM,
                mBinding.changeFor.getCheckedRadioButtonId() == R.id.biome_for_all ?
                        null : mBinding.biomeView.getBiome());
        Biome biomeTo = mBinding.biomeReplace.getBiome();
        bundle.putSerializable(KEY_TO, biomeTo == null ? Biome.THE_VOID : biomeTo);
        mEntry.invokeEditFunction(EditFunction.CHBIOME, bundle);
    }
}
