package com.example.customworkouts;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String WORKOUTS_KEY = "workouts";
    private static Utils instance;
    private SharedPreferences sharedPreferences;
    private static WorkoutRecyclerViewAdapter adapter;
    private static ArrayList<Workout> workoutArrayList;

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

    private void initData() {
        ArrayList<Workout> workouts = new ArrayList<Workout>();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(WORKOUTS_KEY, gson.toJson(workouts));
        editor.commit(); //commit occurs immediately, apply() works in the background

    }

    public boolean addWorkout(Workout workout) {
        ArrayList<Workout> workouts = getWorkouts();


        if (workouts.add(workout)) {

            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(WORKOUTS_KEY);
            editor.putString(WORKOUTS_KEY, gson.toJson(workouts));
            adapter.setWorkouts(workouts);

            editor.apply();
            return true;

        }

        return false;
    }

    public boolean removeWorkout(Workout workout) {
        ArrayList<Workout> workouts = getWorkouts();
        System.out.println("SIZE OF WORKOUTS IS " + workouts.size());
        for (Workout w : workouts) {
            if (w.equals(workout)) {
                if (workouts.remove(w)) {
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(WORKOUTS_KEY);
                    editor.putString(WORKOUTS_KEY, gson.toJson(workouts));

                    editor.apply();
                    return true;
                }
            }
        }

        return false;
    }

    public void deleteAll() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        adapter.setWorkouts(new ArrayList<>());
    }

    public ArrayList<Workout> getWorkouts() {

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>(){}.getType();

        return gson.fromJson(sharedPreferences.getString(WORKOUTS_KEY, null), type);
    }
}
