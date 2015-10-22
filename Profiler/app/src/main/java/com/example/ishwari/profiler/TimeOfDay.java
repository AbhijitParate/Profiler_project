package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by siddhartha on 9/30/15.
 */

public class TimeOfDay extends MainActivity {

    TimePicker timePicker1;
    TimePicker timePicker2;
    TextView time;
    TextView time_2;
    Calendar calendar;
    String format = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_of_day);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        time = (TextView) findViewById(R.id.textView1);
        time_2 = (TextView) findViewById(R.id.textView2);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

    }

    public void setTimeFrom(View view) {

        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }
    public void setTimeTo(View view) {

        int hour = timePicker2.getCurrentHour();
        int min = timePicker2.getCurrentMinute();
        showTime(hour, min);
    }



    public void showTime(int hour, int min) {
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
        time_2.setText(new StringBuilder().append(" From: ").append(hour).append(" : ").append(min)
                .append(" ").append(format));

    }
}

