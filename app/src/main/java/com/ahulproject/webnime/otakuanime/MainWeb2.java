package com.ahulproject.webnime.otakuanime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ahulproject.webnime.otakuanime.Database.Database_BookmarksWeb2;
import com.ahulproject.webnime.otakuanime.Database.Database_HistoryWeb2;

import com.ahulproject.webnime.otakuanime.Bookmarks.BookmarksWeb2;
import com.ahulproject.webnime.otakuanime.History.HistoryWeb2;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

public class MainWeb2 extends AppCompatActivity {
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    private int desktopModeValue = 0;

    SwipeRefreshLayout refreshLayout;
    Button refreshBtn;
    ProgressBar progressBar;
    WebView webView;
    Database_HistoryWeb2 dbHandler_2;
    Database_BookmarksWeb2 dbHandlerbook_2;
    //ImageButton btnbookmarks;
    Toolbar toolbar;
    SpaceNavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Application in fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().getSystemUiVisibility();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main_web2);

        /***
        // custom toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbarCustom_web2);
        setSupportActionBar(toolbar);
         ***/

        // Open Window web Samehadaku
        webView = (WebView)findViewById(R.id.samehadaku_web);
        // Refresh Layout
        refreshLayout = findViewById(R.id.refreshlayout);
        // Loading Animation
        progressBar = findViewById(R.id.process_bar);
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
        // Button Bookmarks
        //btnbookmarks = findViewById(R.id.bookmarks);

        // History Database
        dbHandler_2 = new com.ahulproject.webnime.otakuanime.Database.Database_HistoryWeb2(this, null, null, 1);
        // Bookmarks Database
        dbHandlerbook_2 = new com.ahulproject.webnime.otakuanime.Database.Database_BookmarksWeb2(this,null,null,1);

        /**
        //improve webView performance
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        **/

        webView.clearCache(true);
        // Fix error disallowed user agent android webview
        webView.getSettings().setUserAgentString(USER_AGENT);

        // Enable javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MainWeb2.myWebclient());
        webView.setWebChromeClient(new MainWeb2.myChrome());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);

        // Open Url
        webView.loadUrl("https://samehadaku.vip/");

        // Program History
        if(getIntent().getStringExtra("urls2") !=null){
            webView.loadUrl(getIntent().getStringExtra("urls2"));
        }

        // Download files
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String useragent, String contentdisposition, String mimetype, long contentlenght)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        DownloadDialog(url,useragent, contentdisposition, mimetype);
                         //improve webView performance
                         WebSettings webSettings = webView.getSettings();
                         webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                         webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                         webView.getSettings().setAppCacheEnabled(true);
                         webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                         webSettings.setDomStorageEnabled(true);
                         webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                         webSettings.setUseWideViewPort(true);
                         webSettings.setSavePassword(true);
                         webSettings.setSaveFormData(true);
                         webSettings.setEnableSmoothTransition(true);

                    }
                    else{
                        ActivityCompat.requestPermissions(MainWeb2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                }

                else
                {
                    DownloadDialog(url, useragent, contentdisposition, mimetype);
                }
            }
        });

        // Refresh Page
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        refreshLayout.setRefreshing(false);
                        webView.reload();
                    }
                }, 3000);
            }
        });

        refreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark)
        );

        /***
        // Create Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.samehadaku_nav);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.anoboy_nav:
                        startActivity(new Intent(getApplicationContext(),
                                MainWeb1.class));
                        overridePendingTransition(0,0);
                        MainWeb2.this.finish();
                        return true;

                    case R.id.gomunime_nav:
                        startActivity(new Intent(getApplicationContext(),
                                MainWeb3.class));
                        overridePendingTransition(0,0);
                        MainWeb2.this.finish();
                        return true;

                    case R.id.home_nav:
                        MainWeb2.this.finish();
                        return true;

                    case R.id.samehadaku_nav:
                        recreate();
                        return true;
                }

                return false;
            }
        });
         ***/

        // new Bottom Navigation
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.setInActiveSpaceItemColor(ContextCompat.getColor(this, R.color.white));
        // add icons for Bottom Nav
        navigationView.addSpaceItem(new SpaceItem("Anoboy", R.drawable.ic_baseline_web_1));
        navigationView.addSpaceItem(new SpaceItem("Gomunime", R.drawable.ic_baseline_web_2));
        navigationView.showTextOnly();
        navigationView.hideAllBadges();

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                MainWeb2.this.finish();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                startActivity(new Intent(getApplicationContext(),
                        MainWeb3.class));
                overridePendingTransition(0,0);
                MainWeb2.this.finish();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                startActivity(new Intent(getApplicationContext(),
                        MainWeb1.class));
                overridePendingTransition(0,0);
                MainWeb2.this.finish();

            }

        });

        // new long click menu
        navigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                onBookPressed();
                Toast toast1 = Toast.makeText(MainWeb2.this, "Halaman ditambah ke bookmarks...",Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,0);
                Toast toast2 = Toast.makeText(MainWeb2.this, "Pendam sekali lagi untuk penyimpanan pertama kali",Toast.LENGTH_SHORT);
                //toast2.setGravity(Gravity.BOTTOM,0,0);
                toast1.show();
                toast2.show();

            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                if (desktopModeValue==0){
                    Toast toast = Toast.makeText(MainWeb2.this, "Mode Desktop On",Toast.LENGTH_SHORT);
                    toast.show();
                    desktopMode(webView, true);
                    desktopModeValue=1;
                }
                else{
                    Toast toast = Toast.makeText(MainWeb2.this, "Mode Desktop Off",Toast.LENGTH_SHORT);
                    toast.show();
                    desktopMode(webView, false);
                    desktopModeValue=0;
                }
            }
        });

        //Initialize connectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get Active network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Check network status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            //When internet is inactive

            // Move alert page
            setContentView(R.layout.alert_page);
            refreshBtn = (Button)findViewById(R.id.bt_refresh);
            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });

        } else{
            //When internet is active
            // Reload url
            webView.reload();
        }

        /***
        // Button Add url in bookmarks
        btnbookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onBookPressed();
                Toast.makeText(MainWeb2.this, "Halaman ditambah ke bookmarks", Toast.LENGTH_SHORT).show();
            }
        });
         ***/
    }

    //Option navigation bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_bar_web,menu);
        return true;
    }

    /***
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.forward_item1){
            webView.goForward();
        }
        else if (id == R.id.refresh_item2){
            webView.reload();
        }
        else if (id == R.id.desktopmode_item3){
            if (desktopModeValue==0){
                desktopMode(webView, true);
                desktopModeValue=1;
            }
            else {
                desktopMode(webView, false);
                desktopModeValue=0;
            }
        }
        else if (id == R.id.history_item4){
            Intent historyIntent = new Intent(MainWeb2.this, HistoryWeb2.class);
            startActivity(historyIntent);

        }
        else if (id == R.id.bookmarks_item5){
            Intent bookmarksIntent = new Intent(MainWeb2.this, BookmarksWeb2.class);
            startActivity(bookmarksIntent);

         }
         else if (id == R.id.add_bookmarks){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            onBookPressed();
            Toast toast1 = Toast.makeText(MainWeb2.this, "Halaman ditambah ke bookmarks...",Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER,0,0);
            Toast toast2 = Toast.makeText(MainWeb2.this, "Tekan sekali lagi untuk penyimpanan pertama kali",Toast.LENGTH_SHORT);
            //toast2.setGravity(Gravity.BOTTOM,0,0);
            toast1.show();
            toast2.show();
        }

        return true;
    }
    ***/

    // Untuk mengatasi problem ERROR_URL_SCHEME (bisa secara otomatis redirect ke aplikasi whatsapp ketika ada perintah dalam web
    public class myWebclient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, String url) {
            if(url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return false;
        }
        @Override
        public void onPageStarted(WebView wv, String url, Bitmap favicon){
            super.onPageStarted(wv, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            saveData();
        }
        @Override
        public  void onPageFinished(WebView wv, String url){
            super.onPageFinished(wv, url);
            progressBar.setVisibility(View.GONE);

        }

    }

    // Function Browser streaming fullscreen
    private class myChrome extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        //protected FrameLayout mFullscreenContainer;
        //private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        myChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            //this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    public void DownloadDialog(final String url, final String UserAgent, String contentdisposition, String mimetype){
        final String filename = URLUtil.guessFileName(url, contentdisposition, mimetype);

        AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setTitle("Downloading...")
                .setMessage("Do you want to download "+ ' '+" "+filename+" "+' ')
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        String cookie = CookieManager.getInstance().getCookie(url);
                        request.addRequestHeader("Cookie", cookie);
                        request.addRequestHeader("User-Agent", UserAgent);
                        request.allowScanningByMediaScanner();

                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                DownloadManager.Request.NETWORK_MOBILE);

                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                        DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    // Mode Desktop
    public void desktopMode(WebView webView, boolean enabled){
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled){
            try{
                String ua = webView.getSettings().getUserAgentString();
                String androidDosString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("),ua.indexOf(")")+1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidDosString,"X11; Linux x86_64");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else{
            newUserAgent = null;
        }
        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
    }

    //Save data bookmarks
    private void onBookPressed(){
        Websites web=new Websites(webView.getUrl());
        dbHandlerbook_2.addUrl(web);
    }

    // Save data history
    private void saveData(){
        Websites webv = new Websites(webView.getUrl());
        dbHandler_2.addUrl(webv);
    }

    @Override
    public void onBackPressed(){
        if (webView.canGoBack()){
            webView.goBack();
        }
        else{
            finish();
        }
    }
}