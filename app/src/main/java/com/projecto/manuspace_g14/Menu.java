package com.projecto.manuspace_g14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dbs.dbelementos;

public class Menu extends AppCompatActivity {
    private Button btnanadirplanta;
    private Button btnverplanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        dbelementos DataBase = new dbelementos(Menu.this);


        btnanadirplanta = findViewById(R.id.btanadirplanta);
        btnverplanta = findViewById(R.id.btverplanta);

        btnanadirplanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Menu.this, Planta.class);

                startActivity(intent);
            }
        });

        btnverplanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Menu.this, ListaPlantas.class);

                startActivity(intent);
            }
        });


    }
}