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
    private PopupWindow popup;
    private Button button;
    GridViewAdapter gview;
    Context context;
    int progress = 0;
    private SharedPreferences mValuePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_rem);


        super.onCreate(savedInstanceState);
        AlarmReceiverTest mBatteryLevelReceiver = new AlarmReceiverTest();
        registerReceiver(mBatteryLevelReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));


        sb = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView1);
        mValuePrefs = getSharedPreferences("prefs", MODE_PRIVATE);


        textView.setText("Selected: " + sb.getProgress() + "%");
        button= (Button)findViewById(R.id.button_set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // custom dialog
                final Dialog dialog = new Dialog(BatteryRem.this, R.style.AppTheme);
                dialog.setContentView(R.layout.preferences);
                dialog.setTitle("Preferences");


                GridView gridView = (GridView) dialog.findViewById(R.id.gridView);

               /* View imageView = null;
                */

                gridView.setAdapter(new GridViewAdapter(getApplication()));
                gridView.setNumColumns(4);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                dialog.show();
                Button closeButton = (Button)dialog.findViewById(R.id.button_cancel);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //
            }
        });



        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

                                      {




                                          @Override

                                          public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                                              progress = progresValue;


                                          }

                                          @Override

                                          public void onStartTrackingTouch(SeekBar seekBar) {


                                          }

                                          @Override
                                          public void onStopTrackingTouch(SeekBar seekBar) {
                                          textView.setText("Selected: " + progress + "%");
                                              progress = seekBar.getProgress();
                                              //Save value in shared prefs
                                              SharedPreferences.Editor editor = mValuePrefs.edit();
                                              editor.putInt("seekbar", progress);
                                              editor.commit();


                                      }

                                  }

    );

}

//    public void scheduleAlarmBattery(View V)
//    {
//
//
//
//        // create the object
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intentAlarm = new Intent(this, AlarmReceiverTest.class);
//        //set the alarm for particular time
//        alarmManager.set(AlarmManager.RTC_WAKEUP, progress, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//        Toast.makeText(BatteryRem.this, "Settings will be activated in a minute", Toast.LENGTH_LONG).show();
//
//    }


    }