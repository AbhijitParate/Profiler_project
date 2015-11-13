package com.example.ishwari.profiler;

/**
 * Created by siddhartha on 11/12/15.
 */
public class LocationItem {
    public String name;
    public double latitude;
    public double longitude;
    public String address;

    public LocationItem (String name, double latitude, double longitude,
                         String address) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;

    }
}
