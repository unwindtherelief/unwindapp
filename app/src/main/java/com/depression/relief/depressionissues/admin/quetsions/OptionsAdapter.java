package com.depression.relief.depressionissues.admin.quetsions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;

import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {
    private List<String> optionsList;

    public OptionsAdapter(List<String> optionsList) {
        this.optionsList = optionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = optionsList.get(position);

        // Bind data to ViewHolder
        holder.textViewOption.setText(option);
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOption;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOption = itemView.findViewById(R.id.textViewOption);
        }
    }
}
