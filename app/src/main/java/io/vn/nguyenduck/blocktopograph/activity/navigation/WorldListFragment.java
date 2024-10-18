package io.vn.nguyenduck.blocktopograph.activity.navigation;

import static io.vn.nguyenduck.blocktopograph.Constants.BOGGER;
import static io.vn.nguyenduck.blocktopograph.shell.Runner.isRooted;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.vn.nguyenduck.blocktopograph.R;

public class WorldListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.world_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BOGGER.info(String.valueOf(isRooted()));
    }
}