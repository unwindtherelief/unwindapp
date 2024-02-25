package com.depression.relief.depressionissues.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.MainActivity;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.fragments.HomeFragment;

public class MoodReportActivity extends AppCompatActivity {
    TextView mood_score;
    ProgressBar progress_bar;
    private double passScore;
    TextView txt_wellbeing;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_report);

        mood_score = findViewById(R.id.mood_score);
        progress_bar = findViewById(R.id.progress_bar);
        txt_wellbeing = findViewById(R.id.txt_wellbeing);


        progress_bar.setMax(100);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("overallScore")) {
            double overallScore = intent.getDoubleExtra("overallScore", 0.0);

            updateProgressBar(overallScore);

            mood_score.setText(overallScore + "%");
            setWellBeingText(overallScore);

            passScore = overallScore;

        }
    }

    private void setWellBeingText(double overallScore) {
        if (overallScore >= 1 && overallScore <= 20) {
            txt_wellbeing.setText(getString(R.string.critical_wellbeing));
        } else if (overallScore >= 21 && overallScore <= 40) {
            txt_wellbeing.setText(getString(R.string.critical_wellbeing));

        } else if (overallScore >= 41 && overallScore <= 60) {
            txt_wellbeing.setText(getString(R.string.fair_wellbeing));

        } else if (overallScore > 61 && overallScore <= 80) {
            txt_wellbeing.setText(getString(R.string.good_wellbeing));

        } else if (overallScore >= 81 && overallScore <= 100) {
            txt_wellbeing.setText(getString(R.string.excellent_wellbeing));
        }
    }

    private void updateProgressBar(double overallScore) {
        progress_bar.setProgress(0);

        int progress = (int) Math.round(overallScore);

        progress_bar.setProgress(progress);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("overallScore", passScore);
        startActivity(intent);
        Animatoo.INSTANCE.animateShrink(this);
        finish();
    }
}