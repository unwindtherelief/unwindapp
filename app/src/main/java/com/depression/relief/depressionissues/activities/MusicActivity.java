package com.depression.relief.depressionissues.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.models.MusicData;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MusicActivity extends AppCompatActivity {

    private ImageView imageMusicPlayer;
    private TextView textMusicTitle;
    private Button btnPlay;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        imageMusicPlayer = findViewById(R.id.imageMusicPlayer);
        textMusicTitle = findViewById(R.id.textMusicTitle);
        btnPlay = findViewById(R.id.btnPlay);

        MusicData musicData = getIntent().getParcelableExtra("musicData");
        if (musicData != null) {
            textMusicTitle.setText(musicData.getMusicTitle());
            Picasso.get().load(musicData.getImage()).into(imageMusicPlayer);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(musicData.getSound());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btnPlay.setText("Play");
                    } else {
                        mediaPlayer.start();
                        btnPlay.setText("Pause");
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}