package com.example.workoutnote.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.workoutnote.Repository;
import com.example.workoutnote.model.Workout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class RepositoryViewModel extends AndroidViewModel {

    Repository repository;
    MutableLiveData<List<Workout>> liveData;


    public RepositoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        liveData =  new MutableLiveData<>();
    }


    public LiveData<List<Workout>> getWorkouts(){
        Disposable disposable = repository.getWorkouts()
                .subscribe(liveData::setValue);
        disposable.dispose();
        return liveData;
    }

    public void insertWorkout(Workout workout){
        repository.insertWorkout(workout);
        List<Workout> workoutList = liveData.getValue();
        if (workoutList == null)
            workoutList = new ArrayList<>();
        workoutList.add(workout);

        liveData.setValue(workoutList);
    }
}
