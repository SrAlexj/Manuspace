package com.projecto.manuspace_g14;

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

import java.text.DecimalFormat;
import java.util.ArrayList;

import Entidades.Planta;
import Utilidades.Utilidades;
import dbs.dbelementos;

public class ListaPlantas extends AppCompatActivity {

    private TextView tvnombreplanta;

    private ListView LVplantas;
    private Button btnatras;
    ArrayList<Planta> ListaPlanta;
    dbelementos AreaDataBase;
    int IdPlanta=0;
    double K=0;
    int cantidadDecimales = 2;

    DecimalFormat dF = new DecimalFormat("#." + "0".repeat(cantidadDecimales));
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_plantas);

        LVplantas = findViewById(R.id.LVplantas);
        btnatras = findViewById(R.id.btatraslist);

        SharedPreferences sharedPreferencesnombreplanta = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombre = sharedPreferencesnombreplanta.getString(KEY_NAME, "PLANTA 1");

        IdPlanta = obtenerIdPlanta(nombre);

        SharedPreferences sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        K= Double.parseDouble(sharedPreferences.getString("Kcode", "1"));

        AreaDataBase = new dbelementos(getApplicationContext());
        consultarArea();

        ArrayAdapter<Planta> adaptador = new ArrayAdapter<Planta>(this, R.layout.item_elementos, ListaPlanta) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_elementos, parent, false);
                }

                TextView textNombre = convertView.findViewById(R.id.text_nombre);
                TextView textValorColumna = convertView.findViewById(R.id.text_valor_columna);

                Planta planta = getItem(position);

                textNombre.setText(planta.getNombreplanta());
                textValorColumna.setText(String.valueOf(planta.getAreaplanta()));

                return convertView;
            }
        };

        LVplantas.setAdapter(adaptador);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaPlantas.this, Menu.class);

                startActivity(intent);
            }
        });


    }

    private void consultarArea() {
        SQLiteDatabase db = AreaDataBase.getReadableDatabase();
        Planta planta= null;
        ListaPlanta = new ArrayList<Planta>();
        Cursor cursor = db.rawQuery("SELECT "+ Utilidades.COLUMN_NOMBRE_PLANTA+" FROM "+ Utilidades.TABLE_PLANTAS,null);
        while (cursor.moveToNext()){
            planta = new Planta();
            planta.setNombreplanta(cursor.getString(0));
            double planta1 = calcATotalPorPlanta(IdPlanta,K);
            planta.setAreaplanta(Double.parseDouble(dF.format(planta1)));

            ListaPlanta.add(planta);
        }




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