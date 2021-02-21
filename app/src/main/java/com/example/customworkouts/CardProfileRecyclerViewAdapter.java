package com.example.customworkouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardProfileRecyclerViewAdapter extends RecyclerView.Adapter<CardProfileRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Workout> workouts;

    public void setWorkouts(ArrayList<Workout> w) {
        workouts = w;
    }


    @NonNull
    @Override
    public CardProfileRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
        return new CardProfileRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardProfileRecyclerViewAdapter.ViewHolder holder, int position) {
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

    }

    @Override
    public int getItemCount() {
        return workouts.size();
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
