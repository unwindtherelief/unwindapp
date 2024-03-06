package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;

public class ChantShlokActivity extends AppCompatActivity {
    TextView tvBeedCounter, tvShlok;
    MediaPlayer mediaPlayer;
    int beadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chant_shlok);

        tvBeedCounter = findViewById(R.id.tvBeedCounter);
        tvShlok = findViewById(R.id.tvShlok);

        // Get data from Intent
        int categoryPosition = getIntent().getIntExtra("categoryPosition", 1);
        beadCount = getIntent().getIntExtra("beadCount", 1);


        String shlokText = getShlokText(categoryPosition);
        tvShlok.setText(shlokText);

        tvBeedCounter.setText(String.valueOf(beadCount));

        playShlok(categoryPosition,beadCount);

    }

    private void playShlok(int categoryPosition, int beadCount) {
        int shlokResourceId = getShlokResourceId(categoryPosition);

        mediaPlayer = MediaPlayer.create(this, shlokResourceId);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp -> {
            handleBeadCount(beadCount);
        });
    }

    private int getShlokResourceId(int categoryPosition) {
        switch (categoryPosition) {
            case 1:
                return R.raw.shlok1_audio;
            case 2:
                return R.raw.shlok1_audio;
            case 3:
                return R.raw.shlok1_audio;
            case 4:
                return R.raw.shlok1_audio;
            case 5:
                return R.raw.shlok1_audio;
            default:
                return R.raw.shlok1_audio;
        }
    }

    private void handleBeadCount(int beadCount) {
        beadCount--;

        if (beadCount > 0) {
            mediaPlayer.seekTo(0); // Reset the MediaPlayer to start from the beginning
            mediaPlayer.start();
        } else {
            mediaPlayer.release();
            finish();
        }
    }


    private String getShlokText(int categoryPosition) {
        switch (categoryPosition) {
            case 1:
                return getString(R.string.shlok1);
            case 2:
                return getString(R.string.shlok1);
            case 3:
                return getString(R.string.shlok1);
            case 4:
                return getString(R.string.shlok1);
            case 5:
                return getString(R.string.shlok1);
            default:
                return getString(R.string.shlok1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        mediaPlayer.stop();
        Animatoo.INSTANCE.animateCard(this);
    }
}