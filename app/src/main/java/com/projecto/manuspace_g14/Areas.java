package com.projecto.manuspace_g14;

import static com.projecto.manuspace_g14.Planta.NAMES;
import static com.projecto.manuspace_g14.Planta.KEY_NAME;

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

import Operaciones.Operaciones;
import Utilidades.Utilidades;
import dbs.dbelementos;

public class Areas extends AppCompatActivity {
    private Button btanadiref;
    private Button btveref;
    private Button btanadirem;
    private Button btverem;
    private Button btcalculararean;
    private Button btatrasarea;
    private TextView tvnombrearea;
    private EditText etnombrearea;
    private SharedPreferences sharedPreferences;
    static final String KEY_NAMEA = "nombrearea";




    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombrePlanta = sharedPreferencesnombrearea.getString(KEY_NAME, "PLANTA 1");
        //Toast.makeText(Areas.this,"planta "+nombrePlanta,Toast.LENGTH_SHORT).show();



        btanadiref = findViewById(R.id.btanadiref);
        btveref = findViewById(R.id.btveref);
        btverem = findViewById(R.id.btverem);
        btanadirem = findViewById(R.id.btanadirem);
        btcalculararean = findViewById(R.id.btcalculararean);
        btatrasarea = findViewById(R.id.btatrasarea);
        etnombrearea = findViewById(R.id.etnombrearea);
        tvnombrearea = findViewById(R.id.tvnombrearea);


        btanadiref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Areas.this, ElementosFijos.class);

                startActivity(intent);

                String nombreArea = tvnombrearea.getText().toString();
                dbelementos elementoFijoDb = new dbelementos(Areas.this);
                SQLiteDatabase db = elementoFijoDb.getWritableDatabase();

                String consulta = "SELECT * FROM areas WHERE nombre_area = '" + nombreArea + "'" ;
                Cursor cursor = db.rawQuery(consulta, null);


                if (cursor.getCount() > 0) {

                    cursor.close();
                    Toast.makeText(Areas.this,"Ya existe el Área "+nombreArea,Toast.LENGTH_SHORT).show();
                } else {

                    ContentValues values = new ContentValues();
                    values.put(Utilidades.COLUMN_NOMBRE_AREA, nombreArea);
                    values.put(Utilidades.COLUMN_CF_PLANTA, obtenerIdPlanta(nombrePlanta));

                    db.insert(Utilidades.TABLE_AREAS, null, values);
                    cursor.close();
                    Toast.makeText(Areas.this,"Se ha agregado el Area "+nombreArea,Toast.LENGTH_SHORT).show();

                }


            }
        });


        btveref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Areas.this, ListaElementosFijos.class);

                startActivity(intent);

            }
        });


        btanadirem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Areas.this, ElementosMoviles.class);

                startActivity(intent);

                String nombreArea = tvnombrearea.getText().toString();
                dbelementos elementoFijoDb = new dbelementos(Areas.this);
                SQLiteDatabase db = elementoFijoDb.getWritableDatabase();

                String consulta = "SELECT * FROM areas WHERE nombre_area = '" + nombreArea + "'" ;
                Cursor cursor = db.rawQuery(consulta, null);



                if (cursor.getCount() > 0) {

                    cursor.close();
                    Toast.makeText(Areas.this,"Ya existe el Área "+nombreArea,Toast.LENGTH_SHORT).show();
                } else {

                    ContentValues values = new ContentValues();
                    values.put(Utilidades.COLUMN_NOMBRE_AREA, nombreArea);
                    values.put(Utilidades.COLUMN_CF_PLANTA, obtenerIdPlanta(nombrePlanta));

                    db.insert(Utilidades.TABLE_AREAS, null, values);
                    cursor.close();
                    Toast.makeText(Areas.this,"Se ha agregado el Área "+nombreArea,Toast.LENGTH_SHORT).show();

                }



            }
        });

        btverem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Areas.this, ListaElementosMoviles.class);

                startActivity(intent);

            }
        });


        btcalculararean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Areas.this, CalcularAreaArea.class);

                startActivity(intent);
            }
        });


        btatrasarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Areas.this, Planta.class);

                startActivity(intent);
            }
        });


        sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);

        etnombrearea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAMEA, name);
                editor.apply();

                tvnombrearea.setText(name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String savedName = sharedPreferences.getString(KEY_NAMEA, "AREA 1");
        tvnombrearea.setText(savedName);

    }


    @SuppressLint("Range")
    public int obtenerIdPlanta(String nombrePlanta) {
        dbelementos dbnombre = new dbelementos(getApplicationContext());
        SQLiteDatabase database = dbnombre.getReadableDatabase();

        String consulta = "SELECT " + Utilidades.COLUMN_ID_PLANTAS + " FROM " + Utilidades.TABLE_PLANTAS +
                " WHERE " + Utilidades.COLUMN_NOMBRE_PLANTA + " = ?";
        Cursor cursor = database.rawQuery(consulta, new String[]{nombrePlanta});

        int idPlanta = -1;

        if (cursor.moveToFirst()) {
            idPlanta = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_ID_PLANTAS));

        }

        cursor.close();
        database.close();

        return idPlanta;
    }



}