package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
/**
 * Created by siddhartha on 11/18/15.
 */
public class ScheduleActivity extends AppCompatActivity{


        private AudioManager audio;
        private PendingIntent pendingIntentam;
        private PendingIntent pentdingIntentpm;
        private AlarmManager alarmMgr;
        private PendingIntent alarmIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context=ScheduleActivity.this;

    alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(context, AlarmReceiver.class);
    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    // Set the alarm to start at 21:32 PM
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 21);
    calendar.set(Calendar.MINUTE, 32);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
    AlarmManager.INTERVAL_DAY, alarmIntent);

}