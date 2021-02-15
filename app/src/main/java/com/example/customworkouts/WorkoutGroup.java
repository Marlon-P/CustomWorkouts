package com.example.customworkouts;

import java.util.ArrayList;

public class WorkoutGroup {

    private String name;
    private String id;
    private ArrayList<Workout> workouts;

    public WorkoutGroup(String name) {
        this.name = name;
        this.id = id;
        workouts = new ArrayList<>();
    }



    public void addWorkout(Workout w) {
        workouts.add(w);
    }

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
}
