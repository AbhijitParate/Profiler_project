package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddhartha on 9/30/15.
 */
public class BatteryRem extends Activity {

    private SeekBar sb;
    private TextView textView;
    private PopupWindow popup;
    private Button button;
    GridViewAdapter gview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_rem);

        sb = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView1);


        textView.setText("Selected: " + sb.getProgress() + "%");
        button= (Button)findViewById(R.id.button_set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // custom dialog
                final Dialog dialog = new Dialog(BatteryRem.this, R.style.AppTheme);
                dialog.setContentView(R.layout.preferences);
                dialog.setTitle("Preferences");


                GridView gridView = (GridView) dialog.findViewById(R.id.gridView);

                gridView.setAdapter(new GridViewAdapter(getApplication()));
                gridView.setNumColumns(4);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                dialog.show();
                Button closeButton = (Button)dialog.findViewById(R.id.button_cancel);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

              //
            }
        });


    sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

                                  {

                                      int progress = 0;


                                      @Override

                                      public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                                          progress = progresValue;


                                      }

                                      @Override

                                      public void onStartTrackingTouch(SeekBar seekBar) {


                                      }

                                      @Override
                                      public void onStopTrackingTouch(SeekBar seekBar) {
                                          textView.setText("Selected: " + progress + "%");


                                      }

                                  }

    );

}

    }