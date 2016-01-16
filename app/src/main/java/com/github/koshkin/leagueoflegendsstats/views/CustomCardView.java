package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;

/**
 * Created by tehras on 1/15/16.
 * <p/>
 * This view is the default Card View used in this app
 */
public class CustomCardView extends LinearLayout {
    private TextView mNotFoundText, mTitleEditText, mViewAllText;
    private MaterialRippleLayout mViewAllButton;
    private LinearLayout mViewHolder;

    public CustomCardView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public CustomCardView setTitleText(CharSequence titleText) {
        mTitleEditText.setText(titleText);
        return this;
    }

    public CustomCardView setViewAllText(CharSequence viewAllText) {
        mViewAllText.setText(viewAllText);
        return this;
    }

    public CustomCardView setErrorMessage(CharSequence errorMessage) {
        mNotFoundText.setText(errorMessage);
        return this;
    }

    public CustomCardView setViewAllOnClickListener(OnClickListener onClickListener) {
        mViewAllButton.setOnClickListener(onClickListener);
        return this;
    }

    public void addViewToHolder(View view) {
        if (view != null) {
            mViewHolder.addView(view);
            hideError();
        }
    }

    public void clearViewsFromHolder() {
        mViewHolder.removeAllViews();
    }

    public CustomCardView showError() {
        mNotFoundText.setVisibility(VISIBLE);
        mViewHolder.setVisibility(GONE);
        return this;
    }

    public CustomCardView hideError() {
        mNotFoundText.setVisibility(GONE);
        mViewHolder.setVisibility(VISIBLE);
        return this;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomCardView, 0, 0);
        String title = a.getString(R.styleable.CustomCardView_titleText);
        String viewMore = a.getString(R.styleable.CustomCardView_viewMoreText);
        String notFound = a.getString(R.styleable.CustomCardView_dataNotFound);
        a.recycle();

        //To inflate the view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_custom_card_layout, this);

        mTitleEditText = (TextView) view.findViewById(R.id.custom_view_title);
        mViewAllText = (TextView) view.findViewById(R.id.custom_view_view_all_text);
        mViewAllButton = (MaterialRippleLayout) view.findViewById(R.id.custom_view_view_all);
        mViewHolder = (LinearLayout) view.findViewById(R.id.custom_view_layout_holder);
        mNotFoundText = (TextView) view.findViewById(R.id.custom_view_not_found);

        if (!NullChecker.isNullOrEmpty(title))
            mTitleEditText.setText(title);

        if (!NullChecker.isNullOrEmpty(viewMore))
            mViewAllText.setText(viewMore);

        if (!NullChecker.isNullOrEmpty(notFound))
            mNotFoundText.setText(notFound);
    }

}
