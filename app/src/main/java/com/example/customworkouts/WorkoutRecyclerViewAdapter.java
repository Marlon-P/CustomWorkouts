package com.example.customworkouts;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
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
                        EditText exerciseNameText, sets, repetitions, minutes, seconds;
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);


                        LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View view = inflater.inflate(R.layout.create_workout, null);
                        View titleView = inflater.inflate(R.layout.edit_dialog_title, null);

                        builder.setView(view);
                        builder.setCustomTitle(titleView);

                        exerciseNameText = view.findViewById(R.id.exerciseNameEditTxt);
                        sets = view.findViewById(R.id.numberPicker);
                        repetitions = view.findViewById(R.id.numberPicker2);
                        minutes = view.findViewById(R.id.editTextMinutes);
                        seconds = view.findViewById(R.id.editTextSeconds);

                        exerciseNameText.setText(w.getExerciseName());
                        sets.setText("" + w.getSets());
                        repetitions.setText("" + w.getRepetitions());
                        minutes.setText("" + w.getMinutes());
                        seconds.setText("" + w.getSeconds());

                        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = exerciseNameText.getText().toString();
                                int setNumber = Integer.parseInt(sets.getText().toString());
                                int reps = Integer.parseInt(repetitions.getText().toString());
                                int mins = Integer.parseInt(minutes.getText().toString());
                                int secs = Integer.parseInt(seconds.getText().toString());

                                Utils.getInstantiation(holder.itemView.getContext()).editWorkout(position, name, setNumber, reps, mins, secs);
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
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(holder.itemView.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                        builder.setMessage("Are you sure you want to delete this workout?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.getInstantiation(holder.itemView.getContext()).removeWorkout(w);

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
