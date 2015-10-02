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
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by siddhartha on 9/30/15.
 */

public class TimeOfDay extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_of_day);
    }
}


/*
public class TimeOfDay extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener,AppCompatActivity {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimeOfDay();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }


}*/