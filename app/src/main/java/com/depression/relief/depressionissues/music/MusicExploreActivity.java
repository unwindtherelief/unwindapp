package com.depression.relief.depressionissues.music;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.HourTills;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.customadps.FragmentListadapter;
import com.depression.relief.depressionissues.music.custombase.NeelDownActivity;
import com.depression.relief.depressionissues.music.customhelper.DataGetterSaved;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.CategoryMixer;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.depression.relief.depressionissues.music.modelmix.ListModelMix;
import com.depression.relief.depressionissues.music.modelmix.ScrollableListFragment;
import com.depression.relief.depressionissues.music.precausions.AudioPlayerManager;
import com.depression.relief.depressionissues.music.precausions.ServiceAudioPlayer;
import com.depression.relief.depressionissues.music.timerreminders.RelexingSoundPlayActivity;

import net.idik.lib.slimadapter.SlimAdapter;

import java.util.List;

public class MusicExploreActivity extends NeelDownActivity {
    /* access modifiers changed from: private */
    public LinearLayoutManager linearLayoutManager;
    /* access modifiers changed from: private */
    public ImageView mCoverImage;
    /* access modifiers changed from: private */
    public RelativeLayout mMiniPlayer;
    /* access modifiers changed from: private */
    public ImageView mPlayControlIv;
    /* access modifiers changed from: private */
    public TextView mPlayTime;
    /* access modifiers changed from: private */
    public TextView mSoundName;
    /* access modifiers changed from: private */
    public SlimAdapter slimAdapter;
    RecyclerView mRcvMixSoundType;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(ServiceAudioPlayer.RESULT_CODE, 0) == 1) {
                int intExtra = intent.getIntExtra(ServiceAudioPlayer.UPDATE_PLAY_STATE, 0);
                Log.e("PLAYER_STATE", intExtra + "");
                if (intExtra == 0) {
                    MusicExploreActivity.this.mPlayControlIv.setImageResource(R.drawable.single_play);
                    MusicExploreActivity.this.mMiniPlayer.setVisibility(8);
                } else if (intExtra == 1) {
                    MusicExploreActivity.this.mPlayControlIv.setImageResource(R.drawable.double_pause);
                    if (MusicExploreActivity.this.mMiniPlayer.getVisibility() == 4 || MusicExploreActivity.this.mMiniPlayer.getVisibility() == 8) {
                        MusicExploreActivity.this.mMiniPlayer.setVisibility(0);
                    }
                } else if (intExtra == 2) {
                    MusicExploreActivity.this.mPlayControlIv.setImageResource(R.drawable.single_play);
                }
                ItemMixer mixItem = AudioPlayerManager.getInstance(MusicExploreActivity.this).getMixItem();
                if (mixItem != null) {
                    MusicExploreActivity.this.mCoverImage.setImageResource(MixDataParcer.getSmallCoverResourceID(mixItem));
                    MusicExploreActivity.this.mSoundName.setText(mixItem.getName());
                    Log.e("MIX_MAIN", "OK");
                    return;
                }
                Log.e("MIX_MAIN", "NULL");
            }
        }
    };
    private BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(ServiceAudioPlayer.RESULT_CODE, 0) == 1) {
                final long longExtra = intent.getLongExtra(ServiceAudioPlayer.UPDATE_TIME, -1);
                if (longExtra > 0) {
                    MusicExploreActivity.this.mPlayTime.setVisibility(0);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                MusicExploreActivity.this.mPlayTime.setText(HourTills.mstostringConverter(longExtra));
                            } catch (Exception unused) {
                            }
                        }
                    }, 1000);
                    return;
                }
                MusicExploreActivity.this.mPlayTime.setVisibility(0);
            }
        }
    };
    private ImageView mCloseIv;
    private Context mContext;
    private LinearLayout mLlControl;
    private ViewPager mVpMixSound;
    private ListModelMix mixViewModel;
    private FragmentListadapter viewPagerAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_musicexplore);


        this.mVpMixSound = (ViewPager) findViewById(R.id.vp_mix_sound);
        this.mCoverImage = (ImageView) findViewById(R.id.cover_image);
        this.mSoundName = (TextView) findViewById(R.id.sound_name);
        this.mPlayTime = (TextView) findViewById(R.id.play_time);
        this.mPlayControlIv = (ImageView) findViewById(R.id.play_control_iv);
        this.mCloseIv = (ImageView) findViewById(R.id.close_iv);
        this.mLlControl = (LinearLayout) findViewById(R.id.ll_control);
        this.mMiniPlayer = (RelativeLayout) findViewById(R.id.mini_player);

//        PreloadSoundAds.showGoogleBannerAd(mContext,findViewById(R.id.bannerAds));


        this.mCloseIv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                MusicExploreActivity.this.mMiniPlayer.setVisibility(8);
                Intent intent = new Intent(MusicExploreActivity.this, ServiceAudioPlayer.class);
                intent.setAction(ServiceAudioPlayer.ACTION_CMD);
                intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PAUSE_ALL);
                if (Build.VERSION.SDK_INT >= 26) {
                    MusicExploreActivity.this.startForegroundService(intent);
                } else {
                    MusicExploreActivity.this.startService(intent);
                }
                MusicExploreActivity.this.mPlayControlIv.setImageResource(R.drawable.single_play);
                Intent intent2 = new Intent(MusicExploreActivity.this, ServiceAudioPlayer.class);
                intent2.setAction(ServiceAudioPlayer.ACTION_CMD);
                intent2.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_NOTIFICATION_CLOSE);
                if (Build.VERSION.SDK_INT >= 26) {
                    MusicExploreActivity.this.startForegroundService(intent2);
                } else {
                    MusicExploreActivity.this.startService(intent2);
                }
            }
        });
        this.mPlayControlIv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AudioPlayerManager.getInstance(MusicExploreActivity.this).getPlayerState() == 2) {
                    Intent intent = new Intent(MusicExploreActivity.this, ServiceAudioPlayer.class);
                    intent.setAction(ServiceAudioPlayer.ACTION_CMD);
                    intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_RESUME_ALL);
                    if (Build.VERSION.SDK_INT >= 26) {
                        MusicExploreActivity.this.startForegroundService(intent);
                    } else {
                        MusicExploreActivity.this.startService(intent);
                    }
                    MusicExploreActivity.this.mPlayControlIv.setImageResource(R.drawable.double_pause);
                } else if (AudioPlayerManager.getInstance(MusicExploreActivity.this).getPlayerState() == 1) {
                    Intent intent2 = new Intent(MusicExploreActivity.this, ServiceAudioPlayer.class);
                    intent2.setAction(ServiceAudioPlayer.ACTION_CMD);
                    intent2.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PAUSE_ALL);
                    if (Build.VERSION.SDK_INT >= 26) {
                        MusicExploreActivity.this.startForegroundService(intent2);
                    } else {
                        MusicExploreActivity.this.startService(intent2);
                    }
                    MusicExploreActivity.this.mPlayControlIv.setImageResource(R.drawable.single_play);
                }
            }
        });
        this.mMiniPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MusicExploreActivity.this.startActivity(new Intent(MusicExploreActivity.this, RelexingSoundPlayActivity.class));
            }
        });
        setUpViewPager();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter(ServiceAudioPlayer.ACTION_UPDATE_PLAY_STATE));
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver1, new IntentFilter(ServiceAudioPlayer.ACTION_UPDATE_TIME));

    }

    private void setUpViewPager() {
        FragmentListadapter viewPagerAdapter2 = new FragmentListadapter(this.getSupportFragmentManager());
        this.viewPagerAdapter = viewPagerAdapter2;
        viewPagerAdapter2.addFragment(new ScrollableListFragment(0));
        List<ItemMixer> customMixList = DataGetterSaved.getCustomMixList(this);
        if (customMixList == null || customMixList.size() <= 0) {
            for (int i = 2; i < MixDataParcer.numerTab; i++) {
                this.viewPagerAdapter.addFragment(new ScrollableListFragment(i));
            }
        } else {
            for (int i2 = 1; i2 < MixDataParcer.numerTab; i2++) {
                this.viewPagerAdapter.addFragment(new ScrollableListFragment(i2));
            }
        }
        this.mVpMixSound.setAdapter(this.viewPagerAdapter);
        this.mVpMixSound.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < MusicExploreActivity.this.slimAdapter.getData().size(); i2++) {
                    List<ItemMixer> customMixList = DataGetterSaved.getCustomMixList(MusicExploreActivity.this);
                    if (customMixList == null || customMixList.size() <= 0) {
                        if (i2 == i) {
                            ((CategoryMixer) MusicExploreActivity.this.slimAdapter.getData().get(i2)).setChecked(true);
                        } else {
                            ((CategoryMixer) MusicExploreActivity.this.slimAdapter.getData().get(i2)).setChecked(false);
                        }
                    } else if (((CategoryMixer) MusicExploreActivity.this.slimAdapter.getData().get(i2)).getId() == i) {
                        ((CategoryMixer) MusicExploreActivity.this.slimAdapter.getData().get(i2)).setChecked(true);
                    } else {
                        ((CategoryMixer) MusicExploreActivity.this.slimAdapter.getData().get(i2)).setChecked(false);
                    }
                }
                MusicExploreActivity.this.slimAdapter.notifyDataSetChanged();
                MusicExploreActivity.this.linearLayoutManager.scrollToPositionWithOffset(i, Screentils.getScreenWidth(MusicExploreActivity.this) / 2);
            }
        });
        this.mVpMixSound.setOffscreenPageLimit(7);
    }

    @SuppressLint("WrongConstant")
    public void onResume() {
        super.onResume();
        int playerState = AudioPlayerManager.getInstance(this).getPlayerState();
        Log.e("PLAYER_STATE", playerState + "");
        if (playerState == 1) {
            this.mPlayControlIv.setImageResource(R.drawable.double_pause);
        } else if (playerState == 2 || playerState == 3) {
            this.mPlayControlIv.setImageResource(R.drawable.single_play);
        }
        ItemMixer mixItem = AudioPlayerManager.getInstance(this).getMixItem();
        if (mixItem != null) {
            if (this.mMiniPlayer.getVisibility() == 4 || this.mMiniPlayer.getVisibility() == 8) {
                this.mMiniPlayer.setVisibility(0);
            }
            this.mCoverImage.setImageResource(MixDataParcer.getSmallCoverResourceID(mixItem));
            this.mSoundName.setText(mixItem.getName());
            Log.e("MIX_MAIN", "OK");
        } else {
            Log.e("MIX_MAIN", "NULL");
        }
        if (AudioPlayerManager.getInstance(this).getSizePlayer() == 0 || AudioPlayerManager.getInstance(this).getMixItem() == null) {
            this.mMiniPlayer.setVisibility(8);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver1);
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideDown(this);
    }
}
