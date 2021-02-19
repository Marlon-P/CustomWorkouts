package com.example.customworkouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton menuOpenFAB;
    private BottomNavigationView bottomNavView;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private String currentFragment = "HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();

        bottomNavView = findViewById(R.id.bottomNavView);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals("Profiles")) {
                    currentFragment = "PROFILE";
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, profileFragment, "PROFILE").commit();
                } else  if (item.getTitle().equals("Workouts")) {
                    currentFragment = "HOME";
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment,"HOME").commit();
                }

                return true;
            }

        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();

        menuOpenFAB = findViewById(R.id.open_menu_FAB);
        menuOpenFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                if (currentFragment.equals("HOME")) {
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
                } else {
                    popupMenu.getMenuInflater().inflate(R.menu.profile_popup_menu,popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals("New Profile")) {
                                Toast.makeText(getApplicationContext(), "New Profile", Toast.LENGTH_SHORT).show();
                            } else if (item.getTitle().equals("Add a Workout")) {
                                System.out.println("ADD A WORKOUT BOI");
                            } else {
                                System.out.println("Adding MULTIPLE WORKOUTS");

                            }
                            return true;
                        }
                    });
                }
                popupMenu.show();
            }
        });





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

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage("Are you sure you want to delete all workouts?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Utils.getInstance(view.getContext()).deleteAll()) {
                    Toast.makeText(view.getContext(), "Deleted All Workouts", Toast.LENGTH_SHORT).show();
                    //refresh fragment to show deleted views
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
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