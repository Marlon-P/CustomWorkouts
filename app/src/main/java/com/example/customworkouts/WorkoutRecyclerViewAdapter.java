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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.ViewHolder>  {

    private ArrayList<Workout> workouts;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public void setWorkouts(ArrayList<Workout> workouts) {

        this.workouts = workouts;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == SHOW_MENU) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_delete_workout, parent, false);
            return new ViewHolder(view, true);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
            return new ViewHolder(view, false);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            Workout w = workouts.get(position);

            if (w.isShowEditDelMenu()) {
                holder.editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "NAVIGATE TO EDIT THE WORKOUT", Toast.LENGTH_SHORT).show();

                    }
                });
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "NAVIGATE TO Delete THE WORKOUT", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                String exerciseName = w.getExerciseName();

                String setsAndReps = w.getSets() + "x"+ w.getRepetitions();
                int minutes = w.getMinutes();
                int seconds= w.getSeconds();
                String time = minutes + ":" + seconds;

                holder.exerciseName.setText(exerciseName);
                holder.setsXrepetitions.setText(setsAndReps);
                holder.timer.setText(time);
            }


    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (workouts.get(position).isShowEditDelMenu()) {
            return SHOW_MENU;
        }

        return HIDE_MENU;
    }

    public void showMenu(int pos) {
        workouts.get(pos).setShowEditDelMenu(true);
        notifyDataSetChanged();
    }

    public void closeMenu(int pos) {
        workouts.get(pos).setShowEditDelMenu(false);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        private TextView exerciseName, setsXrepetitions, timer;
        private ImageView editButton, deleteButton;


        public ViewHolder(@NonNull View itemView, boolean showMenu) {
            super(itemView);


            if (showMenu) {

                editButton = itemView.findViewById(R.id.editButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);

            } else {
                exerciseName = itemView.findViewById(R.id.exercise_name);
                setsXrepetitions = itemView.findViewById(R.id.repetitions);
                timer = itemView.findViewById(R.id.timer);
            }


        }
    }


}
