package com.projecto.manuspace_g14;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Utilidades.Utilidades;
import dbs.dbelementos;

public class Planta extends AppCompatActivity {
    private Button btnanadirarea;
    private Button btncalcularareaplanta;
    private Button btnatrasp;
    private Button btnverareas;
    private EditText etnombreplanta;
    private TextView tvnombreplanta;

    private SharedPreferences sharedPreferences;
    static final String NAMES = "Nombres";
    public static final String KEY_NAME = "nombreplanta";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);

        btnanadirarea = findViewById(R.id.btanadirareaarea);
        btncalcularareaplanta = findViewById(R.id.btcalculararea);
        btnatrasp = findViewById(R.id.btatrasp);
        btnverareas = findViewById(R.id.btverareas);

        etnombreplanta = findViewById(R.id.etnombreplantas);
        tvnombreplanta = findViewById(R.id.tvnombreplanta);




        btnanadirarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Planta.this, Areas.class);
                startActivity(intent);

                String nombrePlanta = tvnombreplanta.getText().toString();


                boolean plantaExiste = verificarPlantaExistente(nombrePlanta);

                if (plantaExiste) {
                    Toast.makeText(Planta.this, "La planta " + nombrePlanta + " ya existe", Toast.LENGTH_SHORT).show();
                } else {

                    insertarPlanta(nombrePlanta);
                    Toast.makeText(Planta.this, "Se ha agregado la planta " + nombrePlanta, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnverareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Planta.this, ListaAreas.class);


                startActivity(intent);
            }
        });

        btncalcularareaplanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Planta.this, CalcularAreaPlanta.class);


                startActivity(intent);
            }
        });

        btnatrasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Planta.this, Menu.class);


                startActivity(intent);
            }
        });



        sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);


        etnombreplanta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String name = s.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME, name);
                editor.apply();


                tvnombreplanta.setText(name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String savedName = sharedPreferences.getString(KEY_NAME, "PLANTA 1");
        tvnombreplanta.setText(savedName);



    }



    private boolean verificarPlantaExistente(String nombrePlanta) {
        dbelementos plantas = new dbelementos(Planta.this);
        SQLiteDatabase db = plantas.getReadableDatabase();

        String consulta = "SELECT * FROM " + Utilidades.TABLE_PLANTAS + " WHERE " + Utilidades.COLUMN_NOMBRE_PLANTA + " = ?";
        Cursor cursor = db.rawQuery(consulta, new String[]{nombrePlanta});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return existe;
    }

    private void insertarPlanta(String nombrePlanta) {
        dbelementos plantas = new dbelementos(Planta.this);
        SQLiteDatabase db = plantas.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.COLUMN_NOMBRE_PLANTA, nombrePlanta);
        db.insert(Utilidades.TABLE_PLANTAS, null, values);

        db.close();
    }






}