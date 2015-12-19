package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by siddhartha on 9/30/15.
 */
public class BatteryRem extends Activity {

    private SeekBar sb;
    private TextView textView;
    Context context;
    int progress = 0;
    private SharedPreferences mValuePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_rem);


        sb = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView1);
        mValuePrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);


        textView.setText("Selected: " + sb.getProgress() + "%");


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {

                progress = progresValue;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                textView.setText("Selected: " + progress + "%");
                progress = seekBar.getProgress();
                //Saving values to shared preferences
                SharedPreferences.Editor editor = mValuePrefs.edit();
                editor.putInt("seekbar", progress);
                Log.d("SeekBAR: ",Integer.toString(progress));
                editor.apply();

            }
        });

    }

    }