package com.depression.relief.depressionissues.music.modelizer;

public class CategoryMixer {
    int id;
    boolean isChecked = false;
    String name;

    public CategoryMixer() {
    }

    public CategoryMixer(int i, String str) {
        this.id = i;
        this.name = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }
}
