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
    public AppCompatImageView mPlayControlView;
    public ItemMixer mixItem;
    AudioManager audioManager;
    private List<View> animList = new ArrayList();
    private List<AnimatorSet> animatorSets = new ArrayList();
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
    private TextView mSoundName;
    private RecyclerView mSoundRcv;
    private RelativeLayout mToolbar;
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

        ItemMixer mixItem2 = AudioPlayerManager.getInstance(this).getMixItem();
        this.mixItem = mixItem2;
        if (mixItem2 == null) {
            finish();
        }
        pagerViewneet();
        Screentils.hideActionBar(this);
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
        this.mCloseView = (AppCompatImageView) findViewById(R.id.close_view);
        if (Screentils.isBiggerDevice(this)) {
            this.mCloseView.setPadding((int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp), (int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp), (int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp), (int) getResources().getDimension(com.intuit.sdp.R.dimen._4sdp));
        }
        this.mCloseView.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.sound_name);
        this.mSoundName = textView;
        textView.setOnClickListener(this);
        this.mToolbar = (RelativeLayout) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sound_rcv);
        this.mSoundRcv = recyclerView;
        recyclerView.setOnClickListener(this);
        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.play_control_view);
        this.mPlayControlView = appCompatImageView;
        appCompatImageView.setOnClickListener(this);

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
                this.linearLayoutManager = new LinearLayoutManager(this);
            } else if (this.mixItem.getSoundList().size() < 1) {
                this.linearLayoutManager = new LinearLayoutManager((Context) this);
            } else {
                this.linearLayoutManager = new LinearLayoutManager((Context) this);
            }
            this.mSoundRcv.setLayoutManager(this.linearLayoutManager);
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
    }

    public void onDestroy() {
        super.onDestroy();
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
