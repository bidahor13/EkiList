package com.ekilist.ekilist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Babatunde on 3/28/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "contacts.db";

    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASS = "pass";

    private static final String TABLE_NAME_LIST = "items";
    private static final String COLUMN_LIST_ITEM_ID = "id";
    private static final String COLUMN_LIST_ITEM_LISTID = "list_id";
    private static final String COLUMN_LIST_ITEM_NAME = "item_name";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table contacts (id integer primary key not null , " +
            "name text not null , email text not null , uname text not null , pass text not null);";

    private static final String TABLE_CREATE_ITEMS = "create table items (id integer primary key not null , " +
            "list_id text not null , item_name text not null);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        //Create new table for grocery list
        db.execSQL(TABLE_CREATE_ITEMS);
        this.db = db;
    }

    public void insertContact(Contact c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from contacts";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        if(count != 0)
            return;

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME , c.getName());
        values.put(COLUMN_EMAIL , c.getEmail() );
        values.put(COLUMN_UNAME , c.getUname() );
        values.put(COLUMN_PASS, c.getPass());


        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchPass(String uname)
    {
        db = this.getReadableDatabase();
        String query = "select uname, pass from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query , null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst())
        {
            do{
                a = cursor.getString(0);

                if(a.equals(uname))
                {
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);

        this.onCreate(db);

    }

    //Insert new item (itemName) in to list, using listName as list identifier
    public void insertListItem(String itemName, String listName)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME_LIST;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();


        values.put(COLUMN_LIST_ITEM_ID, count);
        values.put(COLUMN_LIST_ITEM_LISTID , listName);
        values.put(COLUMN_LIST_ITEM_NAME, itemName);


       long id =  db.insert(TABLE_NAME_LIST, null, values);
        db.close();
    }

    //get items for list (listName)
    public ArrayList<String> getItemsForList(String listName)
    {

        db = this.getReadableDatabase();
        ArrayList<String> items = new ArrayList<String>();

        String query = "select "+ COLUMN_LIST_ITEM_NAME +" from "+TABLE_NAME_LIST + " where " + COLUMN_LIST_ITEM_LISTID + " = '" + listName +"'";
        Cursor cursor = db.rawQuery(query , null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst())
        {
            do
            {
                a = cursor.getString(0);
                items.add(a);

            }
            while(cursor.moveToNext());
        }

        return items;

    }

    //remove item (itemName) from list (listName)
    public void removeItem(String toRemove, String listName)
    {

        String query = "select * from "+TABLE_NAME_LIST + " where " + COLUMN_LIST_ITEM_LISTID + " = '" + listName +"' AND " + COLUMN_LIST_ITEM_NAME + " = '" + toRemove + "'";
        Cursor cursor = db.rawQuery(query , null);
        if(cursor.moveToFirst())
        {
            do
            {
                int id = cursor.getInt(0);
                db.delete(TABLE_NAME_LIST, COLUMN_LIST_ITEM_ID + "=" + id, null);

            }
            while(cursor.moveToNext());
        }


    }
}
