package com.example.to_dolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private ArrayList<String> dataList;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public TodoAdapter(ArrayList<String> dataList, OnItemLongClickListener listener) {
        this.dataList = dataList;
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CHANGE THIS: Inflate your custom item_todo layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // We need to split the string or pass an object.
        // For now, let's assume your list has the formatted string.
        String currentItem = dataList.get(position);
        String[] parts = currentItem.split("\n");

        if (parts.length >= 3) {
            holder.tvTitle.setText(parts[0]);
            holder.tvDesc.setText(parts[1]);
            holder.tvDate.setText(parts[2]);
        }

        // Inside onBindViewHolder in TodoAdapter.java
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(position);
            }
            return true; // True indicates the click was handled
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Match these IDs to your item_todo.xml
            tvTitle = itemView.findViewById(R.id.itemTitle);
            tvDesc = itemView.findViewById(R.id.itemDesc);
            tvDate = itemView.findViewById(R.id.itemDate);
        }
    }
}