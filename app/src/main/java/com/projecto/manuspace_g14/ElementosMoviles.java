package com.projecto.manuspace_g14;

import static com.projecto.manuspace_g14.Areas.KEY_NAMEA;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import Operaciones.Operaciones;
import Utilidades.Utilidades;
import dbs.dbelementos;

public class ElementosMoviles extends AppCompatActivity {

    private RadioButton rbequipoacarreo;
    private RadioButton rboperario;

    private RadioGroup rgtipo;

    private LinearLayout lyequipoacarreo;
    private  LinearLayout lyoperarios;
    private EditText etnombreem;
    private EditText etlargoea;
    private EditText etanchoea;
    private EditText etaltoea;
    private EditText etcantidadea;
    private EditText etcantidadop;


    private TextView tvnombrearea;
    private TextView tvnombreelementomovil;
    private SharedPreferences sharedPreferences;


    static final String KEY_NAMEEM = "nombreelementomovil";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos_moviles);



        SharedPreferences sharedPreferencesnombrearea = getSharedPreferences(NAMES, Context.MODE_PRIVATE);
        String nombre = sharedPreferencesnombrearea.getString(KEY_NAMEA, "AREA 1");

        tvnombrearea = findViewById(R.id.tvnombrearea);
        tvnombrearea.setText(nombre);

        rbequipoacarreo = findViewById(R.id.rbequiposacaem);
        rboperario = findViewById(R.id.rboperario);

        rgtipo = findViewById(R.id.rgtipo);

        lyequipoacarreo = findViewById(R.id.lyequipoacarreo);
        lyoperarios = findViewById(R.id.lyoperarios);

        etlargoea = findViewById(R.id.largoem);
        etanchoea = findViewById(R.id.anchoem);
        etaltoea = findViewById(R.id.altoem);
        etcantidadea = findViewById(R.id.cantidadem);
        etcantidadop = findViewById(R.id.cantoperarioem);
        etnombreem = findViewById(R.id.nombreem);


        tvnombreelementomovil = findViewById(R.id.tvelementomovil);



        sharedPreferences = getSharedPreferences(NAMES, Context.MODE_PRIVATE);


        etnombreem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String name = s.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAMEEM, name);
                editor.apply();


                tvnombreelementomovil.setText(name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String savedName = sharedPreferences.getString(KEY_NAMEEM, "ELEMENTO MOVIL 1");
        tvnombreelementomovil.setText(savedName);


    }

    public void mostrartipo (View view){

        boolean checked = ((RadioButton)view).isChecked();

        if (view.getId()==R.id.rbequiposacaem){
            if (checked){
                lyequipoacarreo.setVisibility(View.VISIBLE);
                lyoperarios.setVisibility(View.INVISIBLE);
            }

        }else {
            if (view.getId()==R.id.rboperario) {
                if (checked) {
                    lyequipoacarreo.setVisibility(View.INVISIBLE);
                    lyoperarios.setVisibility(View.VISIBLE);

                }
            }
        }

    }

    public void atrasem (View view) {

        Intent intent = new Intent(ElementosMoviles.this, Areas.class);

        startActivity(intent);
    }

    public void onClick(View view) {
        agregarElementoMovil();


    }

    @SuppressLint("Range")
    private void agregarElementoMovil() {
        String nombreElemento = tvnombrearea.getText().toString();



        dbelementos dbnombre = new dbelementos(ElementosMoviles.this);
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



        dbelementos elementoMovildb = new dbelementos(ElementosMoviles.this);
        SQLiteDatabase db = elementoMovildb.getWritableDatabase();

        ContentValues values = new ContentValues();

        double ss = 0;

        double ssn = 0;
        double ssnh = 0;

        int rbCheckedTipo = rgtipo.getCheckedRadioButtonId();


        if (rbCheckedTipo == R.id.rbequiposacaem) {

            // Validación
            if (tvnombrearea.getText().toString().isEmpty() ||
                    etnombreem.getText().toString().isEmpty() ||
                    etlargoea.getText().toString().isEmpty() ||
                    etanchoea.getText().toString().isEmpty() ||
                    etaltoea.getText().toString().isEmpty() ||
                    etcantidadea.getText().toString().isEmpty() ){
                Toast.makeText(getApplicationContext(), "Ingrese todos los valores requeridos 1", Toast.LENGTH_SHORT).show();
                return;
            }

            values.put(Utilidades.COLUMN_NOMBRE_AREA, tvnombrearea.getText().toString());
            values.put(Utilidades.COLUMN_NOMBRE_EM, etnombreem.getText().toString());
            values.put(Utilidades.COLUMN_TIPO, 1);
            values.put(Utilidades.COLUMN_LARGO_EA, etlargoea.getText().toString());
            values.put(Utilidades.COLUMN_ANCHO_EA, etanchoea.getText().toString());
            values.put(Utilidades.COLUMN_ALTO_EA, etaltoea.getText().toString());
            values.put(Utilidades.COLUMN_CANTIDAD_EA, etcantidadea.getText().toString());

            ss= Operaciones.ssEF(Double.parseDouble(etlargoea.getText().toString()),Double.parseDouble(etanchoea.getText().toString()));
            ssn=Operaciones.ssN(ss,Integer.parseInt(etcantidadea.getText().toString()));
            ssnh=Operaciones.ssNH(ssn,Double.parseDouble(etaltoea.getText().toString()));
            values.put(Utilidades.COLUMN_SS, ss);
            values.put(Utilidades.COLUMN_SSN, ssn);
            values.put(Utilidades.COLUMN_SSNH, ssnh);
            values.put(Utilidades.COLUMN_CF_AREA, idArea);
        } else {
            // Validación
            if (tvnombrearea.getText().toString().isEmpty() ||
                    etnombreem.getText().toString().isEmpty() ||
                    etcantidadop.getText().toString().isEmpty() ){
                Toast.makeText(getApplicationContext(), "Ingrese todos los valores requeridos 2", Toast.LENGTH_SHORT).show();
                return;
            }

            values.put(Utilidades.COLUMN_NOMBRE_AREA, tvnombrearea.getText().toString());
            values.put(Utilidades.COLUMN_NOMBRE_EM, etnombreem.getText().toString());
            values.put(Utilidades.COLUMN_TIPO, 2);
            values.put(Utilidades.COLUMN_CANTIDAD_OP, etcantidadop.getText().toString());
            values.put(Utilidades.COLUMN_SS, 0.5);
            values.put(Utilidades.COLUMN_ALTO_EA, 1.65);
            ssn=Operaciones.ssN(0.5,Integer.parseInt(etcantidadop.getText().toString()));
            ssnh=Operaciones.ssNH(ssn,1.65);
            values.put(Utilidades.COLUMN_SSN, ssn);
            values.put(Utilidades.COLUMN_SSNH, ssnh);
            values.put(Utilidades.COLUMN_CF_AREA, idArea);
        }



        Long idResultante = db.insert(Utilidades.TABLE_EM, Utilidades.COLUMN_ID_EM, values);
        Toast.makeText(getApplicationContext(), "ID Registro: " + idResultante, Toast.LENGTH_SHORT).show();

        etnombreem.setText("");
        etlargoea.setText("");
        etanchoea.setText("");
        etaltoea.setText("");
        etcantidadea.setText("");
        etcantidadop.setText("");


        db.close();
    }


}