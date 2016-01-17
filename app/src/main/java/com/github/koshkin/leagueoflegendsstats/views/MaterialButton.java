package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;

/**
 * Created by tehras on 1/16/16.
 * <p/>
 * Material Button to use throughout the app
 */
public class MaterialButton extends RelativeLayout {
    private MaterialRippleLayout mButton;
    private TextView mText;
    private Context mContext;

    public MaterialButton(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MaterialButton setText(CharSequence text) {
        mText.setText(text);
        return this;
    }

    public MaterialButton setOnButtonClickListener(View.OnClickListener clickListener) {
        mButton.setOnClickListener(clickListener);
        return this;
    }

    public MaterialButton setTextColor(int color) {
        //noinspection deprecation
        mText.setTextColor(mContext.getResources().getColor(color));
        return this;
    }

    public MaterialButton setTextColorResource(int textColorResource) {
        mText.setTextColor(textColorResource);
        return this;
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MaterialButton, 0, 0);
        String buttonText = a.getString(R.styleable.MaterialButton_buttonText);
        @SuppressWarnings("deprecation") int color = a.getColor(R.styleable.MaterialButton_textColor, getResources().getColor(R.color.colorAccent));
        a.recycle();

        //To inflate the view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_material_button, this);

        mButton = (MaterialRippleLayout) view.findViewById(R.id.material_button_layout);
        mText = (TextView) view.findViewById(R.id.material_button_text);

        if (!NullChecker.isNullOrEmpty(buttonText))
            mText.setText(buttonText);

        mText.setTextColor(color);
    }
}
