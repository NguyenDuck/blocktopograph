package com.mithrilmania.blocktopograph.map;

import androidx.fragment.app.Fragment;

public abstract class FloatPaneFragment extends Fragment {

    protected FloatPaneFragment.OnCloseButtonClickListener mOnCloseButtonClickListener;

    public void setOnCloseButtonClickListener(OnCloseButtonClickListener onCloseButtonClickListener) {
        mOnCloseButtonClickListener = onCloseButtonClickListener;
    }

    public interface OnCloseButtonClickListener {
        void onCloseButtonClick();
    }
}
