package com.mithrilmania.blocktopograph.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EdgeEffect;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.mithrilmania.blocktopograph.Log;

import java.lang.reflect.Field;

import androidx.core.widget.NestedScrollView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * The class serves two purpose, for layout height fix and edge effect on expansion.
 *
 * <p>
 * Unlike a usual use case of the ExpansionLayout as nested scrollable component,
 * a "wrap content if space, or match parent if not" behavior was wanted here.
 * So a fix/workaround at expansion time and after expansion was needed.
 * <p>
 * Also a glowing edge effect at the top was wanted, in order to indicate
 * that the current view was scrollable, and there're content below.
 * </p>
 */
public class MeowExpansionLayout extends ExpansionLayout {

    /**
     * Top edge effect of the underlying NestedScrollView.
     */
    private EdgeEffect mEdgeEffect;

    public MeowExpansionLayout(Context context) {
        super(context);
        init();
    }

    public MeowExpansionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeowExpansionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Class clazz = NestedScrollView.class;
        try {
            Field edgeGlowField = clazz.getDeclaredField("mEdgeGlowTop");
            edgeGlowField.setAccessible(true);
            Context context = getContext();
            EdgeEffect edge = new EdgeEffect(context);
            edgeGlowField.set(this, edge);
            //mEdgeEffect = (EdgeEffect) edgeGlowField.get(this);
            edgeGlowField.setAccessible(false);
            edgeGlowField = clazz.getDeclaredField("mEdgeGlowBottom");
            edgeGlowField.setAccessible(true);
            edgeGlowField.set(this, new EdgeEffect(context));
            edgeGlowField.setAccessible(false);
            edge.setSize(20, 20);
            mEdgeEffect = edge;
            //post(this::doOverScroll);
        } catch (Exception e) {
            Log.d(this, e);
        }
    }

    /**
     * Extended to fix display bug.
     *
     * <p>
     * ScrollViews does not fit well in a limited space without a preset height.
     * This was used to apply a "wrap content if space, or match parent if not" rule.
     * </p>
     *
     * @param animated Whether display expansion animation. Always true.
     */
    @Override
    public void expand(boolean animated) {
        if (!isEnabled() || isExpanded()) {
            return;
        }

        if (animated) {
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, getChildAt(0).getHeight());
            valueAnimator.addUpdateListener(valueAnimator1 ->
                    setHeightValue((Float) valueAnimator1.getAnimatedValue()));
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setHeight(WRAP_CONTENT);
                    setAnimator(null);
                    doOverScroll();
                }
            });
            setExpanded(true);
            setAnimator(valueAnimator);
            valueAnimator.start();
        } else {
            // Behavior w/o animation was unchanged.
            setHeightValue(getChildAt(0).getHeight());
            setExpanded(true);
        }
    }

    /**
     * Set height with given value if space, or max remaining value if not.
     *
     * <p>
     * This must be the last child of a vertical LinearLayout with match_parent height.
     * </p>
     *
     * @param height desired height, might not be the final actual height of the view.
     */
    private void setHeightValue(float height) {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            float remaining = ((ViewGroup) parent).getMeasuredHeight() - getY();
            height = Math.min(remaining, height);
            //Log.d(this, "remaining: " + remaining + ", requested: " + height);
            setHeight((int) height);
        }
    }

    private void setHeight(int height) {
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = height;
            setLayoutParams(layoutParams);
        }
    }

    private void setAnimator(Animator animator) {
        try {
            Class clazz = ExpansionLayout.class;
            Field fieldAnimator = clazz.getDeclaredField("animator");
            fieldAnimator.setAccessible(true);
            fieldAnimator.set(this, animator);
            fieldAnimator.setAccessible(false);
        } catch (Throwable e) {
            Log.d(this, e);
        }
    }

    private void setExpanded(boolean expanded) {
        try {
            Class clazz = ExpansionLayout.class;
            Field fieldAnimator = clazz.getDeclaredField("expanded");
            fieldAnimator.setAccessible(true);
            fieldAnimator.setBoolean(this, expanded);
            fieldAnimator.setAccessible(false);
        } catch (Throwable e) {
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
