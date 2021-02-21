package com.example.customworkouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ArrayList<Workout>> workoutSet;
    private Utils profileUtils;

    public void setWorkouts( ArrayList<ArrayList<Workout>> workouts) {
        this.workoutSet = workouts;
    }

    public void setProfileUtils(Utils profileUtils) {
        this.profileUtils = profileUtils;
    }

    @NonNull
    @Override
    public ProfileRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_profile_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecyclerViewAdapter.ViewHolder holder, int position) {


        CardProfileRecyclerViewAdapter adapter = new CardProfileRecyclerViewAdapter();



    }

    @Override
    public int getItemCount() {
        return workoutSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private TextView profileName;
        private RecyclerView cardRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.profileCardView);
            profileName = itemView.findViewById(R.id.profileCardName);
            cardRecyclerView = itemView.findViewById(R.id.cardProfileRecyclerView);
        }
    }
}
