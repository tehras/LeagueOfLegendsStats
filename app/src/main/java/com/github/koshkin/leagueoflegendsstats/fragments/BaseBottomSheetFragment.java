package com.github.koshkin.leagueoflegendsstats.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.touchlisteners.BottomSheetTouchListener;

/**
 * Created by tehras on 2/14/16.
 */
public class BaseBottomSheetFragment extends BaseFragment implements BottomSheetTouchListener.BottomSheetCallback {

    private RelativeLayout mOverallLayout;
    protected LinearLayout mBottomSheet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_layout, container, false);

        initializeLayouts(view);
        View childView = getDesiredView(inflater);
        if (childView != null)
            mBottomSheet.addView(childView);

        addOnTouchListeners();

        return view;
    }

    private void addOnTouchListeners() {
        mBottomSheet.setVisibility(View.INVISIBLE);
        final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBottomSheet.getLayoutParams().height = getDesiredHeight();

                mBottomSheet.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mBottomSheet != null) {
                            mBottomSheet.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                            mBottomSheet.setVisibility(View.VISIBLE);
                        }
                    }
                });
                mBottomSheet.requestLayout();

                mOverallLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        };

        mOverallLayout.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        mBottomSheet.setOnTouchListener(new BottomSheetTouchListener(BaseBottomSheetFragment.this));
        mOverallLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                collapse();
                return true;
            }
        });
    }

    private void collapse() {
        (getActivity()).onBackPressed();
    }

    /**
     * Takes care of initializing the overall and bototm sheet layouts
     *
     * @param rootView root view from #onCreateView
     */
    private void initializeLayouts(View rootView) {
        mOverallLayout = (RelativeLayout) rootView.findViewById(R.id.bottom_sheet_overall_layout);
        mBottomSheet = (LinearLayout) rootView.findViewById(R.id.bottom_sheet_view_container);
    }

    @Override
    public void onExpanded() {
        mOverallLayout.setOnTouchListener(new BottomSheetTouchListener(this));
    }

    @Override
    public void onCollapsed() {
        collapse();
    }

    public int getDesiredHeight() { //default
        return mOverallLayout.getHeight() * 2 / 3;
    }

    public View getDesiredView(LayoutInflater inflater) {
        return null;
    }
}
