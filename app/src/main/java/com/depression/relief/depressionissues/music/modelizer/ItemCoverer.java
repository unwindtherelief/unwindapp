package com.depression.relief.depressionissues.music.modelizer;

public class ItemCoverer {
    private int coverResId;
    private int id;

    public ItemCoverer() {
    }

    public ItemCoverer(int i, int i2) {
        this.id = i;
        this.coverResId = i2;
    }

    public String toString() {
        return "MixSoundCover{id=" + this.id + ", coverResId=" + this.coverResId + '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getCoverResId() {
        return this.coverResId;
    }

    public void setCoverResId(int i) {
        this.coverResId = i;
    }
}
