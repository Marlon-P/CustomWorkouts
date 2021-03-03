package com.example.customworkouts;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

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
                System.out.println(seconds);
            }

            public void onFinish() {
                countDown.setText("GO!");
            }

        }.start();

        Intent intent = getIntent();
        ArrayList<WorkoutGroup> wgs = intent.getParcelableArrayListExtra("profile");
        for (WorkoutGroup wg : wgs) {
            for (Workout w : wg.getWorkouts()) {

            }
        }
    }

}
