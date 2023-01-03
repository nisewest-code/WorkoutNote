package com.example.workoutnote.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise implements Parcelable {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "list_approach")
    private List<ApproachWeight> listApproach;

    public Exercise(String name, List<ApproachWeight> listApproach){
        this(name);
        this.listApproach = listApproach;
    }

    @Ignore
    Exercise(String name){
        this.name = name;
        this.listApproach = new ArrayList<>();
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        listApproach = in.createTypedArrayList(ApproachWeight.CREATOR);
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApproachWeight> getListApproach() {
        return listApproach;
    }

    public void setListApproach(List<ApproachWeight> listApproach) {
        this.listApproach = listApproach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(listApproach);
    }
}
