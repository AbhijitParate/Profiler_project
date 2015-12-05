package com.example.ishwari.profiler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyService extends Service {

    private SharedPreferences mPrefs;
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){


        try {
            while (true) {
                Thread.sleep(1000);
                Log.d("Thread : ", "check");
                int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int batteryHealth = intent.getIntExtra(
                        BatteryManager.EXTRA_HEALTH,
                        BatteryManager.BATTERY_HEALTH_UNKNOWN);
                float batteryPercentage = ((float) batteryLevel / (float) maxLevel) * 100;
                int seekbar = mPrefs.getInt("seekbar", 0);
                Log.d("Service Shared Battery:",Integer.toString(seekbar));
                Log.d("Service Curr Battery:",Float.toString(batteryPercentage));




            }
        }
        catch (Exception e) {
            return START_STICKY;
        }



    }
}
