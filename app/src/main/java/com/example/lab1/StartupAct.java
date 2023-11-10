package com.example.lab1;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.lab1.DB;
import java.util.ArrayList;
import java.util.Collections;

public class StartupAct extends Activity {
    TextView Login;
    TextView Passwd;

    DB Database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Database = new DB(db, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startbuba);
        Button EnterB = findViewById(R.id.EnterBuba);
        EnterB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickEnter(v);
            }
        });

        Button EnterDel = findViewById(R.id.DeleteUser);
        EnterDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickDelete(v);
            }
        });

        Button EnterChange = findViewById(R.id.ChangePass);
        EnterChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickСhange(v);
            }
        });

        Login = findViewById(R.id.LoginBuba);
        Passwd = findViewById(R.id.PasswordBuba);

    }

    final Looper looper = Looper.getMainLooper();
    final Message message = Message.obtain();

    final Handler handler = new Handler(looper) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.sendingUid == 1) {
                if(msg.obj == TRUE){
                    StartIntent(FALSE);
                }
                else
                {
                    new ThreadTask(handler).TryAddUser(Database,(DB.User) msg.obj);
                }
            }
            if(msg.sendingUid == 11){
                if(msg.obj == TRUE){
                    StartIntent(TRUE);
                }
                else
                {
                    WrongUserToast();
                }

            }

            if (msg.sendingUid == 2) {
                if(msg.obj == TRUE){
                    UserDeleted();
                }
                else
                {
                    WrongUser();
                }
            }
            if (msg.sendingUid == 3) {
                if(msg.obj == TRUE){
                    PasswordChanged();
                }
                else
                {
                    WrongUser();
                }
            }
        }
    };

    public void WrongUserToast(){
        Toast.makeText(this, "Введён неверный пароль", Toast.LENGTH_SHORT).show();
    }
    public void WrongUser(){
        Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_SHORT).show();
    }
    public void UserDeleted(){
        Toast.makeText(this, "Пользователь удалён", Toast.LENGTH_SHORT).show();
    }
    public void PasswordChanged(){
        Toast.makeText(this, "Пароль изменён", Toast.LENGTH_SHORT).show();
    }
    public void StartIntent(Boolean newUser){
        if(newUser)
            Toast.makeText(this, "Новый пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_SHORT).show();
        String user = Login.getText().toString();
        Intent intent = new Intent(this, List.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
//    public Boolean TryCheckLogin(DB.User user){
//            if(!Database.dbh.CheckLogin(user)){
//                if(Database.dbh.addUser(user)){
//                    Toast.makeText(this, "Новый пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
//                    return TRUE;
//                }
//                Toast.makeText(this, "Введён неверный пароль", Toast.LENGTH_SHORT).show();
//                return FALSE;
//
//            }
//        Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_SHORT).show();
//        return TRUE;
//    }
    public void onClickEnter(View v) {
        String user = Login.getText().toString();
        String password = Passwd.getText().toString();
        DB.User usera = new DB.User(user,password);

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "CHTOB PUSTO TEBE BILO", Toast.LENGTH_SHORT).show();
            return;
        }
        new ThreadTask(handler).TryLogIn(Database,usera);
//       if(TryCheckLogin(usera)) {
//           Intent intent = new Intent(this, List.class);
//           intent.putExtra("user", user);
//           startActivity(intent);
//       }
    }

    public void onClickDelete(View v) {
        String user = Login.getText().toString();
        String password = Passwd.getText().toString();
        DB.User usera = new DB.User(user,password);

        if (user.isEmpty()) {
            Toast.makeText(this, "Укажите логин поьзователя для удаления", Toast.LENGTH_SHORT).show();
            return;
        }
        new ThreadTask(handler).TryDelete(Database,usera);
//        if(Database.dbh.CheckDelete(usera)) {
//            //Intent intent = new Intent(this, List.class);
//            //intent.putExtra("user", user);
//            //startActivity(intent);
//            Toast.makeText(this, "Пользователь удалён", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_SHORT).show();

    }

    public void onClickСhange(View v) {
        String user = Login.getText().toString();
        String password = Passwd.getText().toString();
        DB.User usera = new DB.User(user,password);

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Укажите логин пользователя и его новый пароль", Toast.LENGTH_SHORT).show();
            return;
        }
        new ThreadTask(handler).TryChangePasswd(Database,usera);
//        if(Database.dbh.ChangePass(usera)) {
//            Toast.makeText(this, "Пароль изменён", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDestroy()");
    }

}

