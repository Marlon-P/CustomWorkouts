package com.example.customworkouts.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.MainActivity;
import com.example.customworkouts.Profile;
import com.example.customworkouts.R;
import com.example.customworkouts.Data;
import com.example.customworkouts.Workout;
import com.example.customworkouts.WorkoutGroup;
import com.example.customworkouts.adapters.OrderGroupsAdapter;
import com.example.customworkouts.adapters.WorkoutGroupRecyclerViewAdapter;

import java.util.ArrayList;

public class OrderGroupsFragment extends DialogFragment {

    private ArrayList<WorkoutGroup> workoutGroups;
    public static final String TAG = "ORDER GROUP FRAGMENT";
    private OrderGroupsAdapter adapter = new OrderGroupsAdapter();
    private String profileName;


    public void setWorkoutGroup(String name, ArrayList<WorkoutGroup> wg) {
        profileName = name;
        workoutGroups = wg;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.order_workouts_layout, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Order Workouts");


        adapter.setGroups(workoutGroups);

        RecyclerView allWg = view.findViewById(R.id.orderGroupsRecyclerView);
        allWg.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        allWg.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(allWg.getContext(),
                linearLayoutManager.getOrientation());

        allWg.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {


            private Drawable icon = getContext().getDrawable(R.drawable.ic_edit);
            private  ColorDrawable background = new ColorDrawable(Color.BLACK);

            //swap two workouts with each other
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int d_pos = dragged.getAdapterPosition();
                int t_pos = target.getAdapterPosition();

                adapter.swap(d_pos, t_pos);

                return true;
            }


            @Override
            public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX,
                        dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 10;

                if (dX > 0) { // Swiping to the right
                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                            itemView.getBottom());

                } else if (dX < 0) { // Swiping to the left
                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX > 0) { // Swiping to the right
                    int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    int iconRight = itemView.getLeft() + iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                            itemView.getBottom());
                } else if (dX < 0) { // Swiping to the left
                    int iconLeft = itemView.getRight() - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - 5;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE   ) {


                    icon.draw(c);
                }
            }


            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return 0f;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemChanged(position);

                WorkoutGroupRecyclerViewAdapter wgAdapter = new WorkoutGroupRecyclerViewAdapter();
                wgAdapter.setGroup(adapter.getGroups().get(position));


                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                OrderIndividualGroupFragment fragment = new OrderIndividualGroupFragment();
                fragment.setArguments(bundle);
                fragment.setAdapter(wgAdapter);
                fragment.show(getParentFragmentManager(), OrderIndividualGroupFragment.TAG);

            }
        });

        helper.attachToRecyclerView(allWg);
        builder.setView(view);
        builder.setCustomTitle(titleView);

        Bundle b = getArguments();
        boolean edit = b.getBoolean("edit");
        String newName = b.getString("profileName");
        if (edit) {
            builder.setPositiveButton("Finish Editing", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Profile p = new Profile(newName, adapter.getGroups());
                    Data.getInstance(getContext()).updateProfile(profileName, newName, p);
                    MainActivity.dismissAllDialogs(getParentFragmentManager());
                    MainActivity.fgm.beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment(), "PROFILE").commit();
                }
            });
        } else {
            builder.setPositiveButton("Create Profile", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Data.getInstance(getContext()).createProfile(profileName, adapter.getGroups());
                    MainActivity.dismissAllDialogs(getParentFragmentManager());
                    MainActivity.fgm.beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment(), "PROFILE").commit();
                }
            });
        }

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        return builder.create();
    }

    public void setWorkoutGroup(int pos, WorkoutGroup wg) {
        adapter.getGroups().set(pos, wg);
        adapter.notifyItemChanged(pos);
    }
}
