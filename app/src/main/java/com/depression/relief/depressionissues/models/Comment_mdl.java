package com.depression.relief.depressionissues.models;

import java.util.Date;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.util.Log;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Comment_mdl {
    private String commentText;
    private String selectedCategory;
    private Date timestamp;
    private String commentimagePath;
    private String userimageurl;
    private String currentusername;
    private String commentId; // Add this field for comment ID
    private boolean liked;
    private int likeCount;
    private List<String> likedBy;
    private int dislikeCount;
    private List<String> dislikedBy;

    // Required no-argument constructor for Firestore
    public Comment_mdl() {
    }

    public Comment_mdl(String commentText, String selectedCategory, Date timestamp, String commentimagePath, String userimageurl, String currentusername) {
        this.commentText = commentText;
        this.selectedCategory = selectedCategory;
        this.timestamp = timestamp;
        this.commentimagePath = commentimagePath;
        this.userimageurl = userimageurl;
        this.currentusername = currentusername;
        this.liked = false;
        this.likeCount = 0;
        this.likedBy = null;
        this.dislikeCount = 0;
        this.dislikedBy = null;

        this.commentId = UUID.randomUUID().toString();
    }

    public String getCommentText() {
        return commentText;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getImagePath() {
        return commentimagePath;
    }

    public String getUserimageurl() {
        return userimageurl;
    }

    public String getCurrentusername() {
        return currentusername;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public List<String> getDislikedBy() {
        return dislikedBy;
    }

    public void setDislikedBy(List<String> dislikedBy) {
        this.dislikedBy = dislikedBy;
    }

    public void updateLikeDislikeInFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference commentRef = db.collection("comments").document(commentId);

        // Firestore में लाइक और डिसलाइक अपडेट करें
        commentRef.update("liked", liked,
                        "likeCount", likeCount,
                        "likedBy", likedBy,
                        "dislikeCount", dislikeCount,
                        "dislikedBy", dislikedBy)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Like/Dislike updated successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating Like/Dislike: " + e.getMessage()));
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommentimagePath() {
        return commentimagePath;
    }

    public void setCommentimagePath(String commentimagePath) {
        this.commentimagePath = commentimagePath;
    }

    public void setUserimageurl(String userimageurl) {
        this.userimageurl = userimageurl;
    }

    public void setCurrentusername(String currentusername) {
        this.currentusername = currentusername;
    }
}


