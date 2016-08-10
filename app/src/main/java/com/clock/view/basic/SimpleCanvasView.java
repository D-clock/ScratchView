package com.clock.view.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Canvas 练习代码
 * <p/>
 * Created by Clock on 2016/8/3.
 */
public class SimpleCanvasView extends View {

    private final static String TAG = SimpleCanvasView.class.getSimpleName();

    public SimpleCanvasView(Context context) {
        super(context);
    }

    public SimpleCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleCanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 50, 78, 95);//绘制整个Canvas的背景色

        drawText(canvas);
        drawPoint(canvas);
        drawRect(canvas);
        drawOval(canvas);

    }

    /**
     * 在Canvas上绘制各种各样的文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30f);
        textPaint.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        //普通文本
        textPaint.setColor(0xff568532);
        canvas.drawText("普通文本", 0, Math.abs(fontMetrics.ascent), textPaint);

        //向下偏移的文本
        canvas.save();//保存原先的Canvas绘制坐标系
        canvas.translate(0, 50);
        textPaint.setColor(0xff261232);
        canvas.drawText("向下偏移的文本", 0, Math.abs(fontMetrics.ascent), textPaint);
        canvas.restore();//恢复原先的Canvas绘制坐标系

        //向右偏移的文本
        canvas.save();
        canvas.translate(200, 0);
        textPaint.setColor(0xff516232);
        canvas.drawText("向右偏移的文本", 0, Math.abs(fontMetrics.ascent), textPaint);
        canvas.restore();

        //文本右对齐
        canvas.save();
        canvas.translate(100, 100);
        textPaint.setTextAlign(Paint.Align.RIGHT);//表示drawText的x坐标是文本的结束位置的坐标
        textPaint.setColor(0xff257232);
        canvas.drawText("文本右对齐", 0, Math.abs(fontMetrics.ascent), textPaint);
        canvas.restore();

        //文本居中
        canvas.save();
        canvas.translate(0, 150);
        textPaint.setTextAlign(Paint.Align.CENTER);//表示drawText的x坐标是文本中央位置的坐标
        textPaint.setColor(0xff228932);
        canvas.drawText("文本居中", 0, Math.abs(fontMetrics.ascent), textPaint);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.restore();

        //文字加粗
        canvas.save();
        canvas.translate(0, 200);
        textPaint.setColor(0xff228932);
        textPaint.setFakeBoldText(true);
        canvas.drawText("文字加粗", 0, Math.abs(fontMetrics.ascent), textPaint);
        textPaint.setFakeBoldText(false);
        canvas.restore();

        //文字加下划线
        canvas.save();
        canvas.translate(0, 250);
        textPaint.setColor(0xff228932);
        textPaint.setUnderlineText(true);
        canvas.drawText("文字加下划线", 0, Math.abs(fontMetrics.ascent), textPaint);
        textPaint.setUnderlineText(false);
        canvas.restore();

        //文字加中划线
        canvas.save();
        canvas.translate(0, 300);
        textPaint.setColor(0xff228932);
        textPaint.setStrikeThruText(true);
        canvas.drawText("文字加中划线", 0, Math.abs(fontMetrics.ascent), textPaint);
        textPaint.setStrikeThruText(false);
        canvas.restore();

    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        int halfWidth = getMeasuredWidth() / 2;
        Paint paint = new Paint();
        paint.setColor(0xff238890);
        paint.setStrokeWidth(100);

        //绘制方点
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(halfWidth, 100, paint);

        //绘制圆点
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(halfWidth, 250, paint);

        //绘制方点，感觉和BUTT一样，目前没看到有啥区别
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(halfWidth, 400, paint);
    }

    private void drawRect(Canvas canvas) {

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        Paint paint = new Paint();
        paint.setColor(0xff365618);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);
        canvas.drawRect((width - 300) / 2, (height - 300) / 2, (width - 300) / 2 + 300, (height - 300) / 2 + 300, paint);

    }

    private void drawOval(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setColor(0xff990000);
        RectF oval = new RectF(0, 300, getMeasuredWidth(), 600);
        canvas.drawOval(oval, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = measureSize(heightMeasureSpec);
        int measuredWidth = measureSize(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureSize(int measureSpec) {
        int measuredSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            measuredSize = size;
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                measuredSize = Math.min(measuredSize, size);
            }
        }
        return measuredSize;
    }
}
