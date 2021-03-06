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

public class Database_HistoryWeb1 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="sites1.db"; // name of file database
    public static final String TABLE_SITES="sites"; // name of table
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_URL="url";

    public Database_HistoryWeb1(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqlite) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_SITES + "("+
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_URL +" TEXT "+")";
        // Execute
        sqlite.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlite, int i, int i1) {
        sqlite.execSQL("DROP TABLE IF EXISTS " + TABLE_SITES);
        onCreate(sqlite);

    }

    //ADD NEW ROW TO DATABASE
    public void addUrl(Websites website){
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, website.get_url());
        SQLiteDatabase sqlite = getWritableDatabase();
        sqlite.insert(TABLE_SITES, null, values);
        sqlite.close();
    }

    //DELETE FROM DATABASE
    public void deleteUrl(String urlName){
        SQLiteDatabase sqlite=getWritableDatabase();
        sqlite.delete(TABLE_SITES,
                COLUMN_URL + "=" + urlName, null);

    }

    // PRINT OUT THE HISTORY AS STRING
    public List<String> databaseToString()
    {
        SQLiteDatabase sqlite = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SITES;

        List<String> dbstring = new ArrayList<>();

        Cursor c = sqlite.rawQuery(query, null);
        c.moveToFirst();
        int i = 0;
        if(c.moveToNext()){
            do{
                if (c.getString(c.getColumnIndex(COLUMN_URL)) != null){

                    String bstring = "";
                    bstring += c.getString(c.getColumnIndex("url"));
                    dbstring.add(bstring);
                }
            } while(c.moveToNext());
        }
        return dbstring;
    }


}
