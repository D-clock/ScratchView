package com.clock.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.clock.view.activity.BasicCustomActivity;
import com.clock.view.activity.ScratchDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_basic_custom).setOnClickListener(this);
        findViewById(R.id.btn_scratch_demo).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_basic_custom) {
            Intent intent = new Intent(this, BasicCustomActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.btn_scratch_demo) {
            Intent intent = new Intent(this, ScratchDemoActivity.class);
            startActivity(intent);
        }

    }
}
