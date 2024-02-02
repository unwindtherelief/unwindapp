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
import com.depression.relief.depressionissues.fragments.HomeFragment;
import com.depression.relief.depressionissues.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> categoryList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.categoryNameTextView.setText(category.getCategoryName());
        Glide.with(context).load(category.getCategoryImage()).into(holder.categoryImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicListActivity.class);
                intent.putExtra("category_id", category.getCategoryId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;
        ImageView categoryImageView;

        ViewHolder(View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.category_name_text_view);
            categoryImageView = itemView.findViewById(R.id.category_image_view);
        }
    }
}