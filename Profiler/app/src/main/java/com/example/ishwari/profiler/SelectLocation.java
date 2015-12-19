package com.example.ishwari.profiler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ishwari on 11/18/15.
 */
public class SelectLocation extends Activity {

    Button save_button;
    Button set_pref;
    SharedPreferences latlngPref;
    public String Profile,lat,lan,Addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);
        save_button = (Button) findViewById(R.id.button_save_location);

        TextView Location = (TextView) findViewById(R.id.TVLocation);
        TextView Title = (TextView) findViewById(R.id.TVTitle);


        Intent receivedIntent = getIntent();
        lat = receivedIntent.getStringExtra("Lat");
        lan = receivedIntent.getStringExtra("Lan");

        LatLng latlng=new LatLng(Double.parseDouble(lat),Double.parseDouble(lan));

        Log.d("latlng", "Latitude="+String.valueOf(latlng.latitude)+" "+"Longitude="+String.valueOf(latlng.longitude));
         Addr = receivedIntent.getStringExtra("Addr");
        Profile = receivedIntent.getStringExtra("Profile");

        findViewById(R.id.button_save_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latlngPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = latlngPref.edit();
                editor.putString(Profile+"Name", Profile);
                editor.putFloat(Profile+"Longitude", Float.parseFloat(lan));
                editor.putString(Profile+"Address",Addr);
                editor.putFloat(Profile+"Latitude", Float.parseFloat(lat));
                editor.apply();

                latlngPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                Toast.makeText(getApplicationContext(), "SharedpRefCheck" + latlngPref.getString("Name", null), Toast.LENGTH_SHORT).show();
            }
        });



        Log.d("Lat : ", lat);
        Log.d("Lan : " , lan);
        Log.d("Addr : " ,  Addr);
        Log.d("Profile : " ,  Profile);


        Toast.makeText(this, lat + " " + lan , Toast.LENGTH_SHORT).show();

        Location.setText("Location : " + Addr );
        Title.setText("Name : " + Profile);



    }



}
