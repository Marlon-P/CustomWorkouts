package com.example.customworkouts;


import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.ViewHolder>  {

    private ArrayList<Workout> workouts;

    public void setWorkouts(ArrayList<Workout> workouts) {

        this.workouts = workouts;
        System.out.println("is workouts null?" + this.workouts == null);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WindowManager windowManager = (WindowManager)parent.getContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.WRAP_CONTENT));
        Log.d("Inflating: ", "VIEW IS BEING INFLATED");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.exerciseName.setText(workouts.get(position).getExerciseName());
        holder.repetitions.setText(""+ workouts.get(position).getRepetitions());
        holder.timer.setText("10:22");

    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView hamburgerIcon;
        private TextView exerciseName, repetitions, timer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hamburgerIcon = itemView.findViewById(R.id.hamburger);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            repetitions = itemView.findViewById(R.id.repetitions);
            timer = itemView.findViewById(R.id.timer);


        }
    }
}
