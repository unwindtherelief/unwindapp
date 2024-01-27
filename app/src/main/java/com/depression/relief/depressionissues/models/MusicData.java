package com.depression.relief.depressionissues.models;

import java.io.Serializable;

public class MusicData implements Serializable {
    private int musicId;
    private String musicTitle;
    private String image;
    private String sound;

    public MusicData() {
    }

    public MusicData(int musicId, String musicTitle, String image, String sound) {
        this.musicId = musicId;
        this.musicTitle = musicTitle;
        this.image = image;
        this.sound = sound;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}
