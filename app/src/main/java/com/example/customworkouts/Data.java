package com.example.customworkouts;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.customworkouts.adapters.CardProfileRecyclerViewAdapter;
import com.example.customworkouts.adapters.ProfileRecyclerViewAdapter;
import com.example.customworkouts.adapters.WorkoutRecyclerViewAdapter;
import com.example.customworkouts.fragments.ProfileFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Data {

    private static final String WORKOUTS_KEY = "workouts";
    private static Data instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences profiles;
    private SharedPreferences.Editor allWorkoutsEditor;
    private SharedPreferences.Editor profilesEditor;
    private static ArrayList<Workout> workoutArrayList; //copy of workouts list in storage
    private static WorkoutRecyclerViewAdapter workoutRecyclerViewAdapter;
    private static Gson gson;


    private Data(Context context) {
        sharedPreferences = context.getSharedPreferences("WorkoutDb", Context.MODE_PRIVATE);
        profiles = context.getSharedPreferences("ProfilesDb", Context.MODE_PRIVATE);
        gson = new Gson();
        if (null == getWorkouts()) {
            initData();
        } else {
            workoutArrayList = getWorkouts();
        }
    }


    public static Data getInstance(Context context) {
        if (instance == null) {
            instance = new Data(context);

        }
        workoutArrayList = getWorkouts();
        return instance;
    }

    public static Data getInstance(Context context, WorkoutRecyclerViewAdapter adapter) {
        if (instance == null) {
            instance = new Data(context);
            Data.workoutRecyclerViewAdapter = adapter;
        }

        workoutArrayList = getWorkouts();
        return instance;
    }








    private void initData() {
        workoutArrayList = new ArrayList<>();

        allWorkoutsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        allWorkoutsEditor.putString(WORKOUTS_KEY, gson.toJson(workoutArrayList));
        allWorkoutsEditor.apply(); //commit() occurs immediately, apply() works in the background

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
        workoutRecyclerViewAdapter.notifyItemMoved(from_pos, target_pos);

        return workoutArrayList;
    }

    public void addWorkout(Workout workout) {
        if (workoutArrayList.add(workout)) {

            updateWorkoutsList();
            workoutRecyclerViewAdapter.setWorkouts(workoutArrayList);

        }
    }

    public void addWorkouts(ArrayList<Workout> w) {
        if (workoutArrayList.addAll(w)) {

            updateWorkoutsList();
            workoutRecyclerViewAdapter.setWorkouts(workoutArrayList);

        }
    }

    public void editWorkout(int pos, String name, int sets, int reps, int minutes, int seconds, int durMin, int durSec) {
        Workout w = workoutArrayList.get(pos);
        w.setExerciseName(name);
        w.setSets(sets);
        w.setRepetitions(reps);
        w.setMinutes(minutes);
        w.setSeconds(seconds);
        w.setWorkoutDurationMinutes(durMin);
        w.setWorkoutDurationSeconds(durSec);

        updateWorkoutsList();
        workoutRecyclerViewAdapter.setWorkouts(workoutArrayList);
    }

    public void removeWorkout(Workout workout) {
        Workout removeWorkout = new Workout("", 0,0,0,0, 0, 0);

        for (Workout w : workoutArrayList) {
            if (w.equals(workout)) {
                removeWorkout = w;
            }
        }
        if (!removeWorkout.equals(new Workout("", 0,0,0,0, 0, 0)) ) {
            workoutArrayList.remove(removeWorkout);
            updateWorkoutsList();
            workoutRecyclerViewAdapter.setWorkouts(workoutArrayList);
        }
    }

    public void updateWorkoutsList() {
        allWorkoutsEditor = sharedPreferences.edit();
        allWorkoutsEditor.remove(WORKOUTS_KEY);
        allWorkoutsEditor.putString(WORKOUTS_KEY, gson.toJson(workoutArrayList));
        allWorkoutsEditor.apply();
    }



    public boolean deleteAll() {
        workoutArrayList.clear();//after clearing all itmes in the list, create a new empty arraylist to add to
        updateWorkoutsList();

        return true;
    }

    private static ArrayList<Workout> getWorkouts() {
        Type type = new TypeToken<ArrayList<Workout>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(WORKOUTS_KEY, null), type);
    }

    public ArrayList<Workout> getWorkoutsList() {
        return workoutArrayList;
    }

    public boolean createProfile(String profileName, ArrayList<WorkoutGroup> w) {

        Profile wList = getProfile(profileName);

        if (wList != null) {
            return false;
        }

        Profile p = new Profile(profileName, w);

        profilesEditor = profiles.edit();
        profilesEditor.putString(profileName, gson.toJson(p));
        profilesEditor.apply();


        return true;
    }

    public boolean updateProfile(String oldName, String newName,  Profile newProfile) {
        Profile wList = getProfile(oldName);
        System.out.println("update: " + newName);
        if (wList == null) {
            return false;
        }

        profilesEditor = profiles.edit();
        profilesEditor.remove(oldName);
        profilesEditor.putString(newName, gson.toJson(newProfile));
        profilesEditor.apply();

        return true;
    }

    public Profile getProfile(String profileName) {
        Type type = new TypeToken<Profile>(){}.getType();
        return gson.fromJson(profiles.getString(profileName, null), type);
    }




    public boolean deleteProfile(String profileName) {
        Profile wList = getProfile(profileName);

        //if list is null then that means list with the key of "profileName" does not exist
        if (wList == null) {
            return false;
        }

        profilesEditor = profiles.edit();
        profilesEditor.remove(profileName);
        profilesEditor.apply();
        return true;
    }

    public boolean deleteAllProfiles() {
        profilesEditor = profiles.edit();
        profilesEditor.clear();
        profilesEditor.apply();

        return true;
    }

    public void populate() {

        Workout a = new Workout("Pull Ups", 3, 6, 1, 30, 0, 12);
        Workout b = new Workout("Pistol Squats", 3, 5, 1, 30, 0, 12);
        Workout c = new Workout("Dips", 3, 15, 1, 30, 0 , 12);
        Workout d = new Workout("Single Leg Deadlift", 3, 12, 1, 30, 0 , 12);
        Workout e = new Workout("Bodyweight Rows", 3, 6, 1, 30, 0, 12);
        Workout f = new Workout("Handstand Push Ups", 3, 7, 1, 30, 0, 12);
        Workout g = new Workout("L-sit", 3, 60, 3, 0, 0, 12);
        Workout h = new Workout("Planche Push Ups", 3, 5, 4, 20, 0, 12);
        Workout i = new Workout("Archer Pull Ups", 3, 9, 10, 20, 0, 12);
        Workout j = new Workout("Chin Ups", 3, 8, 1, 30, 0, 12);
        Workout k = new Workout("Calf Raises", 10, 99, 1, 30, 0, 12);

        workoutArrayList.add(a);
        workoutArrayList.add(b);
        workoutArrayList.add(c);
        workoutArrayList.add(d);
        workoutArrayList.add(e);
        workoutArrayList.add(f);
        workoutArrayList.add(g);
        workoutArrayList.add(h);
        workoutArrayList.add(i);
        workoutArrayList.add(j);
        workoutArrayList.add(k);


        updateWorkoutsList();
    }

    public ArrayList<Profile> getProfiles() {
       ArrayList<Profile> w = new ArrayList<>();

        for (Map.Entry<String, ?> entry: profiles.getAll().entrySet()) {
            System.out.println(entry.getKey());
            w.add((getProfile(entry.getKey())));

        }

        return w;
    }
}
