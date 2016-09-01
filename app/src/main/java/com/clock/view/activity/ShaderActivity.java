package com.clock.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.clock.view.R;

/**
 * Shader Demo Code
 *
 * @author Clock
 * @since 2016-08-10
 */
public class ShaderActivity extends AppCompatActivity {

    private ImageView mTargetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader);

        mTargetView = (ImageView) findViewById(R.id.iv_target);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.shader, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.bitmap_shader) {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            Bitmap bt = BitmapFactory.decodeResource(getResources(), R.mipmap.evernote);
            BitmapShader bitmapShader = new BitmapShader(bt, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(bitmapShader);
            int width = mTargetView.getWidth();
            int height = mTargetView.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(255, 45, 99, 56);
            canvas.drawRect(0, 0, width / 2, height / 2, paint);
            mTargetView.setImageBitmap(bitmap);

        } else if (itemId == R.id.compose_shader) {
            Paint paint = new Paint();
            int width = mTargetView.getWidth();
            int height = mTargetView.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(255, 45, 99, 56);
            paint.setColor(0xFF853456);
            RadialGradient radialGradient = new RadialGradient(width / 2, height / 2, 300, Color.GREEN, Color.BLUE, Shader.TileMode.MIRROR);
            SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2, Color.GREEN, Color.BLUE);
            ComposeShader composeShader = new ComposeShader(radialGradient, sweepGradient, PorterDuff.Mode.MULTIPLY);
            paint.setShader(composeShader);
            canvas.drawRect(0, 0, width, height, paint);
            mTargetView.setImageBitmap(bitmap);

        } else if (itemId == R.id.linear_gradient) {
            Paint paint = new Paint();
            int width = mTargetView.getWidth();
            int height = mTargetView.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(255, 45, 99, 56);
            paint.setColor(0xFF853456);
            LinearGradient linearGradient = new LinearGradient(0, 0, width, height, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
            canvas.drawRect(0, 0, width, height, paint);
            mTargetView.setImageBitmap(bitmap);

        } else if (itemId == R.id.radial_gradient) {
            Paint paint = new Paint();
            int width = mTargetView.getWidth();
            int height = mTargetView.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(255, 45, 99, 56);
            paint.setColor(0xFF853456);
            RadialGradient radialGradient = new RadialGradient(width / 2, height / 2, 300, Color.GREEN, Color.BLUE, Shader.TileMode.MIRROR);
            paint.setShader(radialGradient);
            canvas.drawRect(0, 0, width, height, paint);
            mTargetView.setImageBitmap(bitmap);

        } else if (itemId == R.id.sweep_gradient) {
            Paint paint = new Paint();
            int width = mTargetView.getWidth();
            int height = mTargetView.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(255, 45, 99, 56);
            paint.setColor(0xFF853456);
            SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2, Color.GREEN, Color.BLUE);
            paint.setShader(sweepGradient);
            canvas.drawRect(0, 0, width, height, paint);
            mTargetView.setImageBitmap(bitmap);

        }
        return super.onOptionsItemSelected(item);
    }

}
