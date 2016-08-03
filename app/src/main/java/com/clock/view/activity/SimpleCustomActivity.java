package com.clock.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.clock.view.R;

/**
 * Simple Custom View Demo
 *
 * @author Clock
 * @since 2016-08-02
 */
public class SimpleCustomActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_custom);

        findViewById(R.id.btn_tv).setOnClickListener(this);
        findViewById(R.id.btn_canvas).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_tv) {
            Intent intent = new Intent(this, SimpleTextViewActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.btn_canvas) {
            Intent intent = new Intent(this, SimpleCanvasActivity.class);
            startActivity(intent);
        }
    }
}
