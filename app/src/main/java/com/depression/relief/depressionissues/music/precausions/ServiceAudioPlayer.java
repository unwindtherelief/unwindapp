package com.depression.relief.depressionissues.music.precausions;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.HourTills;
import com.depression.relief.depressionissues.music.abilityutility.SharedPrefsUtils;
import com.depression.relief.depressionissues.music.abilityutility.samaycontroller.TimerGlass;
import com.depression.relief.depressionissues.music.customhelper.Helpers;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;

public class ServiceAudioPlayer extends Service {
    public static final String ACTION_CMD = "com.lubuteam.sleepsound.ACTION_CMD";
    public static final String ACTION_OPEN_FROM_NOTIFI = "com.lubuteam.sleepsound.ACTION_OPEN_FROM_NOTIFI";
    public static final String ACTION_UPDATE_BED = "com.lubuteam.sleepsound.ACTION_UPDATE_BED";
    public static final String ACTION_UPDATE_PLAY_STATE = "com.lubuteam.sleepsound.ACTION_UPDATE_PLAY_STATE";
    public static final String ACTION_UPDATE_SOUND_SELECTED = "com.lubuteam.sleepsound.ACTION_UPDATE_SOUND_SELECTED";
    public static final String ACTION_UPDATE_TIME = "com.lubuteam.sleepsound.ACTION_UPDATE_TIME";
    public static final String CMD_NAME = "CMD_NAME";
    public static final String CMD_NOTIFICATION_CLOSE = "CMD_NOTIFICATION_CLOSE";
    public static final String CMD_NOTIFICATION_PLAY = "CMD_NOTIFICATION_PLAY";
    public static final String CMD_PAUSE = "CMD_PAUSE";
    public static final String CMD_PAUSE_ALL = "CMD_PAUSE_ALL";
    public static final String CMD_PLAY = "CMD_PLAY";
    public static final String CMD_PLAY_ALL = "CMD_PLAY_ALL";
    public static final String CMD_PLAY_MIX = "CMD_PLAY_MIX";
    public static final String CMD_RELEASE = "CMD_RELEASE";
    public static final String CMD_RELEASE_ALL = "CMD_RELEASE_ALL";
    public static final String CMD_RESUME = "CMD_RESUME";
    public static final String CMD_RESUME_ALL = "CMD_RESUME_ALL";
    public static final String CMD_STOP = "CMD_STOP";
    public static final String CMD_STOP_ALL = "CMD_STOP_ALL";
    public static final String CMD_UPDATE_TIME = "CMD_UPDATE_TIME";
    public static final String COVERT_ID = "COVERT_ID";
    public static final String EXTRA_CONNECTED_CAST = "CAST_NAME";
    public static final String MIX_ID = "MIX_ID";
    public static final String RESULT_CODE = "RESULT_CODE";
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_EXISTED = 2;
    public static final String RESULT_MESSAGE = "RESULT_MESSAGE";
    public static final int RESULT_OK = 1;
    public static final String SOUND_ID = "SOUND_ID";
    public static final String UPDATE_PLAY_STATE = "com.lubuteam.sleepsound.UPDATE_PLAY_STATE";
    public static final String UPDATE_SOUND_SELECTED = "com.lubuteam.sleepsound.UPDATE_SOUND_SELECTED";
    public static final String UPDATE_TIME = "com.lubuteam.sleepsound.UPDATE_TIME";
    private static final int REQ_TIMER = 111;
    private static final int STOP_DELAY = 3600000;
    /* access modifiers changed from: private */
    public TimerGlass countDownTimer;
    /* access modifiers changed from: private */
    public boolean isEndTime = false;
    /* access modifiers changed from: private */
    public AudioPlayerManager soundPlayerManager;
    /* access modifiers changed from: private */
    public long timeToStopSound = 0;
    /* access modifiers changed from: private */
    public long timeToStopSound1 = 0;
    private Handler handler = new Handler();
    private SoundPlayerServiceBinder soundPlayerServiceBinder = new SoundPlayerServiceBinder();

    public IBinder onBind(Intent intent) {
        return null;
    }

    public AudioPlayerManager getSoundPlayerManager() {
        return this.soundPlayerManager;
    }

    public long getTimeToStopSound() {
        return this.timeToStopSound1;
    }

    public void onCreate() {
        super.onCreate();
        this.soundPlayerManager = AudioPlayerManager.getInstance(this);
        Log.e("SERVICE", "START");
        this.timeToStopSound = SharedPrefsUtils.getLongPreference(this, Helpers.KEY_PLAY_TIME, 1200000);
        this.timeToStopSound1 = SharedPrefsUtils.getLongPreference(this, Helpers.KEY_PLAY_TIME, 1200000);
    }

    /* access modifiers changed from: private */
    public void timeUpdater(long j) {
        Intent intent = new Intent(ACTION_UPDATE_TIME);
        intent.putExtra(RESULT_CODE, 1);
        intent.putExtra(UPDATE_TIME, j);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        HandleMyNotification.notificationBuilder(this);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        startForeground(HandleMyNotification.notificationID, new NotificationCompat.Builder((Context) this, Build.VERSION.SDK_INT >= 26 ? createNotificationChannel((NotificationManager) getSystemService("notification")) : "").setOngoing(true).setSmallIcon((int) R.mipmap.ic_launcher).setPriority(-2).setCategory(NotificationCompat.CATEGORY_SERVICE).build());
        if (!(intent == null || intent.getAction() == null)) {
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra(CMD_NAME);
            if ("ACTION_CMD_1".equals(action)) {
                handlerPauseAll((Intent) null);
                TimerGlass hourglass = this.countDownTimer;
                if (hourglass != null) {
                    hourglass.pauseTimer();
                }
                HandleMyNotification.notificationRemover(getApplicationContext());
                stopForeground(true);
                onDestroy();
            }
            if (ACTION_CMD.equals(action)) {
                stringExtra.hashCode();
                char c = 65535;
                switch (stringExtra.hashCode()) {
                    case -1835509959:
                        if (stringExtra.equals(CMD_PLAY)) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1835412473:
                        if (stringExtra.equals(CMD_STOP)) {
                            c = 1;
                            break;
                        }
                        break;
                    case -1066542479:
                        if (stringExtra.equals(CMD_PAUSE)) {
                            c = 2;
                            break;
                        }
                        break;
                    case -933927245:
                        if (stringExtra.equals(CMD_PAUSE_ALL)) {
                            c = 3;
                            break;
                        }
                        break;
                    case -864311870:
                        if (stringExtra.equals(CMD_RELEASE)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 163903710:
                        if (stringExtra.equals(CMD_UPDATE_TIME)) {
                            c = 5;
                            break;
                        }
                        break;
                    case 507970761:
                        if (stringExtra.equals(CMD_NOTIFICATION_CLOSE)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 575407796:
                        if (stringExtra.equals(CMD_RESUME_ALL)) {
                            c = 7;
                            break;
                        }
                        break;
                    case 709509667:
                        if (stringExtra.equals(CMD_NOTIFICATION_PLAY)) {
                            c = 8;
                            break;
                        }
                        break;
                    case 922427780:
                        if (stringExtra.equals(CMD_RELEASE_ALL)) {
                            c = 9;
                            break;
                        }
                        break;
                    case 1243522377:
                        if (stringExtra.equals(CMD_STOP_ALL)) {
                            c = 10;
                            break;
                        }
                        break;
                    case 1357816562:
                        if (stringExtra.equals(CMD_RESUME)) {
                            c = 11;
                            break;
                        }
                        break;
                    case 1407467387:
                        if (stringExtra.equals(CMD_PLAY_ALL)) {
                            c = 12;
                            break;
                        }
                        break;
                    case 1407478838:
                        if (stringExtra.equals(CMD_PLAY_MIX)) {
                            c = 13;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        handlerPlay(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 1:
                        handlerStop(intent);
                        break;
                    case 2:
                        handlerPause(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 3:
                        handlerPauseAll(intent);
                        TimerGlass hourglass2 = this.countDownTimer;
                        if (hourglass2 != null) {
                            hourglass2.pauseTimer();
                        }
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 4:
                        handlerRelease(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 5:
                        this.timeToStopSound = SharedPrefsUtils.getLongPreference(this, Helpers.KEY_PLAY_TIME, 1200000);
                        handlerUpdateTime(intent);
                        break;
                    case 6:
                        handlerPauseAll(intent);
                        TimerGlass hourglass3 = this.countDownTimer;
                        if (hourglass3 != null) {
                            hourglass3.pauseTimer();
                        }
                        HandleMyNotification.notificationRemover(getApplicationContext());
                        stopForeground(true);
                        onDestroy();
                        break;
                    case 7:
                        handlerResumeAll(intent);
                        TimerGlass hourglass4 = this.countDownTimer;
                        if (hourglass4 != null) {
                            if (this.isEndTime) {
                                hourglass4.startTimer();
                                this.isEndTime = false;
                            } else {
                                hourglass4.resumeTimer();
                            }
                        }
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 8:
                        if (this.soundPlayerManager.getPlayerState() == 1) {
                            handlerPauseAll(intent);
                            TimerGlass hourglass5 = this.countDownTimer;
                            if (hourglass5 != null) {
                                hourglass5.pauseTimer();
                            }
                        } else {
                            handlerResumeAll(intent);
                            TimerGlass hourglass6 = this.countDownTimer;
                            if (hourglass6 != null) {
                                if (this.isEndTime) {
                                    hourglass6.startTimer();
                                    this.isEndTime = false;
                                } else {
                                    hourglass6.resumeTimer();
                                }
                            }
                        }
                        HandleMyNotification.notificationBuilder(this);
                        Log.e(CMD_NOTIFICATION_PLAY, CMD_NOTIFICATION_PLAY);
                        break;
                    case 9:
                        handlerReleaseAll(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 10:
                        handlerStopAll(intent);
                        break;
                    case 11:
                        handlerResume(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 12:
                        handlerPlayAll(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                    case 13:
                        handlerPlayMix(intent);
                        HandleMyNotification.notificationBuilder(this);
                        break;
                }
            }
        }
        return 2;
    }

    private void handlerUpdateTime(Intent intent) {
        TimerGlass hourglass = this.countDownTimer;
        if (hourglass != null) {
            hourglass.stopTimerWithNoAction();
        }
        if (SharedPrefsUtils.getLongPreference(this, Helpers.KEY_PLAY_TIME, 1200000) > 0) {
            this.countDownTimer = new TimerGlass(this.timeToStopSound, 1000) {
                public void onTimerTick(long j) {
                    ServiceAudioPlayer.this.timeUpdater(j);
                    long unused = ServiceAudioPlayer.this.timeToStopSound1 = j;
                    Log.e("TICK", HourTills.mstostringConverter(j));
                }

                public void onTimerFinish() {
                    if (ServiceAudioPlayer.this.soundPlayerManager.getPlayerState() == 1) {
                        ServiceAudioPlayer.this.handlerPauseAll(new Intent());
                        ServiceAudioPlayer soundPlayerService = ServiceAudioPlayer.this;
                        soundPlayerService.timeUpdater(soundPlayerService.timeToStopSound);
                        ServiceAudioPlayer.this.countDownTimer.setTime(ServiceAudioPlayer.this.timeToStopSound);
                        boolean unused = ServiceAudioPlayer.this.isEndTime = true;
                        Log.e("PAUSE_PLAY_COUNT", "TRUE");
                        return;
                    }
                    Log.e("PAUSE_PLAY_COUNT", "FALSE");
                }
            };
            if (this.soundPlayerManager.getPlayerState() == 1) {
                this.countDownTimer.startTimer();
                this.isEndTime = false;
                Log.e("START_COUNT", "TRUE");
                return;
            }
            Log.e("START_COUNT", "FALSE");
            return;
        }
        timeUpdater(-1);
    }

    private void handlerPlayMix(Intent intent) {
        ItemMixer mixItemById = MixDataParcer.getMixItemById(intent.getIntExtra(MIX_ID, -1));
        ItemMixer mixItem = this.soundPlayerManager.getMixItem();
        if (mixItemById == null || mixItem == null || mixItemById.getMixSoundId() != mixItem.getMixSoundId()) {
            handlerReleaseAll(intent);
            this.soundPlayerManager.setMixItem(mixItemById);
            for (AudioItem next : mixItemById.getSoundList()) {
                if (next != null) {
                    this.soundPlayerManager.play(next);
                }
            }
            handlerUpdateTime(intent);
        } else {
            handlerResumeAll(new Intent());
            Intent intent2 = new Intent();
            handlerResumeAll(intent2);
            handlerUpdateTime(intent2);
            Log.e("PLAY", "SAME_MIX");
        }
        updatePlayerState();
    }

    private void handlerPause(Intent intent) {
        AudioItem soundItemById = MixDataParcer.getSoundItemById(intent.getIntExtra(SOUND_ID, -1));
        if (soundItemById != null) {
            this.soundPlayerManager.pause(soundItemById);
        }
    }

    /* access modifiers changed from: private */
    public void handlerPauseAll(Intent intent) {
        this.soundPlayerManager.pauseAllPlayer();
        updatePlayerState();
    }

    private void handlerPlay(Intent intent) {
        AudioItem soundItemById = MixDataParcer.getSoundItemById(intent.getIntExtra(SOUND_ID, -1));
        if (soundItemById != null) {
            this.soundPlayerManager.play(soundItemById);
        }
        updateSoundSelect();
    }

    private void handlerPlayAll(Intent intent) {
        this.soundPlayerManager.playAllPlayer();
        updatePlayerState();
    }

    private void handlerRelease(Intent intent) {
        AudioItem soundItemById = MixDataParcer.getSoundItemById(intent.getIntExtra(SOUND_ID, -1));
        if (soundItemById != null) {
            this.soundPlayerManager.release(soundItemById);
        }
    }

    private void handlerReleaseAll(Intent intent) {
        this.soundPlayerManager.removeAllPlayer();
    }

    private void handlerResume(Intent intent) {
        AudioItem soundItemById = MixDataParcer.getSoundItemById(intent.getIntExtra(SOUND_ID, -1));
        if (soundItemById != null) {
            this.soundPlayerManager.resume(soundItemById);
        }
    }

    private void handlerResumeAll(Intent intent) {
        this.soundPlayerManager.resumeAllPlayer();
        updatePlayerState();
    }

    private void handlerStop(Intent intent) {
        AudioItem soundItemById = MixDataParcer.getSoundItemById(intent.getIntExtra(SOUND_ID, -1));
        if (soundItemById != null) {
            this.soundPlayerManager.stop(soundItemById);
        }
    }

    private void handlerStopAll(Intent intent) {
        this.soundPlayerManager.stopAllPlayer();
        this.countDownTimer.stopTimer();
        updatePlayerState();
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("SERVICE", "STOP");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel notificationChannel = new NotificationChannel("com.lubuteam.sleepsound.notification", "NotificationService", 4);
        notificationChannel.setImportance(0);
        notificationChannel.setLockscreenVisibility(0);
        notificationManager.createNotificationChannel(notificationChannel);
        return "com.lubuteam.sleepsound.notification";
    }

    private void updateSoundSelect() {
        Intent intent = new Intent(ACTION_UPDATE_SOUND_SELECTED);
        intent.putExtra(RESULT_CODE, 1);
        intent.putExtra(UPDATE_SOUND_SELECTED, this.soundPlayerManager.getSizePlayer());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Log.e("SEND_BROADCAST", "UPDATE_SOUND_SELECTED " + this.soundPlayerManager.getSizePlayer());
    }

    private void updatePlayerState() {
        Intent intent = new Intent(ACTION_UPDATE_PLAY_STATE);
        intent.putExtra(RESULT_CODE, 1);
        intent.putExtra(UPDATE_PLAY_STATE, this.soundPlayerManager.getPlayerState());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Log.e("SEND_BROADCAST", "UPDATE_PLAY_STATE " + this.soundPlayerManager.getPlayerState());
    }

    public class SoundPlayerServiceBinder extends Binder {
        public SoundPlayerServiceBinder() {
        }

        public ServiceAudioPlayer getService() {
            return ServiceAudioPlayer.this;
        }
    }
}
