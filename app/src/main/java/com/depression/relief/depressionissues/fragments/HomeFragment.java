package com.depression.relief.depressionissues.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.JournalActivity;
import com.depression.relief.depressionissues.activities.MusicActivity;
import com.depression.relief.depressionissues.adapters.CategoryAdapter;
import com.depression.relief.depressionissues.adapters.ViewPagerAdapter;
import com.depression.relief.depressionissues.ai.ChatbotActivity;
import com.depression.relief.depressionissues.models.Category;
import com.depression.relief.depressionissues.models.MusicData;
import com.depression.relief.depressionissues.moodtracker.MoodCheckActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView musiccategoryListView;
    ImageView chatbot;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categoryList;
    private static final String API_URL = "https://raw.githubusercontent.com/unwindtherelief/unwindmusicapi/main/unwindmusicapi.json";
    private static final String QUOTE_API = "https://raw.githubusercontent.com/unwindtherelief/quotesapi/main/quotesdata.json";
    private static final String CACHE_KEY = "api_data";
    TextView quotetextview, txt_progressdata;
    LinearLayout btn_mood_track;
    ProgressBar progress_bar;
    ImageView btn_whatonyourmind;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        musiccategoryListView = view.findViewById(R.id.musiccategoryListView);
        chatbot = view.findViewById(R.id.chatbot);

        quotetextview = view.findViewById(R.id.quotetextview);
        btn_mood_track = view.findViewById(R.id.btn_mood_track);
        txt_progressdata = view.findViewById(R.id.txt_progressdata);
        progress_bar = view.findViewById(R.id.progress_bar);
        btn_whatonyourmind = view.findViewById(R.id.btn_whatonyourmind);


        btn_whatonyourmind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JournalActivity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateCard(getActivity());
            }
        });


        //score get from intent
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("overallScore")) {
            double overallScore = intent.getDoubleExtra("overallScore", 0.0);

            txt_progressdata.setText(overallScore + "%");

            updateProgressBar(overallScore);

        }

        btn_mood_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoodCheckActivity.class);
                startActivity(intent);
            }
        });

        fetchQuotesFromApi();
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        musiccategoryListView.setLayoutManager(layoutManager);
        musiccategoryListView.setAdapter(categoryAdapter);

        // Fetch data from the API
        fetchDataFromAPI();

        scheduleQuoteUpdate();


        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ChatbotActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateProgressBar(double overallScore) {
        progress_bar.setProgress(0);

        int progress = (int) Math.round(overallScore);

        progress_bar.setProgress(progress);
    }


    //code here for notification with quotes
    private int currentQuoteIndex = 0;
    private List<String> quotesList = new ArrayList<>();
    private static final String CHANNEL_ID = "quote_channel";


    private static final int ALARM_REQUEST_CODE = 123;

    private void scheduleQuoteUpdate() {
        // Set the time for the alarm to trigger (every 3600 seconds)
        long intervalMillis = 3600 * 1000; // 3600 seconds in milliseconds
        long triggerTimeMillis = SystemClock.elapsedRealtime() + intervalMillis;

        // Create an intent to broadcast
        Intent intent = new Intent(getActivity(), QuoteUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), ALARM_REQUEST_CODE, intent, 0);

        // Set up the alarm manager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerTimeMillis, intervalMillis, pendingIntent);
        }
    }

    public class QuoteUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // This method will be called when the alarm triggers (midnight)
            // Fetch new quotes and display
            fetchQuotesFromApi();
        }
    }

    private void fetchQuotesFromApi() {
        String url = "https://raw.githubusercontent.com/unwindtherelief/quotesapi/main/quotesdata.json";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray quotesArray = response.getJSONArray("quotes");
                            quotesList.clear(); // Clear the existing list
                            for (int i = 0; i < quotesArray.length(); i++) {
                                JSONObject quoteObject = quotesArray.getJSONObject(i);
                                String quoteText = quoteObject.getString("text");
                                quotesList.add(quoteText);
                            }
                            // Display the first quote immediately
                            displayNextQuote();
                        } catch (JSONException e) {
                            Log.e("JSON Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void displayNextQuote() {
        if (!quotesList.isEmpty()) {
            String nextQuote = quotesList.get(currentQuoteIndex);
            quotetextview.setText(nextQuote);
//            showNotification(nextQuote); // Display notification when quote changes
            currentQuoteIndex++;
            // Reset to the first quote if end of list is reached
            if (currentQuoteIndex >= quotesList.size()) {
                currentQuoteIndex = 0;
            }
        }
    }

    private void showNotification(String quoteText) {
        // Create notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Quote Channel";
            String description = "Channel for displaying quotes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle("New Quote")
                .setContentText(quoteText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(1, builder.build());
    }


    //sali no karvi aama aa code chhe music no
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

                    getActivity().runOnUiThread(() -> parseJsonData(jsonString));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void parseJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray categoriesArray = jsonObject.getJSONArray("categories");

            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject categoryObject = categoriesArray.getJSONObject(i);
                int categoryId = categoryObject.getInt("category_id");
                String categoryName = categoryObject.getString("category_name");
                String categoryImage = categoryObject.getString("category_image");

                Category category = new Category(categoryId, categoryName, categoryImage);
                categoryList.add(category);
            }
            getActivity().runOnUiThread(() -> categoryAdapter.notifyDataSetChanged());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromCache(String key) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyCache", MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    private void saveJsonToCache(String key, String json) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyCache", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch quotes from API when the activity is resumed
        scheduleQuoteUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop displaying quotes when the activity is paused
        currentQuoteIndex = 0;
        quotesList.clear();
        quotetextview.setText(R.string.belive_in_yourself); // Clear the TextView
    }
}