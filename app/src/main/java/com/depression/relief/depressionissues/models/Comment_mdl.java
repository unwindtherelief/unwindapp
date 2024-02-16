package com.depression.relief.depressionissues.models;

import java.util.Date;

public class Comment_mdl {
    private String commentText;
    private String selectedCategory;
    private Date timestamp;
    private String commentimagePath;
    private String userimageurl;
    private String currentusername;

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
    }

    public String getCommentText() {
        return commentText;
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

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setImagePath(String commentimagePath) {
        this.commentimagePath = commentimagePath;
    }

    public String getUserimageurl() {
        return userimageurl;
    }

    public void setUserimageurl(String userimageurl) {
        this.userimageurl = userimageurl;
    }

    public String getCurrentusername() {
        return currentusername;
    }

    public void setCurrentusername(String currentusername) {
        this.currentusername = currentusername;
    }
}
