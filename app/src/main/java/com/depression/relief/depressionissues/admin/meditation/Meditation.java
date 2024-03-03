package com.depression.relief.depressionissues.admin.meditation;

import java.io.Serializable;

public class Meditation implements Serializable {

    private String documentId;
    private String title;
    private String creator;
    private String category;
    private String language;
    private String fileName;
    private String fileUrl;
    private String imageUrl; // New field for image URL

    public Meditation() {
        // Default constructor required for Firestore
    }

    public Meditation(String title, String creator, String category, String language, String fileName, String fileUrl, String imageUrl) {
        this.title = title;
        this.creator = creator;
        this.category = category;
        this.language = language;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
