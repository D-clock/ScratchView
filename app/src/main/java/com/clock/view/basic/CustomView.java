package com.clock.view.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.renderscript.Type;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.clock.view.R;

/**
 * This is Basic Custom View.
 * <p/>
 * Created by Clock on 2016/8/1.
 */
public class CustomView extends View {

    private final static String TAG = CustomView.class.getSimpleName();

    /**
     * 显示的文本
     */
    private String mText;
    /**
     * 字体大小
     */
    private int mTextSize;

    public CustomView(Context context) {
        super(context);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CustomView);
        initAttr(context, typedArray);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        initAttr(context, typedArray);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
        initAttr(context, typedArray);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, defStyleRes);
        initAttr(context, typedArray);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    private void initAttr(Context context, TypedArray typedArray) {

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30f);
        textPaint.setARGB(255, 56, 167, 150);
        textPaint.setAntiAlias(true);
        canvas.drawText("D_clock爱吃葱花", 0, 0, textPaint);
        Paint linePaint = new Paint();
        linePaint.setARGB(255, 56, 167, 150);
        linePaint.setStrokeWidth(10f);
        canvas.drawLine(5, 250, 250, 250, linePaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //自定义View系统默认是EXACTLY模式（具体值或者match_parent），其他模式 AT_MOST（wrap_content）、UNSPECIFIED 需要自行实现
        int measuredWidth = measureSize(widthMeasureSpec);
        int measuredHeight = measureSize(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        Log.i(TAG, "onFocusChanged");
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.i(TAG, "onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 测量获取当前View设置的实际尺寸大小
     *
     * @param measureSpec
     * @return
     */
    private int measureSize(int measureSpec) {
        int size = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
        }
        return size;

    }
}
