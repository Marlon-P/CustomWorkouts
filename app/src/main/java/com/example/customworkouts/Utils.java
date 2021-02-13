package com.example.customworkouts;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class Utils {

    private static final String WORKOUTS_KEY = "workouts";
    private static Utils instance;
    private SharedPreferences sharedPreferences;
    private static WorkoutRecyclerViewAdapter adapter;


    public Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("WorkoutDb", Context.MODE_PRIVATE);


        if (null == getWorkouts()) {

            initData();
        }

    }

    public Utils getInstance(Context context, WorkoutRecyclerViewAdapter adapter) {
        if (instance == null) {
            instance = new Utils(context);
            this.adapter = adapter;
        }
            return instance;

    }

    public static Utils getInstantiation(Context context) {
        if (instance == null) {
            instance = new Utils(context);

        }
        return instance;
    }

    public Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);

        }
        return instance;
    }

    public void swap(int from_pos, int target_pos) {
        ArrayList<Workout> workouts = getWorkouts();
        for (Workout d: workouts) {
            System.out.println(d.getExerciseName());
        }
        Collections.swap(workouts, from_pos, target_pos);

        System.out.println("hhh");
        for (Workout d: workouts) {
            System.out.println(d.getExerciseName());
        }
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(WORKOUTS_KEY);
        editor.putString(WORKOUTS_KEY, gson.toJson(workouts));
        editor.apply();
        adapter.notifyItemMoved(from_pos, target_pos);
    }

    private void initData() {
        ArrayList<Workout> workouts = new ArrayList<Workout>();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(WORKOUTS_KEY, gson.toJson(workouts));
        editor.apply(); //commit occurs immediately, apply() works in the background

    }

    public void addWorkout(Workout workout) {
        ArrayList<Workout> workouts = getWorkouts();


        if (workouts.add(workout)) {

            updateWorkoutsList(workouts);


        }


    }

    public void editWorkout(int pos, String name, int sets, int reps, int minutes, int seconds) {
        ArrayList<Workout> workouts = getWorkouts();

        Workout w = workouts.get(pos);
        w.setExerciseName(name);
        w.setSets(sets);
        w.setRepetitions(reps);
        w.setMinutes(minutes);
        w.setSeconds(seconds);

        updateWorkoutsList(workouts);

    }

    public void removeWorkout(Workout workout) {
        ArrayList<Workout> workouts = getWorkouts();

        for (Workout w : workouts) {
            if (w.equals(workout)) {
                if (workouts.remove(w)) {
                    updateWorkoutsList(workouts);

                }
            }
        }


    }

    public void updateWorkoutsList(ArrayList<Workout> workouts) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(WORKOUTS_KEY);
        editor.putString(WORKOUTS_KEY, gson.toJson(workouts));

        adapter.setWorkouts(workouts);
        editor.apply();
    }

    public boolean deleteAll() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ArrayList<Workout> workouts = new ArrayList<>();//after clearing all itmes in the list, create a new empty arraylist to add to
        adapter.setWorkouts(workouts);

        Gson gson = new Gson();

        editor.putString(WORKOUTS_KEY, gson.toJson(workouts));

        return true;
    }

    public ArrayList<Workout> getWorkouts() {


        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>(){}.getType();

        return gson.fromJson(sharedPreferences.getString(WORKOUTS_KEY, null), type);
    }
}
