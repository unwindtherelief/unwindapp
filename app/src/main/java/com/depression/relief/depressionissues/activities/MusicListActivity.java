package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.depression.relief.depressionissues.R;

public class MusicListActivity extends AppCompatActivity {

    int categoryId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("category_id")) {
            categoryId = intent.getIntExtra("category_id", 0);
        }


    }
}