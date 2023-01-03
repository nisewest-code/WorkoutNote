package com.example.workoutnote.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.workoutnote.R;
import com.example.workoutnote.adapter.RecyclerViewAdapter;
import com.example.workoutnote.adapter.WorkoutViewHolder;
import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;
import com.example.workoutnote.model.Workout;
import com.example.workoutnote.viewmodel.RepositoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class WorkoutNoteActivity extends AppCompatActivity {

    private RepositoryViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter<Workout> adapter;
    private FloatingActionButton fab;
    private ActivityResultLauncher<Intent> register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data != null){
                            Workout workout = data.getParcelableExtra("workout");
                            int position = data.getIntExtra("position", 0);
                            boolean isEdit = data.getBooleanExtra("edit", false);
                            if (!isEdit)
                                viewModel.insertWorkout(workout);
                            else {
                                adapter.getItems().set(position, workout);
                                adapter.notifyItemChanged(position);
                            }
                        }
//                doSomeOperations();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_note);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter<>(WorkoutViewHolder.class, result ->{
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra("workout", adapter.getItems().get(result));
            intent.putExtra("edit", true);
//            startActivity(intent);
            register.launch(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener((v)->{
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra("workout", new Workout(adapter.getItemCount(), new Date(Calendar.getInstance().getTime().getTime())));
//            startActivity(intent);
            intent.putExtra("edit", false);
            register.launch(intent);
        });

        viewModel = new ViewModelProvider(this).get(RepositoryViewModel.class);
        viewModel.getWorkouts().observe(this, this::setItems);
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

    public void setItems(List<Workout> workoutList){
        adapter.setItems(workoutList);
    }
}