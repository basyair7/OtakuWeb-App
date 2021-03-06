package com.ahulproject.webnime.otakuanime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import com.ahulproject.webnime.otakuanime.Bookmarks.BookmarksWeb1;
import com.ahulproject.webnime.otakuanime.History.HistoryWeb1;
import com.ahulproject.webnime.otakuanime.VideoPlayer.Activity_Videos;

public class OptionsApp extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton backOut;
    ListView SetOption;
    Intent myFileSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_options_app);

        /***
        // custom toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarCustom_opsi);
        setSupportActionBar(toolbar);
         ***/
        backOut = (ImageButton)findViewById(R.id.back_out);
        backOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsApp.this.finish();
            }
        });


        //List settings
        SetOption = (ListView)findViewById(R.id.options_app);
        final String values[] = {
                "Bookmarks",
                "History",
                "Video Player",
                "VLC For Android",
                "RAR For Android",
                "Informasi app & About",
                "Bantuan Navigasi Terbaru"
        };

        // Adapter -> ArrayAdapter, simpleCursorAdapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arrays.asList(values));
        SetOption.setAdapter(arrayAdapter);
        SetOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(values[i] == "Bookmarks"){
                    Intent bookmarksIntent = new Intent(OptionsApp.this, BookmarksWeb1.class);
                    startActivity(bookmarksIntent);

                }
                else if(values[i] == "History"){
                    Intent historyIntent = new Intent(OptionsApp.this, HistoryWeb1.class);
                    startActivity(historyIntent);

                }
                else if(values[i] == "Informasi app & About"){
                    Intent aboutLayout = new Intent(OptionsApp.this, AboutApp.class);
                    startActivity(aboutLayout);

                }
                else if(values[i] == "Video Player"){
                    Intent videosLayout = new Intent(OptionsApp.this, Activity_Videos.class);
                    Toast toast = Toast.makeText(OptionsApp.this, "Silahkan refresh swipe ke bawah, jika ada video terbaru", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(videosLayout);
                }
                else if(values[i] == "VLC For Android"){
                    Intent launcherVlc = getPackageManager().getLaunchIntentForPackage("org.videolan.vlc");
                    //launcherVlc.setType("Download/*");
                    //launcherVlc.addCategory(Intent.CATEGORY_OPENABLE);

                    // Check app in device
                    if (launcherVlc != null){
                        startActivity(launcherVlc);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + "org.videolan.vlc")));
                        } catch(ActivityNotFoundException e){
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=" + "org.videolan.vlc")));
                        }
                    }
                }
                else if(values[i] == "RAR For Android"){
                    Intent launcherRar = getPackageManager().getLaunchIntentForPackage("com.rarlab.rar");

                    // Check app in device
                    if (launcherRar != null){
                        startActivity(launcherRar);
                    }
                    else {
                        try {
                            Intent openStore = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + "com.rarlab.rar"));
                            startActivity(openStore);
                        } catch (ActivityNotFoundException e){
                            Intent openBrowser = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=" + "com.rarlab.rar"));
                            startActivity(openBrowser);
                        }
                    }
                }
                else if(values[i] == "Bantuan Navigasi Terbaru"){
                    AlertDialog.Builder builder = new AlertDialog.Builder(OptionsApp.this);
                    builder.setTitle("Informasi")
                            .setMessage("1. Cara menambahkan halaman bookmarks dengan pendam tombol Home\n\n" +
                                    "2. Cara mengaktifkan mode desktop dengan cara pendam tombol kiri home\n(seperti anoboy/samehadaku/gomunime)")
                            .setPositiveButton("OK", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        OptionsApp.this.finish();
    }
}