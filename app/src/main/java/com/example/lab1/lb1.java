package com.example.lab1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

public class lb1 extends Activity {
    int counter;
    float birit = 0F;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lb1layout);
        Button button1 = findViewById(R.id.button1);
        counter = 0;

        TextView TextCounter = findViewById(R.id.Counter);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = birit;
                getWindow().setAttributes(layoutParams);
                birit +=0.1F;
                if(birit > 1)
                    birit = 0;
                int sysBackLightValue = (int) (birit * 255);
                //String a = android.provider.Settings.System.SCREEN_BRIGHTNESS;
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        sysBackLightValue);
                counter++;
                TextCounter.setText(String.valueOf(counter));
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                v.getRootView().setBackgroundColor(color);
                TextCounter.setText(String.valueOf(counter));
            }
        });

    }
}

