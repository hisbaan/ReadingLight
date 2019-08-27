package com.hisbaan.ReadingLight;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class SleepTimer extends AppCompatActivity {

    private static long startTimeInMillis = 600000;

    private TextView textViewCountdown;
    private Button buttonStartPause;
    private Button buttonReset;

    com.hisbaan.ReadingLight.CountDownTimer service = new com.hisbaan.ReadingLight.CountDownTimer();

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = startTimeInMillis;
    private long endTime;


    static int timeRemaining;

    Button plus1;
    Button plus5;
    Button plus10;
    Button minus1;
    Button minus5;
    Button minus10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_timer);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //keep screen on and keep screen off flags respectively.
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        textViewCountdown = findViewById(R.id.text_view_countdown);
        buttonStartPause = findViewById(R.id.button_start_pause);
        buttonReset = findViewById(R.id.button_reset);

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountdownText();

        //Button to add 1, 5, and 10 minutes. Also buttons to remove the same amount of time.
//        plus1 = findViewById(R.id.plus1);
//        plus1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view){
//                timeRemaining += 1;
//            }
//        });
//
//        plus5 = findViewById(R.id.plus5);
//        plus5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view){
//                timeRemaining += 5;
//            }
//        });
//
//        plus10 = findViewById(R.id.plus10);
//        plus10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view){
//                timeRemaining += 10;
//            }
//        });
//
//        minus1 = findViewById(R.id.minus1);
//        minus1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timeRemaining -= 1;
//            }
//        });
//
//        minus5 = findViewById(R.id.minus5);
//        minus5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timeRemaining -= 5;
//            }
//        });
//
//        minus10 = findViewById(R.id.minus10);
//        minus10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timeRemaining -= 10;
//            }
//        });
        //timeRemaining counts down. when it does, the keep screen on flag is turned off so the screen will turn off on its own.
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                updateButtons();
            }
        };

        countDownTimer.start();
        timerRunning = true;
        updateButtons();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        updateButtons();

    }

    private void resetTimer() {
        timeLeftInMillis = startTimeInMillis;
        updateCountdownText();
        updateButtons();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        textViewCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    private void updateButtons() {
        if (timerRunning) {
            buttonReset.setVisibility(View.INVISIBLE);
            buttonStartPause.setText("pause");
        } else {
            buttonStartPause.setText("start");

            if (timeLeftInMillis < 1000) {
                buttonStartPause.setVisibility(View.INVISIBLE);
            } else {
                buttonStartPause.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis < startTimeInMillis) {
                buttonReset.setVisibility(View.VISIBLE);
            } else {
                buttonReset.setVisibility(View.INVISIBLE);
            }
        }
    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putLong("millisLeft", timeLeftInMillis);
//        outState.putBoolean("timerRunning", timerRunning);
//        outState.putLong("endTime", endTime);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        timeLeftInMillis = savedInstanceState.getLong("millisLeft");
//        timerRunning = savedInstanceState.getBoolean("timerRunning");
//        updateCountdownText();
//        updateButtons();
//
//        if(timerRunning) {
//            endTime = savedInstanceState.getLong("endTime");
//            timeLeftInMillis = endTime - System.currentTimeMillis();
//            startTimer();
//        }
//    }


}
