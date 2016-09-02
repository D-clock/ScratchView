package com.clock.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clock.scratch.ScratchView;
import com.clock.view.R;

/**
 * Just Scratch Demo Code
 *
 * @author Clock
 * @since 2016-08-26
 */
public class ScratchDemoActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private final static String TAG = ScratchDemoActivity.class.getSimpleName();

    private ScratchView mScratchView;
    private SeekBar mEraseSeekBar, mPercentSeekBar;
    private TextView mPercentView;
    private String mPercentFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_demo);

        mPercentView = (TextView) findViewById(R.id.tv_percent);
        mPercentFormat = getString(R.string.scratch_percent);

        mScratchView = (ScratchView) findViewById(R.id.scratch_view);
        mScratchView.setEraseStatusListener(new ScratchView.EraseStatusListener() {
            @Override
            public void onProgress(int percent) {
                String text = String.format(mPercentFormat, percent);
                mPercentView.setText(text);
            }

            @Override
            public void onCompleted(View view) {
                Toast.makeText(ScratchDemoActivity.this, "completed !", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);

        mEraseSeekBar = (SeekBar) findViewById(R.id.sb_erase_size);
        mEraseSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mScratchView.setEraserSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mPercentSeekBar = (SeekBar) findViewById(R.id.sb_max_percent);
        mPercentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mScratchView.setMaxPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        RadioGroup colorGroup = (RadioGroup) findViewById(R.id.rg_color);
        colorGroup.setOnCheckedChangeListener(this);
        RadioGroup watermarkGroup = (RadioGroup) findViewById(R.id.rg_watermark);
        watermarkGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_reset) {
            mScratchView.reset();
        } else if (viewId == R.id.btn_clear) {
            mScratchView.clear();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_red) {
            mScratchView.setMaskColor(0xffff0000);

        } else if (checkedId == R.id.rb_green) {
            mScratchView.setMaskColor(0xff00ff00);

        } else if (checkedId == R.id.rb_blue) {
            mScratchView.setMaskColor(0xff0000ff);

        } else if (checkedId == R.id.rb_origin) {
            mScratchView.setMaskColor(0xffcccccc);

        } else if (checkedId == R.id.rb_alipay) {
            mScratchView.setWatermark(R.mipmap.alipay);

        } else if (checkedId == R.id.rb_no) {
            mScratchView.setWatermark(-1);

        }
        mScratchView.reset();
    }
}
