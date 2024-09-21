package io.vn.nguyenduck.blocktopograph.activity;

import android.annotation.LayoutRes;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.vn.nguyenduck.blocktopograph.R;

@SuppressLint("ViewConstructor")
public class Navigation extends LinearLayout {

    private final LinearLayout parent;
    private final LinearLayout content_container;
    private final LinearLayout navigation_container;

    private final ArrayList<View> content_list = new ArrayList<>();
    private final ArrayList<TextView> navigation_list = new ArrayList<>();

    private final LayoutInflater inflater;
    private int selectedIndex = -1;

    public Navigation(LinearLayout parent) {
        super(parent.getContext());
        inflater = LayoutInflater.from(getContext());

        this.parent = parent;
        content_container = parent.findViewById(R.id.content);
        navigation_container = parent.findViewById(R.id.navigation);
    }

    public Navigation addTab(String text, @LayoutRes int resId) {
        inflater.inflate(resId, content_container);
        inflater.inflate(R.layout.navigation_tab, navigation_container);
        int index = content_list.size();

        View content = content_container.getChildAt(index);
        content_list.add(content);

        TextView tab = (TextView) navigation_container.getChildAt(index);
        navigation_list.add(tab);

        View.OnClickListener listener = v -> {
            if (selectedIndex != -1) {
                ITabResult current = getTab(selectedIndex);
                var contentView = current.getContentView();
                var navigationTextView = current.getNavigationTextView();

                LayoutParams pr = (LayoutParams) contentView.getLayoutParams();
                pr.weight = 2;
                contentView.setLayoutParams(pr);

                navigationTextView.setTextColor(Color.parseColor("#478735"));
            }

            selectedIndex = index;
            LayoutParams pr = (LayoutParams) content.getLayoutParams();
            pr.weight = 0;
            content.setLayoutParams(pr);
            tab.setTextColor(Color.parseColor("#A1FF91"));
        };
        tab.setOnClickListener(listener);
        tab.setText(text);
        return this;
    }

    public interface ITabResult {
        View getContentView();
        TextView getNavigationTextView();
    }

    public ITabResult getTab(int index) {
        return new ITabResult() {
            @Override
            public View getContentView() {
                return content_list.get(index);
            }

            @Override
            public TextView getNavigationTextView() {
                return navigation_list.get(index);
            }
        };
    }

    public Navigation setTabSelected() {
        return setTabSelected(content_list.size() - 1);
    }

    public Navigation setTabSelected(int index) {
        navigation_list.get(index).performClick();
        return this;
    }

}