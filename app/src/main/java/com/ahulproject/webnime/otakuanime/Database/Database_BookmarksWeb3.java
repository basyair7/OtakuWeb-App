package com.ahulproject.webnime.otakuanime.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ahulproject.webnime.otakuanime.Websites;

import java.util.ArrayList;
import java.util.List;

public class Database_BookmarksWeb3 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="bookmarks3.db";
    public static final String TABLE_BOOKMARK="bookmarks";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="url";

    public Database_BookmarksWeb3(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlite) {
        String query="CREATE TABLE IF NOT EXISTS "+TABLE_BOOKMARK+"("+
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME +" TEXT"+")";
        sqlite.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlite, int i, int i1) {
        sqlite.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
        onCreate(sqlite);
    }

    //ADD NEW ROW TO DATABASE
    public void addUrl(Websites websites){
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME, websites.get_url());
        SQLiteDatabase sqlite=getWritableDatabase();
        sqlite.insert(TABLE_BOOKMARK, null, values);
        sqlite.close();
    }

    //DELETE FROM DATABASE
    public void deleteUrl(String urlName){
        SQLiteDatabase sqlite=getWritableDatabase();
        sqlite.execSQL("DELETE FROM " + TABLE_BOOKMARK + " WHERE "+COLUMN_NAME+"= "+urlName+";" );
    }

    public List<String> databaseToString(){

        SQLiteDatabase sqlite=getWritableDatabase();
        String query ="SELECT * FROM " + TABLE_BOOKMARK;

        List<String> dbstring=new ArrayList<>();

        Cursor c=sqlite.rawQuery(query, null);
        c.moveToFirst();
        int i=0;
        if(c.moveToNext()) {
            do {
                if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null){

                    String bstring = "";
                    bstring += c.getString(c.getColumnIndex("url"));
                    dbstring.add(bstring);
                }
            } while (c.moveToNext());
        }
        return dbstring;
    }


}