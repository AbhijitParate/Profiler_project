package com.example.ishwari.profiler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;


import java.util.List;



public class MainActivity extends Activity {

    private List<String> itemList;
    private int nextItemNumber;
    private RecyclerView.Adapter adapter;
    private ImageButton fab;

    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;


    private boolean expanded = false;

    private View fabaction1;
    private View fabaction2;
    private View fabaction3;

    private float offset1;
    private float offset2;
    private float offset3;

    public SharedPreferences shPrefs;

    String homeProf,homeAddr;
    String workProf,workAddr,timeProf;
    String customProf,customAddr;
    Float latt,longg;
    Integer batteryLev;
    Double homeLat, homeLng;


    private GeoFencing geoFencing;



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //geoFencing.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        geoFencing.start();
        startService(new Intent(this, MyService.class));




    }

    @Override
    protected void onResume(){
        super.onResume();

        //Initialize geofences on resume
        geoFencing.start();

        cardArrayAdapter.removeAllCards();

        Card card = new Card(homeProf,homeAddr);
        Card card1=new Card(workProf,workAddr);
        Card card2=new Card(customProf,customAddr);
        Card card3=new Card("Battery",Integer.toString(batteryLev));
        Card card4=new Card("Time ",timeProf);

        cardArrayAdapter.add(card);
        cardArrayAdapter.add(card1);
        cardArrayAdapter.add(card2);
        cardArrayAdapter.add(card3);
        cardArrayAdapter.add(card4);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildFab();

        initializeGeoFencing();
        addFencesFromSharedPref();

        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);



        listView.setAdapter(cardArrayAdapter);

    }

    /**Locations were added to shared preferences from map
     * These values are retrieved here using sharedpreferences
     * Gets the Profile name, latitude and longitude for each profile
     */
    private void addFencesFromSharedPref(){
        shPrefs = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        batteryLev=shPrefs.getInt("seekbar", 0);
        timeProf=shPrefs.getString("Time",null);

        //Home Profile shared preferences
        homeProf = shPrefs.getString("HomeName", null);
        latt=shPrefs.getFloat("HomeLatitude", (float) 0);
        homeLat =(double)latt;
        longg=shPrefs.getFloat("HomeLongitude", (float) 0);
        homeLng =(double)longg;
        homeAddr=shPrefs.getString("HomeAddress",null);

        if(homeProf!=null && homeLat!=null && homeLng!=null&& homeAddr!=null) {
            Log.d("SharedPredProf", homeProf);
            Log.d("SharedPredProfAddress", homeAddr);
            Log.d("SharedPrefLat", String.valueOf(homeLat));
            Log.d("SharedpRefLong", String.valueOf(homeLng));

            geoFencing.addFence(homeProf, new LatLng(homeLat, homeLng));
        }

        //Work profile shared preferences
        workProf = shPrefs.getString("WorkName", null);
        latt=shPrefs.getFloat("WorkLatitude", (float) 0);
        homeLat =(double)latt;
        longg=shPrefs.getFloat("WorkLongitude",(float)0);
        homeLng =(double)longg;
        workAddr=shPrefs.getString("WorkAddress",null);


        if(workProf!=null && homeLat!=null && homeLng!=null && workAddr!=null) {
            Log.d("SharedPredProf", homeProf);
            Log.d("SharedPrefAddr",workAddr);
            Log.d("SharedPrefLat", String.valueOf(homeLat));
            Log.d("SharedpRefLong", String.valueOf(homeLng));
            geoFencing.addFence(workProf, new LatLng(homeLat, homeLng));
        }

        //Custom Profile shared preferences
        customProf = shPrefs.getString("CustomName", null);
        latt=shPrefs.getFloat("CustomLatitude", (float) 0);
        homeLat =(double)latt;

        longg=shPrefs.getFloat("CustomLongitude",(float)0);
        homeLng =(double)longg;
        customAddr=shPrefs.getString("CustomAddress",null);


        if(customProf!=null && homeLat!=null && homeLng!=null && customAddr!=null) {
            Log.d("SharedPredProf", homeProf);
            Log.d("SharedPrefLat", String.valueOf(homeLat));
            Log.d("SharedpRefLong", String.valueOf(homeLng));
            geoFencing.addFence(customProf, new LatLng(homeLat, homeLng));
        }
    }

    //Initialize geofences
    private void initializeGeoFencing() {

        geoFencing=new GeoFencing(this);
        geoFencing.initialize();

    }

        private void buildFab(){
        final ViewGroup fabContainer = (ViewGroup) findViewById(R.id.fab_container);
        fab = (ImageButton) findViewById(R.id.fab);
        fabaction1 = findViewById(R.id.fab_action_1);
            fabaction2 = findViewById(R.id.fab_action_2);
            fabaction3 = findViewById(R.id.fab_action_3);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expanded = !expanded;
                    if (expanded) {
                        expandFab();
                } else {
                        collapseFab();
                    }
                }


            });

            findViewById(R.id.fab_action_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BatteryRem.class);
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(), "Battery", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            findViewById(R.id.fab_action_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddLocation.class);
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(), "Select Place", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            findViewById(R.id.fab_action_3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TimeOfDay.class);
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(), "Time Of Day", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });


            fabContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    fabContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                    offset1 = fab.getY() - fabaction1.getY();
                    fabaction1.setTranslationY(offset1);
                    offset2 = fab.getY() - fabaction2.getY();
                    fabaction2.setTranslationY(offset2);
                    offset3 = fab.getY() - fabaction3.getY();
                fabaction3.setTranslationY(offset3);
                return true;
            }
        });
    }

    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createCollapseAnimator(fabaction1, offset1),
                createCollapseAnimator(fabaction2, offset2),
                createCollapseAnimator(fabaction3, offset3));
        animatorSet.start();
        animateFab();

        fab.setImageResource(R.drawable.animated_minus);
        fab.setImageResource(R.drawable.animated_plus);

    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createExpandAnimator(fabaction1, offset1),
                createExpandAnimator(fabaction2, offset2),
                createExpandAnimator(fabaction3, offset3));
        animatorSet.start();
        animateFab();
    }

    private static final String TRANSLATION_Y = "translationY";

    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}














