package com.example.sahiwalhealthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapters extends RecyclerView.Adapter<MessageAdapters.viewHolder>{

    List<MessageModels> modelList;

    public MessageAdapters(List<MessageModels> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        MessageModels model = modelList.get(position);

        if (model.getSentBy().equals(MessageModels.SENT_BY_ME)){
            holder.left_chat.setVisibility(View.GONE);
            holder.right_chat.setVisibility(View.VISIBLE);
            holder.right_text.setText(model.getMessage());
        }
        else{
            holder.right_chat.setVisibility(View.GONE);
            holder.left_chat.setVisibility(View.VISIBLE);
            holder.left_text.setText(model.getMessage());

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        LinearLayout left_chat,right_chat;
        TextView left_text,right_text;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            left_chat =itemView.findViewById(R.id.left_chat);
            right_chat = itemView.findViewById(R.id.right_chat);
            left_text = itemView.findViewById(R.id.left_text);
            right_text = itemView.findViewById(R.id.right_text);

        }
    }
}



