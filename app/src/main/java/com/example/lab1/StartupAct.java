package com.example.lab1;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.lab1.DB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class StartupAct extends Activity {
    TextView Login;
    TextView GpsCord;
    TextView NetCord;
    TextView NetAdr;
    TextView GPSAdr;
    TextView Passwd;
    LocationManager locationManager;
    LocationListener gpslistener;
    LocationListener listener;
    DB Database;
    Geocoder geocoder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//       NetCord = findViewById(R.id.NetCords);
//       GpsCord = findViewById(R.id.GPSCords);
//       NetAdr = findViewById(R.id.NetAdr);
//       GPSAdr = findViewById(R.id.GPSAdr);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = (location.getLatitude());
                double longitude = (location.getLongitude());
                String cords = Double.toString(latitude) + "  " + Double.toString(longitude);
                //Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
                if(NetCord == null)
                    return;
                NetCord.setText(cords);

                try {
                    java.util.List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if(addresses != null) {
                        Address returnedAddress = addresses.get(0);

                        String YourLocation = "Ваш адрес NET: " + returnedAddress.getCountryName().toString() + " " + returnedAddress.getAdminArea().toString() + " " + returnedAddress.getSubAdminArea().toString() +
                        " " + returnedAddress.getThoroughfare() + " " + returnedAddress.getSubThoroughfare();
                        NetAdr.setText(YourLocation);

//                        StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
//                        for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
//                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
//                        }
                        //myAddress.setText(strReturnedAddress.toString());
                    }
                    else{
                        //myAddress.setText("No Address returned!");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                   // myAddress.setText("Canont get Address!");
                }

                Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
               // Toast.makeText(StartupAct.this, "Geo_Location", Integer.parseInt("Latitude: " + latitude + ", Longitude: " + longitude)).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        gpslistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = (location.getLatitude());
                double longitude = (location.getLongitude());
                String cords = Double.toString(latitude) + "  " + Double.toString(longitude);
                //Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
                GpsCord.setText(cords);
                try {
                    java.util.List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if(addresses != null) {
                        Address returnedAddress = addresses.get(0);

                        String YourLocation = "Ваш адрес GPS: " + returnedAddress.getCountryName().toString() + " " + returnedAddress.getAdminArea().toString() + " " + returnedAddress.getSubAdminArea().toString() +
                                " " + returnedAddress.getThoroughfare() + " " + returnedAddress.getSubThoroughfare();
                        GPSAdr.setText(YourLocation);

//                        StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
//                        for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
//                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
//                        }
                        //myAddress.setText(strReturnedAddress.toString());
                    }
                    else{
                        //myAddress.setText("No Address returned!");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    // myAddress.setText("Canont get Address!");
                }

                Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                // Toast.makeText(StartupAct.this, "Geo_Location", Integer.parseInt("Latitude: " + latitude + ", Longitude: " + longitude)).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        geocoder = new Geocoder(this, Locale.ENGLISH);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpslistener);

        //locationManager.requestSingleUpdate (LocationManager.GPS_PROVIDER, listener, null);
        //Location location =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double latitude = (location.getLatitude());
//        double longitude = (location.getLongitude());
//
//
//        Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);



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
        NetCord = findViewById(R.id.NetCords);
        GpsCord = findViewById(R.id.GPSCords);
        NetAdr = findViewById(R.id.NetAdr);
        GPSAdr = findViewById(R.id.GPSAdr);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpslistener);
    }

    final Looper looper = Looper.getMainLooper();
    final Message message = Message.obtain();

    final Handler handler = new Handler(looper) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.sendingUid == 1) {
                if (msg.obj == TRUE) {
                    StartIntent(FALSE);
                } else {
                    new ThreadTask(handler).TryAddUser(Database, (DB.User) msg.obj);
                }
            }
            if (msg.sendingUid == 11) {
                if (msg.obj == TRUE) {
                    StartIntent(TRUE);
                } else {
                    WrongUserToast();
                }

            }

            if (msg.sendingUid == 2) {
                if (msg.obj == TRUE) {
                    UserDeleted();
                } else {
                    WrongUser();
                }
            }
            if (msg.sendingUid == 3) {
                if (msg.obj == TRUE) {
                    PasswordChanged();
                } else {
                    WrongUser();
                }
            }
        }
    };

    public void WrongUserToast() {
        Toast.makeText(this, "Введён неверный пароль", Toast.LENGTH_SHORT).show();
    }

    public void WrongUser() {
        Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_SHORT).show();
    }

    public void UserDeleted() {
        Toast.makeText(this, "Пользователь удалён", Toast.LENGTH_SHORT).show();
    }

    public void PasswordChanged() {
        Toast.makeText(this, "Пароль изменён", Toast.LENGTH_SHORT).show();
    }

    public void StartIntent(Boolean newUser) {
        if (newUser)
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
        DB.User usera = new DB.User(user, password);

        //@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(null);

        // double latitude =  (location.getLatitude());
        // double longitude =  (location.getLongitude());
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (location == null) {
//            Toast.makeText(this, "gps not work", Toast.LENGTH_SHORT).show();
//            return;
//        }
       // double latitude = (location.getLatitude());
      //  double longitude = (location.getLongitude());


       // Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
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



//    @Override
//    public void onLocationChanged( Location location) {
//        double latitude =  (location.getLatitude());
//        double longitude =  (location.getLongitude());
//        Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        // TODO Auto-generated method stub
//    }

}

