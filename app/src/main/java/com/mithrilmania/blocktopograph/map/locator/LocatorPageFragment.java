package com.mithrilmania.blocktopograph.map.locator;

import androidx.fragment.app.Fragment;

public abstract class LocatorPageFragment extends Fragment {

    protected CameraMoveCallback mCameraMoveCallback;

    public interface CameraMoveCallback {

        void moveCamera(double x, double z);

    }
}
