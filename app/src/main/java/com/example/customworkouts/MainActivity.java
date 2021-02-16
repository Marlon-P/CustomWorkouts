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
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutRecyclerViewAdapter adapter;
    private Utils utils;
    private FloatingActionButton menuOpenFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        menuOpenFAB = findViewById(R.id.open_menu_FAB);



        menuOpenFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Delete All")) {
                            deleteAll(v);
                        } else if (item.getTitle().equals("Add a Workout")) {
                            addWorkout(v);
                        } else {
                            System.out.println("Adding MULTIPLE WORKOUTS");
                            addMultiWorkouts(v);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        utils = new Utils(this);


        adapter = new WorkoutRecyclerViewAdapter();


        recyclerView =findViewById(R.id.workoutsRecyclerView);

        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
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

            }


        });

        helper.attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        ArrayList<Workout> workouts = utils.getInstance(this, adapter).getWorkouts();
        adapter.setWorkouts(workouts);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addWorkout(View view) {


        //prompt dialog to show up to create workout
        CreateWorkoutFragment createWorkoutFragment = new CreateWorkoutFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("multiAdd", false);
        createWorkoutFragment.setArguments(bundle);
        createWorkoutFragment.show(getFragmentManager(), CreateWorkoutFragment.TAG);



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addMultiWorkouts(View v) {
        CreateWorkoutFragment createWorkoutFragment = new CreateWorkoutFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("multiAdd", true);
        createWorkoutFragment.setArguments(bundle);

        createWorkoutFragment.show(getFragmentManager(), CreateWorkoutFragment.TAG);


    }



    public void deleteAll(View view) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage("Are you sure you want to delete all workouts?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Utils.getInstantiation(view.getContext()).deleteAll()) {
                    Toast.makeText(view.getContext(), "Deleted All Workouts", Toast.LENGTH_SHORT).show();

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