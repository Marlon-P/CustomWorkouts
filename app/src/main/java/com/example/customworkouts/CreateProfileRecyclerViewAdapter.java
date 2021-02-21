package com.example.customworkouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateProfileRecyclerViewAdapter extends RecyclerView.Adapter<CreateProfileRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Workout> workouts;
    private WorkoutGroup group;

    public CreateProfileRecyclerViewAdapter() {
        group = new WorkoutGroup("");
    }

    public void setWorkouts(ArrayList<Workout> w) {
        workouts = w;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);


        return new CreateProfileRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateProfileRecyclerViewAdapter.ViewHolder holder, int position) {


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
            @Override
            public void onClick(View v) {

                if (!clicked) {
                    clicked = true;
                    v.setBackgroundColor(v.getContext().getResources().getColor(R.color.purple_500));
                    group.add(w);
                } else {
                    clicked = false;
                    v.setBackgroundColor(v.getContext().getResources().getColor(R.color.black));
                    group.remove(w);
                }

            }
        });




    }

    public WorkoutGroup getProfile() {
        return group;
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
