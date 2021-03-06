package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.github.koshkin.leagueoflegendsstats.R;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * Rounded Image View with 6 radius
 */
public class RoundedImageView extends ImageView {

    private Context mContext;
    private RectF mRect;

    public RoundedImageView(Context context) {
        super(context);
        mContext = context;
        init(context, null);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs);
    }


    private Path mClipPath;

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundedImageView, 0, 0);
        int rounded = a.getDimensionPixelSize(R.styleable.RoundedImageView_cornerSize, 0);
        a.recycle();

        if (rounded != 0)
            mRadius = rounded;
        else
            mRadius = mContext.getResources().getDimensionPixelSize(R.dimen.rounded_corners_radius);

        mClipPath = new Path();
        mRect = new RectF();
    }

    private float mRadius;

    public void setRadius(float rad) {
        mRadius = rad;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect.set(0, 0, this.getWidth(), this.getHeight());
        mClipPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CW);

        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }

}
