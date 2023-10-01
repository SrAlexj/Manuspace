package com.projecto.manuspace_g14;

import static com.projecto.manuspace_g14.Areas.KEY_NAMEA;
import static com.projecto.manuspace_g14.Planta.KEY_NAME;
import static com.projecto.manuspace_g14.Planta.NAMES;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Entidades.ElementoFijo;
import Utilidades.Utilidades;
import dbs.dbelementos;
import Operaciones.Operaciones;

public class ElementosFijos extends AppCompatActivity {

    private RadioButton rbrectangular;
    private RadioButton rbcircular;
    private RadioButton rbsi;
    private RadioButton rbno;

    private EditText etnombreef;
    private EditText etLargor;
    private EditText etAnchor;
    private EditText etAltor;
    private EditText etAltoc;
    private EditText etRadioc;
    private EditText etCantidad;
    private EditText etLargoal;
    private EditText etAnchoal;
    private EditText etAltoal;
    private EditText etCantidadal;
    private EditText etLadosn;

    private TextView tvejemplo;


    private LinearLayout lyrectangular;
    private LinearLayout lycircular;
    private LinearLayout lyalmacenamiento;
    private LinearLayout lyopsi;
    private Button btanadiref;
    private Button btatrasef;

    private RadioGroup rgdimensionamiento;
    private RadioGroup rgalmacenamiento;

    private double resultadoarea;
    private TextView tvnombreareas;
    private TextView tvnombreelementofijo;
    ArrayList<ElementoFijo> listaObjetos = new ArrayList<>();

    dbelementos sacarefdb;

    private SharedPreferences sharedPreferences;

    static final String KEY_NAMEEF = "nombreelementofijo";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos_fijos);

        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombre = sharedPreferencesnombrearea.getString(KEY_NAMEA, "AREA 1");

        tvnombreareas = findViewById(R.id.tvnombrearea);
        tvnombreareas.setText(nombre);




        rbsi = findViewById(R.id.rbopsi);
        rbno = findViewById(R.id.rbopno);

         rgdimensionamiento = findViewById(R.id.rgdimensionamiento);
         rgalmacenamiento = findViewById(R.id.rgalmacenamiento);

         rbrectangular = findViewById(R.id.rbrectangular);
         rbcircular = findViewById(R.id.rbcircular);

         etnombreef = findViewById(R.id.nombreef);

         etLargor = findViewById(R.id.largor);
         etAnchor = findViewById(R.id.anchor);
         etAltor = findViewById(R.id.altor);

         etAltoc = findViewById(R.id.altocir);
         etRadioc = findViewById(R.id.radioc);

         etLadosn = findViewById(R.id.ladosn);

         lyrectangular = findViewById(R.id.lyrectangular);
         lycircular = findViewById(R.id.lycircular);
         lyalmacenamiento = findViewById(R.id.lyalmacenamiento);

         lyopsi = findViewById(R.id.lyopsi);

         etAnchoal = findViewById(R.id.anchoalm);
         etLargoal = findViewById(R.id.largoalm);
         etAltoal = findViewById(R.id.altoalm);
         etCantidadal = findViewById(R.id.cantidadalmacenamiento);

         etCantidad = findViewById(R.id.cantidadn);

         btanadiref = findViewById(R.id.btanadiref);
         btatrasef = findViewById(R.id.btatrasef);

         tvnombreelementofijo = findViewById(R.id.tvelementofijo);





         sacarefdb = new dbelementos(ElementosFijos.this);



        sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);


        etnombreef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String name = s.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAMEEF, name);
                editor.apply();

                tvnombreelementofijo.setText(name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String savedName = sharedPreferences.getString(KEY_NAMEEF, "ELEMENTO FIJO 1");
        tvnombreelementofijo.setText(savedName);



    }



    public void mostrardimension (View view){

        boolean checked = ((RadioButton)view).isChecked();

        if (view.getId()==R.id.rbrectangular){
            if (checked){
                lyrectangular.setVisibility(View.VISIBLE);
                lycircular.setVisibility(View.INVISIBLE);
            }

        }else {
            if (view.getId()==R.id.rbcircular) {
                if (checked) {
                    lyrectangular.setVisibility(View.INVISIBLE);
                    lycircular.setVisibility(View.VISIBLE);


                }
            }
        }

    }



    public void mostraralmacenamiento (View view){

        boolean checked = ((RadioButton)view).isChecked();

        if (view.getId()==R.id.rbopsi){
            if (checked){
                lyopsi.setVisibility(View.VISIBLE);
                lyalmacenamiento.setVisibility(View.VISIBLE);

            }

        }else {

            if (checked) {
                lyopsi.setVisibility(View.GONE);
                lyalmacenamiento.setVisibility(View.GONE);
            }
        }
    }

    public void atrasef(View view) {

        Intent atrasef = new Intent(ElementosFijos.this, Areas.class);
        startActivity(atrasef);
    }


    public void onClick(View view) {
        agregarElementoFijo();


    }




    @SuppressLint("Range")
    private void agregarElementoFijo() {
        String nombreElemento = tvnombreareas.getText().toString();

        dbelementos dbnombre = new dbelementos(ElementosFijos.this);
        SQLiteDatabase database = dbnombre.getReadableDatabase();

        String consulta = "SELECT " + Utilidades.COLUMN_ID_AREAS + " FROM " + Utilidades.TABLE_AREAS +
                " WHERE " + Utilidades.COLUMN_NOMBRE_AREA + " = ?";
        Cursor cursor = database.rawQuery(consulta, new String[]{nombreElemento});

        int idArea = -1;

        if (cursor.moveToFirst()) {
            idArea = cursor.getInt(cursor.getColumnIndex(Utilidades.COLUMN_ID_AREAS));

        }

        cursor.close();
        database.close();



        dbelementos elementoFijoDb = new dbelementos(ElementosFijos.this);
        SQLiteDatabase db = elementoFijoDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        double ss = 0;
        double sg= 0;
        double ssn=0;
        double ssnh=0;

        int rbCheckedDim = rgdimensionamiento.getCheckedRadioButtonId();
        int rbCheckedAlm = rgalmacenamiento.getCheckedRadioButtonId();

        if (rbCheckedDim == R.id.rbrectangular) {
            if (rbCheckedAlm == R.id.rbopsi) {
                // Validación
                if (tvnombreareas.getText().toString().isEmpty() ||
                        etnombreef.getText().toString().isEmpty() ||
                        etLargor.getText().toString().isEmpty() ||
                        etAnchor.getText().toString().isEmpty() ||
                        etAltor.getText().toString().isEmpty() ||
                        etLadosn.getText().toString().isEmpty() ||
                        etLargoal.getText().toString().isEmpty() ||
                        etAnchoal.getText().toString().isEmpty() ||
                        etAltoal.getText().toString().isEmpty() ||
                        etCantidad.getText().toString().isEmpty()||
                        etCantidadal.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese todos los valores requeridos 1", Toast.LENGTH_SHORT).show();
                    return;
                }

                values.put(Utilidades.COLUMN_NOMBRE_AREA, tvnombreareas.getText().toString());
                values.put(Utilidades.COLUMN_NOMBRE_EF, etnombreef.getText().toString());
                values.put(Utilidades.COLUMN_DIMENSION, 1);
                values.put(Utilidades.COLUMN_LARGO_R, etLargor.getText().toString());
                values.put(Utilidades.COLUMN_ANCHO_R, etAnchor.getText().toString());
                values.put(Utilidades.COLUMN_ALTO_R, etAltor.getText().toString());
                values.put(Utilidades.COLUMN_LADOS, etLadosn.getText().toString());
                values.put(Utilidades.COLUMN_ALMACENAMIENTO, 1);
                values.put(Utilidades.COLUMN_LARGO_ALM, etLargoal.getText().toString());
                values.put(Utilidades.COLUMN_ANCHO_ALM, etAnchoal.getText().toString());
                values.put(Utilidades.COLUMN_ALTO_ALM, etAltoal.getText().toString());
                values.put(Utilidades.COLUMN_CANTIDAD, etCantidad.getText().toString());
                values.put(Utilidades.COLUMN_CANTIDAD_ALM, etCantidadal.getText().toString());

                double ss1=Operaciones.ssEF(Double.parseDouble(etLargor.getText().toString()),Double.parseDouble(etAnchor.getText().toString()));
                double ss2=Operaciones.ssEF(Double.parseDouble(etLargoal.getText().toString()),Double.parseDouble(etAnchoal.getText().toString()));

                sg=Operaciones.sgEF(ss1,Double.parseDouble(etLadosn.getText().toString()));
                values.put(Utilidades.COLUMN_SS, ss1);
                values.put(Utilidades.COLUMN_SS_AL, ss2);
                values.put(Utilidades.COLUMN_SG, sg);

                double ssn1=Operaciones.ssN(ss1,Integer.parseInt(etCantidad.getText().toString()));
                double ssn2=Operaciones.ssN(ss2,Integer.parseInt(etCantidadal.getText().toString()));
                double ssn3=CompararSSN(ssn2,sg);
                ssn = ssn1+ssn3;
                ssnh=ssn1*(Double.parseDouble(etAltor.getText().toString()))+
                        ssn3*(Double.parseDouble(etAltoal.getText().toString()));

                values.put(Utilidades.COLUMN_SSN, ssn);
                values.put(Utilidades.COLUMN_SSNH, ssnh);

                values.put(Utilidades.COLUMN_CF_AREA, idArea);



            } else {
                // Validación
                if (tvnombreareas.getText().toString().isEmpty() ||
                        etnombreef.getText().toString().isEmpty() ||
                        etLargor.getText().toString().isEmpty() ||
                        etAnchor.getText().toString().isEmpty() ||
                        etAltor.getText().toString().isEmpty() ||
                        etLadosn.getText().toString().isEmpty() ||
                        etCantidad.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese todos los valores requeridos 2", Toast.LENGTH_SHORT).show();
                    return;
                }

                values.put(Utilidades.COLUMN_NOMBRE_AREA, tvnombreareas.getText().toString());
                values.put(Utilidades.COLUMN_NOMBRE_EF, etnombreef.getText().toString());
                values.put(Utilidades.COLUMN_DIMENSION, 1);
                values.put(Utilidades.COLUMN_LARGO_R, etLargor.getText().toString());
                values.put(Utilidades.COLUMN_ANCHO_R, etAnchor.getText().toString());
                values.put(Utilidades.COLUMN_ALTO_R, etAltor.getText().toString());
                values.put(Utilidades.COLUMN_LADOS, etLadosn.getText().toString());
                values.put(Utilidades.COLUMN_ALMACENAMIENTO, 2);
                values.put(Utilidades.COLUMN_CANTIDAD, etCantidad.getText().toString());

                ss=Operaciones.ssEF(Double.parseDouble(etLargor.getText().toString()),Double.parseDouble(etAnchor.getText().toString()));
                sg=Operaciones.sgEF(ss,Double.parseDouble(etAnchor.getText().toString()));
                values.put(Utilidades.COLUMN_SS, ss);
                values.put(Utilidades.COLUMN_SG, sg);
                ssn = Operaciones.ssN(ss,Integer.parseInt(etCantidad.getText().toString()));
                ssnh = Operaciones.ssNH(ssn,Double.parseDouble(etAltor.getText().toString()));
                values.put(Utilidades.COLUMN_SSN, ssn);
                values.put(Utilidades.COLUMN_SSNH, ssnh);

                values.put(Utilidades.COLUMN_CF_AREA, idArea);
            }


        } else {
            if (rbCheckedAlm == R.id.rbopsi) {
                // Validación
                if (tvnombreareas.getText().toString().isEmpty() ||
                        etnombreef.getText().toString().isEmpty() ||
                        etAltoc.getText().toString().isEmpty() ||
                        etRadioc.getText().toString().isEmpty() ||
                        etLadosn.getText().toString().isEmpty() ||
                        etLargoal.getText().toString().isEmpty() ||
                        etAnchoal.getText().toString().isEmpty() ||
                        etAltoal.getText().toString().isEmpty() ||
                        etCantidad.getText().toString().isEmpty() ||
                        etCantidadal.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese todos los valores requeridos 3", Toast.LENGTH_SHORT).show();
                    return;
                }

                values.put(Utilidades.COLUMN_NOMBRE_AREA, tvnombreareas.getText().toString());
                values.put(Utilidades.COLUMN_NOMBRE_EF, etnombreef.getText().toString());
                values.put(Utilidades.COLUMN_DIMENSION, 2);
                values.put(Utilidades.COLUMN_ALTO_C, etAltoc.getText().toString());
                values.put(Utilidades.COLUMN_RADIO_C, etRadioc.getText().toString());
                values.put(Utilidades.COLUMN_LADOS, etLadosn.getText().toString());
                values.put(Utilidades.COLUMN_ALMACENAMIENTO, 1);
                values.put(Utilidades.COLUMN_LARGO_ALM, etLargoal.getText().toString());
                values.put(Utilidades.COLUMN_ANCHO_ALM, etAnchoal.getText().toString());
                values.put(Utilidades.COLUMN_ALTO_ALM, etAltoal.getText().toString());
                values.put(Utilidades.COLUMN_CANTIDAD, etCantidad.getText().toString());
                values.put(Utilidades.COLUMN_CANTIDAD_ALM, etCantidadal.getText().toString());

                double ss1=Operaciones.ssEFc(Double.parseDouble(etRadioc.getText().toString()));
                double ss2=Operaciones.ssEF(Double.parseDouble(etLargoal.getText().toString()),Double.parseDouble(etAnchoal.getText().toString()));

                sg=Operaciones.sgEF(ss1,Double.parseDouble(etLadosn.getText().toString()));
                values.put(Utilidades.COLUMN_SS, ss1);
                values.put(Utilidades.COLUMN_SS_AL, ss2);
                values.put(Utilidades.COLUMN_SG, sg);
                double ssn1=Operaciones.ssN(ss1,Integer.parseInt(etCantidad.getText().toString()));
                double ssn2=Operaciones.ssN(ss2,Integer.parseInt(etCantidadal.getText().toString()));
                double ssn3=CompararSSN(ssn2,sg);
                ssn = ssn1+ssn3;
                ssnh=ssn1*(Double.parseDouble(etAltoc.getText().toString()))+
                        ssn3*(Double.parseDouble(etAltoal.getText().toString()));

                values.put(Utilidades.COLUMN_SSN, ssn);
                values.put(Utilidades.COLUMN_SSNH, ssnh);

                values.put(Utilidades.COLUMN_CF_AREA, idArea);


            } else {
                // Validación
                if (tvnombreareas.getText().toString().isEmpty() ||
                        etnombreef.getText().toString().isEmpty() ||
                        etAltoc.getText().toString().isEmpty() ||
                        etRadioc.getText().toString().isEmpty() ||
                        etLadosn.getText().toString().isEmpty() ||
                        etCantidad.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese todos los valores requeridos 4", Toast.LENGTH_SHORT).show();
                    return;
                }

                values.put(Utilidades.COLUMN_NOMBRE_AREA, tvnombreareas.getText().toString());
                values.put(Utilidades.COLUMN_NOMBRE_EF, etnombreef.getText().toString());
                values.put(Utilidades.COLUMN_DIMENSION, 2);
                values.put(Utilidades.COLUMN_ALTO_C, etAltoc.getText().toString());
                values.put(Utilidades.COLUMN_RADIO_C, etRadioc.getText().toString());
                values.put(Utilidades.COLUMN_LADOS, etLadosn.getText().toString());
                values.put(Utilidades.COLUMN_ALMACENAMIENTO, 2);
                values.put(Utilidades.COLUMN_CANTIDAD, etCantidad.getText().toString());

                ss=Operaciones.ssEFc(Double.parseDouble(etRadioc.getText().toString()));

                sg=Operaciones.sgEF(ss,Double.parseDouble(etAnchor.getText().toString()));
                values.put(Utilidades.COLUMN_SS, ss);
                values.put(Utilidades.COLUMN_SG, sg);
                ssn = Operaciones.ssN(ss,Integer.parseInt(etCantidad.getText().toString()));
                ssnh = Operaciones.ssNH(ssn,Double.parseDouble(etAltoc.getText().toString()));
                values.put(Utilidades.COLUMN_SSN, ssn);
                values.put(Utilidades.COLUMN_SSNH, ssnh);

                values.put(Utilidades.COLUMN_CF_AREA, idArea);
            }
        }



        Long idResultante = db.insert(Utilidades.TABLE_EF, Utilidades.COLUMN_ID_EF, values);
        Toast.makeText(getApplicationContext(), "ID Registro: " + idResultante, Toast.LENGTH_SHORT).show();

        etnombreef.setText("");
        etLargor.setText("");
        etAnchor.setText("");
        etAltor.setText("");
        etAltoc.setText("");
        etRadioc.setText("");
        etLadosn.setText("");
        etAnchoal.setText("");
        etLargoal.setText("");
        etAltoal.setText("");
        etCantidad.setText("");
        etCantidadal.setText("");

        db.close();
        double K=calcularK();
        SharedPreferences sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Kcode", String.valueOf(K));
        editor.apply();





    }


    public double CompararSSN (double ssn, double sg){
        double comp = ssn/sg;
        double ssncomp=0;

        if (comp >= 0.3){
            ssncomp = ssn;
        }else {
            ssncomp = 0;
        }

        return ssncomp;
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

    public double calcularK(){


        SharedPreferences sharedPreferencesnombreplanta = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombrePlanta = sharedPreferencesnombreplanta.getString(KEY_NAME, "PLANTA 1");
        //Toast.makeText(getApplicationContext(), "planta " + nombrePlanta, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombreArea = sharedPreferencesnombrearea.getString(KEY_NAMEA, "AREA 1");
        //Toast.makeText(getApplicationContext(), "area " + nombreArea, Toast.LENGTH_SHORT).show();



        int idPlanta = obtenerIdPlanta(nombrePlanta);
        int idArea = obtenerIdArea(nombreArea);
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
    public double obtenerSSNTotalEF(int idPlanta, String s) {

        double sumaSSN = 0;

        dbelementos elementoFijoDb = new dbelementos(getApplicationContext());
        SQLiteDatabase db = elementoFijoDb.getReadableDatabase();

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