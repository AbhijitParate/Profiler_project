package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by siddhartha on 9/30/15.
 */
public class BatteryRem extends Activity {

    private SeekBar sb;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_rem);

        sb = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView1);

        textView.setText("Covered: " + sb.getProgress() + "/" + sb.getMax());

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;



            @Override

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                progress = progresValue;


            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {


            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Covered: " + progress + "/" + seekBar.getMax());


            }

        });

    }
}