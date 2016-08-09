package com.clock.view.simple;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.clock.view.R;

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

        //clear(canvas);

        clearLayer(canvas);

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

        paint.setColor(0xFF75C22A);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Rect rect = new Rect(circleRadius, circleRadius, 3 * circleRadius, 3 * circleRadius);
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);
    }

    private void clearLayer(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawARGB(255, 229, 130, 133);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

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
}
