package com.example.workoutnote;

import android.content.Context;

import com.example.workoutnote.model.Workout;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private Api api;
    private Context context;

    public Repository(Context context){
        this.context = context;
        api = new Api(this.context);
    }

    public Observable<List<Workout>> getWorkouts(){
        return api.getWorkouts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> insertWorkout(Workout workout){
        return api.insertWorkout(workout)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
