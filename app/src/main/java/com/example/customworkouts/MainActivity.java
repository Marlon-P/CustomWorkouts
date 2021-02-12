package com.example.customworkouts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutRecyclerViewAdapter adapter;
    private EditText exerciseName, repetitions, minutes, seconds;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = new Utils(this);


        adapter = new WorkoutRecyclerViewAdapter();
        recyclerView =findViewById(R.id.workoutsRecyclerView);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int dragged_pos = dragged.getAdapterPosition();
                int targe_pos = target.getAdapterPosition();


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });


        adapter.setWorkouts(utils.getInstance(this, adapter).getWorkouts());

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addWorkout(View view) {


        CreateWorkoutFragment createWorkoutFragment = new CreateWorkoutFragment();
        createWorkoutFragment.show(getFragmentManager(), CreateWorkoutFragment.TAG);


    }


    public void deleteAll(View view) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage("Are you sure you want to delete all workouts?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.getInstantiation(view.getContext()).deleteAll();
            }
        });
    }
}