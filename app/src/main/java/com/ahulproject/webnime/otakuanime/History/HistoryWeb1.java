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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ahulproject.webnime.otakuanime.Database.Database_HistoryWeb1;
import com.ahulproject.webnime.otakuanime.MainWeb1;
import com.ahulproject.webnime.otakuanime.Bookmarks.BookmarksWeb1;
import com.ahulproject.webnime.otakuanime.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HistoryWeb1 extends AppCompatActivity {
    Database_HistoryWeb1 mydb;
    Database_HistoryWeb1 dbHandler_1 = new Database_HistoryWeb1(this, null,null,1);
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_history_web1);

        //Create bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_history);
        bottomNav.setSelectedItemId(R.id.anoboy_nav_history);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.samehadaku_nav_history:
                        startActivity(new Intent(getApplicationContext(),
                                HistoryWeb2.class));
                        overridePendingTransition(0,0);
                        HistoryWeb1.this.finish();
                        return true;

                    case R.id.gomunime_nav_history:
                        startActivity(new Intent(getApplicationContext(),
                                HistoryWeb3.class));
                        overridePendingTransition(0,0);
                        HistoryWeb1.this.finish();
                        return true;

                    case R.id.delete_nav_history:
                        //System.exit(0);
                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryWeb1.this);
                        builder.setTitle("Del History Anoboy")
                                .setMessage("Hapus semua history nya?")
                                .setPositiveButton("hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteDatabase("sites1.db");
                                        Toast toast = Toast.makeText(HistoryWeb1.this, "Semua history anoboy sudah dihapus", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER,0,0);
                                        toast.show();
                                        HistoryWeb1.this.recreate();
                                    }
                                })
                                .setNegativeButton("tidak", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                        return false;

                    case R.id.bookmarks_move:
                        Intent bookmarksIntent = new Intent(HistoryWeb1.this, BookmarksWeb1.class);
                        startActivity(bookmarksIntent);
                        HistoryWeb1.this.finish();
                        return true;

                    case R.id.anoboy_nav_history:
                        return true;

                }
                return false;
            }
        });


        final List<String> sites = dbHandler_1.databaseToString();
        if (sites.size() > 0){
            ArrayAdapter myapdater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, sites);
            ListView mylist = (ListView) findViewById(R.id.listviewHistory1);
            mylist.setAdapter(myapdater);

            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = sites.get(position);
                    Intent intent = new Intent(view.getContext(), MainWeb1.class);
                    intent.putExtra("urls1",url);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        HistoryWeb1.this.finish();
    }
}
