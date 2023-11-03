package com.example.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.util.ArrayList;

public class DB {

    SQLiteDatabase db;
   public DatabaseHandler dbh;
    DB(SQLiteDatabase _db, Context context) {
            db = _db;
        dbh = new DatabaseHandler(context);
    }
    public final class DBContract {

        private DBContract() {        }

        public class UserEntry implements BaseColumns {
            public static final String TABLE_NAME = "users";
            public static final String COLUMN_NAME_KEY_ID = "id";
            public static final String COLUMN_NAME_LOGIN = "login";
            public static final String COLUMN_NAME_PASS = "pass";
        }
    }
    public class DatabaseHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "app.db";

        public DatabaseHandler(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + "("
                    + DBContract.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT," + DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";

            db.execSQL(CREATE_USERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
            onCreate(db);
        }

        public Boolean CheckLogin(User user){
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_LOGIN +
                    " = '" + user._login + "' AND " + DBContract.UserEntry.COLUMN_NAME_PASS + " = '" + user._pass + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() <= 0) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }

        public Boolean CheckDelete(User user){
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_LOGIN +
                    " = '" + user._login;
            selectQuery = "delete  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_LOGIN +
                    " = '" + user._login;
            if(db.delete(DBContract.UserEntry.TABLE_NAME,"login=?", new String[]{user._login}) == 0) {
                return Boolean.FALSE;
            }


            return Boolean.TRUE;
        }

        public Boolean ChangePass(User user){
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_LOGIN +
                    " = '" + user._login;
            ContentValues newValues = new ContentValues();
            newValues.put(DBContract.UserEntry.COLUMN_NAME_PASS, user._pass);
            if( db.update(DBContract.UserEntry.TABLE_NAME,newValues,"login=?",new String[]{user._login}) == 0) {
                return Boolean.FALSE;
            }


            return Boolean.TRUE;
        }
        public Boolean addUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_LOGIN + " = '" + user._login + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0) {
                return Boolean.FALSE;

            }
            ContentValues values = new ContentValues();
            values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
            values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

            db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
            db.close();
            return Boolean.TRUE;
        }

        public ArrayList<User> getAllUsers() {
            ArrayList<User> usersList = new ArrayList<User>();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setID(Integer.parseInt(cursor.getString(0)));
                    user.setLogin(cursor.getString(1));
                    user.setPass(cursor.getString(2));
                    usersList.add(user);
                } while (cursor.moveToNext());
            }
            return usersList;
        }

    }

    public static class User {

        int _id;
        String _login;
        String _pass;

        public User(){
        }
        public User(int id, String login, String pass){
            this._id = id;
            this._login = login;
            this._pass = pass;
        }
        public User(String login, String pass){
            this._login = login;
            this._pass = pass;
        }

        public int getID(){
            return this._id;
        }
        public void setID(int id){
            this._id = id;
        }

        public String getLogin(){
            return this._login;
        }
        public void setLogin(String login){
            this._login = login;
        }

        public String getPass(){
            return this._pass;
        }
        public void setPass(String pass){
            this._pass = pass;
        }
    }
}
