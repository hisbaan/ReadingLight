package com.hisbaan.ReadingLight;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class SleepTimer extends AppCompatActivity {
    private EditText editTextInput;
    private TextView textViewCountdown;
    private Button buttonSet;
    private Button buttonStartPause;
    private Button buttonReset;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis;
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

        editTextInput = findViewById(R.id.edit_text_input);
        textViewCountdown = findViewById(R.id.text_view_countdown);

        buttonSet = findViewById(R.id.button_set);
        buttonStartPause = findViewById(R.id.button_start_pause);
        buttonReset = findViewById(R.id.button_reset);

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(SleepTimer.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(SleepTimer.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
            }
        });

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

    private void setTime(long milliseconds) {
        startTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
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
                updateInterface();
            }
        }.start();

        timerRunning = true;
        updateInterface();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        updateInterface();

    }

    private void resetTimer() {
        timeLeftInMillis = startTimeInMillis;
        updateCountdownText();
        updateInterface();
        countDownTimer.cancel();
    }

    private void updateCountdownText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;

//        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
//        } else {
//            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
//        }

        textViewCountdown.setText(timeLeftFormatted);
    }

    private void updateInterface() {
        if (timerRunning) {
            editTextInput.setVisibility(View.INVISIBLE);
            buttonSet.setVisibility(View.INVISIBLE);
            buttonReset.setVisibility(View.INVISIBLE);
            buttonStartPause.setText("pause");
        } else {
            editTextInput.setVisibility(View.VISIBLE);
            buttonSet.setVisibility(View.VISIBLE);

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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm  = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", startTimeInMillis);
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);

        editor.apply();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        startTimeInMillis = prefs.getLong("startTimeInMillis", 60000);
        timeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning", false);

        updateCountdownText();
        updateInterface();

        if (timerRunning) {
            endTime = prefs.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();
            startTimer();

            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
                timerRunning = false;
                updateCountdownText();
                updateInterface();
            } else {
                startTimer();
            }
        }

    }
}
