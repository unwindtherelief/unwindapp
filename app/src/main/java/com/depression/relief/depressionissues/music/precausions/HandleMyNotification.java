package com.depression.relief.depressionissues.music.precausions;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.MusicExploreActivity;
import com.depression.relief.depressionissues.music.abilityutility.HourTills;
import com.depression.relief.depressionissues.music.abilityutility.ShareListUtils;


public class HandleMyNotification {
    private static final String CHANNEL_ID = "com.lubuteam.sleepsound.Notification";
    private static NotificationCompat.Builder builder = null;
    public static final int notificationID = 1127;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createChannel(Context context) {
        @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Sleep sound playback", 2);
        notificationChannel.setDescription("Sleep sound playback controls");
        notificationChannel.setShowBadge(false);
        notificationChannel.setLockscreenVisibility(1);
        ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(notificationChannel);
    }

    public static void notificationBuilder(ServiceAudioPlayer soundPlayerService) {
        if (soundPlayerService == null) {
            Log.e("NOTIFICATION", "NULL");
            return;
        }
        Log.e("NOTIFICATION", "OK");
        RemoteViews remoteViews = new RemoteViews(soundPlayerService.getPackageName(), R.layout.notification_sound_timing_layout);
        Intent intent = new Intent(soundPlayerService, MusicExploreActivity.class);
        intent.putExtra(ServiceAudioPlayer.ACTION_OPEN_FROM_NOTIFI, true);
        intent.addFlags(67108864);
        PendingIntent activity = PendingIntent.getActivity(soundPlayerService, 0, intent, ShareListUtils.getPendingIntentFlag());
        if (Build.VERSION.SDK_INT >= 26) {
            createChannel(soundPlayerService);
        }
        builder = new NotificationCompat.Builder((Context) soundPlayerService, CHANNEL_ID).setCustomContentView(remoteViews).setVisibility(1);
        remoteViews.setOnClickPendingIntent(R.id.sound_notification_layout, activity);
        if (soundPlayerService.getSoundPlayerManager().getMixItem() != null) {
            remoteViews.setTextViewText(R.id.sound_name, soundPlayerService.getSoundPlayerManager().getMixItem().getName());
        }
        long timeToStopSound = soundPlayerService.getTimeToStopSound();
        if (timeToStopSound > 0) {
            remoteViews.setTextViewText(R.id.count_down_tv, HourTills.mstostringConverter(timeToStopSound));
        } else {
            remoteViews.setTextViewText(R.id.count_down_tv, soundPlayerService.getResources().getString(R.string.timer));
        }
        if (soundPlayerService.getSoundPlayerManager().getPlayerState() == 1) {
            builder.setSmallIcon((int) R.mipmap.ic_launcher);
            builder.setOngoing(true);
            remoteViews.setImageViewResource(R.id.play_control_iv_1, R.drawable.double_pause);
        } else {
            builder.setSmallIcon((int) R.mipmap.ic_launcher);
            builder.setOngoing(false);
            remoteViews.setImageViewResource(R.id.play_control_iv_1, R.drawable.single_play);
        }
        adjuster(remoteViews, soundPlayerService);
        NotificationManagerCompat.from(soundPlayerService).notify(notificationID, builder.build());
    }

    private static void adjuster(RemoteViews remoteViews, ServiceAudioPlayer soundPlayerService) {
        Intent playter = new Intent(soundPlayerService, ServiceAudioPlayer.class);
        playter.setAction(ServiceAudioPlayer.ACTION_CMD);
        playter.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_NOTIFICATION_PLAY);
        PendingIntent service = PendingIntent.getService(soundPlayerService, 0, playter, ShareListUtils.getPendingIntentFlag());
        Intent playber = new Intent(soundPlayerService, ServiceAudioPlayer.class);
        playber.setAction("ACTION_CMD_1");
        playber.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_NOTIFICATION_CLOSE);
        PendingIntent service2 = PendingIntent.getService(soundPlayerService, 0, playber, ShareListUtils.getPendingIntentFlag());
        remoteViews.setOnClickPendingIntent(R.id.play_control_iv_1, service);
        remoteViews.setOnClickPendingIntent(R.id.close_iv_1, service2);
    }

    private static PendingIntent onButtonNotificationClick(int i, Context context) {
        Intent intent = new Intent(ServiceAudioPlayer.ACTION_CMD);
        intent.putExtra(ServiceAudioPlayer.CMD_NAME, i);
        return PendingIntent.getBroadcast(context, i, intent, 0);
    }

    public static void notificationRemover(Context context) {
        Log.e("REMOVE", "REMOVE");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            notificationManager.cancel(notificationID);
        }
    }

    public static void buildBedNotification(Context context) {
        if (context == null) {
            Log.e("NOTIFICATION", "NULL");
            return;
        }
        Log.e("NOTIFICATION", "OK");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_handler);
        Intent intent = new Intent(context, MusicExploreActivity.class);
        intent.addFlags(67108864);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 134217728);
        if (Build.VERSION.SDK_INT >= 26) {
            createChannel(context);
        }
        NotificationCompat.Builder visibility = new NotificationCompat.Builder(context, CHANNEL_ID).setWhen(System.currentTimeMillis()).setCategory("android.intent.category.DEFAULT").setPriority(2).setShowWhen(true).setAutoCancel(true).setContent(remoteViews).setSmallIcon((int) R.mipmap.ic_launcher).setVisibility(1);
        visibility.setSound(RingtoneManager.getDefaultUri(2));
        visibility.setContentIntent(activity);
        NotificationManagerCompat.from(context).notify(notificationID, visibility.build());
    }

    public static void update(ServiceAudioPlayer soundPlayerService) {
        if (soundPlayerService == null || builder == null) {
            Log.e("NOTIFICATION", "NULL");
            return;
        }
        Log.e("NOTIFICATION", "OK");
        RemoteViews remoteViews = new RemoteViews(soundPlayerService.getPackageName(), R.layout.notification_sound_timing_layout);
        Intent intent = new Intent(soundPlayerService, MusicExploreActivity.class);
        intent.putExtra(ServiceAudioPlayer.ACTION_OPEN_FROM_NOTIFI, true);
        intent.addFlags(67108864);
        PendingIntent activity = PendingIntent.getActivity(soundPlayerService, 0, intent, 134217728);
        if (Build.VERSION.SDK_INT >= 26) {
            createChannel(soundPlayerService);
        }
        remoteViews.setOnClickPendingIntent(R.id.sound_notification_layout, activity);
        if (soundPlayerService.getSoundPlayerManager().getMixItem() != null) {
            remoteViews.setTextViewText(R.id.sound_name, soundPlayerService.getSoundPlayerManager().getMixItem().getName());
        }
        long timeToStopSound = soundPlayerService.getTimeToStopSound();
        if (timeToStopSound > 0) {
            remoteViews.setTextViewText(R.id.count_down_tv, HourTills.mstostringConverter(timeToStopSound));
        } else {
            remoteViews.setTextViewText(R.id.count_down_tv, soundPlayerService.getResources().getString(R.string.timer));
        }
        if (soundPlayerService.getSoundPlayerManager().getPlayerState() == 1) {
            builder.setSmallIcon((int) R.mipmap.ic_launcher);
            builder.setOngoing(true);
            remoteViews.setImageViewResource(R.id.play_control_iv_1, R.drawable.double_pause);
        } else {
            builder.setSmallIcon((int) R.mipmap.ic_launcher);
            builder.setOngoing(false);
            remoteViews.setImageViewResource(R.id.play_control_iv_1, R.drawable.single_play);
        }
        builder.setContent(remoteViews);
        adjuster(remoteViews, soundPlayerService);
        NotificationManagerCompat.from(soundPlayerService).notify(notificationID, builder.build());
    }
}
