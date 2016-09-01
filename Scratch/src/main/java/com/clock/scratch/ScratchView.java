package com.clock.scratch;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
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
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

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
     * 默认擦除比例
     */
    private final static int DEFAULT_PERCENT = 70;
    /**
     * 最大擦除比例
     */
    private final static int MAX_PERCENT = 100;

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
     * 蒙层的颜色
     */
    private int mMaskColor = DEFAULT_MASKER_COLOR;
    /**
     * 水印的id
     */
    private int mWatermarkResId;
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
    /**
     * 擦除尺寸大小
     */
    private float mEraseSize;
    /**
     * 擦除效果起始点的x坐标
     */
    private float mStartX;
    /**
     * 擦除效果起始点的y坐标
     */
    private float mStartY;
    /**
     * 最小滑动距离
     */
    private int mTouchSlop;
    /**
     * 完成擦除
     */
    private boolean mIsCompleted = false;
    /**
     * 最大擦除比例
     */
    private int mMaxPercent = DEFAULT_PERCENT;
    /**
     * 当前擦除比例
     */
    private int mPercent = 0;

    private EraseStatusListener mEraseStatusListener;

    public ScratchView(Context context) {
        super(context);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.ScratchView);
        init(typedArray);
    }

    public ScratchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchView);
        init(typedArray);
    }

    public ScratchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchView, defStyleAttr, 0);
        init(typedArray);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScratchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchView, defStyleAttr, defStyleRes);
        init(typedArray);
    }

    private void init(TypedArray typedArray) {
        mMaskColor = typedArray.getColor(R.styleable.ScratchView_maskColor, DEFAULT_MASKER_COLOR);
        mWatermarkResId = typedArray.getResourceId(R.styleable.ScratchView_watermark, -1);
        mEraseSize = typedArray.getFloat(R.styleable.ScratchView_eraseSize, DEFAULT_ERASER_SIZE);
        mMaxPercent = typedArray.getInt(R.styleable.ScratchView_maxPercent, DEFAULT_PERCENT);
        typedArray.recycle();

        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);//抗锯齿
        mMaskPaint.setDither(true);//防抖
        setMaskColor(mMaskColor);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);

        if (mWatermarkResId != -1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mWatermarkResId);
            mWatermark = new BitmapDrawable(bitmap);
            mWatermark.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }

        mErasePaint = new Paint();
        mErasePaint.setAntiAlias(true);
        mErasePaint.setDither(true);
        mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//设置擦除效果
        mErasePaint.setStyle(Paint.Style.STROKE);
        mErasePaint.setStrokeCap(Paint.Cap.ROUND);//设置笔尖形状，让绘制的边缘圆滑
        setEraserSize(mEraseSize);

        mErasePath = new Path();

        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();

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

    /**
     * 设置最大的擦除比例
     *
     * @param max 大于0，小于等于100
     */
    public void setMaxPercent(int max) {
        if (max > 100 || max <= 0) {
            return;
        }
        this.mMaxPercent = max;
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
                startErase(event.getX(), event.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                erase(event.getX(), event.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                stopErase();
                invalidate();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createMasker(w, h);
    }

    /**
     * 创建蒙层
     *
     * @param width
     * @param height
     */
    private void createMasker(int width, int height) {
        mMaskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);
        Rect rect = new Rect(0, 0, width, height);
        mMaskCanvas.drawRect(rect, mMaskPaint);//绘制生成和控件大小一致的遮罩 Bitmap

        if (mWatermark != null) {
            Rect bounds = new Rect(rect);
            mWatermark.setBounds(bounds);
            mWatermark.draw(mMaskCanvas);
        }
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
     *
     * @param x
     * @param y
     */
    private void startErase(float x, float y) {
        mErasePath.reset();
        mErasePath.moveTo(x, y);
        this.mStartX = x;
        this.mStartY = y;
    }

    /**
     * 擦除
     *
     * @param x
     * @param y
     */
    private void erase(float x, float y) {
        int dx = (int) Math.abs(x - mStartX);
        int dy = (int) Math.abs(y - mStartY);
        if (dx >= mTouchSlop || dy >= mTouchSlop) {
            this.mStartX = x;
            this.mStartY = y;

            mErasePath.lineTo(x, y);
            mMaskCanvas.drawPath(mErasePath, mErasePaint);
            onErase();

            mErasePath.reset();
            mErasePath.moveTo(mStartX, mStartY);
        }
    }

    private void onErase() {
        int width = getWidth();
        int height = getHeight();
        new AsyncTask<Integer, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(Integer... params) {
                int width = params[0];
                int height = params[1];
                int pixels[] = new int[width * height];
                mMaskBitmap.getPixels(pixels, 0, width, 0, 0, width, height);//获取覆盖图层中所有的像素信息，stride用于表示一行的像素个数有多少

                float erasePixelCount = 0;//擦除的像素个数
                float totalPixelCount = width * height;//总像素个数

                for (int pos = 0; pos < totalPixelCount; pos++) {
                    if (pixels[pos] == 0) {//透明的像素值为0
                        erasePixelCount++;
                    }
                }

                int percent = 0;
                if (erasePixelCount >= 0 && totalPixelCount > 0) {
                    percent = Math.round(erasePixelCount * 100 / totalPixelCount);
                    publishProgress(percent);
                }

                return percent >= mMaxPercent;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                mPercent = values[0];
                onPercentUpdate();
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result && !mIsCompleted) {//标记擦除，并完成回调
                    mIsCompleted = true;
                    if (mEraseStatusListener != null) {
                        mEraseStatusListener.onCompleted(ScratchView.this);
                    }
                }
            }

        }.execute(width, height);
    }

    /**
     * 停止擦除
     */
    private void stopErase() {
        this.mStartX = 0;
        this.mStartY = 0;
        mErasePath.reset();
    }

    private void onPercentUpdate() {
        if (mEraseStatusListener != null) {
            mEraseStatusListener.onProgress(mPercent);
        }
    }

    /**
     * 设置擦除监听器
     *
     * @param listener
     */
    public void setEraseStatusListener(EraseStatusListener listener) {
        this.mEraseStatusListener = listener;
    }

    /**
     * 重置为初始状态
     */
    public void reset() {
        mIsCompleted = false;

        int width = getWidth();
        int height = getHeight();
        createMasker(width, height);
        invalidate();

        onErase();
    }

    /**
     * 清除整个图层
     */
    public void clear() {
        int width = getWidth();
        int height = getHeight();
        mMaskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);
        Rect rect = new Rect(0, 0, width, height);
        mMaskCanvas.drawRect(rect, mErasePaint);
        invalidate();

        onErase();
    }


    /**
     * 擦除状态监听器
     */
    public static interface EraseStatusListener {

        /**
         * 擦除进度
         *
         * @param percent 进度值，大于0，小于等于100；
         */
        public void onProgress(int percent);

        /**
         * 擦除完成回调函数
         *
         * @param view
         */
        public void onCompleted(View view);
    }
}
