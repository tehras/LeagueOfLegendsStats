package com.github.koshkin.leagueoflegendsstats.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;

/**
 * Created by tehras on 1/16/16.
 * <p/>
 * Custom spinner for dropdowns
 */
public class CustomSpinner extends LinearLayout {
    private Context mContext;
    private MaterialRippleLayout mSpinnerButton;
    private String[] mChoices;
    private CustomSpinnerListener mSpinnerListener;
    private TextView mSpinnerText;

    public CustomSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomSpinner setChoices(String[] strings) {
        mChoices = strings;
        return this;
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomSpinner, 0, 0);
        String buttonText = a.getString(R.styleable.CustomSpinner_spinnerText);
        @SuppressWarnings("deprecation") int color = a.getColor(R.styleable.CustomSpinner_spinnerTextColor, getResources().getColor(R.color.colorAccent));
        a.recycle();

        //To inflate the view
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_custom_spinner, this);

        mSpinnerButton = (MaterialRippleLayout) view.findViewById(R.id.material_button_layout);
        mSpinnerText = (TextView) view.findViewById(R.id.custom_spinner_text);

        if (!NullChecker.isNullOrEmpty(buttonText))
            mSpinnerText.setText(buttonText);

        mSpinnerText.setTextColor(color);

        mSpinnerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerButtonWasPressed(mSpinnerButton, inflater);
            }
        });
    }

    private void spinnerButtonWasPressed(View anchor, LayoutInflater inflater) {
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = getSpinnerPopupView(inflater, popupWindow);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.setWidth(contentView.getMeasuredWidth());
        popupWindow.setContentView(getSpinnerPopupView(inflater, popupWindow));
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.elevation_background, null));
        popupWindow.setElevation(mContext.getResources().getDimensionPixelSize(R.dimen.large_elevation));
        popupWindow.setOutsideTouchable(true);

        Log.e(getClass().getSimpleName(), "Showing popup");

        popupWindow.showAsDropDown(anchor, 0, 0, Gravity.END);

        if (mContext != null && mContext instanceof MainActivity) {
            ((MainActivity) mContext).setLastPopupWindow(popupWindow);
        }
    }

    public CustomSpinner setSelectedOption(String option) {
        mSelectedOption = option;
        return this;
    }

    private String mSelectedOption;

    public View getSpinnerPopupView(LayoutInflater inflater, final PopupWindow popup) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.popup_spinner_layout, null);

        LinearLayout container = (LinearLayout) view.findViewById(R.id.container);

        if (mChoices != null) {
            for (String text : mChoices) {
                MaterialButton m = new MaterialButton(mContext);
                m.setText(text);
                if (mSelectedOption != null && mSelectedOption.equalsIgnoreCase(text)) {
                    m.setTextColor(R.color.colorPrimary);
                    m.setOnButtonClickListener(getSpinnerClickListener(popup, false, text));
                } else {
                    m.setTextColor(R.color.text_color_dark);
                    m.setOnButtonClickListener(getSpinnerClickListener(popup, false, text));
                }

                container.addView(m);
            }
        }

        return view;
    }

    public OnClickListener getSpinnerClickListener(final PopupWindow popup, final boolean isActive, final String choice) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isActive && mSpinnerListener != null)
                    mSpinnerListener.spinnerClicked(choice);

                mSelectedOption = choice;
                mSpinnerText.setText(mSelectedOption);
                popup.dismiss();
            }
        };
    }

    public CustomSpinner setSpinnerListener(CustomSpinnerListener listener) {
        mSpinnerListener = listener;
        return this;
    }

    public interface CustomSpinnerListener {
        void spinnerClicked(String text);
    }
}
