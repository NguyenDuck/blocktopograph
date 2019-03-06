package com.mithrilmania.blocktopograph.map.locator;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragLocatorCoordBinding;
import com.mithrilmania.blocktopograph.util.UiUtil;

import org.jetbrains.annotations.NotNull;

public final class LocatorCoordFragment extends LocatorPageFragment {

    private FragLocatorCoordBinding mBinding;

    public static LocatorCoordFragment create() {
        return new LocatorCoordFragment();
    }

    public void onClickGo(View view) {
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
        return mBinding.getRoot();
    }

}
