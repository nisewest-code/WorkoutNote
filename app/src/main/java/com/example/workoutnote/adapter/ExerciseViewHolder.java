package com.example.workoutnote.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutnote.R;
import com.example.workoutnote.activity.ApproachActivity;
import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseViewHolder extends BasicViewHolder<Exercise> {
    private FloatingActionButton fab;
    private RecyclerView rv;

    public ExerciseViewHolder(@NonNull ViewGroup parent, Callback callback) {
        this(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_exercise_holder, parent, false), callback);
    }

    public ExerciseViewHolder(@NonNull View itemView, Callback callback) {
        super(itemView, callback);
        this.callback = callback;
        fab = itemView.findViewById(R.id.fab);
        rv = itemView.findViewById(R.id.rv);
    }

    @Override
    public void setHolder(Exercise item, int position) {
        titleView.setText(item.getName());
        RecyclerViewAdapter<ApproachWeight> adapter = new RecyclerViewAdapter<>(ApproachViewHolder.class, result ->{});
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        adapter.setItems(item.getListApproach());
        fab.setOnClickListener(v -> {
            callback.onClick(position);
        });
    }
}
