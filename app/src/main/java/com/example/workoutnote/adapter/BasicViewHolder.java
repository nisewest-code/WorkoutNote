package com.example.workoutnote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutnote.R;

public class BasicViewHolder<T> extends RecyclerView.ViewHolder {
    protected TextView titleView;
    protected Callback callback;
    public BasicViewHolder(@NonNull ViewGroup parent, Callback callback) {
        this(LayoutInflater.from(parent.getContext())
                .inflate(0, parent, false), callback);
    }

    public BasicViewHolder(View itemView, Callback callback){
        super(itemView);
        titleView = itemView.findViewById(R.id.titleView);
        this.callback = callback;
    }

    public void setHolder(T item, int index) {

    }
}

