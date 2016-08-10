package com.clock.view.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * PorterDuffXfermode Training Code
 * <p/>
 * Created by Clock on 2016/8/9.
 */
public class SimpleXfermodeView extends View {

    private final static String TAG = SimpleXfermodeView.class.getSimpleName();

    public SimpleXfermodeView(Context context) {
        super(context);
    }

    public SimpleXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleXfermodeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //setLayerType(LAYER_TYPE_SOFTWARE, null);//如果混合模式没有正确使用，可以让调用此方法禁用掉View的GPU硬件加速，切换到软件渲染模式
        //clear(canvas);
        //clearNewLayer(canvas);
        srcIn(canvas);
    }

    /**
     * 使用 PorterDuffXfermode 的 CLEAR 模式
     *
     * @param canvas
     */
    private void clear(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawARGB(255, 229, 130, 133);

        paint.setColor(0xFFE8B36D);
        int circleRadius = getWidth() / 5;
        canvas.drawCircle(circleRadius, circleRadius, circleRadius, paint);

        setLayerType(LAYER_TYPE_SOFTWARE, null);//不加这个，CLEAR就会变成黑色的
        paint.setColor(0xFF75C22A);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Rect rect = new Rect(circleRadius, circleRadius, 3 * circleRadius, 3 * circleRadius);
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);
    }

    /**
     * 在Canvas新建的Layer上做CLEAR操作
     *
     * @param canvas
     */
    private void clearNewLayer(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawARGB(255, 229, 130, 133);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);//为Canvas创建一个新图层，图层是完全透明的

        paint.setColor(0xFFE8B36D);
        int circleRadius = getWidth() / 5;
        canvas.drawCircle(circleRadius, circleRadius, circleRadius, paint);

        paint.setColor(0xFF75C22A);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Rect rect = new Rect(circleRadius, circleRadius, 3 * circleRadius, 3 * circleRadius);
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    /**
     * 使用 PorterDuffXfermode 的 SRC_IN 模式
     *
     * @param canvas
     */
    private void srcIn(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawARGB(255, 229, 130, 133);

        //设置画布支持多图层
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.MATRIX_SAVE_FLAG);

        canvas.drawBitmap(makeCircle(), 0, 0, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(makeRect(), 0, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    private Bitmap makeCircle() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(0xFFE8B36D);
        int circleRadius = getWidth() / 5;
        canvas.drawCircle(circleRadius, circleRadius, circleRadius, paint);
        return bitmap;
    }

    private Bitmap makeRect() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int circleRadius = getWidth() / 5;
        paint.setColor(0xFF75C22A);
        Rect rect = new Rect(circleRadius, circleRadius, 3 * circleRadius, 3 * circleRadius);
        canvas.drawRect(rect, paint);
        return bitmap;
    }

}
