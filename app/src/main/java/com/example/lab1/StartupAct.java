package com.example.lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class StartupAct extends Activity {
    TextView Login;
    TextView Passwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.startbuba);
        Button EnterB = findViewById(R.id.EnterBuba);
        Login = findViewById(R.id.LoginBuba);
        Passwd = findViewById(R.id.PasswordBuba);

    }

    public void onClick(View v) {
        String user = Login.getText().toString();
        String password = Passwd.getText().toString();

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "CHTOB PUSTO TEBE BILO", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, List.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }
}

