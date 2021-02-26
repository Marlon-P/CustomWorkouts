package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.WorkoutGroup;
import com.example.customworkouts.adapters.OrderGroupsAdapter;

import java.util.ArrayList;

public class OrderGroupsFragment extends DialogFragment {

    private ArrayList<WorkoutGroup> workoutGroup;
    public static final String TAG = "ORDER GROUP FRAGMENT";

    public void setWorkoutGroup(ArrayList<WorkoutGroup> wg) {
        workoutGroup = wg;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.workout_container_fragment, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Order Workouts");

        OrderGroupsAdapter adapter = new OrderGroupsAdapter();
        adapter.setGroups(workoutGroup);

        RecyclerView allWg = view.findViewById(R.id.workoutsRecyclerView);
        allWg.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        allWg.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(allWg.getContext(),
                linearLayoutManager.getOrientation());

       allWg.addItemDecoration(dividerItemDecoration);

        builder.setView(view);
        builder.setCustomTitle(titleView);

        return builder.create();
    }
}
