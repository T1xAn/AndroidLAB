package com.example.lab1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
public class lb1 extends Activity {
    int counter;
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
                counter++;
                TextCounter.setText(String.valueOf(counter));
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                TextCounter.setText(String.valueOf(counter));
            }
        });

    }
}

