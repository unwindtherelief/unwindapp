package com.depression.relief.depressionissues.music.timerreminders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.abilityutility.ShareListUtils;
import com.depression.relief.depressionissues.music.customView.WrapContentViewPager;
import com.depression.relief.depressionissues.music.customadps.WrappedFragmentListAdapter;
import com.depression.relief.depressionissues.music.custombase.NeelDownActivity;
import com.depression.relief.depressionissues.music.customhelper.DataGetterSaved;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.depression.relief.depressionissues.music.modelmix.MyMixableSoundFragment;
import com.depression.relief.depressionissues.music.precausions.AudioPlayerManager;
import com.depression.relief.depressionissues.music.precausions.ServiceAudioPlayer;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import io.github.kshitij_jain.indicatorview.IndicatorView;


public class CustomizeSoundsActivity extends NeelDownActivity implements View.OnClickListener {
    /* access modifiers changed from: private */
    public IndicatorView indictorCircle;
    /* access modifiers changed from: private */
    public LinearLayout canceller;
    /* access modifiers changed from: private */
    public RecyclerView customRcv;
    /* access modifiers changed from: private */
    public LinearLayout reseter;
    /* access modifiers changed from: private */
    public AppCompatImageView viewsaver;
    /* access modifiers changed from: private */
    public SlimAdapter salimadapter;
    /* access modifiers changed from: private */
    public List<AudioItem> listedsounds = new ArrayList();
    /* access modifiers changed from: private */
    public WrappedFragmentListAdapter viewPagerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private WrapContentViewPager mCustomSoundsSvp;
    private LinearLayout mRootView;
    private ItemMixer mixItem;
    private ItemMixer mixItem1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Screentils.setFullScreenActivity(this);
        setContentView((int) R.layout.its_customized_sound);
        catViewNeet();
        Screentils.hideActionBar(this);
        this.mixItem = AudioPlayerManager.getInstance(this).getMixItem();
        this.listedsounds = AudioPlayerManager.getInstance(this).getPlayingSoundItem();
        pagerSetter();
        listerSetter();
        this.mixItem1 = this.mixItem;
    }

    private void catViewNeet() {
        this.customRcv = (RecyclerView) findViewById(R.id.custom_sounds_rcv);
        this.mCustomSoundsSvp = (WrapContentViewPager) findViewById(R.id.custom_sounds_svp);
        this.indictorCircle = (IndicatorView) findViewById(R.id.circle_indicator_view);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.custom_sounds_cancel_layout);
        this.canceller = linearLayout;
        linearLayout.setOnClickListener(this);
        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.custom_sounds_save_view);
        this.viewsaver = appCompatImageView;
        appCompatImageView.setOnClickListener(this);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.custom_sounds_reset_layout);
        this.reseter = linearLayout2;
        linearLayout2.setOnClickListener(this);
        this.mRootView = (LinearLayout) findViewById(R.id.root_view);
    }

    public void pagerSetter() {
        this.mCustomSoundsSvp.setOffscreenPageLimit(2);
        WrappedFragmentListAdapter viewPagerWrapAdapter = new WrappedFragmentListAdapter(getSupportFragmentManager(), -1);
        this.viewPagerAdapter = viewPagerWrapAdapter;
        viewPagerWrapAdapter.addFragment(new MyMixableSoundFragment(0));
        this.viewPagerAdapter.addFragment(new MyMixableSoundFragment(1));
        this.viewPagerAdapter.addFragment(new MyMixableSoundFragment(2));
        this.mCustomSoundsSvp.setAdapter(this.viewPagerAdapter);
        this.mCustomSoundsSvp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                CustomizeSoundsActivity.this.indictorCircle.setCurrentPage(i);
            }
        });
        this.indictorCircle.setPageIndicators(this.viewPagerAdapter.getCount());
        this.indictorCircle.setActiveIndicatorColor(R.color.textcolorhint);
        this.indictorCircle.setActiveIndicatorColor(R.color.textcolor);
        this.indictorCircle.setInactiveIndicatorColor(R.color.textcolorhint);
    }

    public void listerSetter() {
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, 0, false);
        this.linearLayoutManager = linearLayoutManager2;
        this.customRcv.setLayoutManager(linearLayoutManager2);
        SlimAdapter attachTo = SlimAdapter.create().register(R.layout.sound_mix_customized, new SlimInjector<AudioItem>() {
            public void onInject(final AudioItem soundItem, IViewInjector iViewInjector) {
                iViewInjector.text((int) R.id.sound_name, (CharSequence) soundItem.getName());
                iViewInjector.image((int) R.id.sound_icon_iv, MixDataParcer.getResourceIDUsingsoundId(soundItem));
                iViewInjector.clicked(R.id.sound_delete_iv, new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(CustomizeSoundsActivity.this, ServiceAudioPlayer.class);
                        intent.setAction(ServiceAudioPlayer.ACTION_CMD);
                        intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_RELEASE);
                        intent.putExtra(ServiceAudioPlayer.SOUND_ID, soundItem.getSoundId());
                        CustomizeSoundsActivity.this.startService(intent);
                        CustomizeSoundsActivity.this.listedsounds.remove(soundItem);
                        CustomizeSoundsActivity.this.salimadapter.updateData(CustomizeSoundsActivity.this.listedsounds);
                        CustomizeSoundsActivity.this.salimadapter.notifyDataSetChanged();
                        for (int i = 0; i < CustomizeSoundsActivity.this.viewPagerAdapter.getCount(); i++) {
                            ((MyMixableSoundFragment) CustomizeSoundsActivity.this.viewPagerAdapter.getItem(i)).updateSlimAdapter();
                        }
                    }
                });
                iViewInjector.with(R.id.sound_seekbar, new IViewInjector.Action<SeekBar>() {
                    public void action(SeekBar seekBar) {
                        seekBar.setMax(100);
                        seekBar.setProgress(soundItem.getVolume());
                    }
                });
                iViewInjector.with(R.id.sound_seekbar, new IViewInjector.Action<SeekBar>() {
                    public void action(final SeekBar seekBar) {
                        seekBar.setOnTouchListener(new View.OnTouchListener() {
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                int action = motionEvent.getAction();
                                if (action == 0) {
                                    CustomizeSoundsActivity.this.customRcv.setClickable(false);
                                } else if (action != 1) {
                                    return false;
                                }
                                CustomizeSoundsActivity.this.customRcv.setClickable(true);
                                return true;
                            }
                        });
                        seekBar.setMax(100);
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }

                            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                                seekBar.setProgress(seekBar.getProgress());
                                AudioPlayerManager.getInstance(CustomizeSoundsActivity.this).setVolume(soundItem, (float) seekBar.getProgress());
                            }
                        });
                    }
                });
            }
        }).enableDiff().attachTo(this.customRcv);
        this.salimadapter = attachTo;
        attachTo.updateData(this.listedsounds);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.custom_sounds_cancel_layout /*2131361979*/:
                cancelChanges();
                return;
            case R.id.custom_sounds_reset_layout /*2131361981*/:
                reset();
                ShareListUtils.logFirebaseEvent("User_clicked_reset");
                this.reseter.setClickable(false);
                this.canceller.setClickable(false);
                this.viewsaver.setClickable(false);
                Toast.makeText(this, "Reset mix to original", 0).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        CustomizeSoundsActivity.this.reseter.setClickable(true);
                        CustomizeSoundsActivity.this.canceller.setClickable(true);
                        CustomizeSoundsActivity.this.viewsaver.setClickable(true);
                    }
                }, 1000);
                return;
            case R.id.custom_sounds_save_view /*2131361982*/:
                ItemMixer mixItem2 = AudioPlayerManager.getInstance(this).getMixItem();
                this.mixItem = mixItem2;
                if (mixItem2 == null) {
                    reset();
                    ShareListUtils.logFirebaseEvent("User_clicked_save_with_no_items");
                    Toast.makeText(this, "Reset mix to original", 0).show();
                    this.reseter.setClickable(false);
                    this.canceller.setClickable(false);
                    this.viewsaver.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            CustomizeSoundsActivity.this.reseter.setClickable(true);
                            CustomizeSoundsActivity.this.canceller.setClickable(true);
                            CustomizeSoundsActivity.this.viewsaver.setClickable(true);
                        }
                    }, 1000);
                    return;
                }
                mixItem2.setSoundList(AudioPlayerManager.getInstance(this).getPlayingSoundItem());
                DataGetterSaved.addCustomMixInJSONArray(this, this.mixItem);
                MixDataParcer.createData(this);
                finish();
                return;
            default:
                return;
        }
    }

    public void cancelChanges() {
        AudioPlayerManager.getInstance(this).removeAllPlayer();
        AudioPlayerManager.getInstance(this).setMixItem((ItemMixer) null);
        Intent intent = new Intent(this, ServiceAudioPlayer.class);
        intent.setAction(ServiceAudioPlayer.ACTION_CMD);
        intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PLAY_MIX);
        intent.putExtra(ServiceAudioPlayer.MIX_ID, this.mixItem1.getMixSoundId());
        startService(intent);
        finish();
    }

    public void reset() {
        this.mixItem = AudioPlayerManager.getInstance(this).getMixItem();
        DataGetterSaved.removeCustomMixInJSONArray(this, this.mixItem1);
        MixDataParcer.createData(this);
        ItemMixer mixItemById = MixDataParcer.getMixItemById(this.mixItem1.getMixSoundId());
        this.mixItem = mixItemById;
        List<AudioItem> soundList2 = mixItemById.getSoundList();
        this.listedsounds = soundList2;
        this.salimadapter.updateData(soundList2);
        this.salimadapter.notifyDataSetChanged();
        AudioPlayerManager.getInstance(this).removeAllPlayer();
        AudioPlayerManager.getInstance(this).setMixItem((ItemMixer) null);
        Intent intent = new Intent(this, ServiceAudioPlayer.class);
        intent.setAction(ServiceAudioPlayer.ACTION_CMD);
        intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PLAY_MIX);
        intent.putExtra(ServiceAudioPlayer.MIX_ID, this.mixItem.getMixSoundId());
        for (int i = 0; i < this.viewPagerAdapter.getCount(); i++) {
            ((MyMixableSoundFragment) this.viewPagerAdapter.getItem(i)).updateSlimAdapter();
        }
        startService(intent);
    }

    @SuppressLint("WrongConstant")
    public void addSoundItem(AudioItem soundItem) {
        int i = 0;
        if (isPlaying(soundItem)) {
            Intent playdio = new Intent(this, ServiceAudioPlayer.class);
            playdio.setAction(ServiceAudioPlayer.ACTION_CMD);
            playdio.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PLAY);
            playdio.putExtra(ServiceAudioPlayer.SOUND_ID, soundItem.getSoundId());
            startService(playdio);
            while (true) {
                if (i >= this.listedsounds.size()) {
                    break;
                } else if (this.listedsounds.get(i).getSoundId() == soundItem.getSoundId()) {
                    this.listedsounds.remove(i);
                    break;
                } else {
                    i++;
                }
            }
            ShareListUtils.logFirebaseEvent("Sound_Added_" + soundItem.getName());
            this.salimadapter.updateData(this.listedsounds);
            this.salimadapter.notifyDataSetChanged();
            this.customRcv.scrollToPosition(this.listedsounds.size() - 1);
        } else if (AudioPlayerManager.getInstance(this).isMaxPlayerStart()) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.listedsounds.size()) {
                    break;
                } else if (this.listedsounds.get(i2).getSoundId() == soundItem.getSoundId()) {
                    this.listedsounds.remove(i2);
                    break;
                } else {
                    i2++;
                }
            }
            Toast.makeText(this, String.format(getString(R.string.max_select_toast), new Object[]{"8"}), 0).show();
        } else {
            Intent servaudio = new Intent(this, ServiceAudioPlayer.class);
            servaudio.setAction(ServiceAudioPlayer.ACTION_CMD);
            servaudio.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PLAY);
            servaudio.putExtra(ServiceAudioPlayer.SOUND_ID, soundItem.getSoundId());
            startService(servaudio);
            this.listedsounds.add(soundItem);
            ShareListUtils.logFirebaseEvent("Sound_Added_" + soundItem.getName());
            this.salimadapter.updateData(this.listedsounds);
            this.salimadapter.notifyDataSetChanged();
            this.customRcv.scrollToPosition(this.listedsounds.size() - 1);
        }
    }

    public boolean isPlaying(AudioItem soundItem) {
        boolean z = false;
        for (AudioItem soundId : this.listedsounds) {
            if (soundId.getSoundId() == soundItem.getSoundId()) {
                z = true;
            }
        }
        return z;
    }

    public List<AudioItem> getListedsounds() {
        return this.listedsounds;
    }

    public void onBackPressed() {
        ItemMixer mixItem2 = AudioPlayerManager.getInstance(this).getMixItem();
        this.mixItem = mixItem2;
        if (mixItem2 == null) {
            cancelChanges();
        } else {
            finish();
        }
    }
}
