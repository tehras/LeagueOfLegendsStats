package com.github.koshkin.leagueoflegendsstats.touchlisteners;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.koshkin.leagueoflegendsstats.utils.AnimationHelperUtil;

/**
 * Created by tehras on 2/14/16.
 */
public class BottomSheetTouchListener implements View.OnTouchListener {

    private final BottomSheetCallback mCallback;
    private float prevHeight;
    private float MIN_DISTANCE;

    public BottomSheetTouchListener(BottomSheetCallback callback) {
        mCallback = callback;
    }

    int height = -1;
    float originalY = -1;
    float prevY = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (height < 0) {
            height = v.getMeasuredHeight();
            prevHeight = height;
            MIN_DISTANCE = height * 0.25f;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                originalY = event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float y2 = event.getRawY();
                if (originalY < 0)
                    originalY = y2; //incase of mid move somehow

                if (prevY <= 0) {
                    prevY = y2;
                }

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                prevHeight = prevHeight + (prevY - y2);
                params.height = (int) prevHeight;
                v.requestLayout();

                prevY = y2;

                return true;
            case MotionEvent.ACTION_UP:
                y2 = event.getRawY();

                //either collapse or expand
                if (prevY - originalY > (MIN_DISTANCE)) { //expand
                    View parent = (View) v.getParent();
                    AnimationHelperUtil.expand(v, 300, parent.getHeight());
                    mCallback.onExpanded();
                } else if (prevY - originalY < (height * .25)) {
                    AnimationHelperUtil.collapse(v, 300, 0);
                    mCallback.onCollapsed();
                } else {
                    if ((prevY - originalY) > height)
                        AnimationHelperUtil.collapse(v, 200, height);
                    else
                        AnimationHelperUtil.expand(v, 200, height);
                }
                break;
        }

        return true;
    }

    public interface BottomSheetCallback {
        void onExpanded();

        void onCollapsed();
    }

}
