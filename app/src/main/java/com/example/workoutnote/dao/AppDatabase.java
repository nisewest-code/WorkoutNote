package com.example.workoutnote.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.workoutnote.model.Workout;

@Database(entities = {Workout.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
}
