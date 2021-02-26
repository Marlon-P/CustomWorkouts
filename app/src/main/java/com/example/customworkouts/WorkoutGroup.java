package com.example.customworkouts;

import java.util.ArrayList;

public class WorkoutGroup {

    private String name;
    private ArrayList<Workout> workouts;
    private int color;

    public WorkoutGroup(String name) {
        this.name = name;
        workouts = new ArrayList<>();
    }



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
        return name + " size: " + workouts.get(0).getExerciseName();
    }

    public int getSize() {
        return workouts.size();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
