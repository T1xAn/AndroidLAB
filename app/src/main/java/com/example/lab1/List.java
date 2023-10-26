package com.example.lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class List extends Activity {
    int counter;
    ArrayAdapter<String> adapter;
    ArrayList<String> Tabletki = new ArrayList<>();
    ArrayList<String> VipitieTabletki = new ArrayList<>();

    ArrayList<String> Times = new ArrayList<>();
    SharedPreferences mSettings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listbuba);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        TextView Login = findViewById(R.id.UserName);
        Button buttonDel = findViewById(R.id.buttonRemove);
        ListView PeiTabletki = findViewById(R.id.PeiTabletki);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, Tabletki);
        mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences mSettings;
        PeiTabletki.setAdapter(adapter);
        LoadPreferences();
        Times.add("13:30:00");
        Times.add("13:00:00");
        Times.add("13:50:00");
        Times.add("12:30:00");
        //Collections.addAll(Tabletki, "Кеторол", "Цитромон", "Coldrex", "Дизмораль");
        counter = 0;
        TextView TextCounter = findViewById(R.id.MainName);
        Bundle arguments = getIntent().getExtras();

        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {

                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                        String timeText = timeFormat.format(currentTime);
                        ListView list = findViewById(R.id.PeiTabletki);
                        if(list.getChildCount() == 0 ) {
                            Thread.sleep(1000);
                            continue;
                        }

                       String[] crtime =  timeText.split(":");
                        int CurrentTime = Integer.parseInt(crtime[0]) * 3600 + Integer.parseInt(crtime[1]) * 60 + Integer.parseInt(crtime[2]);
                        for(int i = 0; i < list.getChildCount() && i < Times.size(); i++){
                            TextView TextToChange = (TextView)list.getChildAt(i);
                            String[] ndtime = Times.get(i).split(":");
                            int NeededTime = Integer.parseInt(ndtime[0]) * 3600 + Integer.parseInt(ndtime[1]) * 60 + Integer.parseInt(ndtime[2]);
                            if(NeededTime < CurrentTime)
                                TextToChange.setTextColor(Color.rgb(200,0,0));
                            else
                                TextToChange.setTextColor(Color.rgb(0,200,0));
                        }
                        Thread.sleep(1000); //1000 - 1 сек
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
        run.start();

        if (arguments != null) {
            String user = arguments.getString("user");
            //Login.setText(user);
            Toast.makeText(List.this,
                    "Вы зашли под именем " + user, Toast.LENGTH_LONG).show();
        }
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
            }
        });


        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(v, PeiTabletki);
            }

        });
        PeiTabletki.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String user = adapter.getItem(position);
                if (PeiTabletki.isItemChecked(position)) {
                    VipitieTabletki.add(user);
                    v.setBackgroundColor(Color.CYAN);
                }
                else {
                    VipitieTabletki.remove(user);
                    v.setBackgroundColor(Color.WHITE);
                }
                buttonDel.setEnabled(true); // TODO
            }
        });
    }

    public void add(View view) {
        EditText InputLine = findViewById(R.id.EnterTextLine);
        String NovieTabletki = InputLine.getText().toString();
        if (!NovieTabletki.isEmpty()) {
            adapter.add(NovieTabletki);

            InputLine.setText("");
            adapter.notifyDataSetChanged();
        }
    }
    protected void SavePreferences() {
        String Tabl = Tabletki.toString();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("Tablets", Tabl);
        editor.apply();
    }

    protected void LoadPreferences() {
        SharedPreferences data = this.getSharedPreferences("Settings", MODE_PRIVATE);
        String dataSet = data.getString("Tablets", null);
        if (Objects.equals(dataSet, null)) return;
        if (Objects.equals(dataSet, "[]")) return;
        dataSet = dataSet.replaceAll("^\\[|\\]$", "");
        String[] Tabl = dataSet.split(", ");

        adapter.addAll(Tabl);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SavePreferences();
    }
    public void remove(View view, ListView listView) {
        for (int i = 0; i < VipitieTabletki.size(); i++) {
            adapter.remove(VipitieTabletki.get(i));
        }
        listView.clearChoices();
        VipitieTabletki.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(List.this,
                "Вы забыли таблетки", Toast.LENGTH_SHORT).show();
    }


}

