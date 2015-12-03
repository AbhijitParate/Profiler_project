package com.example.ishwari.profiler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;


/**
 * Created by siddhartha on 11/19/15.
 */

public class AlarmReceiverTest extends BroadcastReceiver {

    private Context mContext;
    private AudioManager audio;
    private Window window;
    private SharedPreferences mPrefs;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext=context;


        int batteryLevel = intent.getIntExtra(
                BatteryManager.EXTRA_LEVEL, 0);
        int maxLevel = intent
                .getIntExtra(BatteryManager.EXTRA_SCALE, 0);
        int batteryHealth = intent.getIntExtra(
                BatteryManager.EXTRA_HEALTH,
                BatteryManager.BATTERY_HEALTH_UNKNOWN);
        float batteryPercentage = ((float) batteryLevel / (float) maxLevel) * 100;
        audio = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
//        window= BatteryRem.getWindow();
        mPrefs = context.getSharedPreferences("prefs", context.MODE_PRIVATE);

        int seekbar = mPrefs.getInt("seekbar", 70);

        if(batteryPercentage<=seekbar){

            audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(false);
            Toast.makeText(context,"Power Saver Mode",Toast.LENGTH_LONG).show();
        }
    }
}
