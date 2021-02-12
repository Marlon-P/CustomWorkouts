package com.example.customworkouts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


public class CreateWorkoutFragment extends DialogFragment {


    private EditText exerciseNameText, repetitions, minutes, seconds;

    public interface EditNameDialogListener {
        void onFinishedWritingData(String exerciseName, String repetitions, String minutes, String seconds);

    }


    public static String TAG = "CreateWorkoutFragmentDialog";

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
        //timer = view.findViewById(R.id.countDownTimer);


    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_workout, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        builder.setView(view);
        builder.setCustomTitle(titleView);

        exerciseNameText = view.findViewById(R.id.exerciseNameEditTxt);
        repetitions = view.findViewById(R.id.numberPicker);
        minutes = view.findViewById(R.id.editTextMinutes);
        seconds = view.findViewById(R.id.editTextSeconds);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (exerciseNameText.getText().toString().isEmpty()     ||
                        repetitions.getText().toString().isEmpty()      ||
                        minutes.getText().toString().isEmpty()          ||
                        seconds.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Some or all fields were left blank, no workout was created", Toast.LENGTH_LONG).show();
                } else {
                    String name = exerciseNameText.getText().toString();
                    int reps = Integer.parseInt(repetitions.getText().toString());
                    int mins = Integer.parseInt(minutes.getText().toString());
                    int secs = Integer.parseInt(seconds.getText().toString());

                    Utils.getInstantiation(getContext()).addWorkout(new Workout(name, reps, mins, secs));
                    Toast.makeText(getContext(), "Created Workout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}

