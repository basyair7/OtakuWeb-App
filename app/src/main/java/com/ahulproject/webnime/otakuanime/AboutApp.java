package com.ahulproject.webnime.otakuanime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Arrays;

public class AboutApp extends AppCompatActivity {
    ListView AboutList;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Application in fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_about);
        imageButton = (ImageButton) findViewById(R.id.backtohome);
        AboutList = (ListView)findViewById(R.id.list_about);
        final String values[] = {
          "Nama app\t:  OtakuWeb",
          "Version\t\t\t:  v1.6.0",
                " ",
          "Aplikasi Editing",
          "Android Studio + Java",
                " ",
          "Programmer",
          "ahulproject (Basyair7)",
                " ",
                "Aplikasi pemutar video :\n\n"+
                "Default from app : Exo player - Video Player Project\n"+
                        "Alternative : VLC For Android (org.videolan.vlc)\n\n"+
                "Jika setelah download tidak muncul di Video Player, Harap ubah format video di folder download .bin ke .mp4\n" +
                "(misal: videoAnime.bin -> ubah ke videoAnime.mp4)",
                " ",
                "Lokasi download :  \n"+
                "folder Download (Internal/Download)",
                " ",
                "Aplikasi extract download : \n"+
                "RAR For Android (com.rarlab.rar)",
                " ",
                "List Version : \n" +
                        "OtakuWeb v1.0 : alpha tester \n\n" +
                        "OtakuWeb v1.1-rev1 : add refresh layout, enable steraming \n\n" +
                        "OtakuWeb v1.2 : fix layout main, add icon MainMenu, fix webview, add menu bottom \n\n" +
                        "OtakuWeb v1.2-rev1 : fix layout, add history databases, add menu bottom, add bar title \n\n" +
                        "OtakuWeb v1.3 : add layout history, fix webview, add bar menu \n\n" +
                        "OtakuWeb v1.3-rev1 : add bookmarks databases, add layout databases \n\n" +
                        "OtakuWeb v1.3-rev2 : fix resolution layout MainMenu \n\n" +
                        "OtakuWeb v1.4 : changes wallpaper background, add the feature download file \n\n" +
                        "OtakuWeb v1.4-rev1 : fix permission download webviev and fix directory saved \n\n" +
                        "OtakuWeb v1.4-rev2 : fix history samehadaku not responding, add Splash Screen\n\n" +
                        "OtakuWeb v1.5 : add Exo - Player(VideoPlayer), fix fullscreen streaming, fix fullscreen video player\n\n"+
                        "OtakuWeb v1.5-rev1 : fix button delete history & bookmarks, fix button back to refresh(klik 2x anoboy = back to refresh," +
                        "fix speed webview all MainWeb)\n\n"+
                        "OtakuWeb v1.5-rev2 : fix button delete history & bookmarks, add desktop mode, fix file download, fix stay screen on as play video offline\n\n"+
                        "OtakuWeb v1.5-rev3 : fix error disallowed user agent android webview in google login, add auto clear cache webview, add swipe refresh video\n\n"+
                        "OtakuWeb v1.6.0 : change to anoboy link, fix menu bar apps, remove bar MainWeb, remove menu bar, fix orientation stay portrait in web"
        };

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, values);
        AboutList.setAdapter(arrayAdapter);

        //Back to Home
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutApp.this.finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        AboutApp.this.finish();
    }
}