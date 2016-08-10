package com.clock.view.activity;

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

        } else if (itemId == R.id.compose_shader) {

        } else if (itemId == R.id.linear_gradient) {

        } else if (itemId == R.id.radial_gradient) {

        } else if (itemId == R.id.sweep_gradient) {

        }
        return super.onOptionsItemSelected(item);
    }
}
