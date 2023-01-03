package com.example.workoutnote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutnote.R;
import com.example.workoutnote.model.Workout;

public class WorkoutViewHolder extends BasicViewHolder<Workout> {

    TextView tv;

    public WorkoutViewHolder(@NonNull ViewGroup parent, Callback callback){
        this(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_workout_holder, parent, false), callback);
    }

    public WorkoutViewHolder(@NonNull View itemView, Callback callback) {
        super(itemView, callback);
        tv = itemView.findViewById(R.id.titleView);
    }

    @Override
    public void setHolder(Workout item, int position) {
        super.setHolder(item, position);
        tv.setText("Workout"+(item.getIndex()+1));
        itemView.setOnClickListener(view -> {
            callback.onClick(position);
        });
    }
}
