package com.example.workoutnote.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutnote.R;
import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;

public class ApproachViewHolder extends BasicViewHolder<ApproachWeight> {
    
    public ApproachViewHolder(@NonNull ViewGroup parent, Callback callback) {
        this(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_approach_holder, parent, false), callback);
    }

    public ApproachViewHolder(@NonNull View itemView, Callback callback) {
        super(itemView, callback);
        this.callback = callback;
    }

    @Override
    public void setHolder(ApproachWeight item, int position) {
        titleView.setText(item.getWeight() + " kg. x " + item.getCountRepeat());
        itemView.setOnClickListener(v -> {
            callback.onClick(position);
        });
    }
}
