package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
//import android.support.v7.app.ActionBarActivity;

/**
 * Created by siddhartha on 9/30/15.
 */
public class Triggers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_triggers);

        findViewById(R.id.location_trigger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Triggers.this, MapViewer.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Map View", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        findViewById(R.id.time_trigger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Triggers.this, TimeOfDay.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Time Of Day", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        findViewById(R.id.battery_trigger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Triggers.this, BatteryRem.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Battery", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
