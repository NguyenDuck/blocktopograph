package com.mithrilmania.blocktopograph.map.locator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragLocatorCoordBinding;
import com.mithrilmania.blocktopograph.util.UiUtil;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public final class LocatorCoordFragment extends LocatorPageFragment {

    private FragLocatorCoordBinding mBinding;

    @NotNull
    @Contract(" -> new")
    public static LocatorCoordFragment create() {
        return new LocatorCoordFragment();
    }

    private void onClickGo(View view) {
        if (mCameraMoveCallback != null) {
            int x, z;
            x = UiUtil.readIntFromView(mBinding.editX);
            z = UiUtil.readIntFromView(mBinding.editZ);
            mCameraMoveCallback.moveCamera(x, z);
        }
    }

    @NotNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.frag_locator_coord, container, false);
        mBinding.buttonGo.setOnClickListener(this::onClickGo);
        mBinding.editZ.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == 233 && keyEvent != null) {
                onClickGo(null);
                return true;
            }
            return false;
        });
        return mBinding.getRoot();
    }

    public void doOverScroll() {
        mBinding.scroll.doOverScroll();
    }

}
