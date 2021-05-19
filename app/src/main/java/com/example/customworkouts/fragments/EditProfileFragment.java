package com.example.customworkouts.fragments;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.MainActivity;
import com.example.customworkouts.R;
import com.example.customworkouts.Data;
import com.example.customworkouts.Workout;
import com.example.customworkouts.adapters.ColorsAdapter;
import com.example.customworkouts.adapters.CreateProfileRecyclerViewAdapter;
import com.example.customworkouts.adapters.GroupWorkoutsAdapter;

import java.util.ArrayList;

public class EditProfileFragment extends DialogFragment {

    public static final String TAG = "Edit Profile Fragment";
    private String profileName;
    private ArrayList<Workout> wg;

    public void setParams(String name, ArrayList<Workout> w) {
        profileName = name;
        wg = w;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_workout_profile, null);
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView title = titleView.findViewById(R.id.dialog_title);
        title.setText("Edit Profile");

        RecyclerView recyclerView = view.findViewById(R.id.editProfileRecyclerView);
        RecyclerView colorsRecyclerView = view.findViewById(R.id.editProfileColorsRecView);

        ColorsAdapter colorsAdapter = new ColorsAdapter();
        GroupWorkoutsAdapter groupWorkoutsAdapter = new GroupWorkoutsAdapter();
        groupWorkoutsAdapter.setWorkouts(wg);
        colorsAdapter.setAdapter(groupWorkoutsAdapter);


        LinearLayoutManager llm = new LinearLayoutManager(context);
        GridLayoutManager glm = new GridLayoutManager(context, 4);

        colorsRecyclerView.setAdapter(colorsAdapter);
        colorsRecyclerView.setLayoutManager(glm);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(groupWorkoutsAdapter);
        builder.setCustomTitle(titleView);
        builder.setView(view);

        //swipe to delete feature
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            private Drawable icon = context.getDrawable(R.drawable.ic_delete);
            private ColorDrawable background = new ColorDrawable(Color.RED);

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                return true;
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
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {


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
                groupWorkoutsAdapter.notifyItemChanged(position);

                AlertDialog.Builder builder = new AlertDialog.Builder((Activity) context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                builder.setMessage("Are you sure you want to delete this workout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        groupWorkoutsAdapter.getWorkouts().remove(position);
                        groupWorkoutsAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create();
                builder.show();
            }
        });

        helper.attachToRecyclerView(recyclerView);


        EditText currProfileName = view.findViewById(R.id.editProfileName);
        currProfileName.setText(profileName);

        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Add Workout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button addBtn = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                Button nxtBtn = dialog.getButton(Dialog.BUTTON_POSITIVE);

                nxtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putBoolean("edit", true);

                        String newName = currProfileName.getText().toString();

                        System.out.println("oldName: " + profileName);
                        System.out.println("newName: " + newName);
                        args.putString("profileName", newName);

                        OrderGroupsFragment fragment = new OrderGroupsFragment();
                        fragment.setArguments(args);
                        fragment.setWorkoutGroup(profileName, groupWorkoutsAdapter.createWorkoutGroups());
                        fragment.show(MainActivity.fgm, OrderGroupsFragment.TAG);
                    }
                });
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder bilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                        LayoutInflater inflater = getActivity().getLayoutInflater();

                        View addWorkoutsView = inflater.inflate(R.layout.edit_profile_add_workouts_layout, null);
                        View addWorkoutsViewTitle = inflater.inflate(R.layout.dialog_title, null);
                        TextView tit = addWorkoutsViewTitle.findViewById(R.id.dialog_title);
                        tit.setText("Select Workouts to Add");

                        RecyclerView recyclerView = addWorkoutsView.findViewById(R.id.editProfileAddRecyclerView);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        CreateProfileRecyclerViewAdapter createAdapter = new CreateProfileRecyclerViewAdapter();
                        createAdapter.setWorkouts(Data.getInstance(context).getWorkoutsList());
                        recyclerView.setAdapter(createAdapter);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                linearLayoutManager.getOrientation());
                        recyclerView.addItemDecoration(dividerItemDecoration);


                        bilder.setCustomTitle(addWorkoutsViewTitle);
                        bilder.setView(addWorkoutsView);

                        bilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wg.addAll(createAdapter.getWorkouts());
                                for (Workout w : wg) {
                                    System.out.println(w.getExerciseName());
                                }
                                groupWorkoutsAdapter.setWorkouts(wg);

                            }
                        });

                        bilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog d = bilder.create();

                        d.show();

                    }
                });
            }
        });
       return dialog;
    }
}
