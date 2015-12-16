package com.example.ishwari.profiler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);
        save_button = (Button) findViewById(R.id.button_save_location);
        set_pref = (Button) findViewById(R.id.button_set_preferences);

        TextView Location = (TextView) findViewById(R.id.TVLocation);
        TextView Title = (TextView) findViewById(R.id.TVTitle);


        Intent receivedIntent = getIntent();
        String lat = receivedIntent.getStringExtra("Lat");
        String  lan = receivedIntent.getStringExtra("Lan");
        String Addr = receivedIntent.getStringExtra("Addr");
        String Profile = receivedIntent.getStringExtra("Profile");

        Log.d("Lat : " ,  lat);
        Log.d("Lan : " , lan);
        Log.d("Addr : " ,  Addr);
        Log.d("Profile : " ,  Profile);


        Toast.makeText(this, lat + " " + lan , Toast.LENGTH_SHORT).show();

        Location.setText("Location : " + Addr );
        Title.setText("Name : "+ Profile);



    }



}
