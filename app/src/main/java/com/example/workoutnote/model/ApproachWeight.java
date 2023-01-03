package com.example.workoutnote.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class ApproachWeight implements Parcelable {

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "countRepeat")
    private int countRepeat;

    public ApproachWeight(int weight, int countRepeat){
        this.weight = weight;
        this.countRepeat = countRepeat;
    }

    public ApproachWeight(Parcel in) {
        weight = in.readInt();
        countRepeat = in.readInt();
    }

    public static final Creator<ApproachWeight> CREATOR = new Creator<ApproachWeight>() {
        @Override
        public ApproachWeight createFromParcel(Parcel in) {
            return new ApproachWeight(in);
        }

        @Override
        public ApproachWeight[] newArray(int size) {
            return new ApproachWeight[size];
        }
    };

    public int getCountRepeat() {
        return countRepeat;
    }

    public void setCountRepeat(int countRepeat) {
        this.countRepeat = countRepeat;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(weight);
        dest.writeInt(countRepeat);
    }
}
