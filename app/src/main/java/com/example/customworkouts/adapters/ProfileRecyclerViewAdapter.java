package com.example.customworkouts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.Profile;
import com.example.customworkouts.R;
import com.example.customworkouts.SwipeToDeleteCallBack;
import com.example.customworkouts.Utils;
import com.example.customworkouts.Workout;
import com.example.customworkouts.WorkoutGroup;

import java.util.ArrayList;

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Profile> workoutSet;
    private Utils profileUtils;

    public void setWorkouts( ArrayList<Profile> workouts) {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        Profile p = workoutSet.get(position);

        ArrayList<Workout> wg =p.getAllWorkouts();

        adapter.setWorkouts(p.getProfileName(), wg);
        holder.profileName.setText(p.getProfileName());
        holder.cardRecyclerView.setAdapter(adapter);
        holder.cardRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.cardRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        holder.cardRecyclerView.addItemDecoration(dividerItemDecoration);



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
