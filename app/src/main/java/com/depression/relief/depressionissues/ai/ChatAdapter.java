package com.depression.relief.depressionissues.ai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;

import java.util.List;

public class ChatAdapter extends  RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    List<Message> messegeList;

    public ChatAdapter(List<Message> messegeList) {
        this.messegeList=messegeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message messege = messegeList.get(position);
        if(messege.getSentby().equalsIgnoreCase(Message.SENT_BY_ME))
        {
            holder.botview.setVisibility(View.GONE);
            holder.userview.setVisibility(View.VISIBLE);
            holder.usertext.setText(messege.getMsg());
        }
        else
        {
            holder.userview.setVisibility(View.GONE);
            holder.botview.setVisibility(View.VISIBLE);
            holder.bottext.setText(messege.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return messegeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout botview,userview;
        TextView bottext,usertext;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            botview=itemView.findViewById(R.id.botview);
            userview=itemView.findViewById(R.id.userview);
            bottext=itemView.findViewById(R.id.bottext);
            usertext=itemView.findViewById(R.id.usertext);
        }
    }
}