package com.mithrilmania.blocktopograph.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EdgeEffect;
import android.widget.ScrollView;

import com.mithrilmania.blocktopograph.Log;

import java.lang.reflect.Field;

public class MeowScrollView extends ScrollView {

    /**
     * Top glowing effect of the underlying scrollView.
     *
     * <p>It was retrieved via reflection and used later.</p>
     */
    private EdgeEffect mEdgeEffect;

    public MeowScrollView(Context context) {
        super(context);
        init();
    }

    public MeowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeowScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Class clazz = ScrollView.class;
        try {
            Field edgeGlowField = clazz.getDeclaredField("mEdgeGlowTop");
            edgeGlowField.setAccessible(true);
            mEdgeEffect = (EdgeEffect) edgeGlowField.get(this);
            edgeGlowField.setAccessible(false);
            mEdgeEffect.setSize(20, 20);
            post(this::doOverScroll);
        } catch (Exception e) {
            Log.d(this, e);
        }
    }

    public void doOverScroll() {
        if (mEdgeEffect != null) {
            mEdgeEffect.onPull(200);
            invalidate();
        }
    }
}
