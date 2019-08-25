package com.hisbaan.ReadingLight;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    static CoordinatorLayout coordinatorLayout;

    FloatingActionButton fab;
    FloatingActionButton palletFAB;
    FloatingActionButton timerFAB;
    FloatingActionButton settingsFAB;

    SeekBar brightnessBar;
    Boolean success = false;

    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        timerFAB = findViewById(R.id.timerFAB);
        palletFAB = findViewById(R.id.palletFAB);
        settingsFAB = findViewById(R.id.settingsFAB);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        brightnessBar = findViewById(R.id.brightnessBar);
        brightnessBar.setMax(255);
        brightnessBar.setProgress(getBrightness());

        getPermission();

        //turn autobrighness off or tell the user to
        //if you do turn it off, inform the user that auto brightness has been turned off so that they know using a snackbar where they can then undo.

        ContentResolver contentResolver = getApplicationContext().getContentResolver();

        if (Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            Toast.makeText(this, "Auto-Brightness has been turned off as this causes problems with the app.", Toast.LENGTH_LONG).show();
        }

        //Animate opening and closing of FAB shelf.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        //Open sleep timer activity.
        timerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SleepTimer.class);
                startActivity(i);
            }
        });

        //Open colour picker activity.
        palletFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ColourPicker.class);
                startActivity(i);
            }
        });

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && success) {
                    setBrightness(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!success) {
                    Toast.makeText(MainActivity.this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setBrightness(int brightness) {
        if (brightness < 0) brightness = 0;
        else if (brightness > 255) brightness = 0;

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

    private int getBrightness() {
        int brightness = 0;

//        try {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }

        return brightness;
    }

    private void getPermission() {
        boolean value;
        value = Settings.System.canWrite(getApplicationContext());
        if (value) {
            success = true;
        } else {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivityForResult(intent, 1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000) {
            boolean value = Settings.System.canWrite(getApplicationContext());
            if (value) {
                success = true;
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void showFABMenu() {
        isFABOpen = true;
        fab.animate().rotation(45);
        palletFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        timerFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        settingsFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    //Closing animation for FAB shelf.
    private void closeFABMenu() {
        isFABOpen = false;
        fab.animate().rotation(0);
        palletFAB.animate().translationY(0);
        timerFAB.animate().translationY(0);
        settingsFAB.animate().translationY(0);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
