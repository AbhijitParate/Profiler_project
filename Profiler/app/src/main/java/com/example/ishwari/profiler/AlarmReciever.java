package com.example.ishwari.profiler;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
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

        // here you can start an activity or service depending on your need
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);

        audio = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);

        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);


        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable();

        // Show the toast  like in above screen shot
        Toast.makeText(context, "Alarm Triggered and actions performed", Toast.LENGTH_LONG).show();
    }

}
