package com.example.ishwari.profiler;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by siddhartha on 12/17/15.
 */

public class GeoFencing implements ResultCallback<Status> {

    private static final float GEOFENCE_RADIUS_IN_METERS = 5000;
    private static final long  GEOFENCE_EXPIRATION_IN_MILLISECONDS = Geofence.NEVER_EXPIRE;
    public HashMap<String, LatLng> LANDMARKS = new HashMap<String, LatLng>();

    //Add geofences
    public void addFence(String name, LatLng latLng){
        LANDMARKS.put(name, latLng);
    }

    public void removeFence(String name){
        LANDMARKS.remove(name);
    }

    protected ArrayList<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;
    private static GoogleApiClient googleApiClient;
    private boolean geoFenceAdded = false;
    private String lat;

    private Context applicationContext;

    public GeoFencing(Context context){
        applicationContext = context;
        geofenceList = new ArrayList<Geofence>();
        geofencePendingIntent = null;
    }

    public void initialize(){
        LANDMARKS.put("JAPAN", new LatLng(134.7634766, 34.43909229999997));
        populateGeofenceList();
        buildGoogleApiClient();
    }

    public void start(){
        connectGoogleAPIClient();
        addGeofences();
    }

    public void stop(){
        removeGeofences();
        disconnectGoogleAPIClient();
    }


    //Step 1 : Build geoFence object and populate it with locations
    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : LANDMARKS.entrySet()) {
            geofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(entry.getValue().latitude, entry.getValue().longitude, GEOFENCE_RADIUS_IN_METERS)
                    .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());


            Log.d("Latitude", Double.toString(entry.getValue().latitude));
            Log.d("Longitude",Double.toString(entry.getValue().longitude));
        }
    }

    //Step 2 : build geoFencing request and initial triggers
    public GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    //Step 3
    public PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(applicationContext, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //Step 4
    public void addGeofences() {
        if (googleApiClient.isConnected()) {
            try {
                LocationServices.GeofencingApi
                        .addGeofences(googleApiClient, getGeofencingRequest(), getGeofencePendingIntent())
                        .setResultCallback(this);
                Toast.makeText(applicationContext, "GeoFences added.", Toast.LENGTH_SHORT).show();
            } catch (SecurityException securityException) {
                Log.e("GeoFencing", "Issues with permissions.", securityException);
            }
        } else {
            Toast.makeText(applicationContext, "Connecting to Google API client...", Toast.LENGTH_LONG).show();
        }
    }

    public void removeGeofences() {

        if (googleApiClient.isConnected()) {
            try {
                LocationServices.GeofencingApi
                        .removeGeofences(googleApiClient, getGeofencePendingIntent())
                        .setResultCallback(this);
                Toast.makeText(applicationContext, "GeoFences removed.", Toast.LENGTH_SHORT).show();
            } catch (SecurityException securityException) {
                Log.e("Geo Fencing", "Invalid location permission. " + "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
            }
        } else {
            Toast.makeText(applicationContext, "Not connected to Google Client.", Toast.LENGTH_SHORT).show();
        }
    }

    //Connect to APIClient
    public void connectGoogleAPIClient(){
        googleApiClient.connect();
    }

    //Disconnect from APIClient
    public void disconnectGoogleAPIClient(){
        googleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(applicationContext)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            if(!geoFenceAdded){
                geoFenceAdded = true;
                Log.d("GeoFence:","Added");
                  }else{
                geoFenceAdded = false;
                Log.d("GeoFence:","Removed");

            }
        } else {
            Toast.makeText(applicationContext, "GeoFence not working.", Toast.LENGTH_SHORT).show();
        }
    }


}

