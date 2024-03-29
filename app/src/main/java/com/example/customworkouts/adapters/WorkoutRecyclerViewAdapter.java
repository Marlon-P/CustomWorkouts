package com.example.customworkouts.adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.fragments.HomeFragment;
import com.example.customworkouts.MainActivity;
import com.example.customworkouts.R;
import com.example.customworkouts.Data;
import com.example.customworkouts.Workout;

import java.util.ArrayList;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Workout> workouts;

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)  {
        Context context = holder.itemView.getContext();
        Data data = Data.getInstance(context);
        Workout w = workouts.get(position);

        String exerciseName = w.getExerciseName();

        String setsAndReps = w.getSets() + "x" + w.getRepetitions();
        int minutes = w.getMinutes();
        int seconds = w.getSeconds();
        String s = "";

        if (seconds < 10) {
            s = "0";
        }

        String time = minutes + ":" + s + seconds;

        holder.exerciseName.setText(exerciseName);
        holder.setsXrepetitions.setText(setsAndReps);
        holder.timer.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = holder.edit_delete_option_menu.getVisibility();

                if (visibility == View.VISIBLE) {
                    holder.edit_delete_option_menu.setVisibility(View.GONE);
                } else {
                    holder.edit_delete_option_menu.setVisibility(View.VISIBLE);
                }


            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText exerciseNameText, sets, repetitions, minutes, seconds, durationMin, durationSec;
                AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = inflater.inflate(R.layout.create_workout, null);
                View titleView = inflater.inflate(R.layout.edit_dialog_title, null);

                builder.setView(view);
                builder.setCustomTitle(titleView);

                exerciseNameText = view.findViewById(R.id.exerciseNameEditTxt);
                sets = view.findViewById(R.id.numberPicker);
                repetitions = view.findViewById(R.id.numberPicker2);
                minutes = view.findViewById(R.id.editTextMinutes);
                seconds = view.findViewById(R.id.editTextSeconds);
                durationMin = view.findViewById(R.id.durationMinutes);
                durationSec = view.findViewById(R.id.durationSeconds);

                String setCount = "" + w.getSets();
                String repCount = "" + w.getRepetitions();
                String minuteCount = "" + w.getMinutes();
                String workoutDurationMinutesPlace = "" + w.getWorkoutDurationMinutes();
                String workoutDurationSecondsPlace = "" + w.getWorkoutDurationSeconds();

                exerciseNameText.setText(w.getExerciseName());
                sets.setText(setCount);
                repetitions.setText(repCount);
                minutes.setText(minuteCount);
                durationMin.setText(workoutDurationMinutesPlace);
                durationSec.setText(workoutDurationSecondsPlace);

                int s = w.getSeconds();
                if (s < 10) {
                    String secone = "0" + s;
                    seconds.setText(secone);
                } else {
                    String secone = "" + s;
                    seconds.setText(secone);
                }

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = exerciseNameText.getText().toString();
                        int setNumber = Integer.parseInt(sets.getText().toString());
                        int reps = Integer.parseInt(repetitions.getText().toString());
                        int mins = Integer.parseInt(minutes.getText().toString());
                        int secs = Integer.parseInt(seconds.getText().toString());
                        int durMin = Integer.parseInt(durationMin.getText().toString());
                        int durSec = Integer.parseInt(durationSec.getText().toString());

                        data.editWorkout(position, name, setNumber, reps, mins, secs, durMin, durSec);
                        setWorkouts(data.getWorkoutsList());

                        notifyItemChanged(position);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create();
                builder.show();
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                builder.setMessage("Are you sure you want to delete this workout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.removeWorkout(w);
                        setWorkouts(data.getWorkoutsList());
                        holder.edit_delete_option_menu.setVisibility(View.GONE);
                        MainActivity.fgm.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return workouts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView exerciseName, setsXrepetitions, timer;
        private ImageView editButton, deleteButton;
        private LinearLayout edit_delete_option_menu;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);


            exerciseName = itemView.findViewById(R.id.exercise_name);
            setsXrepetitions = itemView.findViewById(R.id.repetitions);
            timer = itemView.findViewById(R.id.timer);

            edit_delete_option_menu = itemView.findViewById(R.id.edit_delete_options);

        }
    }


}
