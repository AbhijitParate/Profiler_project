package com.example.ishwari.profiler;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by siddhartha on 11/19/15.
 */
public class AlarmReciever extends BroadcastReceiver {


   private Context mContext;


    private AudioManager audio;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        mContext=context;

        // Turn Wifi off
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);

        //Turn Phone Ringer to Silent
        audio = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        //Turn Bluetooth on
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.disable();

        //Set Brightness to 50/255
        setBrightness(50);

        Toast.makeText(context, "Alarm Triggered and actions performed", Toast.LENGTH_LONG).show();
    }

    //Set Brightness
    private void setBrightness(int brightness) {
        android.provider.Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        android.provider.Settings.System.putInt(mContext.getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                brightness);
    }

}
