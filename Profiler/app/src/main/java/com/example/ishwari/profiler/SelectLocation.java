package com.example.ishwari.profiler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

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
    }

}
