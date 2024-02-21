package com.depression.relief.depressionissues.music.modelmix;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.custombase.NeelDownFragment;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.timerreminders.CustomizeSoundsActivity;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

public class MyMixableSoundFragment extends NeelDownFragment {
    int MAX_ITEM;
    private int category;
    private RecyclerView mRvListMix;
    private List<AudioItem> mixItems;
    private SlimAdapter slimAdapter;

    public MyMixableSoundFragment() {
        this.mixItems = new ArrayList();
        this.category = 0;
        this.MAX_ITEM = 9;
    }

    public MyMixableSoundFragment(int i) {
        this.mixItems = new ArrayList();
        this.MAX_ITEM = 9;
        this.category = i;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.mixItems = filter(i);
                return;
            default:
                this.mixItems = MixDataParcer.getListSoundItem(getActivity());
                return;
        }
    }

    public List<AudioItem> filter(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = this.MAX_ITEM * i; i2 < (i + 1) * this.MAX_ITEM; i2++) {
            if (i2 < MixDataParcer.getListSoundItem(getActivity()).size()) {
                arrayList.add(MixDataParcer.getListSoundItem(getActivity()).get(i2));
            }
        }
        return arrayList;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.my_mixable_sounds, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rcv_mix_sound);
        this.mRvListMix = recyclerView;
        recyclerView.addItemDecoration(new VideoDecoration());
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        runthread();
    }

    private void runthread() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MyMixableSoundFragment.this.setUpList();
            }
        }, 0);
    }

    public void onResume() {
        super.onResume();
    }

    public void setUpList() {
        this.mRvListMix.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        SlimAdapter attachTo = SlimAdapter.create().register(R.layout.sound_mixable_my, new SlimInjector<AudioItem>() {
            public void onInject(final AudioItem soundItem, IViewInjector iViewInjector) {
                iViewInjector.text((int) R.id.sound_name, (CharSequence) soundItem.getName());
                iViewInjector.image((int) R.id.sound_icon_iv, soundItem.getIconResId());
              /*  if (PurchaseHelper.isRemoveAdPurchased(SounListCustomFragment.this.getActivity()).booleanValue()) {
                    iViewInjector.invisible(R.id.iv_sound_download);
                } else */
                if (soundItem.getSoundId() >= 118) {
                    iViewInjector.invisible(R.id.iv_sound_download);
                } else {
                    iViewInjector.invisible(R.id.iv_sound_download);
                }
                if (!MyMixableSoundFragment.this.isPlaying(soundItem)) {
                    iViewInjector.image((int) R.id.sound_icon_bg_layout, MyMixableSoundFragment.this.getActivity().getDrawable(R.drawable.mixer2));
                } else {
                    iViewInjector.image((int) R.id.sound_icon_bg_layout, MyMixableSoundFragment.this.getActivity().getDrawable(R.drawable.mixer1));
                }
                iViewInjector.clicked(R.id.sound_icon_bg_layout, new View.OnClickListener() {
                    public void onClick(View view) {
                        if (MyMixableSoundFragment.this.getActivity() == null) {
                            return;
                        }
                        /*if (PurchaseHelper.isRemoveAdPurchased(SounListCustomFragment.this.getActivity()).booleanValue()) {
                            ((MixCustomActivity) SounListCustomFragment.this.getActivity()).addSoundItem(soundItem);
                            SounListCustomFragment.this.updateSlimAdapter();
                        } else*/
                        if (soundItem.getSoundId() >= 118) {
//                            ShareUtil.logFirebaseEvent("Premium_in_sound_list");
//                            Intent intent = new Intent(SounListCustomFragment.this.getActivity(), PurchaseActivity.class);
//                            intent.putExtra("Calling_Activity", "MixCustomActivity");
//                            SounListCustomFragment.this.startActivity(intent);
                            ((CustomizeSoundsActivity) MyMixableSoundFragment.this.getActivity()).addSoundItem(soundItem);
                            MyMixableSoundFragment.this.updateSlimAdapter();
                        } else {
                            ((CustomizeSoundsActivity) MyMixableSoundFragment.this.getActivity()).addSoundItem(soundItem);
                            MyMixableSoundFragment.this.updateSlimAdapter();
                        }
                    }
                });
            }
        }).enableDiff().attachTo(this.mRvListMix);
        this.slimAdapter = attachTo;
        attachTo.updateData(this.mixItems);
    }

    public boolean isPlaying(AudioItem soundItem) {
        boolean z = false;
        for (AudioItem soundId : ((CustomizeSoundsActivity) getActivity()).getListedsounds()) {
            if (soundId.getSoundId() == soundItem.getSoundId()) {
                z = true;
            }
        }
        return z;
    }

    public void updateSlimAdapter() {
        this.slimAdapter.updateData(this.mixItems);
        this.slimAdapter.notifyDataSetChanged();
    }

    class VideoDecoration extends RecyclerView.ItemDecoration {
        VideoDecoration() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            recyclerView.getLayoutManager();
            recyclerView.getChildAdapterPosition(view);
            rect.set(Screentils.dpToPx(15.0f), Screentils.dpToPx(7.0f), Screentils.dpToPx(15.0f), Screentils.dpToPx(7.0f));
        }
    }
}
