package io.vn.nguyenduck.blocktopograph.activity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import io.vn.nguyenduck.blocktopograph.R;

@SuppressLint("ViewConstructor")
public class WorldItemList extends LinearLayout {

    private final ScrollView parent;
    private final LayoutInflater inflater;
    private final LinearLayout list;

    public WorldItemList(ScrollView parent) {
        super(parent.getContext());
        this.parent = parent;

        list = parent.findViewById(R.id.world_item_list);
        inflater = LayoutInflater.from(list.getContext());
    }

    public void addWorld(String path) {

    }

    public void addWorld(String name, String path) {
        inflater.inflate(R.layout.world_item, list);
        var v = list.getChildAt(list.getChildCount() - 1);
        var wname = (TextView) v.findViewById(R.id.world_item_name);
        wname.setText(name);

        var wgamemode = (TextView) v.findViewById(R.id.world_item_gamemode);
        wgamemode.setText(R.string.gamemode_creative);
    }
}