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

import com.ahulproject.webnime.otakuanime.Database.Database_BookmarksWeb3;
import com.ahulproject.webnime.otakuanime.MainWeb3;
import com.ahulproject.webnime.otakuanime.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ahulproject.webnime.otakuanime.History.HistoryWeb3;

import java.util.List;

public class BookmarksWeb3 extends AppCompatActivity {
    Database_BookmarksWeb3 dbHandlerBook_3 = new Database_BookmarksWeb3(this, null, null, 1);
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bookmarks_web3);

        //Create bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_bookmarks);
        bottomNav.setSelectedItemId(R.id.gomunime_book);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.anoboy_book:
                        startActivity(new Intent(getApplicationContext(),
                                BookmarksWeb1.class));
                        overridePendingTransition(0,0);
                        BookmarksWeb3.this.finish();
                        return true;

                    case R.id.samehadaku_book:
                        startActivity(new Intent(getApplicationContext(),
                                BookmarksWeb2.class));
                        overridePendingTransition(0,0);
                        BookmarksWeb3.this.finish();
                        return true;

                    case R.id.gomunime_book:
                        return true;

                    case R.id.history_move:
                        Intent historyIntent = new Intent(BookmarksWeb3.this, HistoryWeb3.class);
                        startActivity(historyIntent);
                        BookmarksWeb3.this.finish();
                        return true;

                    case R.id.delete_nav_book:
                        //System.exit(0);
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookmarksWeb3.this);
                        builder.setTitle("Del Bookmarks Gomunime")
                                .setMessage("Hapus semua bookmarks nya?")
                                .setPositiveButton("hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteDatabase("bookmarks3.db");
                                        Toast toast = Toast.makeText(BookmarksWeb3.this, "Semua bookmarks gomunime sudah dihapus", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER,0,0);
                                        toast.show();
                                        BookmarksWeb3.this.recreate();
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


        final List<String> books=dbHandlerBook_3.databaseToString();
        if (books.size()>0){
            ArrayAdapter myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, books);
            ListView mylist = (ListView)findViewById(R.id.listviewBookmarks3);
            mylist.setAdapter(myadapter);

            mylist.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String url = books.get(position);
                            /*
                            dbHandlerBook_3.deleteUrl(url);
                            recreate();
                            */
                            Intent intent = new Intent(view.getContext(), MainWeb3.class);
                            intent.putExtra("urls3", url);
                            startActivity(intent);
                            finish();
                             
                        }
                    }
            );
        }

    }

    @Override
    public void onBackPressed(){
        BookmarksWeb3.this.finish();
    }
}
