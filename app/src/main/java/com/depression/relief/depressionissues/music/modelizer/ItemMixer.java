package com.depression.relief.depressionissues.music.modelizer;


import com.depression.relief.depressionissues.music.abilityutility.TillsUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemMixer {
    private int category;
    private ItemCoverer mixSoundCover;
    private int mixSoundId;
    private String name;
    private List<AudioItem> soundList;

    public ItemMixer(int i) {
        this.mixSoundId = i;
    }

    public ItemMixer() {
        this.soundList = new ArrayList();
    }

    public ItemMixer(ItemMixer mixItem) {
        this.mixSoundId = mixItem.mixSoundId;
        this.name = mixItem.name;
        this.mixSoundCover = mixItem.mixSoundCover;
        this.category = mixItem.category;
        this.soundList = TillsUtils.copy(mixItem.soundList);
    }

    public ItemMixer(int i, int i2, String str, ItemCoverer mixCoverItem, List<AudioItem> list) {
        this.mixSoundId = i;
        this.category = i2;
        this.name = str;
        this.mixSoundCover = mixCoverItem;
        this.soundList = list;
    }

    public void copy(ItemMixer mixItem) {
        this.mixSoundId = mixItem.mixSoundId;
        this.category = mixItem.category;
        this.name = mixItem.name;
        this.mixSoundCover = mixItem.mixSoundCover;
        this.soundList = mixItem.soundList;
    }

    public String toString() {
        return "MixSoundModel{mixSoundId=" + this.mixSoundId + ", name='" + this.name + "', mixSoundCover=" + this.mixSoundCover + ", category=" + this.category + ", soundList=" + this.soundList + '}';
    }

    public int getMixSoundId() {
        return this.mixSoundId;
    }

    public void setMixSoundId(int i) {
        this.mixSoundId = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public ItemCoverer getMixSoundCover() {
        return this.mixSoundCover;
    }

    public void setMixSoundCover(ItemCoverer mixCoverItem) {
        this.mixSoundCover = mixCoverItem;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int i) {
        this.category = i;
    }

    public List<AudioItem> getSoundList() {
        return this.soundList;
    }

    public void setSoundList(List<AudioItem> list) {
        this.soundList = list;
    }
}
