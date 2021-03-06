package com.ahulproject.webnime.otakuanime.VideoPlayer;

import android.content.ContentUris;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ahulproject.webnime.otakuanime.OptionsApp;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ahulproject.webnime.otakuanime.R;

public class Activity_Player extends AppCompatActivity {

    long videoId;
    private PlayerView playerView;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Application in fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().getSystemUiVisibility();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        setContentView(R.layout.activity_player);

        initializeViews();
        videoId = getIntent().getExtras().getLong("videoId");
    }

    private void initializeViews() {
        playerView = findViewById(R.id.playerView);
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        Uri videoUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId);
        MediaSource mediaSource = buildMediaSource(videoUri);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        playerView.onResume();
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private void releasePlayer(){
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 30) {
            initializePlayer();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT < 30 || player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        if(Util.SDK_INT<30){
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if(Util.SDK_INT>=30){
            releasePlayer();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed(){
        Activity_Player.this.finish();
    }
}