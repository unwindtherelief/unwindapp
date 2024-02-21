package com.depression.relief.depressionissues.music.precausions;


import com.depression.relief.depressionissues.music.modelizer.AudioItem;

interface MySoundManager {
    void pause(AudioItem soundItem);

    void pauseAllPlayer();

    void play(AudioItem soundItem);

    void playAllPlayer();

    void release(AudioItem soundItem);

    void removeAllPlayer();

    void resume(AudioItem soundItem);

    void resumeAllPlayer();

    void setVolume(AudioItem soundItem, float f);

    void stop(AudioItem soundItem);

    void stopAllPlayer();
}
