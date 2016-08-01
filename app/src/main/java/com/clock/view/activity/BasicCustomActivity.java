package com.clock.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.clock.view.R;

/**
 * Basic custom View or ViewGroup Training Demo
 *
 * @author Clock
 */
public class BasicCustomActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_custom);

        findViewById(R.id.btn_custom_view).setOnClickListener(this);
        findViewById(R.id.btn_custom_viewgroup).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_custom_view) {
            Intent intent = new Intent(this, CustomViewActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.btn_custom_viewgroup) {
            Intent intent = new Intent(this, CustomViewGroupActivity.class);
            startActivity(intent);

        }
    }
}
