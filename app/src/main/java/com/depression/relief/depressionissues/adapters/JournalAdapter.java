package com.depression.relief.depressionissues.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.models.JournalEntry;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private List<JournalEntry> journalList;

    public JournalAdapter(List<JournalEntry> journalList) {
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = journalList.get(position);

        holder.textViewTitle.setText(entry.getTitle());
        holder.textViewDate.setText(entry.getDate());
        holder.textViewTime.setText(entry.getTime());
        holder.textViewDescription.setText(entry.getDescription());
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public void setJournalList(List<JournalEntry> filteredList) {
        this.journalList = filteredList;
    }

    public void clearJournalList() {
        journalList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
