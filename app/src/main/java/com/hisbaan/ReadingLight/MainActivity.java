package com.hisbaan.ReadingLight;

import android.content.Intent;
import android.os.Bundle;
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

        fab.setClickable(true);
        fab.setFocusable(true);

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
                String message = "You just clicked the sleep timer button. This will be coming in the future";
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                snackbar.show();
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
    }

    //Opening animation for FAB shelf.
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
