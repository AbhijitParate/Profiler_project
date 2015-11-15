package com.example.ishwari.profiler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * Created by siddhartha on 10/21/15.
 */
public class GridViewAdapter extends BaseAdapter {

    //String [] toasts = {"Wifi On", "Wifi Off", "High brightness","Medium brightness", "Low brightness", "Airplane mode off", "Airplane mode on", "Bluetooth on", "Bluetooth off",
    //""};

    private Context mContext;

//    private List<Integer> mStates = new ArrayList<Integer>();

    public GridViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIdOff.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//
//        if (convertView == null) {
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//
//        }
//        else
//        {
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageResource(mThumbIdOff[position]);
//        return imageView;

//        View gridItem;

        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);

        convertView = layoutInflater.inflate(R.layout.grid_layout, parent, false);

//        final ImageButton imageButtonOn = (ImageButton) convertView.findViewById(R.id.imageView_grid_off);
//        ImageButton imageButtonOff = (ImageButton) convertView.findViewById(R.id.imageView_grid_off);
        ToggleButton toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_button);
        toggleButton.setLayoutParams(new GridView.LayoutParams(200, 200));
        toggleButton.setPadding(8, 8, 8, 8);
        toggleButton.setText("Wifi");
        toggleButton.setBackgroundResource(mThumbIdOn[position]);
//        boolean clicked = true;
//        imageButtonOn.setImageResource(mThumbIdOff[position]);
        toggleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                if (wifi on)
                    wifi off
                else
                    wifi on
                 */
            }
        });
        return toggleButton;

    }



    public Integer[] mThumbIdOn = {
            R.drawable.ic_signal_wifi_4_bar_24dp,
            R.drawable.ic_brightness_high_24dp,
            R.drawable.ic_airplanemode_off_24dp,
            R.drawable.ic_bluetooth_24dp,
            R.drawable.ic_volume_off_24dp,
            R.drawable.ic_screen_lock_rotation_24dp,
            R.drawable.ic_vibration_24dp,
    };

    public Integer[] mThumbIdOff = {
            R.drawable.ic_signal_wifi_off_24dp,
            R.drawable.ic_brightness_medium_24dp,
//            R.drawable.ic_brightness_low_24dp,
            R.drawable.ic_airplanemode_on,
            R.drawable.ic_bluetooth_disabled_24dp,
            R.drawable.ic_volume_up_24dp,
            R.drawable.ic_screen_rotation_24dp,
            R.drawable.ic_swap_vert_24dp
    };



}
