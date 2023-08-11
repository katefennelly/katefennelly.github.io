package com.example.destinationslideshow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

//Set up Database Helper Class to help connect to SQL database
public class DatabaseHelper extends SQLiteOpenHelper {

    //define database variables
    private final Context context;
    private static final String DATABASE_NAME = "slideshow.db";
    private static final int DATABASE_VERSION = 1;

    //Set up Database helper with database name and version
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Create SQLite database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDBSQL = "";
        InputStream is = context.getResources().openRawResource(R.raw.destinations_db_schema);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String line = reader.readLine();
            while (line != null) {
                createDBSQL += line;
                line = reader.readLine();
            }
        } catch (Exception e) {
            Log.d("Error", "something bad happened");
        }

        //Set up for SQL database information intake
        String insertsSQL = "";
        InputStream insertsStream = context.getResources().openRawResource(R.raw.destinations_db_inserts);

        BufferedReader insertsReader = new BufferedReader(new InputStreamReader(insertsStream));
        try {
            String line = insertsReader.readLine();
            while (line != null) {
                insertsSQL += line;
                line = insertsReader.readLine();
            }
        } catch (Exception e) {
            Log.d("Error", "something bad happend");
        }
        //create and insert SQL database data
        db.execSQL(createDBSQL);
        db.execSQL(insertsSQL);

        //Log to make sure everything is working properly
        Log.d("Helper", "Made Stuff");

    }

    //Upgrade SQL, if table exists, drop the creation of the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Destinations");
        onCreate(db);
    }

    //Open SQLite Database
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Force update database 
//        onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION);
    }

}
