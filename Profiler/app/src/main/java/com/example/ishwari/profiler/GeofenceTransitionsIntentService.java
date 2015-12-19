package com.example.ishwari.profiler;

/**
 * Created by siddhartha on 12/2/15.
 *
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceTransitionsIntentService extends IntentService {

    protected static final String TAG = "GeofenceTransitionsIS";
    Handler toastHandler;

    public GeofenceTransitionsIntentService() {
        super(TAG);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        toastHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {

            String errorMessage = GeofenceErrorMessages.getErrorString(this, geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();


        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition, triggeringGeofences);
            sendNotification(geofenceTransitionDetails);
            if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                onEnter(geofenceTransitionDetails);
            } else {
                onExit(geofenceTransitionDetails);
            }

            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
    }


    /**Perform these actions on entering geofence
        -Set Brightness to 100/255
        -Set Phone Ringer to Vibrate mode
        -Set Screen Time out ot 25 seconds
        -Turn Wifi on
     */
    private void onEnter(final String geofenceTransitionDetails) {
        toastHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), geofenceTransitionDetails, Toast.LENGTH_SHORT).show();
            }
        });
        setBrightness(100);
        setRingerOff();
        setScreenTimeOut(25000);
        setWiFiOn();

    }

    /**Perform these actions on exiting geofence
        -Set Brightness to 200/255
        -Set Phone Ringer to Normal mode
        -Set Screen Time out ot 15 seconds
        -Turn Wifi Off
     */
    private void onExit(final String geofenceTransitionDetails) {
        toastHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GeofenceTransitionsIntentService.this , geofenceTransitionDetails, Toast.LENGTH_SHORT).show();
            }
        });
        setBrightness(200);
        setRingerOn();
        setScreenTimeOut(15000);
        setWiFiOff();



    }

    /**
     * Gets transition details and returns them as a formatted string.
     * @param geofenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return The transition details formatted as String.
     */
    private String getGeofenceTransitionDetails(int geofenceTransition, List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.app_logo)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }

    //Set screen timeout duration
    private void setScreenTimeOut(int timeOut) {
        android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, timeOut);
    }

    //Turn Wifi off
    private void setWiFiOff() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }

    //Turn Wifi on
    private void setWiFiOn() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
    }

    //Set phone Ringer to Vibrate
    public void setRingerOff() {
        AudioManager alarmManager;
        alarmManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        alarmManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    //Set Ringer to Normal
    public void setRingerOn(){
        AudioManager audioManager;
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    //Set Brightness
    private void setBrightness(int brightness) {
        android.provider.Settings.System.putInt(getApplication().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        android.provider.Settings.System.putInt(getApplication().getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                brightness);
    }

}

