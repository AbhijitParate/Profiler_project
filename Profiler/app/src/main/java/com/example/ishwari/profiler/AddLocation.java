package com.example.ishwari.profiler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by siddhartha on 10/28/15.
 */
public class AddLocation extends Activity {

    private ImageButton button,button1;
    private ImageView imageview;
    //private TextView textview,textview2,textview3,textview4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlocation);
        imageview = (ImageView) findViewById(R.id.imageView);

        TextView textview=(TextView)findViewById(R.id.txtview);
        TextView textview2=(TextView)findViewById(R.id.textHome);
        TextView textview3=(TextView)findViewById(R.id.textView3);
        TextView textview4=(TextView)findViewById(R.id.workTextView);
        TextView textview5 = (TextView) findViewById(R.id.textView5);

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocation.this, MapViewer.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Select Home Location", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        findViewById(R.id.workButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocation.this, MapViewer.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Select Work Place", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        findViewById(R.id.customButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocation.this, MapViewer.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Select Custom Place", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }



}
