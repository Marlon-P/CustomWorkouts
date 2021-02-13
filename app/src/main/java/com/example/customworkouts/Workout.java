package com.example.customworkouts;

import java.util.Objects;

public class Workout {

    private String exerciseName;
    private int sets, repetitions, minutes, seconds;
    private boolean showEditDelMenu = false;

    public Workout(String exerciseName, int sets, int repetitions, int minutes, int seconds) {

        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repetitions = repetitions;
        this.minutes = minutes;
        this.seconds = seconds;
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

    public boolean isShowEditDelMenu() {
        return showEditDelMenu;
    }

    public void setShowEditDelMenu(boolean showEditDelMenu) {
        this.showEditDelMenu = showEditDelMenu;
    }
}
