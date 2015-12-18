package com.zeuss_works.armageddonas.supersimplevideoplayer;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    Intent takeVideoIntent;
    VideoView mVideoView;
    boolean playing = false;
    DisplayMetrics dm;

    MediaController media_Controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Test 1 commit 2 commit 3 commit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getInit();
        //testVideo();
        mVideoView = (VideoView) findViewById(R.id.videoView);
        ImageButton btnRecord = (ImageButton) findViewById(R.id.btnRecord);
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                ((ImageView) findViewById(R.id.btnPlay)).setImageResource(R.drawable.ic_play_arrow_black_36dp);
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoView.start();
                if (takeVideoIntent == null) {
                    Toast.makeText(MainActivity.this, "Please record a video first",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (playing == true) {
                    playing = false;
                    ((ImageView) view).setImageResource(R.drawable.ic_play_arrow_black_36dp);
                } else {
                    playing = true;
                    ((ImageView) view).setImageResource(R.drawable.ic_pause_black_36dp);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            mVideoView.setVideoURI(videoUri);
        }
    }

    private void dispatchTakeVideoIntent() {
        takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

}
