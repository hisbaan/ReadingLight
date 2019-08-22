package com.hisbaan.ReadingLight;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        timerFAB = findViewById(R.id.timerFAB);
        palletFAB = findViewById(R.id.palletFAB);
        fab.setClickable(true);
        fab.setFocusable(true);
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

        timerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "You just clicked the sleep timer button. This will be coming in the future";
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }


    FloatingActionButton fab;
    FloatingActionButton palletFAB;
    FloatingActionButton timerFAB;

    boolean isFABOpen = false;

    private void showFABMenu() {
        isFABOpen = true;
        fab.animate().rotation(45);
        palletFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        timerFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab.animate().rotation(0);
        palletFAB.animate().translationY(0);
        timerFAB.animate().translationY(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
