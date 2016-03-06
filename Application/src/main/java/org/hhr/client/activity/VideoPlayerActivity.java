package org.hhr.client.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.VideoView;

import org.hhr.client.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by ke on 7/15/2015.
 */
public class VideoPlayerActivity extends AppCompatActivity {

    private static final String TAG = "VideoPlayer";
    private MediaController mediaController;
    private VideoView videoView;
    private EditText mPath;
    private SurfaceHolder holder;
    private ImageButton mPlay;
    private ImageButton mPause;
    private ImageButton mReset;
    private ImageButton mStop;
    private String current;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        videoView = (VideoView) findViewById(R.id.surface_view);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        final String tempPath = getIntent().getStringExtra("path");
        videoView.setVideoPath(tempPath);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });

        videoView.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}