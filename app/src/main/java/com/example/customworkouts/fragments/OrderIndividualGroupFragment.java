package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.WorkoutGroup;
import com.example.customworkouts.adapters.OrderGroupsAdapter;
import com.example.customworkouts.adapters.WorkoutGroupRecyclerViewAdapter;

import java.util.ArrayList;

/*After grouping all the workouts into their respective groups
* if a user wants to change the order of a specific group this is where
* they are prompted to do so*/
public class OrderIndividualGroupFragment extends DialogFragment {


    private WorkoutGroupRecyclerViewAdapter adapter;
    public static final String TAG = "ORDER INDIVIDUAL GROUP FRAGMENT";


    public void setAdapter(WorkoutGroupRecyclerViewAdapter a) {
        adapter = a;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.order_workouts_layout, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Order Workout Group");


        RecyclerView allWg = view.findViewById(R.id.orderGroupsRecyclerView);
        allWg.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        allWg.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(allWg.getContext(),
                linearLayoutManager.getOrientation());

        allWg.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            //swap two workouts with each other
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int d_pos = dragged.getAdapterPosition();
                int t_pos = target.getAdapterPosition();

                adapter.swap(d_pos, t_pos);
                adapter.notifyItemMoved(d_pos, t_pos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        helper.attachToRecyclerView(allWg);
        builder.setView(view);
        builder.setCustomTitle(titleView);

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        return builder.create();
    }
}
