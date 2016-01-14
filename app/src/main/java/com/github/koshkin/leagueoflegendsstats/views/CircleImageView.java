package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * CircleIamgeView
 */
public class CircleImageView extends ImageView {
    private RectF mRecF;

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private Path mClipPath;

    private void init() {

        mClipPath = new Path();
        mRecF = new RectF();

        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = canvas.getHeight();
        mRecF.set(0, 0, this.getWidth(), this.getHeight());
        mClipPath.addRoundRect(mRecF, radius, radius, Path.Direction.CW);

        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }
}
