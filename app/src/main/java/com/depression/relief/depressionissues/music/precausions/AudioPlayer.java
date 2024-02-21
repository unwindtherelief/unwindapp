package com.depression.relief.depressionissues.music.precausions;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import com.depression.relief.depressionissues.music.customhelper.Helpers;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;

import java.io.IOException;

public class AudioPlayer implements MySound {
    Context context;
    AssetFileDescriptor descriptor;
    MediaPlayer mCurrentPlayer;
    MediaPlayer mNextPlayer;
    /* access modifiers changed from: private */
    public final MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            AudioPlayer soundPlayer = AudioPlayer.this;
            soundPlayer.mCurrentPlayer = soundPlayer.mNextPlayer;
            AudioPlayer.this.rawNextplayerCreator();
            mediaPlayer.release();
        }
    };
    int playerState = -1;
    AudioItem soundItem;

    public void setContext(Context context2) {
        this.context = context2;
    }

    public void setDescriptor(AssetFileDescriptor assetFileDescriptor) {
        this.descriptor = assetFileDescriptor;
    }

    public void setMCurrentPlayer(MediaPlayer mediaPlayer) {
        this.mCurrentPlayer = mediaPlayer;
    }

    public void setMNextPlayer(MediaPlayer mediaPlayer) {
        this.mNextPlayer = mediaPlayer;
    }

    public MediaPlayer getMCurrentPlayer() {
        return this.mCurrentPlayer;
    }

    public MediaPlayer getMNextPlayer() {
        return this.mNextPlayer;
    }

    public Context getContext() {
        return this.context;
    }

    public AssetFileDescriptor getDescriptor() {
        return this.descriptor;
    }

    public AudioPlayer(Context context2, AudioItem soundItem2) {
        this.soundItem = soundItem2;
        this.context = context2;
        musicdataget();
    }

    public AudioItem getSoundItem() {
        return this.soundItem;
    }

    public void setSoundItem(AudioItem soundItem2) {
        this.soundItem = soundItem2;
    }

    public int getPlayerState() {
        return this.playerState;
    }

    public void setPlayerState(int i) {
        this.playerState = i;
    }

    private void musicdataget() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mCurrentPlayer = mediaPlayer;
        mediaPlayer.setAudioStreamType(3);
        try {
            this.soundItem.getFileName();
            this.descriptor = this.context.getAssets().openFd(Helpers.SOURCE_SOUND_PATH + this.soundItem.getFileName() + ".mp3");
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mCurrentPlayer = mediaPlayer2;
            float log = 1.0f - ((float) (Math.log((double) (100 - this.soundItem.getVolume())) / Math.log(100.0d)));
            mediaPlayer2.setVolume(log, log);
            this.playerState = 0;
            this.mCurrentPlayer.setDataSource(this.descriptor.getFileDescriptor(), this.descriptor.getStartOffset(), this.descriptor.getLength());
            this.mCurrentPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    AudioPlayer.this.mCurrentPlayer.start();
                }
            });
            this.mCurrentPlayer.prepareAsync();
            rawNextplayerCreator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void rawNextplayerCreator() {
        this.mNextPlayer = new MediaPlayer();
        try {
            float log = 1.0f - ((float) (Math.log((double) (100 - this.soundItem.getVolume())) / Math.log(100.0d)));
            this.mCurrentPlayer.setVolume(log, log);
            this.mNextPlayer.setAudioStreamType(3);
            this.mNextPlayer.setDataSource(this.descriptor.getFileDescriptor(), this.descriptor.getStartOffset(), this.descriptor.getLength());
            this.mNextPlayer.start();
            this.mNextPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    AudioPlayer.this.mCurrentPlayer.setNextMediaPlayer(AudioPlayer.this.mNextPlayer);
                    AudioPlayer.this.mCurrentPlayer.setOnCompletionListener(AudioPlayer.this.onCompletionListener);
                }
            });
            this.mNextPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return this.onCompletionListener;
    }

    public void play() {
        MediaPlayer mediaPlayer = this.mCurrentPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.start();
            this.playerState = 1;
        }
    }

    public void resume() {
        MediaPlayer mediaPlayer = this.mCurrentPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.start();
            this.playerState = 1;
        }
    }

    public void pause() {
        MediaPlayer mediaPlayer = this.mCurrentPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            this.playerState = 2;
        }
    }

    public void stop() {
        MediaPlayer mediaPlayer = this.mCurrentPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.playerState = 3;
        }
    }

    public void release() {
        stop();
        MediaPlayer mediaPlayer = this.mCurrentPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            Log.e("RELEASE", "release");
        }
    }

    public void init(AudioItem soundItem2) {
        this.soundItem = soundItem2;
    }

    public void setVolume(float f) {
        float log = 1.0f - ((float) (Math.log((double) (100.0f - f)) / Math.log(100.0d)));
        this.mCurrentPlayer.setVolume(log, log);
        this.soundItem.setVolume((int) f);
    }
}
