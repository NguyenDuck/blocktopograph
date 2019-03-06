package com.mithrilmania.blocktopograph.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.qozix.tileview.TileView;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SelectionView extends FrameLayout {

    @IdRes
    private int mDragger;

    private float mDragLastPos;

    private Paint mPaint;
    private Rect mSelectionRect;
    private WeakReference<MapTileView> mTileView;

    private RectF mSelectionPixelRange;
    private RectF mVisiblePixelRange;

    /**
     * Minimal distance between drag button and bound.
     * -----O---------------------------
     * |----| Like this.
     */
    private float BUTTON_TO_BOUND_MIN_DIST;

    public SelectionView(Context context) {
        super(context);
        meow(context);
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        meow(context);
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        meow(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        meow(context);
    }

    private void meow(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_selection_view, this, true);
        mPaint = new Paint();
        mPaint.setColor(Color.argb(0x80, 0, 0, 0));
        mSelectionRect = new Rect(425, 0, 800, 400);
        mSelectionPixelRange = new RectF();
        mVisiblePixelRange = new RectF();
        setWillNotDraw(false);
        BUTTON_TO_BOUND_MIN_DIST = UiUtil.dpToPx(context, 72);
        mDragger = 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        child.setOnTouchListener(this::onTouch);
    }

    private boolean onTouch(View view, MotionEvent motionEvent) {

        MapTileView tileView;
        if (mTileView == null || (tileView = mTileView.get()) == null) return false;

        // If already dragging another button, disallow dragging a second one.
        @IdRes int which = view.getId();
        if (mDragger != 0 && which != mDragger) {
            // Could we...
            //motionEvent.recycle();
            // Well if we return false for an ACTION_DOWN then it won't bother popping
            // Tons of confusing ACTION_MOVEs.
            return false;
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                // Prevents tileView being touched while dragging.
                tileView.setTouchable(false);

                // Set current dragging item.
                mDragger = which;
                mDragLastPos = 0.0f;

                // IMPORTANT! Forgot this once. Fuck you man, fuck you!
                return true;

            case MotionEvent.ACTION_UP:

                // Unlock.
                tileView.setTouchable(true);
                mDragger = 0;
                return true;

            case MotionEvent.ACTION_MOVE:
                onMove(tileView, which, motionEvent);
                return true;
        }

        return false;
    }

    private void onMove(@NotNull TileView tileView, @IdRes int which, @NotNull MotionEvent event) {

        // Get move distance.
        float distOnScreen;

        // New position to be stored.
        float lastPosNew;

        switch (which) {
            case R.id.left:
            case R.id.right: {
                float x = event.getX();
                distOnScreen = x - mDragLastPos;
                lastPosNew = x;
                break;
            }
            case R.id.top:
            case R.id.bottom: {
                float y = event.getY();
                distOnScreen = y - mDragLastPos;
                lastPosNew = y;
                break;
            }
            default:
                return;
        }

        // Translate it back to blocks.
        float scale = tileView.getScale();
        float pxPerBlx = scale * MCTileProvider.TILESIZE / 16;
        int distanceInBlocks = Math.round(distOnScreen / pxPerBlx);

        // If it's less than a block let the accumulation grow.
        if (distanceInBlocks == 0 && Math.abs(distOnScreen) >= 0.00001f) {
            return;
        }

        // Otherwise update last position.
        mDragLastPos = lastPosNew;

        // Change selection.
        // Selection shall be at least 1x1.
        switch (which) {
            case R.id.left:
                if (mSelectionRect.left + distanceInBlocks >= mSelectionRect.right) {
                    mSelectionRect.left = mSelectionRect.right - 1;
                    distanceInBlocks = 0;
                } else mSelectionRect.left += distanceInBlocks;
                break;
            case R.id.right:
                if (mSelectionRect.right + distanceInBlocks <= mSelectionRect.left) {
                    mSelectionRect.right = mSelectionRect.left + 1;
                    distanceInBlocks = 0;
                } else mSelectionRect.right += distanceInBlocks;
                break;
            case R.id.top:
                if (mSelectionRect.top + distanceInBlocks >= mSelectionRect.bottom) {
                    mSelectionRect.top = mSelectionRect.bottom - 1;
                    distanceInBlocks = 0;
                } else mSelectionRect.top += distanceInBlocks;
                break;
            case R.id.bottom:
                if (mSelectionRect.bottom + distanceInBlocks <= mSelectionRect.top) {
                    mSelectionRect.bottom = mSelectionRect.top + 1;
                    distanceInBlocks = 0;
                } else mSelectionRect.bottom += distanceInBlocks;
                break;
        }

        // If no movement, return.
        if (distanceInBlocks == 0) return;

        // Move the tileView.
        switch (which) {
            case R.id.left:
            case R.id.right:
                tileView.setScrollX((int) (tileView.getScrollX() + distOnScreen));
                break;
            case R.id.top:
            case R.id.bottom:
                tileView.setScrollY((int) (tileView.getScrollY() + distOnScreen));
                break;
        }
    }

    public void setTileView(MapTileView tileView) {
        mTileView = new WeakReference<>(tileView);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        float sw = getMeasuredWidth();
        float sh = getMeasuredHeight();

        MapTileView tileView;
        if (mTileView == null || (tileView = mTileView.get()) == null) return;

        float scale = tileView.getScale();

        // Pixels per block.
        float pxPerBlx = scale * MCTileProvider.TILESIZE / 16;

        // This would translate coordinate related to view, e.g. getScrollX() result,
        // into coordinate related to World.
        float halfWorld = pxPerBlx * MCTileProvider.HALF_WORLDSIZE;

        // These translate selection dimensions (rel. to World) to screen.
        float transToScrX = (float) tileView.getScrollX() - halfWorld;
        float transToScrY = (float) tileView.getScrollY() - halfWorld;

        // Draw selected range

        mSelectionPixelRange.left = pxPerBlx * mSelectionRect.left - transToScrX;
        mSelectionPixelRange.top = pxPerBlx * mSelectionRect.top - transToScrY;
        mSelectionPixelRange.right = pxPerBlx * mSelectionRect.right - transToScrX;
        mSelectionPixelRange.bottom = pxPerBlx * mSelectionRect.bottom - transToScrY;

        // To the middle of lefter bound of on-screen selection area.
        float horix = ((mSelectionPixelRange.left < 0 ? 0.0f : mSelectionPixelRange.left)
                + (mSelectionPixelRange.right > sw ? sw : mSelectionPixelRange.right)) / 2.0f;
        float verty = ((mSelectionPixelRange.top < 0 ? 0.0f : mSelectionPixelRange.top)
                + (mSelectionPixelRange.bottom > sh ? sh : mSelectionPixelRange.bottom)) / 2.0f;

        // In case the selection's not too small.
        if (mSelectionPixelRange.right - mSelectionPixelRange.left > BUTTON_TO_BOUND_MIN_DIST * 2) {

            if (horix - mSelectionPixelRange.left < BUTTON_TO_BOUND_MIN_DIST)
                horix = mSelectionPixelRange.left + BUTTON_TO_BOUND_MIN_DIST;

            if (mSelectionPixelRange.right - horix < BUTTON_TO_BOUND_MIN_DIST)
                horix = mSelectionPixelRange.right - BUTTON_TO_BOUND_MIN_DIST;

        }
        if (mSelectionPixelRange.bottom - mSelectionPixelRange.top > BUTTON_TO_BOUND_MIN_DIST * 2) {

            if (verty - mSelectionPixelRange.top < BUTTON_TO_BOUND_MIN_DIST)
                verty = mSelectionPixelRange.top + BUTTON_TO_BOUND_MIN_DIST;

            if (mSelectionPixelRange.bottom - verty < BUTTON_TO_BOUND_MIN_DIST)
                verty = mSelectionPixelRange.bottom - BUTTON_TO_BOUND_MIN_DIST;

        }

        int horit = (int) mSelectionPixelRange.top;
        int vertl = (int) mSelectionPixelRange.left;
        int horib = (int) mSelectionPixelRange.bottom;
        int vertr = (int) mSelectionPixelRange.right;
        int horixi = (int) horix;
        int vertyi = (int) verty;

        // Left
        View view = getChildAt(0);
        int humw = view.getMeasuredWidth() / 2;
        int humh = view.getMeasuredHeight() / 2;
        view.layout(vertl - humw, vertyi - humh, vertl + humw, vertyi + humh);

        // Top
        view = getChildAt(1);
        humw = view.getMeasuredWidth() / 2;
        humh = view.getMeasuredHeight() / 2;
        view.layout(horixi - humw, horit - humh, horixi + humw, horit + humh);

        // Right
        view = getChildAt(2);
        humw = view.getMeasuredWidth() / 2;
        humh = view.getMeasuredHeight() / 2;
        view.layout(vertr - humw, vertyi - humh, vertr + humw, vertyi + humh);

        // Top
        view = getChildAt(3);
        humw = view.getMeasuredWidth() / 2;
        humh = view.getMeasuredHeight() / 2;
        view.layout(horixi - humw, horib - humh, horixi + humw, horib + humh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Screen dimensions.
        float sw = getMeasuredWidth();
        float sh = getMeasuredHeight();

        if (!canvas.quickReject(mSelectionPixelRange, Canvas.EdgeType.BW)) {

            mVisiblePixelRange.left = (mSelectionPixelRange.left < 0.0f) ? 0.0f : mSelectionPixelRange.left;
            mVisiblePixelRange.top = (mSelectionPixelRange.top < 0.0f) ? 0.0f : mSelectionPixelRange.top;
            mVisiblePixelRange.right = (mSelectionPixelRange.right > sw) ? sw : mSelectionPixelRange.right;
            mVisiblePixelRange.bottom = (mSelectionPixelRange.bottom > sh) ? sh : mSelectionPixelRange.bottom;

            canvas.drawRect(mVisiblePixelRange, mPaint);
        }

    }
}
