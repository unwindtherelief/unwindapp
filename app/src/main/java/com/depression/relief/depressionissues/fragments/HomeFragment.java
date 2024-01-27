package com.depression.relief.depressionissues.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.MusicActivity;
import com.depression.relief.depressionissues.adapters.ViewPagerAdapter;
import com.depression.relief.depressionissues.ai.ChatbotActivity;
import com.depression.relief.depressionissues.models.MusicData;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView musicrecyclerview;
    ImageView chatbot;
    private ViewPager2 viewPager2;
    private WormDotsIndicator dotsIndicator;
    private MusicAdapter musicAdapter;
    private List<MusicData> musicDataList;


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        musicrecyclerview = view.findViewById(R.id.musicrecyclerview);
        chatbot = view.findViewById(R.id.chatbot);

        musicrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        musicDataList = new ArrayList<>();
        musicAdapter = new MusicAdapter(musicDataList);
        musicrecyclerview.setAdapter(musicAdapter);


        fetchDataFromApi();

        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ChatbotActivity.class);
                startActivity(intent);
            }
        });

//        setupViewPager();
//        dotsIndicator.setViewPager2(viewPager2);

        return view;
    }

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
//            Glide.with(getContext()).load(musicData.getImage()).into(holder.imageMusic);
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
                textTitle = itemView.findViewById(R.id.textTitle);
                imageMusic = itemView.findViewById(R.id.imageMusic);
            }
        }
    }

    private void setupViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ProgressFragment());
        fragmentList.add(new ChartDataFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragmentList);
        viewPager2.setAdapter(adapter);
    }
}