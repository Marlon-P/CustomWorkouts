package com.example.customworkouts.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.Workout;
import com.example.customworkouts.WorkoutGroup;

//adapts the workout_group layout, inflates all the workouts in a workout group
public class WorkoutGroupRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutGroupRecyclerViewAdapter.ViewHolder> {

    private WorkoutGroup group;

    public void setGroup(WorkoutGroup g) {
        group = g;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout w = group.getWorkout(position);

        String exerciseName = w.getExerciseName();

        String setsAndReps = w.getSets() + "x"+ w.getRepetitions();
        int minutes = w.getMinutes();
        int seconds= w.getSeconds();
        String s = "";
        if (seconds < 10) {
            s = "0";
        }
        String time = minutes + ":" + s + seconds;

        String hexColor = String.format("#%06X", (0xFFFFFF & w.getColor()));
        System.out.println(hexColor);
        holder.itemView.setBackgroundColor(Color.parseColor(hexColor));
        holder.exerciseName.setText(exerciseName);
        holder.setsXrepetitions.setText(setsAndReps);
        holder.timer.setText(time);
    }

    @Override
    public int getItemCount() {
        return group.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseName, setsXrepetitions, timer;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            exerciseName = itemView.findViewById(R.id.exercise_name);
            setsXrepetitions = itemView.findViewById(R.id.repetitions);
            timer = itemView.findViewById(R.id.timer);

        }
    }
}
