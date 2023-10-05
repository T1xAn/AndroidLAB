package com.example.lab1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
public class List extends Activity {
    int counter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listbuba);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        counter = 0;
        TextView TextCounter = findViewById(R.id.MainName);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                TextCounter.setText(String.valueOf(counter));
            }
        });

        Button buttonDel = findViewById(R.id.buttonRemove);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                TextCounter.setText(String.valueOf(counter));
            }
        });

    }
}

