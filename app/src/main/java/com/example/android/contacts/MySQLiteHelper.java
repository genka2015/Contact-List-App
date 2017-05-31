package com.example.android.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**Helper to create a Database
 * Created by Android on 5/30/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String CONTACTS = "contacts";
    public static final String FIRST_NAME = "fname";
    public static final String LAST_NAME = "lname";
    public static final String IMAGE_PATH = "path";

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + CONTACTS + "( " + FIRST_NAME + " char[20] primary key , " + LAST_NAME + " char[20] , " + IMAGE_PATH + " char[50]);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS);
        onCreate(db);

    }
}
