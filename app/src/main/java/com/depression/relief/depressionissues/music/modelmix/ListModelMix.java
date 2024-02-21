package com.depression.relief.depressionissues.music.modelmix;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.CategoryMixer;

import java.util.List;

public class ListModelMix extends ViewModel {
    private MutableLiveData<List<CategoryMixer>> listMutableLiveData;
    private MutableLiveData<String> mText;

    public ListModelMix() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("This is mix fragment_setting");
    }

    public LiveData<String> getText() {
        return this.mText;
    }

    public LiveData<List<CategoryMixer>> getListCategoryData(Context context) {
        this.listMutableLiveData.setValue(MixDataParcer.getListMixCategoryItem(context));
        return this.listMutableLiveData;
    }
}
