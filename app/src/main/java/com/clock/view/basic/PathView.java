package com.clock.view.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Clock on 2016/8/30.
 */
public class PathView extends View {
    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xffff0000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);

        Path path = new Path();
        path.lineTo(300, 300);
        canvas.drawPath(path, paint);

        paint.setColor(0xff47c7b6);
        path.reset();
        path.moveTo(300, 300);
        path.lineTo(0, 300);
        canvas.drawPath(path, paint);

        paint.setColor(0xff47c726);
        path.reset();
        path.moveTo(300, 300);
        path.quadTo(550, 550, 600, 350);//二阶贝塞尔曲线
        canvas.drawPath(path, paint);

        paint.setColor(0xff474226);
        path.reset();
        path.moveTo(300, 300);
        path.cubicTo(550, 550, 600, 600, 600, 100);//三阶贝塞尔曲线
        canvas.drawPath(path, paint);

    }
}
