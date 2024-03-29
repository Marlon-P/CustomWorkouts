package com.example.customworkouts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.customworkouts.fragments.CreateProfileFragment;
import com.example.customworkouts.fragments.CreateWorkoutFragment;
import com.example.customworkouts.fragments.GroupWorkoutsFragment;
import com.example.customworkouts.fragments.HomeFragment;
import com.example.customworkouts.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    //TODO replace click to edit with swipe to delete
    //TODO make tts of exercise name play immediately after the timer runs out
    //TODO make timers work with orientation changes
    //TODO fix issue of edit workout dialog being cropped


    private FloatingActionButton menuOpenFAB;
    private BottomNavigationView bottomNavView;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private String currentFragment = "HOME";
    public static FragmentManager fgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fgm = getSupportFragmentManager();
        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();


        bottomNavView = findViewById(R.id.bottomNavView);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals("Profiles")) {
                    currentFragment = "PROFILE";
                    fgm.beginTransaction().replace(R.id.fragmentContainer, profileFragment, "PROFILE").commit();
                } else  if (item.getTitle().equals("Workouts")) {
                    currentFragment = "HOME";
                    fgm.beginTransaction().replace(R.id.fragmentContainer, homeFragment,"HOME").commit();
                } else {
                        Data.getInstance(MainActivity.this).populate();
                        homeFragment.setWorkouts();

                }

                return true;
            }

        });


        fgm.beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();

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
                                CreateProfileFragment createProfileFragment = new CreateProfileFragment();
                                createProfileFragment.show(fgm, CreateProfileFragment.TAG);
                            } else if (item.getTitle().equals("Delete All")) {
                                deleteAllProfiles(v);
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

    public void deleteAllProfiles(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage("Are you sure you want to delete all profiles?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Data.getInstance(view.getContext()).deleteAllProfiles()) {
                    Toast.makeText(view.getContext(), "Deleted All Profiles", Toast.LENGTH_SHORT).show();
                    //refresh fragment to show deleted views
                    fgm.beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
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

    //deletes all workouts from the list of workouts in the homepage
    public void deleteAll(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage("Are you sure you want to delete all workouts?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Data.getInstance(view.getContext()).deleteAll()) {
                    Toast.makeText(view.getContext(), "Deleted All Workouts", Toast.LENGTH_SHORT).show();
                    //refresh fragment to show deleted views
                   fgm.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
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


    public static void dismissAllDialogs(FragmentManager manager) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment instanceof DialogFragment) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismissAllowingStateLoss();
            }

            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            dismissAllDialogs(childFragmentManager);
        }
    }




}