package com.depression.relief.depressionissues.music.modelmix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.customadps.FirstScreenAdapter;
import com.depression.relief.depressionissues.music.custombase.NeelDownFragment;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;

import java.util.ArrayList;
import java.util.List;

public class ScrollableListFragment extends NeelDownFragment {
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        }
    };
    private int category = 0;
    private TextView mNoItemText;
    private RecyclerView mRvListMix;
    private FirstScreenAdapter mixItemAdapter;
    private List<ItemMixer> mixItems = new ArrayList();
    private int positionAds = 3;

    public ScrollableListFragment() {
    }

    public List<ItemMixer> filter(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            arrayList.addAll(MixDataParcer.getListMixItem(getActivity()));
        } else {
            for (ItemMixer next : MixDataParcer.getListMixItem(getActivity())) {
                if (next.getCategory() == i) {
                    arrayList.add(next);
                }
            }
        }
        if (arrayList.isEmpty()) {
            this.positionAds = 0;
        } else if (arrayList.size() <= this.positionAds) {
            this.positionAds = arrayList.size();
        }
        return arrayList;
    }

    public ScrollableListFragment(int i) {
        this.category = i;
        switch (i) {
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
                this.mixItems = MixDataParcer.getListMixItem(getActivity());
                return;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fast_scrollable_list, viewGroup, false);
        this.mRvListMix = (RecyclerView) inflate.findViewById(R.id.rv_list_mix);
        this.mNoItemText = (TextView) inflate.findViewById(R.id.no_item);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setUpList();
    }

    public void setUpList() {
        this.mRvListMix.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        this.mRvListMix.addItemDecoration(new VideoDecoration());
        FirstScreenAdapter mixItemAdapter2 = new FirstScreenAdapter(getActivity(), this.positionAds, this.mixItems);
        this.mixItemAdapter = mixItemAdapter2;
        this.mRvListMix.setAdapter(mixItemAdapter2);
    }

    class VideoDecoration extends RecyclerView.ItemDecoration {
        VideoDecoration() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int dpToPx = Screentils.dpToPx(Screentils.isBiggerDevice(ScrollableListFragment.this.getActivity()) ? 24.0f : 12.0f);
            int dpToPx2 = Screentils.dpToPx(Screentils.isBiggerDevice(ScrollableListFragment.this.getActivity()) ? 90.0f : 60.0f);
            if (layoutManager instanceof GridLayoutManager) {
                if (childAdapterPosition % 2 == 0) {
                    rect.set(dpToPx, 0, dpToPx, dpToPx);
                } else {
                    rect.set(dpToPx, 0, dpToPx, dpToPx);
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                rect.set(dpToPx, 0, dpToPx, dpToPx);
            }
            int itemCount = state.getItemCount();
            if (itemCount > 0 && childAdapterPosition == itemCount - 1) {
                if (childAdapterPosition % 2 == 0) {
                    rect.set(dpToPx, 0, dpToPx, dpToPx2);
                } else {
                    rect.set(dpToPx, 0, dpToPx, dpToPx2);
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        List<ItemMixer> filter = filter(this.category);
        this.mixItems = filter;
        FirstScreenAdapter mixItemAdapter2 = this.mixItemAdapter;
        if (mixItemAdapter2 != null) {
            mixItemAdapter2.updateData(filter);
            this.mixItemAdapter.notifyDataSetChanged();
        }
        if (this.mixItems.size() == 0) {
            this.mNoItemText.setVisibility(View.VISIBLE);
        } else {
            this.mNoItemText.setVisibility(View.GONE);
        }
    }
}
