package com.example.workoutnote.dao;

import androidx.room.TypeConverter;

import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<Exercise> fromStringExercise(String value) {
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromListExercise(List<Exercise> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static List<ApproachWeight> fromStringApproachWeight (String value) {
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromListApproachWeight(List<ApproachWeight> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public Date fromTimestamp(Long value){
        return new Date(value == null ? 0 : value);
    }
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : 0;
    }
}
