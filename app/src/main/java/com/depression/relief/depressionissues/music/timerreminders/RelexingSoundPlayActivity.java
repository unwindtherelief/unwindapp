package com.depression.relief.depressionissues.music.timerreminders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkRequest;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.HourTills;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.abilityutility.ShareListUtils;
import com.depression.relief.depressionissues.music.abilityutility.SharedPrefsUtils;
import com.depression.relief.depressionissues.music.custombase.NeelDownActivity;
import com.depression.relief.depressionissues.music.customhelper.Helpers;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.depression.relief.depressionissues.music.precausions.AudioPlayerManager;
import com.depression.relief.depressionissues.music.precausions.ServiceAudioPlayer;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

public class RelexingSoundPlayActivity extends NeelDownActivity implements View.OnClickListener {
    /* access modifiers changed from: private */
    public AppCompatImageView mPlayControlView;
    /* access modifiers changed from: private */
    public TextView mPlayCountTimeTv;
    /* access modifiers changed from: private */
    public TextView mPlaySetTimeTv;
    /* access modifiers changed from: private */
    public ItemMixer mixItem;
    AudioManager audioManager;
    private List<View> animList = new ArrayList();
    private List<AnimatorSet> animatorSets = new ArrayList();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("WrongConstant")
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(ServiceAudioPlayer.RESULT_CODE, 0) == 1) {
                final long longExtra = intent.getLongExtra(ServiceAudioPlayer.UPDATE_TIME, -1);
                if (longExtra > 0) {
                    RelexingSoundPlayActivity.this.mPlaySetTimeTv.setVisibility(8);
                    RelexingSoundPlayActivity.this.mPlayCountTimeTv.setVisibility(0);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                RelexingSoundPlayActivity.this.mPlayCountTimeTv.setText(HourTills.mstostringConverter(longExtra));
                            } catch (Exception unused) {
                            }
                        }
                    }, 1000);
                    return;
                }
                RelexingSoundPlayActivity.this.mPlaySetTimeTv.setVisibility(0);
                RelexingSoundPlayActivity.this.mPlayCountTimeTv.setVisibility(8);
            }
        }
    };
    private BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(ServiceAudioPlayer.RESULT_CODE, 0) == 1) {
                int intExtra = intent.getIntExtra(ServiceAudioPlayer.UPDATE_PLAY_STATE, 0);
                Log.e("PLAYER_STATE", intExtra + "");
                if (intExtra == 0) {
                    RelexingSoundPlayActivity.this.mPlayControlView.setImageResource(R.drawable.single_play);
                    RelexingSoundPlayActivity.this.animationPause();
                } else if (intExtra == 1) {
                    RelexingSoundPlayActivity.this.mPlayControlView.setImageResource(R.drawable.double_pause);
                    RelexingSoundPlayActivity.this.animationPlayer();
                } else if (intExtra == 2) {
                    RelexingSoundPlayActivity.this.mPlayControlView.setImageResource(R.drawable.single_play);
                    RelexingSoundPlayActivity.this.animationPause();
                }
            }
        }
    };
    private Intent intent;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout mActivityPlayBottomAd;
    private AppCompatImageView mCloseView;
    private View mMixSoundCoverAnimView1;
    private View mMixSoundCoverAnimView2;
    private View mMixSoundCoverAnimView3;
    private AppCompatImageView mMoreView;
    private AppCompatImageView mPlayBgIv;
    private RelativeLayout mPlayView;
    private TextView mSoundName;
    private RecyclerView mSoundRcv;
    private RelativeLayout mToolbar;
    private TextView mTvSetTimerHint;
    private View mViewSetTimer;
    private int mixId = 0;
    private SlimAdapter slimAdapter;
    private SeekBar volControl;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Screentils.setFullScreenActivity(this);
        setContentView((int) R.layout.relexing_sound_player);
        volumeinitialbar();

//        PreloadSoundAds.showGoogleBannerAd(this, findViewById(R.id.bannerAds));

        ItemMixer mixItem2 = AudioPlayerManager.getInstance(this).getMixItem();
        this.mixItem = mixItem2;
        if (mixItem2 == null) {
            finish();
        }
        pagerViewneet();
        Screentils.hideActionBar(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter(ServiceAudioPlayer.ACTION_UPDATE_TIME));
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver1, new IntentFilter(ServiceAudioPlayer.ACTION_UPDATE_PLAY_STATE));
    }

    private void volumeinitialbar() {
        @SuppressLint("WrongConstant") AudioManager audioManager2 = (AudioManager) getSystemService("audio");
        this.audioManager = audioManager2;
        int streamMaxVolume = audioManager2.getStreamMaxVolume(3);
        int streamVolume = this.audioManager.getStreamVolume(3);
        SeekBar seekBar = (SeekBar) findViewById(R.id.volume_seekbar);
        this.volControl = seekBar;
        seekBar.setMax(streamMaxVolume);
        this.volControl.setProgress(streamVolume);
        this.volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                RelexingSoundPlayActivity.this.audioManager.setStreamVolume(3, i, 0);
            }
        });
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 24) {
            this.volControl.setProgress(this.volControl.getProgress() + 1);
            return true;
        } else if (i != 25) {
            return super.onKeyDown(i, keyEvent);
        } else {
            this.volControl.setProgress(this.volControl.getProgress() - 1);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    private void pagerViewneet() {
        this.mPlayBgIv = (AppCompatImageView) findViewById(R.id.play_bg_iv);
        this.mCloseView = (AppCompatImageView) findViewById(R.id.close_view);
        if (Screentils.isBiggerDevice(this)) {
                    this.mCloseView.setPadding((int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp), (int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp), (int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp), (int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp));
        }
        this.mCloseView.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.sound_name);
        this.mSoundName = textView;
        textView.setOnClickListener(this);
        this.mToolbar = (RelativeLayout) findViewById(R.id.toolbar);
        this.mMixSoundCoverAnimView1 = findViewById(R.id.mix_sound_cover_anim_view1);
        this.mMixSoundCoverAnimView2 = findViewById(R.id.mix_sound_cover_anim_view2);
        this.mMixSoundCoverAnimView3 = findViewById(R.id.mix_sound_cover_anim_view3);
        View findViewById = findViewById(R.id.view_set_timer);
        this.mViewSetTimer = findViewById;
        findViewById.setOnClickListener(this);
        this.mPlayCountTimeTv = (TextView) findViewById(R.id.play_count_time_tv);
        this.mPlaySetTimeTv = (TextView) findViewById(R.id.play_set_time_tv);
        this.mTvSetTimerHint = (TextView) findViewById(R.id.tv_set_timer_hint);
        this.mPlayView = (RelativeLayout) findViewById(R.id.play_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sound_rcv);
        this.mSoundRcv = recyclerView;
        recyclerView.setOnClickListener(this);
        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.play_control_view);
        this.mPlayControlView = appCompatImageView;
        appCompatImageView.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_play_bottom_ad);
        this.mActivityPlayBottomAd = linearLayout;
        linearLayout.setOnClickListener(this);
        this.animList.add(findViewById(R.id.mix_sound_cover_anim_view1));
        this.animList.add(findViewById(R.id.mix_sound_cover_anim_view2));
        this.animList.add(findViewById(R.id.mix_sound_cover_anim_view3));
        ViewGroup.LayoutParams layoutParams = findViewById(R.id.play_view).getLayoutParams();
        int m18448a = Screentils.m18448a(this, 70.0f, 41.0f);
        layoutParams.width = m18448a;
        layoutParams.height = m18448a;
        this.mSoundRcv.addItemDecoration(new ViewVideoDecorator());
        this.slimAdapter = SlimAdapter.create().register(R.layout.relexing_sounds_player_item, new SlimInjector<AudioItem>() {
            public void onInject(AudioItem soundItem, IViewInjector iViewInjector) {
                iViewInjector.text((int) R.id.sound_volume_tv, soundItem.getVolume() + "%");
                iViewInjector.image((int) R.id.sound_icon_iv, MixDataParcer.getResourceIDUsingsoundId(soundItem));
                iViewInjector.invisible(R.id.sound_count_tv);
                iViewInjector.clicked(R.id.sound_play_layout, new View.OnClickListener() {
                    public void onClick(View view) {
                        RelexingSoundPlayActivity.this.startActivity(new Intent(RelexingSoundPlayActivity.this, CustomizeSoundsActivity.class));
                    }
                });
            }
        }).register(R.layout.relexing_sounds_player_item, new SlimInjector<String>() {
            public void onInject(String str, IViewInjector iViewInjector) {
                iViewInjector.text((int) R.id.sound_volume_tv, (CharSequence) "Edit");
                iViewInjector.image((int) R.id.sound_icon_iv, (int) R.drawable.add_more_icon);
                iViewInjector.visible(R.id.sound_count_tv);
                iViewInjector.text((int) R.id.sound_count_tv, RelexingSoundPlayActivity.this.mixItem.getSoundList().size() + "");
                iViewInjector.clicked(R.id.sound_play_layout, new View.OnClickListener() {
                    public void onClick(View view) {
                        RelexingSoundPlayActivity.this.startActivity(new Intent(RelexingSoundPlayActivity.this, CustomizeSoundsActivity.class));
                    }
                });
            }
        }).enableDiff().attachTo(this.mSoundRcv);
        this.mActivityPlayBottomAd = (LinearLayout) findViewById(R.id.activity_play_bottom_ad);
        animationCreator();
        animationPlayer();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.close_view) {
            finish();
        } else if (id == R.id.play_control_view) {
            Log.e("STATE", AudioPlayerManager.getInstance(this).getPlayerState() + "");
            if (AudioPlayerManager.getInstance(this).getPlayerState() == 2) {
                Intent intent2 = new Intent(this, ServiceAudioPlayer.class);
                this.intent = intent2;
                intent2.setAction(ServiceAudioPlayer.ACTION_CMD);
                this.intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_RESUME_ALL);
                startService(this.intent);
                this.mPlayControlView.setImageResource(R.drawable.double_pause);
            } else if (AudioPlayerManager.getInstance(this).getPlayerState() == 1) {
                Intent intent3 = new Intent(this, ServiceAudioPlayer.class);
                this.intent = intent3;
                intent3.setAction(ServiceAudioPlayer.ACTION_CMD);
                this.intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PAUSE_ALL);
                startService(this.intent);
                this.mPlayControlView.setImageResource(R.drawable.single_play);
            }
        }
    }

    @SuppressLint("WrongConstant")
    private void animationCreator() {
        for (int i = 0; i < this.animList.size(); i++) {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.animList.get(i), "alpha", new float[]{0.3f, 0.0f});
            ofFloat.setRepeatCount(-1);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.animList.get(i), "scaleX", new float[]{1.0f, 1.3f});
            ofFloat2.setRepeatCount(-1);
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.animList.get(i), "scaleY", new float[]{1.0f, 1.3f});
            ofFloat3.setRepeatCount(-1);
            animatorSet.play(ofFloat).with(ofFloat2).with(ofFloat3);
            animatorSet.setInterpolator((TimeInterpolator) null);
            animatorSet.setStartDelay((long) (i * 2000));
            animatorSet.setDuration(6000);
            this.animatorSets.add(animatorSet);
        }
        float screenWidth = (float) Screentils.getScreenWidth(this);
        this.mPlayBgIv.getLayoutParams().width = (int) (1.3f * screenWidth);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mPlayBgIv, "translationX", new float[]{(float) ((int) (0.15f * screenWidth)), (float) ((int) (screenWidth * -0.15f))});
        ofFloat4.setInterpolator((TimeInterpolator) null);
        ofFloat4.setDuration(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
        ofFloat4.setRepeatCount(-1);
        ofFloat4.setRepeatMode(2);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(ofFloat4);
        this.animatorSets.add(animatorSet2);
    }

    public void animationPlayer() {
        for (AnimatorSet next : this.animatorSets) {
            if (next.isPaused()) {
                next.resume();
            } else if (!next.isRunning()) {
                next.start();
            }
        }
    }

    public void animationPause() {
        for (AnimatorSet pause : this.animatorSets) {
            pause.pause();
        }
    }

    public void onResume() {
        super.onResume();
        updateUI();
    }

    @SuppressLint("WrongConstant")
    public void updateUI() {
        if (ShareListUtils.isbIsTimerSet()) {
            ShareListUtils.setbIsTimerSet(false);
        }
        ItemMixer mixItem2 = AudioPlayerManager.getInstance(this).getMixItem();
        this.mixItem = mixItem2;
        if (mixItem2 != null) {
            ItemMixer mixItemById = MixDataParcer.getMixItemById(mixItem2.getMixSoundId());
            this.mixItem = mixItemById;
            if (mixItemById.getSoundList().size() >= 6) {
                this.linearLayoutManager = new GridLayoutManager((Context) this, 5, 1, false);
            } else if (this.mixItem.getSoundList().size() < 1) {
                this.linearLayoutManager = new GridLayoutManager((Context) this, 1, 1, false);
            } else {
                this.linearLayoutManager = new GridLayoutManager((Context) this, this.mixItem.getSoundList().size() + 1, 1, false);
            }
            this.mSoundRcv.setLayoutManager(this.linearLayoutManager);
            this.mPlayBgIv.setImageResource(R.drawable.main_app_background);
            this.mSoundName.setText(this.mixItem.getName());
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mixItem.getSoundList());
            arrayList.add("Edit");
            this.slimAdapter.updateData(arrayList);
            this.slimAdapter.notifyDataSetChanged();
        }
        if (AudioPlayerManager.getInstance(this).getPlayerState() == 2) {
            this.mPlayControlView.setImageResource(R.drawable.single_play);
        } else if (AudioPlayerManager.getInstance(this).getPlayerState() == 1) {
            this.mPlayControlView.setImageResource(R.drawable.double_pause);
        }
        long longPreference = SharedPrefsUtils.getLongPreference(this, Helpers.KEY_PLAY_TIME, 1200000);
        if (longPreference > 0) {
            this.mPlaySetTimeTv.setVisibility(8);
            this.mPlayCountTimeTv.setVisibility(0);
            this.mPlayCountTimeTv.setText(HourTills.mstostringConverter(longPreference));
            return;
        }
        this.mPlaySetTimeTv.setVisibility(0);
        this.mPlayCountTimeTv.setVisibility(8);
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver1);
    }

    class ViewVideoDecorator extends RecyclerView.ItemDecoration {
        ViewVideoDecorator() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            recyclerView.getLayoutManager();
            recyclerView.getChildAdapterPosition(view);
            rect.set(Screentils.dpToPx(6.0f), Screentils.dpToPx(6.0f), Screentils.dpToPx(6.0f), Screentils.dpToPx(6.0f));
        }
    }

}
