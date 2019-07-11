package com.mithrilmania.blocktopograph.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mithrilmania.blocktopograph.map.selection.SelectionView;
import com.qozix.tileview.TileView;

import java.lang.ref.WeakReference;

/**
 * A wrapper of TileView in order to handle some of its events.
 */
public class MapTileView extends TileView {

    /**
     * Long press callback.
     */
    private OnLongPressListener mOnLongPressListener;
    private WeakReference<SelectionView> mSelectionView;
    private GestureDetector.OnDoubleTapListener mOuterDoubleTapListener;

    private boolean mTouchable = true;

    public MapTileView(Context context) {
        super(context);
    }

    public MapTileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapTileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onLongPress(MotionEvent event) {
        if (mOnLongPressListener != null) mOnLongPressListener.onLongPressed(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        updateSelection();
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (mOuterDoubleTapListener != null) mOuterDoubleTapListener.onDoubleTap(event);
        return true;
    }

    public void setOuterDoubleTapListener(GestureDetector.OnDoubleTapListener outerDoubleTapListener) {
        mOuterDoubleTapListener = outerDoubleTapListener;
    }

    public void updateSelection() {
        SelectionView selectionView;
        if (mSelectionView != null && (selectionView = mSelectionView.get()) != null
                && selectionView.hasSelection()) {
            //selectionView.invalidate();
            selectionView.requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchable)
            return super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        if (super.onSingleTapConfirmed(event)) return true;
        if (mOnLongPressListener != null) {
            mOnLongPressListener.onLongPressed(event);
            return true;
        }
        return false;
    }

    /**
     * Sets the long press callback.
     *
     * @param mOnLongPressListener long press callback.
     */
    public void setOnLongPressListener(OnLongPressListener mOnLongPressListener) {
        this.mOnLongPressListener = mOnLongPressListener;
    }

    public void setSelectionView(SelectionView selectionView) {
        mSelectionView = new WeakReference<>(selectionView);
    }

    public void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }

    /**
     * Interface for long press callback.
     */
    public interface OnLongPressListener {
        void onLongPressed(MotionEvent event);
    }
}
