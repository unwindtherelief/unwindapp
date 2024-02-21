package com.depression.relief.depressionissues.music.modelizer;

public class AudioItem {
    private String fileName;
    private int iconResId;
    private String name;
    private int soundId;
    private int volume;

    public AudioItem() {
        this.volume = 50;
    }

    public AudioItem(AudioItem soundItem) {
        if (soundItem != null) {
            this.soundId = soundItem.soundId;
            this.name = soundItem.name;
            this.iconResId = soundItem.iconResId;
            this.volume = soundItem.volume;
            this.fileName = soundItem.fileName;
        }
    }

    public AudioItem(int i, String str, String str2, int i2, int i3) {
        this.soundId = i;
        this.name = str;
        this.fileName = str2;
        this.iconResId = i2;
        this.volume = i3;
    }

    public String toString() {
        return "SoundModel{soundId=" + this.soundId + ", name='" + this.name + "', iconResId=" + this.iconResId + ", volume=" + this.volume + ", fileName='" + this.fileName + "'}";
    }

    public void update(AudioItem soundItem) {
        if (soundItem != null) {
            this.soundId = soundItem.soundId;
            this.name = soundItem.name;
            this.iconResId = soundItem.iconResId;
            this.volume = soundItem.volume;
            this.fileName = soundItem.fileName;
        }
    }

    public int getSoundId() {
        return this.soundId;
    }

    public void setSoundId(int i) {
        this.soundId = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getIconResId() {
        return this.iconResId;
    }

    public void setIconResId(int i) {
        this.iconResId = i;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int i) {
        this.volume = i;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }
}
