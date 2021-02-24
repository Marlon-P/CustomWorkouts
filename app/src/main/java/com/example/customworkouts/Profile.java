package com.example.customworkouts;

import java.util.ArrayList;

public class Profile {

    /*adapt this to the profile fragment after pressing the play button in the profile fragment, the timers in the workouts in the frist group
    * will go off and then alternate between the workouts until the sets reach 0 and and the timer goes off then play the timer
    * for the next workouts group in the groups list*/
    private ArrayList<WorkoutGroup> groups;

    public Profile() {

    }


    public ArrayList<WorkoutGroup>getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<WorkoutGroup> groups) {
        this.groups = groups;
    }

    public void add(WorkoutGroup wg) {
        groups.add(wg);
    }

    public void remove(WorkoutGroup wg) {
        groups.remove(wg);
    }
}
