package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.customworkouts.MainActivity;
import com.example.customworkouts.R;
import com.example.customworkouts.Utils;
import com.example.customworkouts.Workout;
import com.example.customworkouts.fragments.HomeFragment;

import java.util.ArrayList;


public class CreateWorkoutFragment extends DialogFragment {


    private EditText exerciseNameText, sets, repetitions, minutes, seconds, durationMinutes, durationSeconds;


    private boolean multiAdd = false;
    public static String TAG = "CreateWorkoutFragmentDialog";//used as a key for storing the workouts in local storage

    @RequiresApi(api = Build.VERSION_CODES.M)
    public CreateWorkoutFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.create_workout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        multiAdd = bundle.getBoolean("multiAdd");

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_workout, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);

        builder.setView(view);
        builder.setCustomTitle(titleView);

        exerciseNameText = view.findViewById(R.id.exerciseNameEditTxt);
        sets = view.findViewById(R.id.numberPicker);
        repetitions = view.findViewById(R.id.numberPicker2);
        minutes = view.findViewById(R.id.editTextMinutes);
        seconds = view.findViewById(R.id.editTextSeconds);

        durationMinutes = view.findViewById(R.id.durationMinutes);
        durationSeconds = view.findViewById(R.id.durationSeconds);

        Context context = getContext();

        if (multiAdd) {

            ArrayList<Workout> workouts = new ArrayList<>();

            builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (workouts.isEmpty()) {
                        Toast.makeText(context, "No Workouts Added", Toast.LENGTH_LONG).show();
                    } else {
                        Utils.getInstance(context).addWorkouts(workouts);
                        if (workouts.size() > 1) {
                            Toast.makeText(context, "Added " + workouts.size() + " workouts to your list", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Added a new Workout", Toast.LENGTH_SHORT).show();
                        }
                        MainActivity.fgm.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                    }
                }
            });

            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    workouts.clear();
                }
            });


            builder.setNegativeButton("Add Workout", null);

            dialog = builder.create();

            //do this so the add workout button doesn't dismiss the dialog
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button addWorkoutButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                    addWorkoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = exerciseNameText.getText().toString();
                            String setNumberEditText = sets.getText().toString();
                            String repsEditText = repetitions.getText().toString();
                            String minsEditText = minutes.getText().toString();
                            String secondsEditText = seconds.getText().toString();
                            String durationMinEditText = durationMinutes.getText().toString();
                            String durationSecondsEditText = durationSeconds.getText().toString();

                            if (    name.isEmpty()                          ||//form validation
                                    setNumberEditText.isEmpty()             ||
                                    repsEditText.isEmpty()                  ||
                                    minsEditText.isEmpty()                  ||
                                    secondsEditText.isEmpty()               ||
                                    durationMinEditText.isEmpty()           ||
                                    durationSecondsEditText.isEmpty()
                            ){

                                Toast.makeText(context, "Some or all fields were left blank, no workout was created", Toast.LENGTH_LONG).show();


                            } else {
                                int setNumber = Integer.parseInt(setNumberEditText);
                                int reps = Integer.parseInt(repsEditText);
                                int mins = Integer.parseInt(minsEditText);
                                int secs = Integer.parseInt(secondsEditText);
                                int durMins = Integer.parseInt(durationMinEditText);
                                int durSecs = Integer.parseInt(durationSecondsEditText);

                                workouts.add(new Workout(name, setNumber, reps, mins, secs, durMins, durSecs));
                                Toast.makeText(context, "Added " + name + " to your workouts", Toast.LENGTH_SHORT).show();

                                //clear the edit texts to allow for another entry
                                exerciseNameText.setText("");
                                sets.setText("");
                                repetitions.setText("");
                                minutes.setText("");
                                seconds.setText("");

                                durationMinutes.setText("");
                                durationSeconds.setText("");

                            }
                        }
                    });
                }
            });


            return dialog;


        } else {

            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String name = exerciseNameText.getText().toString();
                    String setNumberEditText = sets.getText().toString();
                    String repsEditText = repetitions.getText().toString();
                    String minsEditText = minutes.getText().toString();
                    String secondsEditText = seconds.getText().toString();
                    String durationMinEditText = durationMinutes.getText().toString();
                    String durationSecondsEditText = durationSeconds.getText().toString();

                    if (    name.isEmpty()                          ||//form validation
                            setNumberEditText.isEmpty()             ||
                            repsEditText.isEmpty()                  ||
                            minsEditText.isEmpty()                  ||
                            secondsEditText.isEmpty()               ||
                            durationMinEditText.isEmpty()           ||
                            durationSecondsEditText.isEmpty()
                    ) {

                            Toast.makeText(context, "Some or all fields were left blank, no workout was created", Toast.LENGTH_LONG).show();


                    } else {

                        int setNumber = Integer.parseInt(setNumberEditText);
                        int reps = Integer.parseInt(repsEditText);
                        int mins = Integer.parseInt(minsEditText);
                        int secs = Integer.parseInt(secondsEditText);

                        int durMins = Integer.parseInt(durationMinEditText);
                        int durSecs = Integer.parseInt(durationSecondsEditText);

                        Utils.getInstance(context).addWorkout(new Workout(name, setNumber, reps, mins, secs, durMins, durSecs));
                        MainActivity.fgm.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                        Toast.makeText(context, "Add " + name + " to your workouts list", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}

