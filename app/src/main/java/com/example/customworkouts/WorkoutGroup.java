package com.example.customworkouts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class WorkoutGroup implements Parcelable {

    private String name;
    private ArrayList<Workout> workouts;
    private String color;

    public WorkoutGroup(String name) {
        this.name = name;
        workouts = new ArrayList<>();
    }


    protected WorkoutGroup(Parcel in) {
        name = in.readString();
        workouts = in.createTypedArrayList(Workout.CREATOR);
        color = in.readString();
    }

    public static final Creator<WorkoutGroup> CREATOR = new Creator<WorkoutGroup>() {
        @Override
        public WorkoutGroup createFromParcel(Parcel in) {
            return new WorkoutGroup(in);
        }

        @Override
        public WorkoutGroup[] newArray(int size) {
            return new WorkoutGroup[size];
        }
    };

    public void add(Workout w) {
        workouts.add(w);
    }

    public void remove(Workout w) {workouts.remove(w);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    public Workout getWorkout(int position) {
        return workouts.get(position);
    }

    @Override
    public String toString() {

        String s = "";
        for (Workout w : workouts) {
            s += w.toString() + "\n";
        }
        return s;
    }

    public int getSize() {
        return workouts.size();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(workouts);
        dest.writeString(color);
    }
}
