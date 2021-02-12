package com.example.customworkouts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutRecyclerViewAdapter adapter;
    private EditText exerciseName, repetitions, minutes, seconds;
    private TextView deleteAll;
    private Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteAll = findViewById(R.id.deleteAll);
        utils = new Utils(this);


        adapter = new WorkoutRecyclerViewAdapter();


        recyclerView =findViewById(R.id.workoutsRecyclerView);

        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int dragged_pos = dragged.getAdapterPosition();
                int target_pos = target.getAdapterPosition();

                utils.getInstance(recyclerView.getContext(), adapter).swap(dragged_pos, target_pos);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        helper.attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        ArrayList<Workout> workouts = utils.getInstance(this, adapter).getWorkouts();
        adapter.setWorkouts(workouts);
        if (workouts.size() > 0) {
            deleteAll.setVisibility(View.VISIBLE);
        } else {
            deleteAll.setVisibility(View.GONE);
        }

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
                if (Utils.getInstantiation(view.getContext()).deleteAll()) {
                    deleteAll.setVisibility(View.GONE);

                }

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
}