package com.mithrilmania.blocktopograph.map.locator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.databinding.FragMapGotoBinding;
import com.mithrilmania.blocktopograph.map.FloatPaneFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public final class AdvancedLocatorFragment extends FloatPaneFragment {

    public static final String PREF_KEY_LOCATOR_PAGE = "locator_page";
    private World mWorld;
    private LocatorPageFragment.CameraMoveCallback mCameraMoveCallback;
    private LocatorPagerAdapter mAdapter;

    public static AdvancedLocatorFragment create(World world, LocatorPageFragment.CameraMoveCallback cameraMoveCallback) {
        AdvancedLocatorFragment ret = new AdvancedLocatorFragment();
        ret.mWorld = world;
        ret.mCameraMoveCallback = cameraMoveCallback;
        return ret;
    }

    @NotNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragMapGotoBinding binding = DataBindingUtil.inflate(inflater, R.layout.frag_map_goto, container, false);
        mAdapter = new LocatorPagerAdapter(getChildFragmentManager(), this);
        binding.pager.setAdapter(mAdapter);
        binding.header.close.setOnClickListener(view -> {
            if (mOnCloseButtonClickListener != null)
                mOnCloseButtonClickListener.onCloseButtonClick();
        });
        Activity act = getActivity();
        assert act != null;
        int page = act.getPreferences(Context.MODE_PRIVATE).getInt(PREF_KEY_LOCATOR_PAGE, 1);
        if (page >= 0 && page < LocatorPagerAdapter.PAGES_COUNT)
            binding.pager.setCurrentItem(page);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Activity activity = getActivity();
                if (activity == null) return;
                activity.getPreferences(Context.MODE_PRIVATE)
                        .edit()
                        .putInt(PREF_KEY_LOCATOR_PAGE, i)
                        .apply();
                mAdapter.doOverScroll(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        return binding.getRoot();
    }

    public static class LocatorPagerAdapter extends FragmentPagerAdapter {

        static final int PAGES_COUNT = 3;

        private LocatorPlayersFragment locatorPlayersFragment;
        private LocatorMarkersFragment locatorMarkersFragment;
        private LocatorCoordFragment locatorCoordFragment;
        private WeakReference<AdvancedLocatorFragment> owner;

        LocatorPagerAdapter(FragmentManager fm, AdvancedLocatorFragment owner) {
            super(fm);
            this.owner = new WeakReference<>(owner);
        }

        @NonNull
        private LocatorPlayersFragment getLocatorPlayersFragment(
                @NonNull AdvancedLocatorFragment owner) {
            if (locatorPlayersFragment == null) {
                locatorPlayersFragment = LocatorPlayersFragment.create(owner.mWorld);
                locatorPlayersFragment.mCameraMoveCallback = owner.mCameraMoveCallback;
            }
            return locatorPlayersFragment;
        }

        @NonNull
        private LocatorMarkersFragment getLocatorMarkersFragment(
                @NonNull AdvancedLocatorFragment owner) {
            if (locatorMarkersFragment == null) {
                locatorMarkersFragment = LocatorMarkersFragment.create(owner.mWorld);
                locatorMarkersFragment.mCameraMoveCallback = owner.mCameraMoveCallback;
            }
            return locatorMarkersFragment;
        }

        @NonNull
        private LocatorCoordFragment getLocatorCoordFragment(
                @NonNull AdvancedLocatorFragment owner) {
            if (locatorCoordFragment == null) {
                locatorCoordFragment = LocatorCoordFragment.create();
                locatorCoordFragment.mCameraMoveCallback = owner.mCameraMoveCallback;
            }
            return locatorCoordFragment;
        }

        void doOverScroll(int i) {
            if (i == 0 && locatorCoordFragment != null)
                locatorCoordFragment.doOverScroll();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            AdvancedLocatorFragment owner = this.owner.get();
            if (owner == null) return null;
            switch (position) {
                case 1:
                    return owner.getString(R.string.locator_page_player);
                case 2:
                    return owner.getString(R.string.locator_page_marker);
                case 0:
                    return owner.getString(R.string.locator_page_coor);
                default:
                    return null;
            }
        }

        @NotNull
        private Fragment getAnyPlaceHolderFragment() {
            if (locatorPlayersFragment != null) return locatorPlayersFragment;
            if (locatorMarkersFragment != null) return locatorMarkersFragment;
            if (locatorCoordFragment != null) return locatorCoordFragment;
            throw new RuntimeException();
        }

        @Override
        @NotNull
        public Fragment getItem(int i) {
            AdvancedLocatorFragment owner = this.owner.get();
            if (owner == null) {
                // Try not trigger a crash.
                return getAnyPlaceHolderFragment();
            }
            switch (i) {
                case 1:
                    return getLocatorPlayersFragment(owner);
                case 2:
                    return getLocatorMarkersFragment(owner);
                case 0:
                    return getLocatorCoordFragment(owner);
                default:
                    return getAnyPlaceHolderFragment();
            }
        }

        @Override
        public int getCount() {
            return PAGES_COUNT;
        }
    }
}
