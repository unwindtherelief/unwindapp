package com.depression.relief.depressionissues.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.MusicActivity;
import com.depression.relief.depressionissues.adapters.CategoryAdapter;
import com.depression.relief.depressionissues.adapters.ViewPagerAdapter;
import com.depression.relief.depressionissues.ai.ChatbotActivity;
import com.depression.relief.depressionissues.models.Category;
import com.depression.relief.depressionissues.models.MusicData;
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
    private static final String QUOTE_API = "https://unwindtherelief.github.io/quotesapi/quotesdata.json";
    private static final String CACHE_KEY = "api_data";
    TextView quotetextview;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        musiccategoryListView = view.findViewById(R.id.musiccategoryListView);
        chatbot = view.findViewById(R.id.chatbot);

        quotetextview = view.findViewById(R.id.quotetextview);

        quotefetchmethod();
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        musiccategoryListView.setLayoutManager(layoutManager);
        musiccategoryListView.setAdapter(categoryAdapter);

        // Fetch data from the API
        fetchDataFromAPI();

        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ChatbotActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void quotefetchmethod() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);

        String cacheKey = "quote_" + formattedDate; // Use a unique cache key for each day
        String cachedQuote = loadJsonFromCache(cacheKey);
        if (cachedQuote != null) {
            try {
                String quote = new JSONObject(cachedQuote).getJSONArray("quotes").getJSONObject(0).getString("text");
                // Set the cached quote to the TextView
                quotetextview.setText(quote);
            } catch (JSONException e) {
                Log.e("JSON Error", e.getMessage());
            }
        } else {
            String urlWithDate = QUOTE_API + "?date=" + formattedDate;
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, urlWithDate, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String quote = response.getJSONArray("quotes").getJSONObject(0).getString("text");
                                quotetextview.setText(quote);
                                saveJsonToCache(cacheKey, response.toString());
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

/*
    private void parseJsonData(String jsonString) throws JSONException {
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
//        runOnUiThread(() -> categoryAdapter.notifyDataSetChanged());
    }*/

/*
    private void fetchDataFromApi() {

        String url = "https://raw.githubusercontent.com/unwindtherelief/unwindmusicapi/main/unwindmusicapi.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray musicArray = response.getJSONArray("music_data");
                            for (int i = 0; i < musicArray.length(); i++) {
                                JSONObject musicObject = musicArray.getJSONObject(i);
                                int musicId = musicObject.getInt("music_id");
                                String musicTitle = musicObject.getString("music_title");
                                String image = musicObject.getString("image");
                                String sound = musicObject.getString("sound");

                                musicDataList.add(new MusicData(musicId, musicTitle, image, sound));
                            }
                            musicAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }
*/
/*
    private class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

        private List<MusicData> musicList;

        public MusicAdapter(List<MusicData> musicList) {
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MusicData musicData = musicList.get(position);
            holder.textTitle.setText(musicData.getMusicTitle());
            Picasso.get().load(musicData.getImage()).into(holder.imageMusic);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MusicActivity.class);
                    intent.putExtra("musicData", musicData);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textTitle;
            ImageView imageMusic;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textTitle = itemView.findViewById(R.id.musicTitle);
                imageMusic = itemView.findViewById(R.id.imageMusic);
            }
        }
    }*/
}