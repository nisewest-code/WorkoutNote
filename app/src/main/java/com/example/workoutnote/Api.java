package com.example.workoutnote;

import android.content.Context;

import androidx.room.Room;

import com.example.workoutnote.dao.AppDatabase;
import com.example.workoutnote.model.Workout;
import com.example.workoutnote.utils.AssetsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;

public class Api {

    private AppDatabase database;

    Api(Context context){
        database = Room.databaseBuilder(context,
                AppDatabase.class, "workouts").build();
    }

    Observable<List<Workout>> getWorkouts(){
        Observable<List<Workout>> observable;
        FutureTask<List<Workout>> futureTask = new FutureTask<>(() -> database.workoutDao().getAll());
        observable = Observable.fromFuture(futureTask)
                .doOnSubscribe(disposable -> futureTask.run());
        return observable;
    }

    Observable<Void> insertWorkout(Workout workout){
        Observable<Void> observable;
        FutureTask<Void> futureTask = new FutureTask<>(() -> {
            database.workoutDao().insertAll(workout);
            return null;
        });
        observable = Observable.fromFuture(futureTask)
                .doOnSubscribe(disposable -> futureTask.run());
        return observable;
    }
}
