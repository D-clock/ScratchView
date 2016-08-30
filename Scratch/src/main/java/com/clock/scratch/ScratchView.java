package com.clock.scratch;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Clock on 2016/8/26.
 */
public class ScratchView extends View {

    private final static String TAG = ScratchView.class.getSimpleName();
    /**
     * 最小的橡皮擦尺寸大小
     */
    private final static float DEFAULT_ERASER_SIZE = 60f;
    /**
     * 默认蒙板的颜色
     */
    private final static int DEFAULT_MASKER_COLOR = 0xffcccccc;

    /**
     * 遮罩 Paint
     */
    private Paint mMaskPaint;
    /**
     * 产生遮罩效果的 Bitmap
     */
    private Bitmap mMaskBitmap;
    /**
     * 绘制遮罩的 Canvas
     */
    private Canvas mMaskCanvas;
    /**
     * 普通绘制 Bitmap 用的 Paint
     */
    private Paint mBitmapPaint;
    /**
     * 水印
     */
    private BitmapDrawable mWatermark;
    /**
     * 橡皮檫画笔
     */
    private Paint mErasePaint;
    /**
     * 擦除轨迹
     */
    private Path mErasePath;

    public ScratchView(Context context) {
        super(context);
        init();
    }

    public ScratchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScratchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScratchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);//抗锯齿
        mMaskPaint.setDither(true);//防抖
        setMaskColor(DEFAULT_MASKER_COLOR);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.veins);
        mWatermark = new BitmapDrawable(bitmap);
        mWatermark.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        mErasePaint = new Paint();
        mErasePaint.setAntiAlias(true);
        mErasePaint.setDither(true);
        mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//设置擦除效果
        mErasePaint.setStyle(Paint.Style.STROKE);
        mErasePaint.setStrokeCap(Paint.Cap.ROUND);//设置笔尖形状，让绘制的边缘圆滑
        setEraserSize(DEFAULT_ERASER_SIZE);

        mErasePath = new Path();
    }

    /**
     * 设置橡皮檫尺寸大小（默认大小是 60）
     *
     * @param eraserSize 橡皮檫尺寸大小
     */
    public void setEraserSize(float eraserSize) {
        mErasePaint.setStrokeWidth(eraserSize);
    }

    /**
     * 设置蒙板颜色
     *
     * @param color 十六进制颜色值，如：0xffff0000（不透明的红色）
     */
    public void setMaskColor(int color) {
        mMaskPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measureSize(widthMeasureSpec);
        int measuredHeight = measureSize(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mMaskBitmap, 0, 0, mBitmapPaint);//绘制图层遮罩
        canvas.drawPath(mErasePath, mErasePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startErase();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                erase();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                stopErase();
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMaskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);
        Rect rect = new Rect(0, 0, w, h);
        mMaskCanvas.drawRect(rect, mMaskPaint);//绘制生成和控件大小一致的遮罩 Bitmap

        Rect bounds = new Rect(rect);
        mWatermark.setBounds(bounds);
        mWatermark.draw(mMaskCanvas);
    }

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

    /**
     * 开始擦除
     */
    private void startErase() {

    }

    /**
     * 擦除
     */
    private void erase() {

    }

    /**
     * 停止擦除
     */
    private void stopErase() {

    }
}
