package com.example.ishwari.profiler;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by siddhartha on 9/30/15.
 */

public class MapViewer extends AppCompatActivity{

    private GoogleMap mMap;
    double latitude,longitude;
    String profile;

    Marker marker;
    Button b1;
    Location location;

    HttpURLConnection urlConnection=null;
    boolean canGetLocation = false;
    //DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        b1=(Button)findViewById(R.id.setLocationButton);
        String queryParam;

        Intent receivedIntent = getIntent();
        profile =  receivedIntent.getStringExtra("Profile");

        if(canGetLocationCheck()){
            latitude=getLatitudeVal();
            longitude=-getLongitudeVal();
            queryParam=Double.toString(latitude)+","+Double.toString(longitude);

        }
        else
        {
            queryParam="San+Francisco+State+University,1600+Holloway+Ave,+San+Francisco,+CA+94132";
        }

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapViewer.this, SelectLocation.class);
                intent.putExtra("Lat", Double.toString(latitude) );
                intent.putExtra("Lan", Double.toString( longitude ) );
                intent.putExtra("Addr", marker.getTitle());
                intent.putExtra("Profile", profile);

                Log.d("OnClick Lat : ", Double.toString(latitude));
                Log.d("OnClick Lan : ", Double.toString( longitude ));



                startActivity(intent);

            }
        });

        setUpMapIfNeeded();
        MapSearch mapSearch =new MapSearch();
        mapSearch.execute(queryParam);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.map_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                Log.v("LISTENER","onQueryTextChange called - "+newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {

                Log.v("LISTENER", "OnQuerySubmit called - " + query);
                hideSoftKeyboard(MapViewer.this);
                MapSearch mapSearch = new MapSearch();
                mapSearch.execute(query);

                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        marker=mMap.addMarker(new MarkerOptions().position(latLng)
                .title("You are here")
                .snippet(String.valueOf(latLng))
                .draggable(true));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("Marker", "Dragging");
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                LatLng markerLocation = marker.getPosition();
                Toast.makeText(getApplicationContext(), markerLocation.toString(), Toast.LENGTH_LONG).show();
                marker.setSnippet(String.valueOf(markerLocation));
                Log.d("Marker", "finished");
            }

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("Marker", "Started");

            }
        });
       }

    public class MapSearch extends AsyncTask<String, Void, HashMap<String,String>> {


        @Override
        protected HashMap<String,String> doInBackground(String... params) {
            HashMap<String,String> location = new HashMap<String,String>();
            String apiKey = "AIzaSyAUSETHO5_4d_lGrGfjX4vAowf6DrqaNmk";

            try {
                final String GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
                final String QUERY_PARAM = "query";
                final String APIKEY_PARAM = "key";

                Uri builtUri = Uri.parse(GOOGLE_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(APIKEY_PARAM, apiKey)
                        .build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray resultsArr = jsonObject.getJSONArray("results");
                location.put("addressStr", resultsArr.getJSONObject(0).getString("formatted_address"));
                JSONObject locationObject = resultsArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");

                location.put("lat", locationObject.getString("lat"));
                location.put("lng",locationObject.getString("lng"));

                latitude = locationObject.getDouble("lat");
                longitude = locationObject.getDouble("lng");

                Log.v( "MAPS - LNG", locationObject.getString("lat"));
                Log.v( "MAPS - LNG", locationObject.getString("lng"));
                Log.v( "MAPS - LNG", resultsArr.getJSONObject(0).getString("formatted_address"));



            } catch (Exception  e) {
                Log.v("ERROR",e.toString());

            }


            return location;

        }

        protected void onPostExecute(HashMap<String,String> locationMap) {
            String markerTitle = locationMap.get("addressStr");
            double latitude = Double.parseDouble(locationMap.get("lat"));
            double longitude = Double.parseDouble(locationMap.get("lng"));
            marker.remove();
            setUpMap(latitude,longitude, markerTitle);
        }
    }

        private boolean isGooglePlayServicesAvailable() {
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
            if (ConnectionResult.SUCCESS == status) {
                return true;
            } else {
                GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
                return false;
            }
        }

        public double getLatitudeVal(){
            if(location!=null){
                latitude = location.getLatitude();
            }
            return latitude;
        }

        public double getLongitudeVal(){
            if(location!=null){
                longitude = location.getLongitude();
            }
            return longitude;
        }

        public boolean canGetLocationCheck(){
            return this.canGetLocation;
        }

        @Override
        protected void onResume() {
            super.onResume();
            setUpMapIfNeeded();
        }

    /* /**
      * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
      * installed) and the map has not already been instantiated.. This will ensure that we only ever
      * call {@link #setUpMap()} once when {@link #mMap} is not null.
      * <p/>
      * If it isn't installed {@link SupportMapFragment} (and
      * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
      * install/update the Google Play services APK on their device.
      * <p/>
      * A user can return to this FragmentActivity after following the prompt and correctly
      * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
      * have been completely destroyed during this process (it is likely that it would only be
      * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
      * method in {@link #onResume()} to guarantee that it will be called.
      */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(latitude,longitude,"Current Location");
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(double latitude, double longitude, String titleStr) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(titleStr));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16.9f));
        locationTv.setText(titleStr);

    }


}