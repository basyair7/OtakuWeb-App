package com.ahulproject.webnime.otakuanime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;

public class MainMenu extends AppCompatActivity {
    CardView mButtonAnoboy;
    CardView mButtonSamehadaku;
    CardView mButtonGomunime;
    CardView mButtonSetting;
    Toolbar toolbar;
    File newFolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Application in fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        //if one click button, open web Anoboy
        mButtonAnoboy = (CardView) findViewById(R.id.anoboy_url);
        mButtonAnoboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anoboyIntent = new Intent (MainMenu.this, MainWeb1.class);
                startActivity(anoboyIntent);
            }
        });

        //if one click button, open web Samehadaku
        mButtonSamehadaku = (CardView) findViewById(R.id.samehadaku_url);
        mButtonSamehadaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SamehadakuIntent = new Intent (MainMenu.this, MainWeb2.class);
                startActivity(SamehadakuIntent);
            }
        });

        //if one click button, open web Gomunime
        mButtonGomunime = (CardView) findViewById(R.id.gomunime_url);
        mButtonGomunime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GomunimeIntent = new Intent(MainMenu.this, MainWeb3.class);
                startActivity(GomunimeIntent);
            }
        });

        //if one click button, open history
        mButtonSetting = (CardView) findViewById(R.id.History_nav);
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(MainMenu.this, OptionsApp.class);
                startActivity(historyIntent);
            }
        });
    }


    //untuk menambahkan perintah memunculkan notifikasi ketika tombol back ditekan
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainMenu.this.finish();
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        //System.exit(1);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}