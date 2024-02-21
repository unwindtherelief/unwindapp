package com.depression.relief.depressionissues.music.modelizer;

public class SetterItem {
    int id;
    String subTitle;
    String title;

    public SetterItem() {
    }

    public SetterItem(int i, String str, String str2) {
        this.id = i;
        this.title = str;
        this.subTitle = str2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String str) {
        this.subTitle = str;
    }
}
