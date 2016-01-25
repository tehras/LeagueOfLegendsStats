package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

/**
 * Created by tehras on 1/18/16.
 */
public class MatchPerTenView extends LinearLayout {
    private TextView mZeroToTenTV, mTenToTwentyTV, mTwentyToThirtyTV, mThirtyPlusTV;
    private TextView mTitle;
    private Context mContext;

    public MatchPerTenView(Context context) {
        super(context);
        init(context, null);
    }

    public MatchPerTenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MatchPerTenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MatchPerTenView setZeroToTen(CharSequence string, MatchColor matchColor) {
        return assignText(string, matchColor, mZeroToTenTV);
    }

    public MatchPerTenView setTenToTwenty(CharSequence string, MatchColor matchColor) {
        return assignText(string, matchColor, mTenToTwentyTV);
    }

    public MatchPerTenView setTwentyToThirty(CharSequence string, MatchColor matchColor) {
        return assignText(string, matchColor, mTwentyToThirtyTV);
    }

    public MatchPerTenView setThirtyPlus(CharSequence string, MatchColor matchColor) {
        return assignText(string, matchColor, mThirtyPlusTV);
    }

    public MatchPerTenView setTitleText(CharSequence string) {
        mTitle.setText(string);
        return this;
    }

    public enum MatchColor {
        BAD(R.color.bad), NEUTRAL(R.color.average), GOOD(R.color.good), GREAT(R.color.great);

        private final int mColor;

        MatchColor(int color) {
            mColor = color;
        }
    }

    private MatchPerTenView assignText(CharSequence string, MatchColor matchColor, TextView textView) {
        textView.setText(string);
        if (string == Utils.NOT_AVAILABLE || ((String) string).contains("-0.00"))
            textView.setTextColor(mContext.getResources().getColor(R.color.text_color_grey));
        else
            textView.setTextColor(mContext.getResources().getColor(matchColor.mColor));

        return this;
    }

    public void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MatchPerTenView, 0, 0);
        String title = a.getString(R.styleable.MatchPerTenView_matchPerTenTitle);
        a.recycle();

        //To inflate the view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_match_per_ten, this);

        mZeroToTenTV = (TextView) view.findViewById(R.id.zero_to_ten_text);
        mTenToTwentyTV = (TextView) view.findViewById(R.id.ten_to_twenty_text);
        mTwentyToThirtyTV = (TextView) view.findViewById(R.id.twenty_to_thirty_text);
        mThirtyPlusTV = (TextView) view.findViewById(R.id.thirty_plus_text);

        mTitle = (TextView) view.findViewById(R.id.match_per_ten_title);

        if (!NullChecker.isNullOrEmpty(title))
            mTitle.setText(title);
    }
}
