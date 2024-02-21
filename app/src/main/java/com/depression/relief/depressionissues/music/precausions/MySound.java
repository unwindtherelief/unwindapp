package com.depression.relief.depressionissues.music.precausions;


import com.depression.relief.depressionissues.music.modelizer.AudioItem;

public interface MySound {
    void init(AudioItem soundItem);

    void pause();

    void play();

    void release();

    void resume();

    void setVolume(float f);

    void stop();
}
