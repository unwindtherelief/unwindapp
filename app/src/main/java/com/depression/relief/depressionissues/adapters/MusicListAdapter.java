package com.depression.relief.depressionissues.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.activities.MusicListActivity;
import com.depression.relief.depressionissues.models.Category;
import com.depression.relief.depressionissues.models.MusicData;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MusicData> musicdataList;

    public MusicListAdapter(Context context, ArrayList<MusicData> categoryList) {
        this.context = context;
        this.musicdataList = categoryList;
    }

    @NonNull
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.ViewHolder holder, int position) {
        MusicData musicData = musicdataList.get(position);

        holder.musictitle.setText(musicData.getMusicTitle());
        Glide.with(context).load(musicData.getImage()).into(holder.musicimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicListActivity.class);
                intent.putExtra("music_id", musicData.getMusicId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicdataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView musictitle;
        ImageView musicimage;

        ViewHolder(View itemView) {
            super(itemView);
            musictitle = itemView.findViewById(R.id.musicTitle);
            musicimage = itemView.findViewById(R.id.imageMusic);
        }
    }
}
