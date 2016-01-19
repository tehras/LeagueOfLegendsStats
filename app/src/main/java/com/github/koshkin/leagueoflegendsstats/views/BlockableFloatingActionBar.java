package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Created by tehras on 1/18/16.
 */
public class BlockableFloatingActionBar extends FloatingActionButton {
    public BlockableFloatingActionBar(Context context) {
        super(context);
    }

    public BlockableFloatingActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockableFloatingActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean mIsBlocked = true;

    public BlockableFloatingActionBar setBlocked(boolean isBlocked) {
        mIsBlocked = isBlocked;
        return this;
    }

    @Override
    public void show() {
        if (mIsBlocked) return; //block

        super.show();
    }
}
