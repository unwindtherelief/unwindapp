package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.adapters.CategoryAdapter;
import com.depression.relief.depressionissues.adapters.MusicListAdapter;
import com.depression.relief.depressionissues.models.Category;
import com.depression.relief.depressionissues.models.MusicData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MusicListActivity extends AppCompatActivity {
    private int categoryId;
    private static final String API_URL = "https://raw.githubusercontent.com/unwindtherelief/unwindmusicapi/main/unwindmusicapi.json";
    private static final String CACHE_KEY = "api_music_data";
    private ArrayList<MusicData> musicdatalist;
    private MusicListAdapter musicDataAdapter;
    RecyclerView musicRecyclerview;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        musicRecyclerview = findViewById(R.id.musicRecyclerview);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("category_id")) {
            categoryId = intent.getIntExtra("category_id", -1);
        }

        musicdatalist = new ArrayList<>();
        musicDataAdapter = new MusicListAdapter(MusicListActivity.this, musicdatalist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        musicRecyclerview.setLayoutManager(layoutManager);
        musicRecyclerview.setAdapter(musicDataAdapter);

        fetchCategoryId(categoryId);

    }

    private void fetchCategoryId(int categoryId) {
        if (this.categoryId == categoryId) {
            fetchDataFromAPI();
        } else {
            Toast.makeText(this, "Server is busy for please wait!", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDataFromAPI() {
        String cachedData = loadJsonFromCache(CACHE_KEY);
        if (cachedData != null) {
            parseJsonData(cachedData);
        } else {
            new Thread(() -> {
                try {
                    URL url = new URL(API_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    String jsonString = stringBuilder.toString();

                    saveJsonToCache(CACHE_KEY, jsonString);

                    runOnUiThread(() -> parseJsonData(jsonString));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
/*
    private void parseJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray musicArray = jsonObject.getJSONArray("music_data");

            for (int i = 0; i < musicArray.length(); i++) {
                JSONObject musicObject = musicArray.getJSONObject(i);
                int musicId = musicObject.getInt("music_id");
                String musicTitle = musicObject.getString("music_title");
                String musicImage = musicObject.getString("image");
                String sound = musicObject.getString("sound");

                MusicData musicData = new MusicData(musicId, musicTitle, musicImage, sound);
                musicdatalist.add(musicData);
            }
//            runOnUiThread(() -> MusicListAdapter.notifyDataSetChanged());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

    private void parseJsonData(String jsonString) {
        try {
            JSONArray musicDataArray = new JSONObject(jsonString).getJSONArray("music_data");

            for (int i = 0; i < musicDataArray.length(); i++) {
                JSONObject musicObject = musicDataArray.getJSONObject(i);
                int musicId = musicObject.getInt("music_id");
                String musicTitle = musicObject.getString("music_title");
                String musicImage = musicObject.getString("image");
                String sound = musicObject.getString("sound");

                MusicData musicData = new MusicData(musicId, musicTitle, musicImage, sound);
                musicdatalist.add(musicData);
            }

            runOnUiThread(() -> musicDataAdapter.notifyDataSetChanged());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private String loadJsonFromCache(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCache", MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    private void saveJsonToCache(String key, String json) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCache", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

}