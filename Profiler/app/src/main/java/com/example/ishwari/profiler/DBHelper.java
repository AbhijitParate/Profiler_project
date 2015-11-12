package com.example.ishwari.profiler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddhartha on 11/10/15.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Location";

    public static final int _ID = 0;
    public static final int NAME = 1;
    public static final int LATITUDE = 2;
    public static final int LONGITUDE = 3;
    public static final int ADDRESS = 4;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + LocationEntry.TABLE + "(" +
                LocationEntry._ID + " INTEGER PRIMARY KEY, " + LocationEntry.COLUMN_NAME +
                " TEXT UNIQUE NOT NULL, " + LocationEntry.COLUMN_LATITUDE + " REAL NOT NULL, " +
                LocationEntry.COLUMN_LONGITUDE + " REAL NOT NULL, " + LocationEntry.COLUMN_ADDRESS
                + " TEXT, );";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertLocation(LocationItem location) {
        long rowId = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LocationEntry.COLUMN_NAME, location.name);
        values.put(LocationEntry.COLUMN_LATITUDE, location.latitude);
        values.put(LocationEntry.COLUMN_LONGITUDE, location.longitude);
        values.put(LocationEntry.COLUMN_ADDRESS, location.address);

        rowId = db.insert(LocationEntry.TABLE, null, values);
        db.close();
        return rowId;
    }

    public int insertLocations (List<LocationItem> locations) {
        int rowsInserted = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        for (LocationItem location : locations) {
            ContentValues values = new ContentValues();
            values.put(LocationEntry.COLUMN_NAME, location.name);
            values.put(LocationEntry.COLUMN_LATITUDE, location.latitude);
            values.put(LocationEntry.COLUMN_LONGITUDE, location.longitude);
            values.put(LocationEntry.COLUMN_ADDRESS, location.address);

            if (db.insert(LocationEntry.TABLE, null, values)==-1) {
                break;
            }
            rowsInserted++;
        }
        db.close();
        return rowsInserted;
    }

    public Cursor selectAllRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.LocationEntry.TABLE
                + " ORDER BY _ID DESC", null);
        return cursor;
    }


    public void insertTestRows() {
        LocationItem loc1, loc2, loc3;

        loc1 = new LocationItem("Seattle Waterfront", 46.315134, -119.39579,
                "Alaskan Way & Pike St, Seattle, WA 98001, USA");
        loc2 = new LocationItem("2015-09-07_113247", 36.159431, -121.672289,
                "McWay Waterfall Trail, Big Sur, CA 93920");
        loc3 = new LocationItem("GGB", 37.791693, -122.484574,
                "Presidio, San Francisco, CA");

        List<LocationItem> list = new ArrayList<LocationItem>();
        list.add(loc1);
        list.add(loc2);
        list.add(loc3);

        insertLocations(list);
    }

    public class LocationEntry implements BaseColumns {

        public static final String TABLE = "location";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ADDRESS = "address";

    }
}

