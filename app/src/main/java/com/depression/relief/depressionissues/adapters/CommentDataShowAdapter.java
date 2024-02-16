package com.depression.relief.depressionissues.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.models.Comment_mdl;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentDataShowAdapter extends RecyclerView.Adapter<CommentDataShowAdapter.CommentViewHolder> {
    private final Context context;
    private final List<Comment_mdl> commentList;

    public CommentDataShowAdapter(Context context, List<Comment_mdl> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commentview, parent, false);
        return new CommentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment_mdl comment = commentList.get(position);

        holder.tvCommentText.setText(comment.getCommentText());
        holder.tvUsername.setText(comment.getCurrentusername());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.tvTimestamp.setText(sdf.format(comment.getTimestamp()));

        // Load comment image using Picasso

        if (comment.getImagePath() != null && !comment.getImagePath().isEmpty()) {
            holder.ivCommentImage.setVisibility(View.VISIBLE);
            Picasso.get().load(comment.getImagePath()).into(holder.ivCommentImage);
        } else {
            holder.ivCommentImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommentText;
        TextView tvUsername;
        TextView tvTimestamp;
        ImageView ivCommentImage;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCommentText = itemView.findViewById(R.id.tvCommentText);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivCommentImage = itemView.findViewById(R.id.ivCommentImage);
        }
    }
}