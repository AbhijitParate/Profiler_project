package com.example.ishwari.profiler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


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
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPrefs = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return START_STICKY;
    }


    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            int prefBatteryLevel = mPrefs.getInt("seekbar", 0);
            Log.d("Sharedpref Batery: ", Integer.toString(prefBatteryLevel));
            int currBatteryLevel = i.getIntExtra("level", 0);
            Log.d("Current Battery:", Integer.toString(currBatteryLevel));

            if (prefBatteryLevel >= currBatteryLevel) { //80 >= 75

                setBrightness(30);
                setRingerOff();
                setWiFiOff();
               setScreenTimeOut(15000);
            }

        }
    };

    private void setScreenTimeOut(int timeOut) {
        android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, timeOut);
    }

    private void setWiFiOff() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }

    public void setRingerOff() {
        AudioManager alarmManager;
        alarmManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        alarmManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
  private void setBrightness(int brightness) {
        android.provider.Settings.System.putInt(getApplication().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        android.provider.Settings.System.putInt(getApplication().getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                brightness);
    }

}