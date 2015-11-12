package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

               /* View imageView = null;
                */

                gridView.setAdapter(new GridViewAdapter(getApplication()));
                gridView.setNumColumns(4);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                        switch (position)
                        {
                            case 0:
                                Toast.makeText(BatteryRem.this, "Wi-Fi On" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(BatteryRem.this, "Wi-Fi Off" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(BatteryRem.this, "High Brightness" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(BatteryRem.this, "Medium Brightness" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(BatteryRem.this, "Low Brightness" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                Toast.makeText(BatteryRem.this, "Airplane Mode Off " ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                Toast.makeText(BatteryRem.this, "Airplane Mode On" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 7:
                                Toast.makeText(BatteryRem.this, "Bluetooth On" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 8:
                                Toast.makeText(BatteryRem.this, "Bluetooth Off" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 9:
                                Toast.makeText(BatteryRem.this, "Vibrate" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 10:
                                Toast.makeText(BatteryRem.this, "Silent" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 11:
                                Toast.makeText(BatteryRem.this, "Loud" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 12:
                                Toast.makeText(BatteryRem.this, "Auto-rotate" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 13:
                                Toast.makeText(BatteryRem.this, "Lock Auto-rotate" ,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 14:
                                Toast.makeText(BatteryRem.this, "Mobile Data On" ,
                                        Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                    Toast.makeText(BatteryRem.this, "Wrong Input" ,
                                            Toast.LENGTH_SHORT).show();
                                break;
                        }


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