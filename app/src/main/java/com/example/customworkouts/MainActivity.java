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
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
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

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.purple_500));

            //swap two workouts with each other
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int dragged_pos = dragged.getAdapterPosition();
                int target_pos = target.getAdapterPosition();

                utils.getInstance(recyclerView.getContext(), adapter).swap(dragged_pos, target_pos);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    adapter.showMenu(viewHolder.getAdapterPosition());
                    background = new ColorDrawable(getResources().getColor(R.color.gray));//since bg is already purple change it to gray after swiping left
                } else {
                    adapter.closeMenu(viewHolder.getAdapterPosition());
                    background = new ColorDrawable(getResources().getColor(R.color.purple_500));
                }
            }

            //just drawing the purple or gray background when swiping left or right on the recycler view
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
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


        //prompt dialog to show up to create workout
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