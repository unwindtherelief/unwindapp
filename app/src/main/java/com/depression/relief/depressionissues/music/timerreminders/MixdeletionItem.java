package com.depression.relief.depressionissues.music.timerreminders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Guideline;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.abilityutility.UriUtills;
import com.depression.relief.depressionissues.music.custombase.NeelDownActivity;
import com.depression.relief.depressionissues.music.customhelper.DataGetterSaved;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.depression.relief.depressionissues.music.precausions.ServiceAudioPlayer;
import com.facebook.drawee.view.SimpleDraweeView;

public class MixdeletionItem extends NeelDownActivity implements View.OnClickListener {
    private Intent intent;
    private ConstraintLayout mClRoot;
    private Guideline mGlH14;
    private Guideline mGlH8;
    private Guideline mGlH86;
    private Guideline mGlH92;
    private Group mGroupGoPremium;
    private Group mGroupMix;
    private Group mGroupSingleSound;
    private AppCompatImageView mIvClose;
    private AppCompatImageView mIvIconPremium;
    private AppCompatImageView mIvIconWatch;
    private AppCompatImageView mIvSoundIcon;
    private SimpleDraweeView mSivMixCover;
    private TextView mTvHint;
    private TextView mTvMixSoundName;
    private TextView mTvTitle;
    private TextView mTvUnlockAllSounds;
    private TextView mTvWatch;
    private View mViewGoSubscribeBg;
    private View mViewMixNameBg;
    private View mViewSoundIconBg;
    private View mViewWatchBtBg;
    private View mViewWatchBtBgSolid;
    private ItemMixer mixItem;
    private AudioItem soundItem;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.sound_mix_deletion);
        Intent intent2 = getIntent();
        this.intent = intent2;
        if (intent2 != null) {
            int intExtra = intent2.getIntExtra(ServiceAudioPlayer.MIX_ID, -1);
            if (intExtra != -1) {
                this.mixItem = MixDataParcer.getMixItemById(intExtra);
                mixinitial();
            } else {
                int intExtra2 = this.intent.getIntExtra(ServiceAudioPlayer.SOUND_ID, -1);
                if (intExtra2 != -1) {
                    this.soundItem = MixDataParcer.getSoundItemById(intExtra2);
                    mixinitial();
                }
            }
        } else {
            finish();
        }
        Screentils.hideActionBar(this);
    }

    @SuppressLint("WrongConstant")
    private void mixinitial() {
        this.mIvClose = (AppCompatImageView) findViewById(R.id.iv_close);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mSivMixCover = (SimpleDraweeView) findViewById(R.id.siv_mix_cover);
        this.mViewMixNameBg = findViewById(R.id.view_mix_name_bg);
        this.mTvMixSoundName = (TextView) findViewById(R.id.tv_mix_sound_name);
        this.mViewSoundIconBg = findViewById(R.id.view_sound_icon_bg);
        this.mIvSoundIcon = (AppCompatImageView) findViewById(R.id.iv_sound_icon);
        this.mTvHint = (TextView) findViewById(R.id.tv_hint);
        this.mViewWatchBtBg = findViewById(R.id.view_watch_bt_bg);
        this.mViewWatchBtBgSolid = findViewById(R.id.view_watch_bt_bg_solid);
        this.mIvIconWatch = (AppCompatImageView) findViewById(R.id.iv_icon_watch);
        this.mTvWatch = (TextView) findViewById(R.id.tv_watch);
        this.mViewGoSubscribeBg = findViewById(R.id.view_go_subscribe_bg);
        this.mIvIconPremium = (AppCompatImageView) findViewById(R.id.iv_icon_premium);
        this.mTvUnlockAllSounds = (TextView) findViewById(R.id.tv_unlock_all_sounds);
        this.mGroupMix = (Group) findViewById(R.id.group_mix);
        this.mGroupSingleSound = (Group) findViewById(R.id.group_single_sound);
        this.mGroupGoPremium = (Group) findViewById(R.id.group_go_premium);
        this.mGlH8 = (Guideline) findViewById(R.id.gl_h_8);
        this.mGlH92 = (Guideline) findViewById(R.id.gl_h_92);
        this.mGlH14 = (Guideline) findViewById(R.id.gl_h_14);
        this.mGlH86 = (Guideline) findViewById(R.id.gl_h_86);
        this.mIvClose.setOnClickListener(this);
        this.mViewWatchBtBgSolid.setOnClickListener(this);
        this.mViewGoSubscribeBg.setOnClickListener(this);
        this.mClRoot = (ConstraintLayout) findViewById(R.id.cl_root);
        ItemMixer mixItem2 = this.mixItem;
        if (mixItem2 != null) {
            this.mSivMixCover.setImageURI(UriUtills.resourcetoURI(this, mixItem2.getMixSoundCover().getCoverResId()));
            this.mTvMixSoundName.setText(this.mixItem.getName());
            this.mGroupSingleSound.setVisibility(8);
            this.mGroupMix.setVisibility(0);
            return;
        }
        AudioItem soundItem2 = this.soundItem;
        if (soundItem2 != null) {
            this.mIvSoundIcon.setImageResource(soundItem2.getIconResId());
            this.mGroupSingleSound.setVisibility(0);
            this.mGroupMix.setVisibility(8);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_close) {
            finish();
        } else if (id == R.id.view_watch_bt_bg_solid) {
            mixDeleetion();
        }
    }

    @SuppressLint("WrongConstant")
    private void mixDeleetion() {
        DataGetterSaved.removeCustomMixInJSONArray(this, this.mixItem);
        MixDataParcer.createData(this);
        Toast.makeText(this, R.string.delete_success, 0).show();
        finish();
    }
}
