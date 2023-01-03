package com.example.workoutnote.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.workoutnote.R;
import com.example.workoutnote.adapter.Callback;
import com.example.workoutnote.adapter.ExerciseViewHolder;
import com.example.workoutnote.adapter.RecyclerViewAdapter;
import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;
import com.example.workoutnote.model.Workout;

import java.util.List;
import java.util.Objects;

public class WorkoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter<Exercise> adapter;
    private Workout workout;
    private Callback callback;
    private boolean isEdit;
    private ActivityResultLauncher<Intent> register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();
                if (data != null){
                    ApproachWeight approachWeight = data.getParcelableExtra("approachWeight");
                    int position = data.getIntExtra("position", 0);
//                    Exercise exercise  = workout.getListExercise().get(position);
                    workout.getListExercise().get(position).getListApproach().add(approachWeight);
                    adapter.notifyItemChanged(position);
                }
//                doSomeOperations();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        workout = (Workout) getIntent().getParcelableExtra("workout");
        isEdit = getIntent().getBooleanExtra("edit", false);
        recyclerView = findViewById(R.id.recyclerView);
        callback = position -> {
            Intent intent = new Intent(this, ApproachActivity.class);
            intent.putExtra("approachWeight", new ApproachWeight(0, 0));
            intent.putExtra("position", position);
            register.launch(intent);
        };
        adapter = new RecyclerViewAdapter<>(ExerciseViewHolder.class, callback);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItems(workout.getListExercise());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_main_save:
                Intent data = new Intent();
                data.putExtra("workout", workout);
                data.putExtra("position", getIntent().getIntExtra("position", 0));
                data.putExtra("edit", isEdit);
                setResult(Activity.RESULT_OK, data);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}