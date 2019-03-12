package com.mithrilmania.blocktopograph.map.selection;

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
import com.mithrilmania.blocktopograph.map.MCTileProvider;
import com.mithrilmania.blocktopograph.map.MapTileView;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.UiUtil;

import org.jetbrains.annotations.Contract;

import java.lang.ref.WeakReference;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SelectionView extends FrameLayout {

    /**
     * The view being dragged now.
     */
    @Nullable
    private View mDragger;

    /**
     * Correction of drag begin point.
     *
     * <p>
     * It's the relative distance between the initially touched point
     * and the view's left-top coordinates.
     * ------------------
     * |                |
     * |--[ This ]--X   |
     * |                |
     * |                |
     * ------------------
     * </p>
     */
    private float mDragBeginPosCorr;

    /**
     * Drag distance accumulation.
     *
     * <p>
     * If the drag distance of a given moment cannot move the selection
     * at least 1 block's wide, we accumulate the distance to have a larger
     * chance to move on the next round.
     * </p>
     */
    private float mDragAccumulation;

    /**
     * Current touch position.
     *
     * <p>
     * Produced in `onTouch` and consumed in `onMove`.
     * </p>
     */
    private float mDragCurrentPos;

    /**
     * Current dragging direction.
     *
     * <p>
     * Used for anti-jitter purpose.
     * </p>
     */
    private int mDragDirection;

    /**
     * Paint object used to draw selection onto canvas.
     */
    private final Paint mPaint = new Paint();

    /**
     * Selection range.
     */
    private final Rect mSelectionRect = new Rect();

    /**
     * The tileView we serves for.
     */
    private WeakReference<MapTileView> mTileView;

    /**
     * Range of selection in pixel.
     */
    private final RectF mSelectionPixelRange = new RectF();

    /**
     * Visible range of selection in pixel.
     */
    private final RectF mVisiblePixelRange = new RectF();

    /**
     * Minimal distance between drag button and bound.
     * -----O---------------------------
     * |----| Like this.
     */
    private float BUTTON_TO_BOUND_MIN_DIST;

    /**
     * The minimal non-auto-scroll distance from touched point to screen boundary.
     *
     * <p>
     * Once it's exceed the tileView would be scrolled automatically.
     * </p>
     */
    private int MIN_DIST_TO_SCREEN_BOUND;

    private int MIN_DIST_DRAGGERS;

    private int HALF_MIN_DIST_DRAGGERS;

    /**
     * Runnable used to frequently alter selection while user dragging a adjust button.
     */
    private final Runnable mHoldingMover = this::onMove;

    /**
     * Indicates whether a selection exists.
     */
    private boolean mHasSelection;

    /**
     * The selection could be changed outsides, so we need this.
     */
    @Nullable
    private SelectionChangedListener mSelectionChangedListener;

    public SelectionView(Context context) {
        super(context);
        init(context);
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        // The viewGroup has static content.
        LayoutInflater.from(context).inflate(R.layout.view_selection_view, this, true);

        mPaint.setColor(Color.argb(0x80, 0, 0, 0));
        setWillNotDraw(false);// Otherwise `onDraw` won't be called.
        BUTTON_TO_BOUND_MIN_DIST = UiUtil.dpToPx(context, 72);
        MIN_DIST_TO_SCREEN_BOUND = UiUtil.dpToPxInt(context, 100);
        MIN_DIST_DRAGGERS = UiUtil.dpToPxInt(context, 50);
        HALF_MIN_DIST_DRAGGERS = MIN_DIST_DRAGGERS / 2;

        mDragger = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewAdded(View child) {
        switch (child.getId()) {
            case R.id.left:
            case R.id.right:
            case R.id.top:
            case R.id.bottom:
                child.setOnTouchListener(this::onTouch);
                break;
        }
    }

    private boolean onTouch(View view, MotionEvent motionEvent) {

        MapTileView tileView;
        if (mTileView == null || (tileView = mTileView.get()) == null) return false;

        // If already dragging another button, disallow dragging a second one.
        @IdRes int which = view.getId();
        if (mDragger != null && which != mDragger.getId()) {
            // Well if we return false for an ACTION_DOWN then it won't bother popping
            // Tons of confusing ACTION_MOVEs.
            return false;
        }

        // Set current pos.
        switch (which) {
            case R.id.left:
            case R.id.right:
                // Motion event's get x is relative to view's x. Sum 'em up.
                mDragCurrentPos = view.getX() + motionEvent.getX();
                break;
            case R.id.top:
            case R.id.bottom:
                mDragCurrentPos = view.getY() + motionEvent.getY();
                break;
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                // Begin visual effect on the pressed button.
                view.setPressed(true);

                // Show icon.
                View actionIcon = getChildAt(4);
                actionIcon.setVisibility(VISIBLE);

                switch (which) {
                    case R.id.left:
                        actionIcon.setRotation(270.0f);
                        break;
                    case R.id.right:
                        actionIcon.setRotation(90.0f);
                        break;
                    case R.id.top:
                        actionIcon.setRotation(0.0f);
                        break;
                    case R.id.bottom:
                        actionIcon.setRotation(180.0f);
                        break;
                }

                // Prevents tileView being touched while dragging.
                tileView.setTouchable(false);

                // Set current dragging item.
                mDragger = view;
                switch (which) {
                    case R.id.left:
                    case R.id.right:
                        mDragBeginPosCorr = motionEvent.getX();// - view.getX();
                        break;
                    case R.id.top:
                    case R.id.bottom:
                        mDragBeginPosCorr = motionEvent.getY();// - view.getY();
                        break;
                }
                mDragAccumulation = 0.0f;
                mDragDirection = 0;

                // And trigger a continuous detecting.
                post(mHoldingMover);

                // IMPORTANT! Forgot this once. Fuck you man, fuck you!
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                // End visual effect.
                view.setPressed(false);

                // Hide icon.
                getChildAt(4).setVisibility(GONE);

                // Clear & Unlock.
                tileView.setTouchable(true);
                mDragger = null;
                return true;

            case MotionEvent.ACTION_MOVE:
                //onMove(tileView, which, motionEvent);
                return true;
        }

        return false;
    }

    private void onMove() {

        // The tileView we serves for.
        MapTileView tileView;

        // If user no longer holding or we don't have a tileView to serve for.
        if (mDragger == null || !isEnabled() ||
                mTileView == null || (tileView = mTileView.get()) == null) return;

        // Distance between view position and current holding position.
        float distOnScreen;

        // Get the view to retrieve its position.

        // Won't happen.
        if (mDragger == null) return;

        // Retrieve view position then get the distance and screen size.
        int draggerId = mDragger.getId();
        switch (draggerId) {
            case R.id.left:
            case R.id.right:
                // `dragged.getX() - mDragBeginPosCorr` is the current position of user's initially
                // touched point of the View.
                distOnScreen = mDragCurrentPos - mDragger.getX() - mDragBeginPosCorr;
                break;
            case R.id.top:
            case R.id.bottom:
                distOnScreen = mDragCurrentPos - mDragger.getY() - mDragBeginPosCorr;
                break;
            default:
                return;
        }

        flow:
        {
            // Amplify movement. Maybe we'd allow user to set it, or calculate based on screen
            // density and tileView's scale.
            float amp = 0.2f;

            //Log.d(this, "" + distOnScreen + "," + mDragCurrentPos + "," + mDragBeginPosCorr);

            // If a previous round failed to move at least 1 block's wide,
            // it would accumulate the distance till in a future round we could move.
            float movement = distOnScreen * amp + mDragAccumulation;

            // Translate the distance back to blocks count.
            float scale = tileView.getScale();
            float pxPerBlx = scale * MCTileProvider.TILESIZE / 16;
            int distanceInBlocks = Math.round(movement / pxPerBlx);

            // If it's less than a block we couldn't move, let the accumulation grow.
            if (distanceInBlocks == 0) {//&& Math.abs(movement) >= 0.00001f) {
                mDragAccumulation = movement;
                break flow;
            }

            // Anti-jitter. If the user's scrolling right carefully, we don't want it suddenly goes
            // left because the view also moved and moved faster than the finger.
            if (// If previous direction set, is negative to current, and current movement is short.
                    (distanceInBlocks < 0 && mDragDirection > 0 && distOnScreen > -10.0f)
                            || (distanceInBlocks > 0 && mDragDirection < 0 && distOnScreen < 10.0f)) {
                // Then we do not move and clear accumulation.
                mDragAccumulation = 0;
                break flow;
            }

            // Set direction for next round's anti-jitter.
            mDragDirection = distanceInBlocks > 0 ? 1 : -1;

            // We've decided to move NOW AND TODAY clear accumulation.
            mDragAccumulation = 0;

            // Alter selection.
            // Selection shall be at least 1x1.
            switch (draggerId) {
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
            // It would be caused by the "Selection must be at least 1x1" rule.
            // For instance in case it's already 200x1 we can't move vertically.
            if (distanceInBlocks == 0) break flow;

            // Notify outsides.
            if (mSelectionChangedListener != null)
                mSelectionChangedListener.onSelectionChanged(mSelectionRect);

            // Should we move the underlying tileView as well?
            // If touched point is near the moving direction (not the dragger position)
            // then we scroll.

            int sw = getMeasuredWidth();
            int sh = getMeasuredHeight();
            int minw = Math.max(MIN_DIST_TO_SCREEN_BOUND, sw / 8);
            int minh = Math.max(MIN_DIST_TO_SCREEN_BOUND, sh / 8);

            switch (draggerId) {
                case R.id.left:
                case R.id.right:
                    // (Moving right and near right bound) or
                    // (Moving left and near left bound)
                    if (mDragDirection > 0 && sw - mDragCurrentPos < minw
                            || (mDragDirection < 0 && mDragCurrentPos < minw)
                    )
                        tileView.setScrollX((int) (tileView.getScrollX() + movement));
                    else
                        requestLayout();
                    break;
                case R.id.top:
                case R.id.bottom:
                    if (mDragDirection > 0 && sh - mDragCurrentPos < minh
                            || (mDragDirection < 0 && mDragCurrentPos < minh)
                    )
                        tileView.setScrollY((int) (tileView.getScrollY() + movement));
                    else
                        requestLayout();
                    break;
            }
        }

        // Schedule the next round.
        postDelayed(mHoldingMover, 40);
    }

    public void setTileView(MapTileView tileView) {
        mTileView = new WeakReference<>(tileView);
    }

    public void beginSelection(Rect selection) {
        mHasSelection = true;
        mSelectionRect.set(selection);
        setVisibility(VISIBLE);
        requestLayout();
    }

    public void beginSelection(int centerX, int centerZ) {

        // Requires this.
        MapTileView tileView;
        if (mTileView == null || (tileView = mTileView.get()) == null) return;

        // Self's not visible now and not sure whether has measured dimensions.
        // Use tileView's instead.
        float scale = tileView.getScale();
        int w = tileView.getMeasuredWidth();
        int h = tileView.getMeasuredHeight();

        // Selection would be a square with length of half screen dimension.
        if (w > h) w = h / 4;
        else w /= 4;

        // Translate to blocks.
        float pxPerBlx = scale * MCTileProvider.TILESIZE / 16;
        int rad = Math.round(w / pxPerBlx);
        mHasSelection = true;

        // Set it.
        mSelectionRect.set(centerX - rad, centerZ - rad,
                centerX + rad, centerZ + rad);
        setVisibility(VISIBLE);
        requestLayout();
    }

    public void onSelectionChangedOutsides(Rect rect) {
        mSelectionRect.set(rect);
        // If the selection's far away from current viewport,
        // We want tho scroll the tileView to a nearest corner of the selection.
        MapTileView tileView;
        if (mTileView != null && (tileView = mTileView.get()) != null) {
            float pxPerBlx = tileView.getScale() * MCTileProvider.TILESIZE / 16;
            RectF r = new RectF();
            r.left = (rect.left + MCTileProvider.HALF_WORLDSIZE) * pxPerBlx;
            r.top = (rect.top + MCTileProvider.HALF_WORLDSIZE) * pxPerBlx;
            r.right = (rect.right + MCTileProvider.HALF_WORLDSIZE) * pxPerBlx;
            r.bottom = (rect.bottom + MCTileProvider.HALF_WORLDSIZE) * pxPerBlx;
            int scrollX = tileView.getScrollX();
            int scrollY = tileView.getScrollY();
            int sw = getMeasuredWidth();
            int sh = getMeasuredHeight();
            RectF viewport = new RectF(scrollX, scrollY, scrollX + sw, scrollY + sh);
            if (!viewport.intersect(r)) {
                float hw = sw / 2.0f;
                float currentX = scrollX + hw;
                float hh = sh / 2.0f;
                float currentY = scrollY + hh;
                float dlt = ConvertUtil.distance(currentX, currentY, r.left, r.top);
                float drt = ConvertUtil.distance(currentX, currentY, r.right, r.top);
                float dlb = ConvertUtil.distance(currentX, currentY, r.left, r.bottom);
                float drb = ConvertUtil.distance(currentX, currentY, r.right, r.bottom);
                if (dlt < drt && dlt < dlb && dlt < drb) {
                    tileView.setScrollX((int) (r.left - hw));
                    tileView.setScrollY((int) (r.top - hh));
                } else if (drt < dlb && drt < drb) {
                    tileView.setScrollX((int) (r.right - hw));
                    tileView.setScrollY((int) (r.top - hh));
                } else if (dlb < drb) {
                    tileView.setScrollX((int) (r.left - hw));
                    tileView.setScrollY((int) (r.bottom - hh));
                } else {
                    tileView.setScrollX((int) (r.right - hw));
                    tileView.setScrollY((int) (r.bottom - hh));
                }
            }
        }
        requestLayout();
    }

    public void endSelection() {
        mHasSelection = false;
        setVisibility(GONE);
        requestLayout();
    }

    public boolean hasSelection() {
        return mHasSelection;
    }

    @Contract(pure = true)
    public Rect getSelection() {
        return new Rect(mSelectionRect);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        if (!mHasSelection) return;

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

        // Let's prevent draggers collapse together when the selection's small.

        if (horib - horit <= MIN_DIST_DRAGGERS) {
            horit -= HALF_MIN_DIST_DRAGGERS;
            horib += HALF_MIN_DIST_DRAGGERS;
        }

        if (vertr - vertl <= MIN_DIST_DRAGGERS) {
            vertl -= HALF_MIN_DIST_DRAGGERS;
            vertr += HALF_MIN_DIST_DRAGGERS;
        }

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

        // Current action icon.
        view = getChildAt(4);
        {
            int vw = view.getMeasuredWidth();
            int vh = view.getMeasuredHeight();
            int l = (getMeasuredWidth() - vw) / 2;
            int t = (getMeasuredHeight() - vh) / 2;
            view.layout(l, t, l + vw, t + vh);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mHasSelection) return;

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

    public void setSelectionChangedListener(@Nullable SelectionChangedListener selectionChangedListener) {
        mSelectionChangedListener = selectionChangedListener;
    }
}
