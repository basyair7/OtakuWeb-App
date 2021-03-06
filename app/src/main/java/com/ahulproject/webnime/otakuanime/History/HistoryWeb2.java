package com.ahulproject.webnime.otakuanime.History;

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

import com.ahulproject.webnime.otakuanime.Database.Database_HistoryWeb2;
import com.ahulproject.webnime.otakuanime.Bookmarks.BookmarksWeb2;
import com.ahulproject.webnime.otakuanime.MainWeb2;
import com.ahulproject.webnime.otakuanime.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HistoryWeb2 extends AppCompatActivity {
    Database_HistoryWeb2 mydb;
    Database_HistoryWeb2 dbHandler_2 = new Database_HistoryWeb2(this, null,null,1);
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_history_web2);

        //Create bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_history);
        bottomNav.setSelectedItemId(R.id.samehadaku_nav_history);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.gomunime_nav_history:
                        startActivity(new Intent(getApplicationContext(), HistoryWeb3.class));
                        overridePendingTransition(0,0);
                        HistoryWeb2.this.finish();
                        return true;

                    case R.id.anoboy_nav_history:
                        startActivity(new Intent(getApplicationContext(), HistoryWeb1.class));
                        overridePendingTransition(0,0);
                        HistoryWeb2.this.finish();
                        return true;

                    case R.id.bookmarks_move:
                        Intent bookmarksIntent = new Intent(HistoryWeb2.this, BookmarksWeb2.class);
                        startActivity(bookmarksIntent);
                        HistoryWeb2.this.finish();
                        return true;

                    case R.id.delete_nav_history:
                        //System.exit(0);
                        //HistoryWeb2.this.finish();
                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryWeb2.this);
                        builder.setTitle("Del History Samehadaku")
                                .setMessage("Hapus semua history nya?")
                                .setPositiveButton("hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteDatabase("sites2.db");
                                        Toast toast = Toast.makeText(HistoryWeb2.this, "Semua history samehadaku sudah dihapus", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER,0,0);
                                        toast.show();
                                        HistoryWeb2.this.recreate();
                                    }
                                })
                                .setNegativeButton("tidak", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                        return false;

                    case R.id.samehadaku_nav_history:
                        return true;

                }
                return false;
            }
        });


        final List<String> sites = dbHandler_2.databaseToString();
        if (sites.size() > 0){
            ArrayAdapter myapdater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sites);
            ListView mylist = (ListView) findViewById(R.id.listviewHistory2);
            mylist.setAdapter(myapdater);

            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String url = sites.get(i);
                    Intent intent = new Intent(view.getContext(), MainWeb2.class);
                    intent.putExtra("urls2",url);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        HistoryWeb2.this.finish();
    }
}