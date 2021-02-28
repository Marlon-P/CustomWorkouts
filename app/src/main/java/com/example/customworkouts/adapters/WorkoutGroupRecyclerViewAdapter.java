package com.example.customworkouts.adapters;

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

import java.util.Collections;

//adapts the workout_group layout, inflates all the workouts in a workout group
public class WorkoutGroupRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutGroupRecyclerViewAdapter.ViewHolder> {

    private WorkoutGroup group;

    public void setGroup(WorkoutGroup g) {
        group = g;
    }

    public WorkoutGroup getGroup() {
        return group;
    }

    public void swap(int from, int to) {

        Collections.swap(group.getWorkouts(), from, to);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout w = group.getWorkout(position);

        String exerciseName = w.getExerciseName();

        String setsAndReps = w.getSets() + "x"+ w.getRepetitions();
        int minutes = w.getMinutes();
        int seconds= w.getSeconds();
        String s = "";
        if (seconds < 10) {
            s = "0";
        }
        String time = minutes + ":" + s + seconds;


        System.out.println("Color: " + w.getColor());
        holder.itemView.setBackgroundColor(Color.parseColor(w.getColor()));
        holder.exerciseName.setText(exerciseName);
        holder.setsXrepetitions.setText(setsAndReps);
        holder.timer.setText(time);
    }

    @Override
    public int getItemCount() {
        return group.getSize();
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
