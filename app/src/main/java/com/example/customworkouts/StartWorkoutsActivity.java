package com.example.customworkouts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StartWorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_workouts);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ConstraintLayout constraintLayout = findViewById(R.id.startWorkoutsBG);
        TextView countDown = findViewById(R.id.startWorkoutsTxtView);
        TextView workoutTitle = findViewById(R.id.startWorkoutTitle);

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished /1000;
                countDown.setText("" + seconds);

            }

            public void onFinish() {

                Intent intent = getIntent();
                ArrayList<WorkoutGroup> wgs = intent.getParcelableArrayListExtra("profile");
                ArrayList<WorkoutGroup> copy = new ArrayList<>(wgs);


                WorkoutGroup wg = copy.get(0);

                ArrayList<Workout> w = wg.getWorkouts();
                while (!w.isEmpty()) {

                    for (int i = 0; i < w.size(); i++) {

                        Workout workout = w.get(i);
                        constraintLayout.setBackgroundColor(Color.parseColor("#00FF00"));
                        workoutTitle.setVisibility(View.VISIBLE);
                        workoutTitle.setText(workout.getExerciseName());

                        workout.setSets(workout.getSets() - 1);
                        long duration = getMilli(workout.getWorkoutDurationMinutes(), workout.getWorkoutDurationSeconds());
                        long restTime = getMilli(workout.getMinutes(), workout.getSeconds());

                        countDown.setTextColor(Color.parseColor("#FFFFFF"));
                        new CountDownTimer(duration, 1000) {

                            /**
                             * Callback fired on regular interval.
                             *
                             * @param millisUntilFinished The amount of time until finished.
                             */
                            @Override
                            public void onTick(long millisUntilFinished) {

                                 String ms = String.format(
                                        Locale.US, "%02d:%02d",
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                                 countDown.setText(ms);
                            }

                            /**
                             * Callback fired when the time is up.
                             */
                            @Override
                            public void onFinish() {
                                constraintLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                                workoutTitle.setText("REST TIME");
                                new CountDownTimer(restTime, 1000) {

                                    /**
                                     * Callback fired on regular interval.
                                     *
                                     * @param millisUntilFinished The amount of time until finished.
                                     */
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String ms = String.format(
                                                Locale.US, "%02d:%02d",
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                                        countDown.setText(ms);
                                    }

                                    /**
                                     * Callback fired when the time is up.
                                     */
                                    @Override
                                    public void onFinish() {

                                    }
                                }.start();
                            }
                        }.start();



                        if (workout.getSets() <= 0) {
                            w.remove(workout);
                        }
                    }

                }

            }

        }.start();


    }

    private long getMilli(int mins, int secs) {


        mins *= 60;
        mins += secs;
        mins *= 1000;

        return (long) mins;
    }

}
