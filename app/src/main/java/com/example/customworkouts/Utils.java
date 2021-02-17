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
    private static SharedPreferences sharedPreferences;
    private static ArrayList<Workout> workoutArrayList; //copy of workouts list in storage
    private static WorkoutRecyclerViewAdapter adapter;


    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("WorkoutDb", Context.MODE_PRIVATE);

        if (null == getWorkouts()) {
            initData();
        } else {
            workoutArrayList = getWorkouts();
        }

    }


    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);

        }

        workoutArrayList = getWorkouts();
        return instance;
    }

    public static Utils getInstance(Context context, WorkoutRecyclerViewAdapter adapter) {
        if (instance == null) {
            instance = new Utils(context);
            Utils.adapter = adapter;
        }

        workoutArrayList = getWorkouts();
        return instance;
    }




    private void initData() {

        workoutArrayList = new ArrayList<>();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(WORKOUTS_KEY, gson.toJson(workoutArrayList));
        editor.apply(); //commit occurs immediately, apply() works in the background

    }

    public ArrayList<Workout> swap(int from_pos, int target_pos) {

        for (Workout d: workoutArrayList) {
            System.out.println(d.getExerciseName());
        }

        Collections.swap(workoutArrayList, from_pos, target_pos);


        System.out.println("---------------");
        for (Workout d: workoutArrayList) {
            System.out.println(d.getExerciseName());
        }

        updateWorkoutsList();
        adapter.notifyItemMoved(from_pos, target_pos);

        return workoutArrayList;
    }

    public void addWorkout(Workout workout) {



        if (workoutArrayList.add(workout)) {

            updateWorkoutsList();
            adapter.setWorkouts(workoutArrayList);

        }


    }

    public void addWorkouts(ArrayList<Workout> w) {



        if (workoutArrayList.addAll(w)) {

            updateWorkoutsList();
            adapter.setWorkouts(workoutArrayList);

        }
    }

    public void editWorkout(int pos, String name, int sets, int reps, int minutes, int seconds) {



        Workout w = workoutArrayList.get(pos);
        w.setExerciseName(name);
        w.setSets(sets);
        w.setRepetitions(reps);
        w.setMinutes(minutes);
        w.setSeconds(seconds);

        updateWorkoutsList();
        adapter.setWorkouts(workoutArrayList);
    }

    public void removeWorkout(Workout workout) {

        Workout removeWorkout = new Workout("", 0,0,0,0);

        for (Workout w : workoutArrayList) {
            if (w.equals(workout)) {
                removeWorkout = w;
            }
        }
        if (!removeWorkout.equals(new Workout("", 0,0,0,0)) ) {
            workoutArrayList.remove(removeWorkout);
            updateWorkoutsList();
            setAdapterData();
        }



    }

    public void updateWorkoutsList() {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(WORKOUTS_KEY);
        editor.putString(WORKOUTS_KEY, gson.toJson(workoutArrayList));
        editor.apply();



    }

    private void setAdapterData() {
        adapter.setWorkouts(workoutArrayList);
    }

    public boolean deleteAll() {


        workoutArrayList.clear();//after clearing all itmes in the list, create a new empty arraylist to add to
        updateWorkoutsList();

        return true;
    }

    private static ArrayList<Workout> getWorkouts() {


        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>(){}.getType();

        return gson.fromJson(sharedPreferences.getString(WORKOUTS_KEY, null), type);
    }

    public ArrayList<Workout> getWorkoutsList() {
        return workoutArrayList;
    }
}
