package com.example.ishwari.profiler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by siddhartha on 10/21/15.
 */
public class GridViewAdapter extends BaseAdapter {

    //String [] toasts = {"Wifi On", "Wifi Off", "High brightness","Medium brightness", "Low brightness", "Airplane mode off", "Airplane mode on", "Bluetooth on", "Bluetooth off",
    //""};

    private Context mContext;
    private static final int WHITE = 0;
    private static final int TEAL = 1;
    private static final int MAROON = 2;
    private List<Integer> mStates = new ArrayList<Integer>();

    public GridViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;


    }



    public Integer[] mThumbIds = {
           R.drawable.ic_signal_wifi_4_bar_24dp,
            R.drawable.ic_signal_wifi_off_24dp,
            R.drawable.ic_brightness_high_24dp,
            R.drawable.ic_brightness_medium_24dp,
            R.drawable.ic_brightness_low_24dp,
            R.drawable.ic_airplanemode_off_24dp,
            R.drawable.ic_airplanemode_on,
            R.drawable.ic_bluetooth_24dp,
            R.drawable.ic_bluetooth_disabled_24dp,
            R.drawable.ic_vibration_24dp,
            R.drawable.ic_volume_off_24dp,
            R.drawable.ic_volume_up_24dp,
            R.drawable.ic_screen_rotation_24dp,
            R.drawable.ic_screen_lock_rotation_24dp,
            R.drawable.ic_swap_vert_24dp

    };


}
