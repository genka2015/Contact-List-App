package com.example.android.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 5/30/2017.
 */

public class ContactsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private ContentValues values;

    private String[] allColumns = { MySQLiteHelper.FIRST_NAME,
            MySQLiteHelper.LAST_NAME, MySQLiteHelper.IMAGE_PATH };

    public ContactsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addContact(Contact c){      // TODO change return value
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.FIRST_NAME, c.getFirstName());
        values.put(MySQLiteHelper.LAST_NAME, c.getLastName());
        values.put(MySQLiteHelper.IMAGE_PATH, c.getPath());
        database.insert(MySQLiteHelper.CONTACTS,null,values);
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        Cursor cursor = database.query(MySQLiteHelper.CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return contacts;
    }

    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setFirstName(cursor.getString(0));
        contact.setLastName(cursor.getString(1));
        contact.setPath(cursor.getString(2));
        return contact;
    }


}
