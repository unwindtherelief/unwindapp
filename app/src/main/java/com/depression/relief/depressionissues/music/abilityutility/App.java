package com.depression.relief.depressionissues.music.abilityutility;

import androidx.multidex.MultiDexApplication;

import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends MultiDexApplication {
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MixDataParcer.createData(this);
    }
}
