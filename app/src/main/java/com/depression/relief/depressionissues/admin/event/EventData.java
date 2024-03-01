package com.depression.relief.depressionissues.admin.event;

import android.util.Log;

import java.io.Serializable;

public class EventData implements Serializable {
    private String documentId;
    private String title;
    private String date;
    private String time;
    private String location;
    private double eventPrice;
    private int attendeeLimit;
    private String description;
    private String imageUrl;

    public EventData() {
    }

    public EventData(String documentId, String title, String date, String time, String location, double eventPrice, int attendeeLimit, String description, String imageUrl) {
        this.documentId = documentId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.eventPrice = eventPrice;
        this.attendeeLimit = attendeeLimit;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public double getEventPrice() {
        Log.d("EventData", "Event Price: " + eventPrice);
        System.out.println("EventData" + eventPrice);
        return eventPrice;
    }

    public void setEventPrice(double eventPrice) {
        this.eventPrice = eventPrice;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAttendeeLimit() {
        return attendeeLimit;
    }

    public void setAttendeeLimit(int attendeeLimit) {
        this.attendeeLimit = attendeeLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
