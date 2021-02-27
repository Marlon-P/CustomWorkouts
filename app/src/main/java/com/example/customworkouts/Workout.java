package com.example.customworkouts;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Workout {

    public String groupName;
    private String exerciseName;
    private int sets, repetitions, minutes, seconds;
    private boolean showEditDelMenu = false;
    private String color;

    public Workout(String exerciseName, int sets, int repetitions, int minutes, int seconds) {

        this.color = "#000000";
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repetitions = repetitions;
        this.minutes = minutes;
        this.seconds = seconds;
        groupName = "asdfasdfasdfasdfl;kj;lkj;lkj;lkj";
    }

    public Workout(String exerciseName, int sets, int repetitions, int minutes, int seconds, String groupName) {
        this.color = "#000000";
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repetitions = repetitions;
        this.minutes = minutes;
        this.seconds = seconds;
        this.groupName = groupName;
    }



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
}
