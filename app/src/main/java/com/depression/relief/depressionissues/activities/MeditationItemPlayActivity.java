package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class MeditationItemPlayActivity extends AppCompatActivity {
    private ImageView imageViewThumbnail;
    private TextView textViewTitle;
    private TextView textViewCreator;
    private TextView textViewLanguage;
    private SeekBar seekBar;
    private ImageButton btnPrevious;
    private ImageButton btnPlayPause;
    private ImageButton btnNext;
    private TextView textViewCurrentTime;
    private TextView textViewTotalTime;

    private Handler handler;
    private Runnable timerRunnable;
    private SimpleExoPlayer exoPlayer; // Declare ExoPlayer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_item_play);

        imageViewThumbnail = findViewById(R.id.imageViewThumbnail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewCreator = findViewById(R.id.textViewCreator);
        textViewLanguage = findViewById(R.id.textViewLanguage);
        seekBar = findViewById(R.id.seekBar);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnNext);
        textViewCurrentTime = findViewById(R.id.textViewCurrentTime);
        textViewTotalTime = findViewById(R.id.textViewTotalTime);

        handler = new Handler();

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String creator = getIntent().getStringExtra("creator");
        String language = getIntent().getStringExtra("language");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String audioUrl = getIntent().getStringExtra("audioUrl");

        // Set data to views
        Glide.with(this).load(imageUrl).into(imageViewThumbnail);
        textViewTitle.setText(title);
        textViewCreator.setText("Creator: " + creator);
        textViewLanguage.setText("Language: " + language);

        // Set up custom music player controls
        setupCustomMusicPlayerControls(audioUrl);
        handlePlayPauseButtonClick();
    }

    private void setupCustomMusicPlayerControls(String audioUrl) {
        // Set up custom music player controls here

        // Example listeners (replace with your logic)
        btnPrevious.setOnClickListener(v -> handlePreviousButtonClick());
        btnPlayPause.setOnClickListener(v -> handlePlayPauseButtonClick());
        btnNext.setOnClickListener(v -> handleNextButtonClick());

        // Example seekBar listener (replace with your logic)
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle tracking touch start
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle tracking touch stop
            }
        });

        // Set up the timer to update seekBar and time display
        updateTimer(audioUrl);
    }

    private void handlePreviousButtonClick() {
        // Handle previous button click
    }

    private void handlePlayPauseButtonClick() {
        if (exoPlayer != null) {
            if (exoPlayer.isPlaying()) {
                // If the player is currently playing, pause it
                exoPlayer.pause();
            } else {
                // If the player is paused or stopped, start playing
                exoPlayer.setPlayWhenReady(true);
            }
        }
    }

    private void handleNextButtonClick() {
        // Handle next button click
    }

    private void updateTimer(String audioUrl) {
        handler.postDelayed(timerRunnable = () -> {
            // Update seekBar and time display logic

            // Call itself every second
            updateTimer(audioUrl);
        }, 1000);
    }
}