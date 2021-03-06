package com.ahulproject.webnime.otakuanime.Bookmarks;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ahulproject.webnime.otakuanime.Database.Database_BookmarksWeb1;
import com.ahulproject.webnime.otakuanime.MainWeb1;
import com.ahulproject.webnime.otakuanime.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import com.ahulproject.webnime.otakuanime.History.HistoryWeb1;

public class BookmarksWeb1 extends AppCompatActivity {
    Database_BookmarksWeb1 dbHandlerBook_1 = new Database_BookmarksWeb1(this, null, null, 1);
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bookmarks_web1);


        //Create bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_bookmarks);
        bottomNav.setSelectedItemId(R.id.anoboy_book);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.anoboy_book:
                        return true;

                    case R.id.samehadaku_book:
                        startActivity(new Intent(getApplicationContext(),
                                BookmarksWeb2.class));
                        overridePendingTransition(0,0);
                        BookmarksWeb1.this.finish();

                        return true;

                    case R.id.gomunime_book:
                        startActivity(new Intent(getApplicationContext(),
                                BookmarksWeb3.class));
                        overridePendingTransition(0,0);
                        BookmarksWeb1.this.finish();
                        return true;

                    case R.id.history_move:
                        Intent historyIntent = new Intent(BookmarksWeb1.this, HistoryWeb1.class);
                        startActivity(historyIntent);
                        BookmarksWeb1.this.finish();
                        return true;

                    case R.id.delete_nav_book:
                        //System.exit(0);
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookmarksWeb1.this);
                        builder.setTitle("Del Bookmarks Anoboy")
                                .setMessage("Hapus semua bookmarks nya?")
                                .setPositiveButton("hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteDatabase("bookmarks1.db");
                                        Toast toast = Toast.makeText(BookmarksWeb1.this, "Semua bookmarks anoboy sudah dihapus", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0,0);
                                        toast.show();
                                        BookmarksWeb1.this.recreate();
                                    }
                                })
                                .setNegativeButton("tidak", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                        return false;

                }
                return false;
            }
        });

        final List<String> books=dbHandlerBook_1.databaseToString();
        if (books.size()>0){
            ArrayAdapter myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, books);
            ListView mylist = (ListView)findViewById(R.id.listviewBookmarks1);
            mylist.setAdapter(myadapter);

            mylist.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //*String url=books.get(position);*//*
                            String url=books.get(position);
                            Intent intent = new Intent(view.getContext(), MainWeb1.class);
                            intent.putExtra("urls1",url);
                            startActivity(intent);
                            finish();
                        }
                    }
            );
        }

    }

    @Override
    public void onBackPressed(){
        BookmarksWeb1.this.finish();
    }
}
