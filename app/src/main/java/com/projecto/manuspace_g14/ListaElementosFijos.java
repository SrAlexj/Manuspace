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

import Entidades.ElementoFijo;
import Operaciones.Operaciones;
import Utilidades.Utilidades;
import dbs.dbelementos;

public class ListaElementosFijos extends AppCompatActivity {

    private Button btnatras;
    private ListView LVelementofijos;
    ArrayList<ElementoFijo> listaElementoFijo;

    dbelementos EFDataBase;

    int cantidadDecimales = 2;
    private TextView tvnombreareas;
    int IdPlanta;
    int IdArea;
    double K;



    DecimalFormat dF = new DecimalFormat("#." + "0".repeat(cantidadDecimales));


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_elementos_fijos);
        btnatras = findViewById(R.id.btatraslistaef);

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

        LVelementofijos = findViewById(R.id.LVelementosfijos);

        EFDataBase = new dbelementos(getApplicationContext());
        consultarEF();

        ArrayAdapter<ElementoFijo> adaptador = new ArrayAdapter<ElementoFijo>(this, R.layout.item_elementos, listaElementoFijo) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_elementos, parent, false);
                }

                TextView textNombreEF = convertView.findViewById(R.id.text_nombre);
                TextView textValorColumna = convertView.findViewById(R.id.text_valor_columna);

                ElementoFijo elementoFijo = getItem(position);

                textNombreEF.setText(elementoFijo.getNombreef());
                textValorColumna.setText(String.valueOf(elementoFijo.getSuma()));

                return convertView;
            }
        };

        LVelementofijos.setAdapter(adaptador);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaElementosFijos.this, Areas.class);

                startActivity(intent);
            }
        });


    }


    @SuppressLint("Range")
    private void consultarEF() {

        SQLiteDatabase db = EFDataBase.getReadableDatabase();
        ElementoFijo elementofijo= null;
        listaElementoFijo = new ArrayList<ElementoFijo>();
        String consulta = "SELECT " + Utilidades.COLUMN_NOMBRE_EF +", "+ Utilidades.COLUMN_SS + ", " +
                Utilidades.COLUMN_SG +", " +
                Utilidades.COLUMN_SS_AL +", " +
                Utilidades.COLUMN_CANTIDAD +", " +
                Utilidades.COLUMN_CANTIDAD_ALM +

                " FROM " + Utilidades.TABLE_EF +
                " INNER JOIN " + Utilidades.TABLE_AREAS +
                " ON " + Utilidades.TABLE_EF + "." + Utilidades.COLUMN_CF_AREA +
                " = " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " WHERE " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_CF_PLANTA +
                " = " + IdPlanta +
                " AND " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " = " + IdArea;

        Cursor cursor = db.rawQuery(consulta, null);
        while (cursor.moveToNext()){
            elementofijo = new ElementoFijo();
            elementofijo.setNombreef(cursor.getString(0));


            double ss = cursor.getDouble(cursor.getColumnIndex(Utilidades.COLUMN_SS));
            double sg = cursor.getDouble(cursor.getColumnIndex(Utilidades.COLUMN_SG));
            double ss_al = cursor.getDouble(cursor.getColumnIndex(Utilidades.COLUMN_SS_AL));
            int cant = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_CANTIDAD));
            int cant_al = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_CANTIDAD_ALM));
            double sum_al;
            double se1 = (ss+sg)*K;
            double se_al = ss_al*K;
            double sum=(ss+sg+se1)*cant;

            if(((ss_al*cant_al)/sg) >0.3){
                sum_al=(ss_al+se_al)*cant_al;
            }else {
                sum_al=0;
            }

            double total =(sum+sum_al);

            elementofijo.setSuma(Double.parseDouble(dF.format(total)));

            listaElementoFijo.add(elementofijo);
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