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
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StartWorkoutsActivity extends AppCompatActivity {


    public int currentWorkoutGroupPos = 0;
    public int currentWorkoutPos = 0;
    public ArrayList<WorkoutGroup> groups;
    public ArrayList<Workout> currentWorkoutsList;
    public WorkoutGroup currentWorkoutGroup;
    public Workout currentWorkout;

    ConstraintLayout constraintLayout;
    TextView countDown;
    TextView workoutTitle;

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


        constraintLayout = findViewById(R.id.startWorkoutsBG);
        countDown = findViewById(R.id.startWorkoutsTxtView);
        workoutTitle = findViewById(R.id.startWorkoutTitle);


        new CountDownTimer(6000, 100) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished /1000;
                countDown.setText("" + seconds);

            }

            public void onFinish() {

                Intent intent = getIntent();
                ArrayList<WorkoutGroup> wgs = intent.getParcelableArrayListExtra("profile");
                groups = new ArrayList<>(wgs);


                currentWorkoutGroup = groups.get(currentWorkoutGroupPos);

                currentWorkoutsList = currentWorkoutGroup.getWorkouts();


                currentWorkout =  currentWorkoutsList.get(currentWorkoutPos);
                System.out.println(currentWorkout.getExerciseName());
                constraintLayout.setBackgroundColor(Color.parseColor("#00FF00"));
                workoutTitle.setVisibility(View.VISIBLE);
                workoutTitle.setText(currentWorkout.getExerciseName());




                long duration = getMilli( currentWorkout.getWorkoutDurationMinutes(),  currentWorkout.getWorkoutDurationSeconds());


                countDown.setTextColor(Color.parseColor("#FFFFFF"));

                CountDownTimer start = startTimer(duration);

                start.start();
            }

        }.start();


    }

    private long getMilli(int mins, int secs) {
        mins *= 60;
        mins += secs + 1; //timer takes 1 second to load so + 1 to get back to OG time
        mins *= 1000;

        return 3000;
    }

    public CountDownTimer restTimer(long restTime) {
        return new CountDownTimer(restTime,100) {
            @Override
            public void onTick(long millis) {

                String ms = String.format(Locale.US,"%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );
                countDown.setText(ms);
            }

            @Override
            public void onFinish() {

                if (currentWorkoutGroupPos >= groups.size()) {
                    constraintLayout.setBackgroundColor(Color.parseColor("#0000FF"));
                    workoutTitle.setText("Good Job ");
                    countDown.setText("Workout Done!");
                    return;
                } else {
                    currentWorkoutGroup = groups.get(currentWorkoutGroupPos);
                }

                constraintLayout.setBackgroundColor(Color.parseColor("#00FF00"));
                currentWorkout = currentWorkoutGroup.getWorkout(currentWorkoutPos);
                workoutTitle.setText(currentWorkout.getExerciseName());

                int mins = currentWorkout.getWorkoutDurationMinutes();
                int sec = currentWorkout.getWorkoutDurationSeconds();

                long duration = getMilli(mins, sec);

                startTimer(duration).start();

            }
        };
    }

    private CountDownTimer startTimer(long duration) {
    return  new CountDownTimer(duration, 100) {



            @Override
            public void onTick(long millis) {


                String ms = String.format(Locale.US,"%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );

                countDown.setText(ms);

            }


            @Override
            public void onFinish() {
                currentWorkout.setSets(currentWorkout.getSets() - 1);
                constraintLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                workoutTitle.setText("REST TIME");

                int mins =  currentWorkout.getMinutes();
                int sec = currentWorkout.getSeconds();

                long restTime = getMilli(mins, sec);
                restTimer(restTime).start();
                currentWorkoutPos++; // prepare to go to the next workout

                if (currentWorkout.getSets() <= 0) {//to know when all sets are complete, decrement sets, if a workout reaches 0 rmeove it
                    currentWorkoutPos--;
                    System.out.println("REMOVING");
                    currentWorkoutGroup.remove(currentWorkout);

                    if (!currentWorkoutGroup.getWorkouts().isEmpty()) {
                        currentWorkout = currentWorkoutGroup.getWorkout(currentWorkoutPos);
                    }
                }

                if (currentWorkoutPos >= currentWorkoutGroup.getWorkouts().size() ) {
                    if (currentWorkoutGroup.getWorkouts().isEmpty()) {
                        currentWorkoutPos = 0;
                        currentWorkoutGroupPos++;

                    } else {
                        currentWorkoutPos = 0;

                    }
                }

            }
        };


    }





}
