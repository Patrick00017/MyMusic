package com.example.mymusic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.mymusic.R;


@SuppressLint("AppCompatCustomView")
public class LoadingView extends ImageView {
    private int rotateDegree = 0;
    private boolean isRotate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isRotate = true;
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree += 2;
                rotateDegree = rotateDegree <= 360 ? rotateDegree : 0;
                if (isRotate) {
                    postDelayed(this,10);
                }
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRotate = false;
    }

    /**
     * 绘制方法中让其转动
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.rotate(rotateDegree, getWidth()/2, getHeight()/2);
        super.draw(canvas);
    }
}
