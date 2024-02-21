package com.depression.relief.depressionissues.music.customadps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.ShareListUtils;
import com.depression.relief.depressionissues.music.abilityutility.UriUtills;
import com.depression.relief.depressionissues.music.customhelper.MixDataParcer;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.depression.relief.depressionissues.music.precausions.ServiceAudioPlayer;
import com.depression.relief.depressionissues.music.timerreminders.MixdeletionItem;
import com.depression.relief.depressionissues.music.timerreminders.RelexingSoundPlayActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class FirstScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /* access modifiers changed from: private */
    public Context mContext;
    private List<ItemMixer> mixItems;

    public FirstScreenAdapter(Context context, int i, List<ItemMixer> list) {
        this.mContext = context;
        this.mixItems = list;
    }

    public int getItemViewType(int i) {
        return 1;
    }

    public void updateData(List<ItemMixer> list) {
        this.mixItems.clear();
        this.mixItems.addAll(list);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.mContext = context;
        return new MixItemViewHolder(LayoutInflater.from(context).inflate(R.layout.sound_mixer_item, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((MixItemViewHolder) viewHolder).binData(this.mixItems.get(i));
    }

    public int getItemCount() {
        return this.mixItems.size();
    }

    public class MixItemViewHolder extends RecyclerView.ViewHolder {
        private View coverContainer;
        private ImageView imAdsIcon;
        private SimpleDraweeView imCover;
        private TextView tvTag;
        private TextView tvTitle;

        public MixItemViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.title_text);
            this.tvTag = (TextView) view.findViewById(R.id.mix_sound_tag_tv);
            this.imCover = (SimpleDraweeView) view.findViewById(R.id.cover_image);
            this.coverContainer = view.findViewById(R.id.cover_container);
        }

        public void binData(final ItemMixer mixItem) {
            this.tvTitle.setText(mixItem.getName());
            this.imCover.setImageURI(UriUtills.resourcetoURI(FirstScreenAdapter.this.mContext, MixDataParcer.getSmallCoverResourceID(mixItem)));

            if (mixItem.getCategory() == 1) {
                this.tvTag.setVisibility(0);
                this.coverContainer.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        Intent intent = new Intent(FirstScreenAdapter.this.mContext, MixdeletionItem.class);
                        intent.putExtra(ServiceAudioPlayer.MIX_ID, mixItem.getMixSoundId());
                        FirstScreenAdapter.this.mContext.startActivity(intent);
                        return true;
                    }
                });
            } else {
                this.tvTag.setVisibility(8);
            }
            if (getItemViewType() == 1) {
                this.coverContainer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ShareListUtils.logFirebaseEvent("Theme_clicked_" + mixItem.getName());
                        Intent intent = new Intent(FirstScreenAdapter.this.mContext, ServiceAudioPlayer.class);
                        intent.setAction(ServiceAudioPlayer.ACTION_CMD);
                        intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_PLAY_MIX);
                        intent.putExtra(ServiceAudioPlayer.MIX_ID, mixItem.getMixSoundId());
                        FirstScreenAdapter.this.mContext.startService(intent);
                        Intent intent2 = new Intent(FirstScreenAdapter.this.mContext, RelexingSoundPlayActivity.class);
                        intent2.putExtra(ServiceAudioPlayer.MIX_ID, mixItem.getMixSoundId());
                        FirstScreenAdapter.this.mContext.startActivity(intent2);

                    }
                });
            }
        }
    }
}
