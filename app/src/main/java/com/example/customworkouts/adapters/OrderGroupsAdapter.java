package com.example.customworkouts.adapters;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.SwipeToDeleteCallBack;
import com.example.customworkouts.Workout;
import com.example.customworkouts.WorkoutGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class OrderGroupsAdapter extends RecyclerView.Adapter<OrderGroupsAdapter.ViewHolder> {

    private WorkoutGroupRecyclerViewAdapter adapter;
    private ArrayList<WorkoutGroup> groups;
    public void setGroups(ArrayList<WorkoutGroup>g) {
        groups = g;
    }

    public ArrayList<WorkoutGroup> getGroups() {
        return groups;
    }

    public void swap(int from, int to) {
        Collections.swap(groups, from, to);
        notifyItemMoved(from, to);
    }

    public void swap(int wgPos, int from, int to) {
        WorkoutGroup wg = groups.get(wgPos);
        Collections.swap(wg.getWorkouts(), from, to);
        notifyItemChanged(wgPos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //reusing layout with lone recycler view
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_workouts_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        adapter = new WorkoutGroupRecyclerViewAdapter();
        adapter.setGroup(groups.get(position));

        holder.workoutGroupsRV.setAdapter(adapter);
        holder.workoutGroupsRV.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.workoutGroupsRV.getContext(),
                linearLayoutManager.getOrientation());
        holder.workoutGroupsRV.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView workoutGroupsRV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            workoutGroupsRV = itemView.findViewById(R.id.orderGroupsRecyclerView);
        }
    }
}
