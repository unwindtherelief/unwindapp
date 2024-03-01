package com.depression.relief.depressionissues.admin.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;

import java.util.List;

public class EventUserShowAdapter extends RecyclerView.Adapter<EventUserShowAdapter.EventViewHolder> {
    private List<EventData> eventList;
    private Context context;
    private EventUserShowAdapter.OnItemClickListener listener;

    public EventUserShowAdapter(List<EventData> eventList, Context context, EventUserShowAdapter.OnItemClickListener listener) {
        this.eventList = eventList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventUserShowAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_show_user, parent, false);
        return new EventUserShowAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventUserShowAdapter.EventViewHolder holder, int position) {
        EventData eventData = eventList.get(position);

        if (eventData != null) {
            Glide.with(context).load(eventData.getImageUrl()).into(holder.imageView);

            holder.titleTextView.setText(eventData.getTitle());
            holder.txt_eventlocation.setText(eventData.getLocation());
            holder.timeTextView.setText(eventData.getTime());
            holder.priceTextView.setText(String.format("%s %.2f", context.getString(R.string.price_prefix), eventData.getEventPrice()));

            holder.itemView.setOnClickListener(view -> listener.onItemClick(eventData));

        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setData(List<EventData> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }



    public class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView, timeTextView, priceTextView,txt_eventlocation;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            timeTextView = itemView.findViewById(R.id.textViewTime);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            txt_eventlocation = itemView.findViewById(R.id.txt_eventlocation);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(EventData eventData);

    }
}
