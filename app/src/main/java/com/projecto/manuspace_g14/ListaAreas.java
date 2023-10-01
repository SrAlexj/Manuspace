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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Entidades.Area;
import Utilidades.Utilidades;
import dbs.dbelementos;

public class ListaAreas extends AppCompatActivity {


    private Button btnatras;
    private TextView tvnombreplanta;
    private ListView LVareas;
    ArrayList<Area> ListaArea;
    dbelementos AreaDataBase;
    int IdArea;
    int IdPlanta;
    double K;
    int cantidadDecimales = 2;
    DecimalFormat dF = new DecimalFormat("#." + "0".repeat(cantidadDecimales));
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_areas);
        btnatras = findViewById(R.id.btatraslist);

        SharedPreferences sharedPreferencesnombreplanta = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombre = sharedPreferencesnombreplanta.getString(KEY_NAME, "PLANTA 1");

        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombreArea = sharedPreferencesnombrearea.getString(KEY_NAMEA, "AREA 1");
        Toast.makeText(getApplicationContext(), "area " + nombreArea, Toast.LENGTH_SHORT).show();

        //IdArea = obtenerIdArea(nombreArea);
        IdPlanta = obtenerIdPlanta(nombre);
        SharedPreferences sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        K= Double.parseDouble(sharedPreferences.getString("Kcode", "1"));

        tvnombreplanta = findViewById(R.id.nombreplantas);
        tvnombreplanta.setText(nombre);


        LVareas = findViewById(R.id.LVareas);

        AreaDataBase = new dbelementos(getApplicationContext());
        consultarArea();

        ArrayAdapter<Area> adaptador = new ArrayAdapter<Area>(this, R.layout.item_elementos, ListaArea) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_elementos, parent, false);
                }

                TextView textNombre = convertView.findViewById(R.id.text_nombre);
                TextView textValorColumna = convertView.findViewById(R.id.text_valor_columna);

                Area area = getItem(position);

                textNombre.setText(area.getNombrearea());
                textValorColumna.setText(String.valueOf(area.getArea()));

                return convertView;
            }

        };


        LVareas.setAdapter(adaptador);


        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAreas.this, Planta.class);

                startActivity(intent);
            }
        });






    }

    private void consultarArea() {
        SQLiteDatabase db = AreaDataBase.getReadableDatabase();
        Area area= null;
        ListaArea = new ArrayList<Area>();

        Cursor cursor = db.rawQuery("SELECT "+ Utilidades.COLUMN_NOMBRE_AREA+" FROM "+ Utilidades.TABLE_AREAS,null);
        while (cursor.moveToNext()){

            area = new Area();

            area.setNombrearea(cursor.getString(0));

            IdArea=obtenerIdArea(cursor.getString(0));
            double area1= calcATotalPorArea(IdPlanta,IdArea,K);
            area.setArea(Double.parseDouble(dF.format(area1)));

            ListaArea.add(area);

        }




    }


    @SuppressLint("Range")
    public double calcATotalPorArea(int idPlanta, int idArea,double k){
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
                " = " + idPlanta +
                " AND " + Utilidades.TABLE_AREAS + "." + Utilidades.COLUMN_ID_AREAS +
                " = " + idArea;

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