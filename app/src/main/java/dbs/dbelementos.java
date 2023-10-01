package dbs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Utilidades.Utilidades;


public class dbelementos extends SQLiteOpenHelper {


    public dbelementos( Context context) {
        super(context, Utilidades.DATABASE_NAME, null, Utilidades.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREAR_TABLA_PLANTAS);
        db.execSQL(Utilidades.CREAR_TABLA_AREAS);
        db.execSQL(Utilidades.CREAR_TABLA_ELEMENTO_FIJO);
        db.execSQL(Utilidades.CREAR_TABLA_ELEMENTO_MOVIL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_PLANTAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_AREAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_EF);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_EM);
        onCreate(db);
    }
}


