package com.example.lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;

public class List extends Activity {
    int counter;
    ArrayAdapter<String> adapter;
    ArrayList<String> Tabletki = new ArrayList<>();
    ArrayList<String> VipitieTabletki = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ///менять цвет при выделении доп задание
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listbuba);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDel = findViewById(R.id.buttonRemove);
        ListView PeiTabletki = findViewById(R.id.PeiTabletki);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, Tabletki);
        PeiTabletki.setAdapter(adapter);
        Collections.addAll(Tabletki, "Кеторол", "Цитромон", "Coldrex", "Дизмораль");
        counter = 0;
        TextView TextCounter = findViewById(R.id.MainName);
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

