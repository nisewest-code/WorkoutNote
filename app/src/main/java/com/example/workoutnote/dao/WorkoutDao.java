package com.example.workoutnote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workoutnote.model.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workout")
    List<Workout> getAll();

    @Query("SELECT * FROM workout WHERE `index` IN (:workoutIds)")
    List<Workout> loadAllByIds(int[] workoutIds);

    @Query("SELECT * FROM workout WHERE `index` LIKE :index  LIMIT 1")
    Workout findById(int index);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Workout... users);

    @Delete
    void delete(Workout user);
}
