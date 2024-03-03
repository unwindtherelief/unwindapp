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
import com.depression.relief.depressionissues.activities.MeditationItemPlayActivity;
import com.depression.relief.depressionissues.admin.meditation.Meditation;

import java.util.List;

public class MeditationEachUserShowAdapter extends RecyclerView.Adapter<MeditationEachUserShowAdapter.ViewHolder> {

    private final List<Meditation> meditationList;
    private final Context context;

    public MeditationEachUserShowAdapter(List<Meditation> meditationList, Context context) {
        this.meditationList = meditationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meditation_user_show_each, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meditation meditation = meditationList.get(position);

        holder.textViewTitle.setText(meditation.getTitle());
        holder.text_medtation_creator.setText(meditation.getCreator());
        holder.text_meditation_lang.setText(meditation.getLanguage());
        Glide.with(context).load(meditation.getImageUrl()).into(holder.imageViewThumbnail);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MeditationItemPlayActivity.class);

            intent.putExtra("title", meditation.getTitle());
            intent.putExtra("creator", meditation.getCreator());
            intent.putExtra("language", meditation.getLanguage());
            intent.putExtra("imageUrl", meditation.getImageUrl());
            intent.putExtra("audioUrl", meditation.getFileUrl());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return meditationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewThumbnail;
        TextView textViewTitle, text_meditation_lang, text_medtation_creator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            text_meditation_lang = itemView.findViewById(R.id.text_meditation_lang);
            text_medtation_creator = itemView.findViewById(R.id.text_medtation_creator);

        }
    }
}