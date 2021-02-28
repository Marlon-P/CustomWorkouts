package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.Workout;
import com.example.customworkouts.WorkoutGroup;
import com.example.customworkouts.adapters.ColorsAdapter;
import com.example.customworkouts.adapters.GroupWorkoutsAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupWorkoutsFragment extends DialogFragment {

    private ArrayList<Workout> workouts;
    private String profileName;
    public static final String TAG = "Group Workouts Fragment";

    public void setProfile(String name, ArrayList<Workout> w) {
        profileName = name;
        workouts = w;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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

    private ArrayList<WorkoutGroup> createWorkoutGroup() {
        System.out.println("CREATING WORKOUTGROUP");
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        AlertDialog dialog;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.group_workouts_layout, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Group Workouts");

        RecyclerView colorsRecView = view.findViewById(R.id.colorsRecyclerView);
        RecyclerView workoutGroupRecView = view.findViewById(R.id.groupWorkoutsRecyclerView);

        //TODO Add scroll bar to color recycler view
        ColorsAdapter colorsAdapter = new ColorsAdapter();
        GroupWorkoutsAdapter groupWorkoutsAdapter = new GroupWorkoutsAdapter();
        groupWorkoutsAdapter.setWorkouts(workouts);

        colorsAdapter.setAdapter(groupWorkoutsAdapter); //need to pass the color selected to the group to change color of the workouts layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager colorsLLM = new GridLayoutManager(getContext(), 4);

        colorsRecView.setAdapter(colorsAdapter);
        colorsRecView.setLayoutManager(colorsLLM);

        workoutGroupRecView.setAdapter(groupWorkoutsAdapter);
        workoutGroupRecView.setLayoutManager(linearLayoutManager);

        builder.setView(view);
        builder.setCustomTitle(titleView);

        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderGroupsFragment fragment = new OrderGroupsFragment();
                        fragment.setWorkoutGroup(profileName, createWorkoutGroup());
                        fragment.show(getParentFragmentManager(), OrderGroupsFragment.TAG);
                    }
                });
            }
        });
        return dialog;
    }
}
