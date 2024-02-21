package com.depression.relief.depressionissues.music.precausions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.depression.relief.depressionissues.music.abilityutility.TillsUtils;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AudioPlayerManager implements MySoundManager {
    public static SparseArray<AudioItem> soundItemSparseArray = new SparseArray<>();
    static AudioPlayerManager soundPlayerManager;
    public static SparseArray<AudioPlayer> sparseArray = new SparseArray<AudioPlayer>();
    Context context;
    public ItemMixer mixItem;
    int playerState = -1;

    public void setContext(Context context2) {
        this.context = context2;
    }

    public void setPlayerState(int i) {
        this.playerState = i;
    }

    public Context getContext() {
        return this.context;
    }

    public AudioPlayerManager(Context context2) {
        this.context = context2;
    }

    public static AudioPlayerManager getInstance(Context context2) {
        AudioPlayerManager soundPlayerManager2 = soundPlayerManager;
        if (soundPlayerManager2 != null) {
            return soundPlayerManager2;
        }
        AudioPlayerManager soundPlayerManager3 = new AudioPlayerManager(context2);
        soundPlayerManager = soundPlayerManager3;
        return soundPlayerManager3;
    }

    public ItemMixer getMixItem() {
        return this.mixItem;
    }

    public void setMixItem(ItemMixer mixItem2) {
        this.mixItem = mixItem2;
    }

    public void play(AudioItem soundItem) {
        SparseArray<AudioPlayer> sparseArray2 = sparseArray;
        if (sparseArray2 != null) {
            if (sparseArray2.get(soundItem.getSoundId()) != null) {
                release(soundItem);
                Log.e("release ", sparseArray.size() + "");
                if (soundPlayerManager.getSizePlayer() == 0) {
                    this.mixItem = null;
                    Intent intent = new Intent(ServiceAudioPlayer.ACTION_UPDATE_PLAY_STATE);
                    intent.putExtra(ServiceAudioPlayer.RESULT_CODE, 1);
                    intent.putExtra(ServiceAudioPlayer.UPDATE_PLAY_STATE, 0);
                    LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
                    Log.e("SEND_BROADCAST", "UPDATE_PLAY_STATE 0");
                    Intent intent2 = new Intent(this.context, ServiceAudioPlayer.class);
                    intent2.setAction(ServiceAudioPlayer.ACTION_CMD);
                    intent2.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_NOTIFICATION_CLOSE);
                    this.context.startService(intent2);
                    return;
                }
                return;
            }
            if (sparseArray.size() < 8) {
                AudioPlayer soundPlayer = new AudioPlayer(this.context, soundItem);
                soundPlayer.play();
                sparseArray.put(soundItem.getSoundId(), soundPlayer);
                Log.e("SoundPlayer play ", soundItem.getSoundId() + "");
            } else {
                Log.e("MAX_SIZE_PLAYER", "TRUE");
            }
            Log.e("SIZE_PLAYER ", sparseArray.size() + "");
            setVolume(soundItem, (float) soundItem.getVolume());
        }
    }

    public void resume(AudioItem soundItem) {
        SparseArray<AudioPlayer> sparseArray2 = sparseArray;
        if (sparseArray2 != null) {
            AudioPlayer soundPlayer = sparseArray2.get(soundItem.getSoundId());
            if (soundPlayer != null) {
                soundPlayer.play();
            } else {
                Log.e("SoundPlayer", "SoundPlayer null");
            }
            PrintStream printStream = System.out;
            printStream.println("SoundPlayer resume " + soundItem.getSoundId());
        }
    }

    public void pause(AudioItem soundItem) {
        SparseArray<AudioPlayer> sparseArray2 = sparseArray;
        if (sparseArray2 != null) {
            AudioPlayer soundPlayer = sparseArray2.get(soundItem.getSoundId());
            if (soundPlayer != null) {
                soundPlayer.pause();
            } else {
                Log.e("SoundPlayer", "SoundPlayer null");
            }
            PrintStream printStream = System.out;
            printStream.println("SoundPlayer pause " + soundItem.getSoundId());
        }
    }

    public void stop(AudioItem soundItem) {
        SparseArray<AudioPlayer> sparseArray2 = sparseArray;
        if (sparseArray2 != null) {
            AudioPlayer soundPlayer = sparseArray2.get(soundItem.getSoundId());
            if (soundPlayer != null) {
                soundPlayer.stop();
            } else {
                Log.e("SoundPlayer", "SoundPlayer null");
            }
            PrintStream printStream = System.out;
            printStream.println("SoundPlayer stop " + soundItem.getSoundId());
        }
    }

    public void release(AudioItem soundItem) {
        SparseArray<AudioPlayer> sparseArray2 = sparseArray;
        if (sparseArray2 != null) {
            AudioPlayer soundPlayer = sparseArray2.get(soundItem.getSoundId());
            if (soundPlayer != null) {
                soundPlayer.release();
                sparseArray.remove(soundItem.getSoundId());
            } else {
                Log.e("SoundPlayer", "SoundPlayer null");
            }
            PrintStream printStream = System.out;
            printStream.println("SoundPlayer release " + soundItem.getSoundId());
            if (soundPlayerManager.getSizePlayer() == 0) {
                this.mixItem = null;
                Intent intent = new Intent(ServiceAudioPlayer.ACTION_UPDATE_PLAY_STATE);
                intent.putExtra(ServiceAudioPlayer.RESULT_CODE, 1);
                intent.putExtra(ServiceAudioPlayer.UPDATE_PLAY_STATE, 0);
                LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
                Log.e("SEND_BROADCAST", "UPDATE_PLAY_STATE 0");
                Intent intent2 = new Intent(this.context, ServiceAudioPlayer.class);
                intent2.setAction(ServiceAudioPlayer.ACTION_CMD);
                intent2.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_NOTIFICATION_CLOSE);
                this.context.startService(intent2);
            }
        }
    }

    public void setVolume(AudioItem soundItem, float f) {
        SparseArray<AudioPlayer> sparseArray2 = sparseArray;
        if (sparseArray2 != null) {
            AudioPlayer soundPlayer = sparseArray2.get(soundItem.getSoundId());
            if (soundPlayer != null) {
                soundPlayer.setVolume(f);
                Log.e("CURRENR_VOLUME", f + "");
                return;
            }
            Log.e("SoundPlayer", "SoundPlayer null");
        }
    }

    public void removeAllPlayer() {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                sparseArray.valueAt(i).release();
            }
            sparseArray.clear();
        }
    }

    public void pauseAllPlayer() {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                AudioPlayer valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    valueAt.pause();
                } else {
                    Log.e("SoundPlayer", "SoundPlayer null");
                }
            }
            System.out.println("SoundPlayer pauseAllPlayer");
        }
    }

    public void stopAllPlayer() {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                AudioPlayer valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    valueAt.stop();
                } else {
                    Log.e("SoundPlayer", "SoundPlayer null");
                }
            }
            System.out.println("SoundPlayer stopAllPlayer");
        }
    }

    public void playAllPlayer() {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                AudioPlayer valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    valueAt.play();
                } else {
                    Log.e("SoundPlayer", "SoundPlayer null");
                }
            }
            System.out.println("SoundPlayer playAllPlayer");
        }
    }

    public void resumeAllPlayer() {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                AudioPlayer valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    valueAt.resume();
                } else {
                    Log.e("SoundPlayer", "SoundPlayer null");
                }
            }
            System.out.println("SoundPlayer resumeAllPlayer");
        }
    }

    public boolean isMaxPlayerStart() {
        return sparseArray.size() >= 8;
    }

    public int getSizePlayer() {
        return sparseArray.size();
    }

    public AudioPlayer getSoundPlayerById(int i) {
        return sparseArray.get(i);
    }

    public List<AudioItem> getPlayingSoundItem() {
        ArrayList arrayList = new ArrayList();
        for (AudioPlayer soundItem : TillsUtils.asList(sparseArray)) {
            arrayList.add(soundItem.getSoundItem());
        }
        return arrayList;
    }

    public int getPlayerState() {
        if (sparseArray.size() > 0) {
            return sparseArray.valueAt(0).getPlayerState();
        }
        return 0;
    }

    public void addCustomSound(AudioItem soundItem) {
        if (soundItem != null) {
            soundItemSparseArray.put(soundItem.getSoundId(), soundItem);
        }
    }

    public void addListCustomSound(List<AudioItem> list) {
        if (list != null) {
            for (AudioItem next : list) {
                soundItemSparseArray.put(next.getSoundId(), next);
            }
        }
    }

    public void updateCustomSound(AudioItem soundItem) {
        if (soundItem != null) {
            soundItemSparseArray.get(soundItem.getSoundId()).update(soundItem);
            soundItemSparseArray.put(soundItem.getSoundId(), soundItem);
        }
    }

    public void removeCustomSound(int i) {
        soundItemSparseArray.remove(i);
    }

    public List<AudioItem> getAllCustomSound() {
        return TillsUtils.asList(soundItemSparseArray);
    }

    public void removeAllCustomSound() {
        soundItemSparseArray.clear();
    }
}
