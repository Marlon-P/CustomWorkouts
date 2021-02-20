package com.example.customworkouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutRecyclerViewAdapter adapter = new WorkoutRecyclerViewAdapter();
    private Utils utils;
    private Context context = getActivity();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("CREATING FRAGMENT");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("INFLATING FRAGMENT");
        View view = inflater.inflate(R.layout.workout_container_fragment, container, false);



        recyclerView = view.findViewById(R.id.workoutsRecyclerView);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        utils = Utils.getInstance(recyclerView.getContext(), adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {





            //swap two workouts with each other
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int d_pos = dragged.getAdapterPosition();
                int t_pos = target.getAdapterPosition();

                utils.swap(d_pos, t_pos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }


        });

        helper.attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        adapter.setWorkouts(Utils.getInstance(context).getWorkoutsList());
        return view;
    }


    public void update(FragmentManager fmg) {
        if (fmg == null) {
            System.out.println("NULL AS FUCK");
        } else {
           fmg.beginTransaction()
                   .detach(this)
                   .attach(this)
                   .commit();
        }
    }
}
