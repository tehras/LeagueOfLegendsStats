package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by tehras on 1/18/16.
 */
public class BlockableNestedScrollView extends NestedScrollView {
    public BlockableNestedScrollView(Context context) {
        super(context);
    }

    public BlockableNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockableNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean mBlocked;

    public void setBlocked(boolean blocked) {
        mBlocked = blocked;
    }


    private OnTouchListener mOnTouchListener;

    public void addListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mOnTouchListener != null)
            mOnTouchListener.onTouch(this, ev);

        return mBlocked || super.onTouchEvent(ev);
    }
}
