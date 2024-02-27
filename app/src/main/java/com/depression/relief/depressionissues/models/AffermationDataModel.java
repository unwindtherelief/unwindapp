package com.depression.relief.depressionissues.models;

public class AffermationDataModel {

    private int affermationthoughts;
    private int backColor;

    public AffermationDataModel(int affermationthoughts, int backColor) {
        this.affermationthoughts = affermationthoughts;
        this.backColor = backColor;
    }


    public int getAffermationThoughts() {
        return affermationthoughts;
    }

    public void setAffermationThoughts(int affermationthoughts) {
        this.affermationthoughts = affermationthoughts;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setGetColorId(int backColor) {
        this.backColor = backColor;
    }
}
