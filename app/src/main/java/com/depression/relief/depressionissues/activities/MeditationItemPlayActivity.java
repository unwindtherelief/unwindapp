package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;

import java.io.IOException;

public class MeditationItemPlayActivity extends AppCompatActivity implements Runnable {
    private TextView textViewTitle;
    private TextView textViewCreator;
    private TextView textViewLanguage;
    private ImageView imageViewThumbnail;
    private SeekBar seekBar;
    private ImageButton btnPlayPause;
    private TextView textViewTimer;

    private Handler handler;
    private Runnable timerRunnable;
    MediaPlayer mediaPlayer = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_item_play);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewCreator = findViewById(R.id.textViewCreator);
        textViewLanguage = findViewById(R.id.textViewLanguage);
        imageViewThumbnail = findViewById(R.id.imageViewThumbnail);
        seekBar = findViewById(R.id.seekBar);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        textViewTimer = findViewById(R.id.textViewTimer);

        handler = new Handler();

        imageViewThumbnail.post(()->{
            Point point = new Point();
            getWindowManager().getDefaultDisplay().getSize(point);
            float width = imageViewThumbnail.getMeasuredWidth();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageViewThumbnail, "translationX", 0, -(width - point.x));
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setDuration(2000);
            objectAnimator.start();
        });

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String creator = getIntent().getStringExtra("creator");
        String language = getIntent().getStringExtra("language");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String audioUrl = getIntent().getStringExtra("audioUrl");

        // Set data to views
        textViewTitle.setText(title);
        textViewCreator.setText("Creator: " + creator);
        textViewLanguage.setText("Language: " + language);

        // Load and display the image
        Glide.with(this).load(imageUrl).into(imageViewThumbnail);

        if (!audioUrl.isEmpty()) {
            // Initialize the MediaPlayer with an initial position of 0
            // Set up the timer to update seekBar and time display
            updateTimer(audioUrl);
        } else {
            Toast.makeText(this, "Network issue!", Toast.LENGTH_SHORT).show();
        }

    }

    private void initializeMediaPlayer(String audioUrl, int initialPosition) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync(); // Use prepareAsync to prepare in the background
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.seekTo(initialPosition);
                mediaPlayer.start(); // Start playing once prepared
                btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }

    private void updateTimer(String audioUrl) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress change
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle tracking touch start
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        seekBar.setMax(mediaPlayer.getDuration());

        handler.postDelayed(timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int total = mediaPlayer.getDuration();

                    seekBar.setProgress(currentPosition);
                    textViewTimer.setText(formatTime(currentPosition) + " / " + formatTime(total));

                    handler.postDelayed(this, 1000);

                    if (!mediaPlayer.isPlaying()) {
                        btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    }
                }
            }
        }, 1000);

    }

    private String formatTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int hours = (milliseconds / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public void run() {
        // Your run method logic here
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(timerRunnable);
    }

}