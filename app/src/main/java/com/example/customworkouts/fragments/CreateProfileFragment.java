package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.MainActivity;
import com.example.customworkouts.R;
import com.example.customworkouts.Utils;
import com.example.customworkouts.adapters.CreateProfileRecyclerViewAdapter;

public class CreateProfileFragment extends DialogFragment {


    public static String TAG = "CREATE_PROFILE_FRAGMEN";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_workout_profile, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Create Profile");

        Context context = view.getContext();

        builder.setView(view);
        builder.setCustomTitle(titleView);

        RecyclerView recyclerView = view.findViewById(R.id.create_profile_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CreateProfileRecyclerViewAdapter adapter = new CreateProfileRecyclerViewAdapter();
        adapter.setWorkouts(Utils.getInstance(getContext()).getWorkoutsList());
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        EditText profileName = view.findViewById(R.id.editProfileName);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = profileName.getText().toString();
                if ( Utils.getInstance(context).createProfile(name, adapter.getProfile())) {


                } else {
                    Toast.makeText(context, "Couldn't create profile", Toast.LENGTH_SHORT).show();
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