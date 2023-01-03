package com.example.workoutnote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.workoutnote.R;
import com.example.workoutnote.adapter.ApproachViewHolder;
import com.example.workoutnote.adapter.Callback;
import com.example.workoutnote.adapter.ExerciseViewHolder;
import com.example.workoutnote.adapter.RecyclerViewAdapter;
import com.example.workoutnote.adapter.WorkoutViewHolder;
import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;
import com.example.workoutnote.model.Workout;
import com.example.workoutnote.viewmodel.RepositoryViewModel;

public class ExerciseActivity extends AppCompatActivity {
    private RepositoryViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter<ApproachWeight> adapter;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        exercise = (Exercise) getIntent().getParcelableExtra("exercise");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter<>(ApproachViewHolder.class, position -> {

        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItems(exercise.getListApproach());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}