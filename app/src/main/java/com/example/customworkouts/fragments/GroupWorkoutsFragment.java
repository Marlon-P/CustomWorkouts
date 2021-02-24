package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.adapters.ColorsAdapter;
import com.example.customworkouts.adapters.GroupWorkoutsAdapter;

public class GroupWorkoutsFragment extends DialogFragment {

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

    public static final String TAG = "Group Workouts Fragment";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.group_workouts_layout, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Group Workouts");

        RecyclerView colorsRecView = view.findViewById(R.id.colorsRecyclerView);
        RecyclerView workoutGroupRecView = view.findViewById(R.id.groupWorkoutsRecyclerView);

        ColorsAdapter colorsAdapter = new ColorsAdapter();
        GroupWorkoutsAdapter groupWorkoutsAdapter = new GroupWorkoutsAdapter();

        colorsAdapter.setAdapter(groupWorkoutsAdapter); //need to pass the color selected to the group to change color of the workouts layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager colorsLLM = new GridLayoutManager(getContext(), 4);

        colorsRecView.setAdapter(colorsAdapter);
        colorsRecView.setLayoutManager(colorsLLM);

        workoutGroupRecView.setAdapter(groupWorkoutsAdapter);
        workoutGroupRecView.setLayoutManager(linearLayoutManager);

        builder.setView(view);
        builder.setCustomTitle(titleView);


        return builder.create();
    }
}
