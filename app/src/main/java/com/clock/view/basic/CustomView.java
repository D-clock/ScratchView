package com.clock.view.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
    private float mTextSize;
    /**
     * 绘制文字的笔
     */
    private TextPaint mTextPaint;

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
        mText = typedArray.getString(R.styleable.CustomView_text);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15f, metrics);
        mTextSize = typedArray.getDimension(R.styleable.CustomView_textSize, defaultSize);
        Log.i(TAG, "mTextSize: " + mTextSize);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setARGB(255, 56, 167, 150);
        mTextPaint.setAntiAlias(true);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);

        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if (!TextUtils.isEmpty(mText)) {
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            Log.i(TAG, "fontMetrics.top : " + fontMetrics.top);//baseline到文本最顶端的距离+为一些特殊字符预留了顶部空间
            Log.i(TAG, "fontMetrics.bottom : " + fontMetrics.bottom);//baseline到文本最底端的距离+为一些特殊字符预留了底部空间
            Log.i(TAG, "fontMetrics.leading : " + fontMetrics.leading);//行间距，表示上一行字符的descent到该行字符的ascent之间的距离
            Log.i(TAG, "fontMetrics.descent : " + fontMetrics.descent);//baseline到文本最顶端的距离
            Log.i(TAG, "fontMetrics.ascent : " + fontMetrics.ascent);//baseline到文本最底端的距离
            canvas.drawText(mText, 0, Math.abs(fontMetrics.ascent), mTextPaint);//设置baseline的坐标
        }
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//在 onDraw 前被调用，应该是 View 的尺寸大小发生改变时被调用，在于 Measure 之后
        Log.i(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
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
