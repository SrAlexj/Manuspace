package com.projecto.manuspace_g14;

import static com.projecto.manuspace_g14.Areas.KEY_NAMEA;
import static com.projecto.manuspace_g14.Planta.KEY_NAME;
import static com.projecto.manuspace_g14.Planta.NAMES;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import Utilidades.Utilidades;
import dbs.dbelementos;

public class CalcularAreaPlanta extends AppCompatActivity {

    private Button btnatras;
    private TextView tvareadeplanta;
    private TextView tvareadeplanta1;
    private TextView tvareadeplanta2;
    int cantidadDecimales = 2;
    DecimalFormat dF = new DecimalFormat("#." + "0".repeat(cantidadDecimales));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_area_planta);
        btnatras = findViewById(R.id.btatrasadeplanta);
        tvareadeplanta = findViewById(R.id.areadeplanta);
        tvareadeplanta1 = findViewById(R.id.ladodearea1planta);
        tvareadeplanta2 = findViewById(R.id.ladodearea2planta);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalcularAreaPlanta.this, Planta.class);

                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferencesnombreplanta = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombrePlanta = sharedPreferencesnombreplanta.getString(KEY_NAME, "PLANTA 1");

        int idPlanta = obtenerIdPlanta(nombrePlanta);

        double K = calcularK();

        double areaporplanta = calcATotalPorPlanta(idPlanta, K) ;
        tvareadeplanta.setText(dF.format(areaporplanta));
        tvareadeplanta1.setText(dF.format(Math.sqrt(areaporplanta)));
        tvareadeplanta2.setText(dF.format(Math.sqrt(areaporplanta)));
    }

    @SuppressLint("Range")
    public double calcATotalPorPlanta(int idPlanta, double k){
        double total = 0;
        double se = 0;
        dbelementos sacarefdb = new dbelementos(getApplicationContext());
        SQLiteDatabase db = sacarefdb.getReadableDatabase();

        String consulta = "SELECT " + Utilidades.COLUMN_SS + ", " +
                Utilidades.COLUMN_SG +", " +
                Utilidades.COLUMN_SS_AL +", " +
                Utilidades.COLUMN_CANTIDAD +", " +
                Utilidades.COLUMN_CANTIDAD_ALM +

                " FROM " + Utilidades.TABLE_EF +
                " INNER JOIN " + Utilidades.TABLE_AREAS +
                " ON " + Utilidades.TABLE_EF + "." + Utilidades.COLUMN_CF_AREA +
                " = " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " WHERE " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_CF_PLANTA +
                " = " + idPlanta;
        Cursor cursor = db.rawQuery(consulta, null);
        if (cursor.moveToFirst()) {
            do {

                double ss = cursor.getDouble(cursor.getColumnIndex(Utilidades.COLUMN_SS));
                double sg = cursor.getDouble(cursor.getColumnIndex(Utilidades.COLUMN_SG));
                double ss_al = cursor.getDouble(cursor.getColumnIndex(Utilidades.COLUMN_SS_AL));
                int cant = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_CANTIDAD));
                int cant_al = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_CANTIDAD_ALM));
                double sum_al;
                double se1 = (ss+sg)*k;
                double se_al = ss_al*k;
                double sum=(ss+sg+se1)*cant;

                if(((ss_al*cant_al)/sg) >0.3){
                    sum_al=(ss_al+se_al)*cant_al;
                }else {
                    sum_al=0;
                }

                total+=(sum+sum_al);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return total;
    }

    public double calcularK(){


        SharedPreferences sharedPreferencesnombreplanta = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombrePlanta = sharedPreferencesnombreplanta.getString(KEY_NAME, "PLANTA 1");
        //Toast.makeText(getApplicationContext(), "planta " + nombrePlanta, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombreArea = sharedPreferencesnombrearea.getString(KEY_NAMEA, "AREA 1");
        //Toast.makeText(getApplicationContext(), "area " + nombreArea, Toast.LENGTH_SHORT).show();



        int idPlanta = obtenerIdPlanta(nombrePlanta);
        int idArea = obtenerIdArea(nombreArea);
        //Asegúrate de que este método devuelve un int válido
        //Toast.makeText(getApplicationContext(), "idplanta:  " + idPlanta, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "idarea:  " + idArea, Toast.LENGTH_SHORT).show();

        double sumaSSNef = obtenerSSNTotalEF(idPlanta, Utilidades.COLUMN_SSN);
        double sumaSSNHef = obtenerSSNTotalEF(idPlanta, Utilidades.COLUMN_SSNH);
        double sumaSSNem = obtenerSSNTotalEM(idPlanta, Utilidades.COLUMN_SSN);
        double sumaSSNHem = obtenerSSNTotalEM(idPlanta, Utilidades.COLUMN_SSNH);

        double Hee = sumaSSNHef/sumaSSNef;
        double Hem = sumaSSNHem/sumaSSNem;
        double Kcode = Hem/(2*Hee);

        return Kcode;
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
    public double obtenerSSNTotalEF(int idPlanta, String s) {

        double sumaSSN = 0;

        dbelementos elementoFijoDb = new dbelementos(getApplicationContext());
        SQLiteDatabase db = elementoFijoDb.getReadableDatabase();

        // Consulta SQL utilizando JOIN y condiciones WHERE
        String consulta = "SELECT " + s +
                " FROM " + Utilidades.TABLE_EF +
                " INNER JOIN " + Utilidades.TABLE_AREAS +
                " ON " + Utilidades.TABLE_EF + "." + Utilidades.COLUMN_CF_AREA +
                " = " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " WHERE " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_CF_PLANTA +
                " = " + idPlanta;

        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") double valorSS = cursor.getDouble(cursor.getColumnIndex(s));
                sumaSSN += valorSS;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sumaSSN;
    }


    public double obtenerSSNTotalEM(int idPlanta, String s) {

        double sumaSSN = 0;

        dbelementos elementoFijoDb = new dbelementos(getApplicationContext());
        SQLiteDatabase db = elementoFijoDb.getReadableDatabase();

        // Consulta SQL utilizando JOIN y condiciones WHERE
        String consulta = "SELECT " + s +
                " FROM " + Utilidades.TABLE_EM +
                " INNER JOIN " + Utilidades.TABLE_AREAS +
                " ON " + Utilidades.TABLE_EM + "." + Utilidades.COLUMN_CF_AREA +
                " = " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " WHERE " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_CF_PLANTA +
                " = " + idPlanta;

        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") double valorSS = cursor.getDouble(cursor.getColumnIndex(s));
                sumaSSN += valorSS;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sumaSSN;
    }
}
