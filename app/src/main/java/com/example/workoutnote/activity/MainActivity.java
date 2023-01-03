package com.example.workoutnote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.workoutnote.R;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private int mInterval = 1000; // 1 second in this case
    private Handler mHandler  = null;
    private long timeInSeconds = 0L;
    private boolean startButtonClicked = false;

    //Views
    private TextView textViewStopWatch;
    private TextView resetButton;
    private TextView startOrStopTextView;
    private TextView textViewWorkoutNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        textViewStopWatch = findViewById(R.id.textViewStopWatch);
        resetButton = findViewById(R.id.resetButton);
        startOrStopTextView = findViewById(R.id.startOrStopTextView);
        textViewWorkoutNote = findViewById(R.id.textViewWorkoutNote);
        textViewWorkoutNote.setOnClickListener(v -> {
            startActivity(new Intent(this, WorkoutNoteActivity.class));
        });
        startOrStopTextView.setOnClickListener(this::startOrStopButtonClicked);

        resetButton.setOnClickListener(v -> {
            stopTimer();
            resetTimerView();
        });

        initStopWatch();
    }

    private void initStopWatch() {
        textViewStopWatch.setText("00:00:00");
    }

    private void resetTimerView() {
        timeInSeconds = 0;
        startButtonClicked = false;
        startOrStopTextView.setBackgroundColor(
                ContextCompat.getColor(
                        this,
                        R.color.teal_700
                )
        );
        startOrStopTextView.setText("Start");
        initStopWatch();
    }

    void startOrStopButtonClicked(View v) {
        if (!startButtonClicked) {
            startTimer();
            startTimerView();
        } else {
            stopTimer();
            stopTimerView();
        }
    }

    private void stopTimerView() {
        startOrStopTextView.setBackgroundColor(
                ContextCompat.getColor(
                        this,
                        R.color.teal_700
                )
        );
        startOrStopTextView.setText("Resume");
        startButtonClicked = false;
    }

    private void startTimerView() {
        startOrStopTextView.setBackgroundColor(
                ContextCompat.getColor(
                        this,
                        R.color.purple_200
                )
        );
        startOrStopTextView.setText("Stop");
        startButtonClicked = true;
    }

    private void startTimer() {
        mHandler = new Handler(Looper.getMainLooper());
        mStatusChecker.run();
    }

    private void stopTimer() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                timeInSeconds += 1;
                Log.e("timeInSeconds", String.valueOf(timeInSeconds));
                updateStopWatchView(timeInSeconds);
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(this, mInterval);
            }
        }
    };

    private void updateStopWatchView(Long timeInSeconds) {
        String formattedTime = getFormattedStopWatch((timeInSeconds * 1000));
        Log.e("formattedTime", formattedTime);
        textViewStopWatch.setText(formattedTime);
    }
    @Override public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    String getFormattedStopWatch(Long ms){
        Long milliseconds = ms;
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        return  ((hours < 10) ? "0" : "") + hours + ":"+
                (((minutes < 10) ? "0" : "")) + minutes + ":"+
                (((seconds < 10) ? "0" : "")) + seconds;
    }
}