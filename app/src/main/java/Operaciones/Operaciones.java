package Operaciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

import Utilidades.Utilidades;
import dbs.dbelementos;

public class Operaciones {

    public static double ssEF(double largo, double ancho){
        double ss;
        ss= largo * ancho;

        return ss;
    }
    public static double sgEF(double ss, double n){
        double sg;
        sg= ss* n;

        return sg;
    }
    public static double ssEFc(double radio){
        double ss;
        ss = Math.PI * radio*radio;
        return ss;
    }

    public static double ssN(double ss, int n){
        double ssn;
        ssn = ss*n;
        return ssn;
    }
    public static double ssNH(double ssn, double h){
        double ssnh;
        ssnh = ssn*h;
        return ssnh;
    }



















}
