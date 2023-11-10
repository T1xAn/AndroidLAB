package com.example.lab1;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.os.Message;
import android.os.Handler;
import com.example.lab1.DB;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ThreadTask {

    Handler thr_handler;
    DB Database;
    final Message message = Message.obtain();

    ThreadTask(Handler main_handler){
        this.thr_handler = main_handler;
    }

    public void TryLogIn(DB _Database, DB.User user) {
        Database = _Database;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Database.dbh.CheckLogin(user);

                message.sendingUid = 1;
                if(Database.dbh.CheckLogin(user))
                message.obj = TRUE;
                else message.obj = user;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void TryAddUser(DB _Database, DB.User user) {
        Database = _Database;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Database.dbh.CheckLogin(user);

                message.sendingUid = 11;
                if(Database.dbh.addUser(user))
                    message.obj = TRUE;
                else message.obj = FALSE;
                thr_handler.sendMessage(message);
            }
        }).start();
    }
    public void TryDelete(DB _Database, DB.User user) {
        Database = _Database;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Database.dbh.CheckLogin(user);

                message.sendingUid = 2;
                if(Database.dbh.CheckDelete(user))
                    message.obj = TRUE;
                else message.obj = FALSE;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void TryChangePasswd(DB _Database, DB.User user) {
        Database = _Database;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Database.dbh.CheckLogin(user);

                message.sendingUid = 3;
                if(Database.dbh.ChangePass(user))
                    message.obj = TRUE;
                else message.obj = FALSE;
                thr_handler.sendMessage(message);
            }
        }).start();
    }
}
