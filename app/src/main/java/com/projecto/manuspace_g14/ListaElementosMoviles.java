package com.projecto.manuspace_g14;

import static com.projecto.manuspace_g14.Areas.KEY_NAMEA;
import static com.projecto.manuspace_g14.Planta.KEY_NAME;
import static com.projecto.manuspace_g14.Planta.NAMES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Entidades.ElementoMovil;
import Utilidades.Utilidades;
import dbs.dbelementos;

public class ListaElementosMoviles extends AppCompatActivity {
    private Button btnatras;
    private ListView LVelementomovil;
    ArrayList<ElementoMovil> listaElementoMovil;
    dbelementos EFDataBase;
    private TextView tvnombreareas;

    int IdPlanta;
    int IdArea;
    double K;
    int cantidadDecimales = 2;

    DecimalFormat dF = new DecimalFormat("#." + "0".repeat(cantidadDecimales));
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_elementos_moviles);
        LVelementomovil = findViewById(R.id.LVelementosmoviles);
        btnatras= findViewById(R.id.btatraslistaem);

        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombre = sharedPreferencesnombrearea.getString(KEY_NAMEA, "AREA 1");

        SharedPreferences sharedPreferencesnombreplanta = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombrePlanta = sharedPreferencesnombreplanta.getString(KEY_NAME, "PLANTA 1");
        Toast.makeText(getApplicationContext(), "area " + nombrePlanta, Toast.LENGTH_SHORT).show();

        IdArea = obtenerIdArea(nombre);
        IdPlanta = obtenerIdPlanta(nombrePlanta);
        SharedPreferences sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        K= Double.parseDouble(sharedPreferences.getString("Kcode", "1"));

        tvnombreareas = findViewById(R.id.nombreareas);
        tvnombreareas.setText(nombre);

        EFDataBase = new dbelementos(getApplicationContext());
        consultarEM();

        ArrayAdapter<ElementoMovil> adaptador = new ArrayAdapter<ElementoMovil>(this, R.layout.item_elementos, listaElementoMovil) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_elementos, parent, false);
                }

                TextView textNombreEF = convertView.findViewById(R.id.text_nombre);
                TextView textValorColumna = convertView.findViewById(R.id.text_valor_columna);

                ElementoMovil elementoMovil = getItem(position);

                textNombreEF.setText(elementoMovil.getNombreem());
                textValorColumna.setText(String.valueOf(elementoMovil.getSs()));

                return convertView;
            }
        };

        LVelementomovil.setAdapter(adaptador);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaElementosMoviles.this, Areas.class);

                startActivity(intent);
            }
        });
    }

    private void consultarEM() {
        SQLiteDatabase db = EFDataBase.getReadableDatabase();
        ElementoMovil elementomovil= null;
        listaElementoMovil = new ArrayList<ElementoMovil>();
        String consulta = "SELECT " + Utilidades.COLUMN_NOMBRE_EM +", "+ Utilidades.COLUMN_SS +

                " FROM " + Utilidades.TABLE_EM +
                " INNER JOIN " + Utilidades.TABLE_AREAS +
                " ON " + Utilidades.TABLE_EM + "." + Utilidades.COLUMN_CF_AREA +
                " = " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " WHERE " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_CF_PLANTA +
                " = " + IdPlanta +
                " AND " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " = " + IdArea;

        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()){
            elementomovil = new ElementoMovil();
            elementomovil.setNombreem(cursor.getString(0));
            elementomovil.setSs(Double.parseDouble(dF.format(cursor.getDouble(1))));

            listaElementoMovil.add(elementomovil);
        }




    }

    @SuppressLint("Range")
    public int obtenerIdArea(String nombreArea) {
        dbelementos dbnombre = new dbelementos(getApplicationContext());
        SQLiteDatabase database = dbnombre.getReadableDatabase();

        String consulta = "SELECT " + Utilidades.COLUMN_ID_AREAS + " FROM " + Utilidades.TABLE_AREAS +
                " WHERE " + Utilidades.COLUMN_NOMBRE_AREA + " = ?";
        Cursor cursor = database.rawQuery(consulta, new String[]{nombreArea});

        int idArea = -1;

        if (cursor.moveToFirst()) {
            idArea = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_ID_AREAS));

        }

        cursor.close();
        database.close();

        return idArea;
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