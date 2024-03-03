package com.depression.relief.depressionissues.admin.meditation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;

import java.util.List;

public class MeditationAdapter extends RecyclerView.Adapter<MeditationAdapter.ViewHolder> {

    private final Context context;
    private final List<Meditation> meditationList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public MeditationAdapter(Context context, List<Meditation> meditationList, OnItemClickListener listener) {
        this.context = context;
        this.meditationList = meditationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meditation_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meditation meditation = meditationList.get(position);

        // Set data to views
        holder.textViewTitle.setText(meditation.getTitle());
        holder.textViewCreator.setText(meditation.getCreator());
        holder.textViewLanguage.setText(meditation.getLanguage());

        // Load image using Glide (add the Glide dependency in your build.gradle)
        Glide.with(context).load(meditation.getImageUrl()).placeholder(R.drawable.rainback).into(holder.imageViewMeditation);

        // Set click listener for delete button
        holder.btnDelete.setOnClickListener(view -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meditationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMeditation;
        TextView textViewTitle, textViewCreator, textViewLanguage;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMeditation = itemView.findViewById(R.id.imageViewMeditation);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCreator = itemView.findViewById(R.id.textViewCreator);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Interface for delete button click
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}