package io.vn.nguyenduck.blocktopograph.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.vn.nguyenduck.blocktopograph.R;

public class Navigation extends LinearLayout {

    private final LinearLayout layout;
    private final LayoutInflater navigationInflater;
    private TextView selected;

    public Navigation(Context context) {
        super(context);
        layout = (LinearLayout) inflate(context, R.layout.navigation_tab, this);
        navigationInflater = LayoutInflater.from(layout.getContext());
    }

    public Navigation(LinearLayout l) {
        super(l.getContext());
        layout = l;
        navigationInflater = LayoutInflater.from(layout.getContext());
    }

    public TextView addTab(String text) {
        navigationInflater.inflate(R.layout.navigation_tab, layout);
        var index = layout.getChildCount() - 1;
        var tab = (TextView) layout.getChildAt(index);
        View.OnClickListener listener = v -> {
            if (selected != null) {
                selected.setTextColor(Color.parseColor("#478735"));
            }
            selected = (TextView) v;
            selected.setTextColor(Color.parseColor("#A1FF91"));
        };
        tab.setOnClickListener(listener);
        tab.setText(text);
        return tab;
    }

}