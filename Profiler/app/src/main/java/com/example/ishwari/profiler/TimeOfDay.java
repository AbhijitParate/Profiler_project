package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by siddhartha on 9/30/15.
 */

public class TimeOfDay extends MainActivity {

    TimePicker timePicker1;
    TimePicker timePicker2;
    TextView time;
    TextView time_2;
    Calendar calendar, calendar2,calendar3;
    String format = "";

    int hour1;
    long timeDiff;
    int min1;
    private AudioManager audio;
    private PendingIntent pendingIntentam;
    private PendingIntent pentdingIntentpm;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private WifiManager wifi;
    private BluetoothAdapter bluetoothAdapter;
    private SharedPreferences shP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_of_day);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        time = (TextView) findViewById(R.id.textView1);

        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        hour1= calendar.get(Calendar.HOUR_OF_DAY);
        min1 = calendar.get(Calendar.MINUTE);


        shP = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        showTimeFrom(hour, min);
    }

    public void setTimeFrom(View view) {

        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        calendar2.set(Calendar.HOUR_OF_DAY,hour);
        calendar2.set(Calendar.MINUTE,min);
         timeDiff=(calendar2.getTimeInMillis()- calendar.getTimeInMillis());

        Log.d("Time Difference",String.valueOf(Math.abs(timeDiff)));
        showTimeFrom(hour, min);
     }


    public void showTimeFrom(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        time.setText(new StringBuilder().append(" From: ").append(hour).append(" : ").append(min)
                .append(" ").append(format));

        SharedPreferences.Editor editor = shP.edit();
        editor.putString("Time", String.valueOf(time.getText()));

        editor.apply();

    }





    public void scheduleAlarm(View V)
    {

        Intent intentAlarm = new Intent(this, AlarmReciever.class);
        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //set the alarm for particular time from time picker and repeat daily at the same time
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,Math.abs(timeDiff),AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

    }




}

