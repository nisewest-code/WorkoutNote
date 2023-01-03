package com.example.workoutnote.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Workout implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int index;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "list_exercise")
    private List<Exercise> listExercise;

    public Workout(int index, Date date, List<Exercise> listExercise) {
        this(index, date);
        this.listExercise = listExercise;
    }


    @Ignore
    public Workout(int index, Date date) {
        this.index = index;
        this.date = date;
        this.listExercise = Arrays.asList(
                new Exercise("Leg Press"),
                new Exercise("Bench Press"),
                new Exercise("Barbell Squats"),
                new Exercise("Deadlift"),
                new Exercise("Lunges"));
    }

    public Workout(Parcel in) {
        index = in.readInt();
        date = new Date(in.readLong());
        listExercise = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeLong(date.getTime());
        dest.writeTypedList(listExercise);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Exercise> getListExercise() {
        return listExercise;
    }

    public void setListExercise(List<Exercise> listExercise) {
        this.listExercise = listExercise;
    }
}
