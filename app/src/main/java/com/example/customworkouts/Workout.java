package com.example.customworkouts;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Workout implements Parcelable {


    private String exerciseName;
    private int sets, repetitions, minutes, seconds;
    private int workoutDurationMinutes, workoutDurationSeconds; //the time it takes to perform a full set of an exercise
    private String color;

    public Workout(String exerciseName, int sets, int repetitions, int minutes, int seconds, int durMins, int durSecs) {

        this.color = "#000000";//used to group workouts together, default is black to indicate a workout by itself
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repetitions = repetitions;
        this.minutes = minutes;
        this.seconds = seconds;
        this.workoutDurationMinutes = durMins;
        this.workoutDurationSeconds = durSecs;

    }

    protected Workout(Parcel in) {
        exerciseName = in.readString();
        sets = in.readInt();
        repetitions = in.readInt();
        minutes = in.readInt();
        seconds = in.readInt();
        workoutDurationMinutes = in.readInt();
        workoutDurationSeconds = in.readInt();
        color = in.readString();
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

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getWorkoutDurationMinutes() {
        return workoutDurationMinutes;
    }

    public void setWorkoutDurationMinutes(int workoutDurationMinutes) {
        this.workoutDurationMinutes = workoutDurationMinutes;
    }

    public int getWorkoutDurationSeconds() {
        return workoutDurationSeconds;
    }

    public void setWorkoutDurationSeconds(int workoutDurationSeconds) {
        this.workoutDurationSeconds = workoutDurationSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout)) return false;
        Workout workout = (Workout) o;
        return getRepetitions() == workout.getRepetitions() &&
                getMinutes() == workout.getMinutes() &&
                getSeconds() == workout.getSeconds() &&
                getExerciseName().equals(workout.getExerciseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExerciseName(), getRepetitions(), getMinutes(), getSeconds());
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return exerciseName + " " + sets + "x" + repetitions;
    }


    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exerciseName);
        dest.writeInt(sets);
        dest.writeInt(repetitions);
        dest.writeInt(minutes);
        dest.writeInt(seconds);
        dest.writeInt(workoutDurationMinutes);
        dest.writeInt(workoutDurationSeconds);
        dest.writeString(color);
    }
}
