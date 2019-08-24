package com.hisbaan.ReadingLight;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.os.Bundle;
import com.larswerkman.holocolorpicker.*;

public class ColourPicker extends Activity {

    int c;
    Button select;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_picker);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        ColorPicker picker = findViewById(R.id.picker);
        SVBar svBar = findViewById(R.id.svbar);

        picker.addSVBar(svBar);
        picker.setShowOldCenterColor(false);

        //To get the color
        picker.getColor();

        int colour = Color.TRANSPARENT;
        Drawable background = MainActivity.coordinatorLayout.getBackground();
        if (background instanceof ColorDrawable)
            colour = ((ColorDrawable) background).getColor();
        picker.setColor(colour);

        //Adds listener to the colour picker which is implemented in the activity
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int colour) {
                c = colour;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        select.setTextColor(Color.rgb(Color.red(c), Color.green(c), Color.blue(c)));
                        cancel.setTextColor(Color.rgb(Color.red(c), Color.green(c), Color.blue(c)));
                    }
                }, 0);
            }
        });

        //Choose colour and close activity when select button is pressed.
        select = findViewById(R.id.selectButton);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colour = c;
                MainActivity.coordinatorLayout.setBackgroundColor(Color.rgb(Color.red(colour), Color.green(colour), Color.blue(colour)));
                finish();
            }
        });

        //Do not choose colour and close activity when cancel button is pressed.
        cancel = findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Turns off showing old colour
        picker.setShowOldCenterColor(false);

//        adding onChangeListeners to bars
//        opacitybar.setOnOpacityChangeListener(new OnOpacityChangeListener …)
//        valuebar.setOnValueChangeListener(new OnValueChangeListener …)
//        saturationBar.setOnSaturationChangeListener(new OnSaturationChangeListener …)
    }
}
