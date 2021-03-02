package com.example.customworkouts.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.Workout;
import com.example.customworkouts.WorkoutGroup;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GroupWorkoutsAdapter extends RecyclerView.Adapter<GroupWorkoutsAdapter.ViewHolder> {


    private ArrayList<Workout> workouts;
    private String color = "#000000";



    public void setColor(String color) {
        this.color = color;
    }

    public void setWorkouts(ArrayList<Workout> w) {
        workouts = w;
        notifyDataSetChanged();
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    @NonNull
    @Override
    public GroupWorkoutsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupWorkoutsAdapter.ViewHolder holder, int position) {
        Workout w = workouts.get(position);

        String exerciseName = w.getExerciseName();

        String setsAndReps = w.getSets() + "x"+ w.getRepetitions();
        int minutes = w.getMinutes();
        int seconds= w.getSeconds();
        String s = "";
        if (seconds < 10) {
            s = "0";
        }
        String time = minutes + ":" + s + seconds;

        holder.exerciseName.setText(exerciseName);
        holder.setsXrepetitions.setText(setsAndReps);
        holder.timer.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            boolean clicked = false;

            private String lastColor = color;
            @Override
            public void onClick(View v) {

                if (!clicked || !lastColor.equals(color)) {
                    clicked = true;
                    lastColor = color;
                    v.setBackgroundColor(Color.parseColor(color));
                    w.setColor(color);

                } else {
                    clicked = false;
                    v.setBackgroundColor(Color.parseColor("#000000"));
                    w.setColor("#000000");

                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public ArrayList<WorkoutGroup> createWorkoutGroups() {
        ArrayList<WorkoutGroup> workoutGroups = new ArrayList<>();
        HashMap<String, WorkoutGroup> colors = new HashMap<>();
        //group workouts by their colors in a hashmap
        for (Workout w : workouts) {
            String c = w.getColor();
            if (c.equals("#000000")) {//for the individual workouts that were not grouped
                WorkoutGroup wg = new WorkoutGroup("single");
                wg.add(w);
                workoutGroups.add(wg);
            } else {
                WorkoutGroup wg = colors.get(c);
                if (wg == null) {
                    wg = new WorkoutGroup("" + c);
                    wg.add(w);
                    colors.put(c, wg);
                } else {
                    wg.add(w);
                }
            }
        }


        //add all groupings to the list
        for (String d : colors.keySet()) {
            workoutGroups.add(colors.get(d));
        }


        return workoutGroups;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseName, setsXrepetitions, timer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exercise_name);
            setsXrepetitions = itemView.findViewById(R.id.repetitions);
            timer = itemView.findViewById(R.id.timer);

        }
    }
}
