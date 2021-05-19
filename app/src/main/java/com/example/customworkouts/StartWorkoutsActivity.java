package com.example.customworkouts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StartWorkoutsActivity extends AppCompatActivity {

    //TODO fix when the phone rotates while pressing play or lock the phone in portrait mode
    int currentWorkoutGroupPos = 0;
    int currentWorkoutPos = 0;
    ArrayList<WorkoutGroup> groups;
    ArrayList<Workout> currentWorkoutsList;
    WorkoutGroup currentWorkoutGroup;
    Workout currentWorkout;
    TextToSpeech tts;

    MediaPlayer AlarmMusic;

    boolean backPressed = false;

    CountDownTimer startTimer;
    CountDownTimer workoutTimer;
    CountDownTimer restTimer;

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

                backPressed = true;

                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                }

                if (startTimer != null) {
                    startTimer.cancel();
                }

                if (workoutTimer != null) {
                    workoutTimer.cancel();
                }

                if (restTimer != null) {
                    restTimer.cancel();
                }



            }
        });

        AlarmMusic = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
        AlarmMusic.setLooping(false);



        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });




        constraintLayout = findViewById(R.id.startWorkoutsBG);
        countDown = findViewById(R.id.startWorkoutsTxtView);
        workoutTitle = findViewById(R.id.startWorkoutTitle);


        startTimer = new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                countDown.setText("" + seconds);
                System.out.println("second: " + seconds);
                if (!backPressed) {
                    playSound();
                }
            }

            public void playSound() {

                Context context = getApplicationContext();
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_DTMF, audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF), AudioManager.FLAG_PLAY_SOUND);

                ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_DTMF, 100); // 100 is max volume
                tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 250); // 500ms

            }

            public void onFinish() {


                Intent intent = getIntent();
                ArrayList<WorkoutGroup> wgs = intent.getParcelableArrayListExtra("profile");
                groups = new ArrayList<>(wgs);


                currentWorkoutGroup = groups.get(currentWorkoutGroupPos);

                currentWorkoutsList = currentWorkoutGroup.getWorkouts();


                currentWorkout = currentWorkoutsList.get(currentWorkoutPos);
                tts.speak(currentWorkout.getExerciseName(), TextToSpeech.QUEUE_FLUSH, null);

                constraintLayout.setBackgroundColor(Color.parseColor("#00FF00"));
                workoutTitle.setVisibility(View.VISIBLE);
                workoutTitle.setText(currentWorkout.getExerciseName());


                long duration = getMilli(currentWorkout.getWorkoutDurationMinutes(), currentWorkout.getWorkoutDurationSeconds());


                countDown.setTextColor(Color.parseColor("#FFFFFF"));

                workoutTimer = startTimer(duration);

                workoutTimer.start();
            }

        };
        startTimer.start();


    }

    private long getMilli(int mins, int secs) {
        mins *= 60;
        mins += secs + 1; //timer takes 1 second to load so + 1 to get back to OG time
        mins *= 1000;

        return mins;
    }

    public CountDownTimer restTimer(long restTime) {
        return new CountDownTimer(restTime,1000) {
            @Override
            public void onTick(long millis) {

                if (backPressed) {return;}

                String ms = String.format(Locale.US,"%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );

                countDown.setText(ms);
                if (!backPressed && millis <= 6000) {
                    playSound();
                }

            }

            public void playSound() {

                Context context = getApplicationContext();
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_DTMF, audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF), AudioManager.FLAG_PLAY_SOUND);

                ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_DTMF, 100); // 100 is max volume
                tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 250); // 500ms

            }

            @Override
            public void onFinish() {
                if (backPressed) {return;}

                if (currentWorkoutGroupPos >= groups.size()) {
                    constraintLayout.setBackgroundColor(Color.parseColor("#0000FF"));
                    workoutTitle.setText("Good Job ");
                    countDown.setText("Workout Done!");

                    if (tts != null) {
                        tts.stop();
                        tts.shutdown();
                    }

                    return;
                } else {
                    currentWorkoutGroup = groups.get(currentWorkoutGroupPos);
                }

                constraintLayout.setBackgroundColor(Color.parseColor("#00FF00"));
                currentWorkout = currentWorkoutGroup.getWorkout(currentWorkoutPos);
                workoutTitle.setText(currentWorkout.getExerciseName());
                tts.speak(currentWorkout.getExerciseName(), TextToSpeech.QUEUE_FLUSH, null);

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
                if (backPressed) {return;}

                String ms = String.format(Locale.US,"%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );

                countDown.setText(ms);

            }


            @Override
            public void onFinish() {

                if (backPressed) {return;}

                currentWorkout.setSets(currentWorkout.getSets() - 1);
                constraintLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                workoutTitle.setText("REST TIME");

                int mins =  currentWorkout.getMinutes();
                int sec = currentWorkout.getSeconds();

                long restTime = getMilli(mins, sec);
                restTimer = restTimer(restTime);
                restTimer.start();
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
