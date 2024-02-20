package com.depression.relief.depressionissues.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.models.Comment_mdl;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
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
        if (comment.getUserimageurl() != null && !comment.getUserimageurl().isEmpty()) {
            Glide.with(context).load(comment.getUserimageurl()).into(holder.userimageset);
        } else {
            holder.userimageset.setImageResource(R.drawable.rm1);
        }

        if (comment.getImagePath() != null && !comment.getImagePath().isEmpty()) {
            holder.ivCommentImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(comment.getImagePath()).into(holder.ivCommentImage);
        } else {
            holder.ivCommentImage.setVisibility(View.GONE);
            holder.commentimagecard.setVisibility(View.GONE);
        }

        // लाइक और डिसलाइक की स्थिति को अपडेट करें
        if (comment.isLiked()) {
            holder.btnLikeDislike.setImageResource(R.drawable.ic_likedthumb);
        } else {
            holder.btnLikeDislike.setImageResource(R.drawable.ic_unliked_thumb);
        }
        // Like and Dislike functionality
        updateLikeDislikeUI(holder, comment);
        holder.btnLikeDislike.setOnClickListener(view -> {
            // Toggle like state
            comment.setLiked(!comment.isLiked());

            // Update like count and UI
            if (comment.isLiked()) {
                comment.setLikeCount(comment.getLikeCount() + 1);
            } else {
                comment.setLikeCount(Math.max(comment.getLikeCount() - 1, 0));
            }

            // Update like status in Firestore
            updateLikeStatusInFirestore(comment);

            // Update UI
            updateLikeDislikeUI(holder, comment);
        });

    }

    private void updateLikeStatusInFirestore(Comment_mdl comment) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference commentRef = db.collection("comments").document(comment.getCommentId());

        // Check if the document exists
        commentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null && task.getResult().exists()) {
                    // Document exists, update the like status
                    updateExistingComment(commentRef, comment);
                } else {
                    // Document does not exist, create a new one
                    createNewComment(commentRef, comment);
                }
            } else {
                // Handle the exception
                Toast.makeText(context, "Error checking document existence!", Toast.LENGTH_SHORT).show();
                Log.e("likedislike", "Error checking document existence: " + task.getException());
            }
        });
    }

    private void updateExistingComment(DocumentReference commentRef, Comment_mdl comment) {
        // Update only the like status in Firestore
        commentRef.update("liked", comment.isLiked())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show();
                    Log.d("likedislike", "Update successful!");
                    // Update successful
                    // You can add log or toast here if needed
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Update failed!", Toast.LENGTH_SHORT).show();
                    Log.e("likedislike", "Update failed: " + e.getMessage());
                    // Handle the error
                    // You can add log or toast here if needed
                });
    }

    private void createNewComment(DocumentReference commentRef, Comment_mdl comment) {
        // Create a new document in Firestore
        commentRef.set(comment)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Document created successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("likedislike", "Document created successfully!");
                    // Document created successfully
                    // You can add log or toast here if needed
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error creating document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("likedislike", "Error creating document: " + e.getMessage());
                    // Handle the error
                    // You can add log or toast here if needed
                });
    }



    private void updateLikeDislikeUI(CommentViewHolder holder, Comment_mdl comment) {
        // Set like/dislike icon
        if (comment.isLiked()) {
            holder.btnLikeDislike.setImageResource(R.drawable.ic_likedthumb);
        } else {
            holder.btnLikeDislike.setImageResource(R.drawable.ic_unliked_thumb);
        }

        // Set like count
        holder.likeDislikeCount.setText(String.valueOf(comment.getLikeCount()));

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
        ImageView userimageset;
        CardView commentimagecard;
        ImageView btnLikeDislike;
        TextView likeDislikeCount;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCommentText = itemView.findViewById(R.id.tvCommentText);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivCommentImage = itemView.findViewById(R.id.ivCommentImage);
            userimageset = itemView.findViewById(R.id.userimageset);
            commentimagecard = itemView.findViewById(R.id.commentimagecard);
            btnLikeDislike = itemView.findViewById(R.id.btn_likedislike);
            likeDislikeCount = itemView.findViewById(R.id.likedislikecount);
        }
    }
}