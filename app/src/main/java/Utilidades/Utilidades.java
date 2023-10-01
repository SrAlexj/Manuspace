package Utilidades;

public class Utilidades {

    public static final String DATABASE_NAME = "mi_base_de_datos";
    public static final int DATABASE_VERSION = 19;
    public static final String TABLE_EF = "elementos_fijos";
    public static final String COLUMN_ID_EF = "id_ef";
    public static final String COLUMN_NOMBRE_AREA = "nombre_area";
    public static final String COLUMN_NOMBRE_EF = "nombre_ef";
    public static final String COLUMN_DIMENSION = "dimension";
    public static final String COLUMN_LARGO_R = "largo_r";
    public static final String COLUMN_ANCHO_R = "ancho_r";
    public static final String COLUMN_ALTO_R = "alto_r";
    public static final String COLUMN_ALTO_C = "alto_c";
    public static final String COLUMN_RADIO_C = "radio_c";
    public static final String COLUMN_LADOS = "lados";
    public static final String COLUMN_ALMACENAMIENTO = "almacenamiento";
    public static final String COLUMN_LARGO_ALM = "largo_alm";
    public static final String COLUMN_ANCHO_ALM = "ancho_alm";
    public static final String COLUMN_ALTO_ALM = "alto_alm";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_CANTIDAD_ALM = "cantidad_alm";
    public static final String COLUMN_CF_AREA = "id_area";
    public static final String COLUMN_SS = "s_estatica";
    public static final String COLUMN_SS_AL = "s_estatica_al";
    public static final String COLUMN_SG = "s_gravitacional";
    public static final String COLUMN_SE = "s_evolucion";
    public static final String COLUMN_SE_AL = "s_evolucion_al";
    public static final String COLUMN_SUMA = "sumatoria";
    public static final String COLUMN_SSN = "ssn";
    public static final String COLUMN_SSNH = "ssnh";
    public static final String COLUMN_PORCENTAJE = "porcentaje_alm";


    //ElementoMoviles//
    public static final String COLUMN_ID_EM = "id_em";
    public static final String TABLE_EM = "elementos_moviles";
    public static final String COLUMN_NOMBRE_EM = "nombre_em";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_LARGO_EA = "largo_ea";
    public static final String COLUMN_ANCHO_EA = "ancho_ea";
    public static final String COLUMN_ALTO_EA = "alto_ea";
    public static final String COLUMN_CANTIDAD_EA = "cantidad_ea";
    public static final String COLUMN_CANTIDAD_OP = "cantidad_op";


    //Areas//
    public static final String COLUMN_ID_AREAS = "id_areas";
    public static final String TABLE_AREAS = "areas";
    public static final String COLUMN_AREA_DE_AREA = "area_de_areas";

    public static final String COLUMN_CF_PLANTA = "planta_id";

    //Areas//
    public static final String COLUMN_ID_PLANTAS = "id_plantas";
    public static final String TABLE_PLANTAS = "plantas";
    public static final String COLUMN_NOMBRE_PLANTA = "nombre_planta";




    public final static String CREAR_TABLA_ELEMENTO_FIJO ="CREATE TABLE "+ TABLE_EF +" ( " +
            COLUMN_ID_EF+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOMBRE_AREA+" TEXT,"+
            COLUMN_NOMBRE_EF+" TEXT, "+
            COLUMN_DIMENSION+" INTEGER, "+
            COLUMN_LARGO_R +" REAL, "+
            COLUMN_ANCHO_R +" REAL, "+
            COLUMN_ALTO_R+ " REAL, "+
            COLUMN_ALTO_C+" REAL, "+
            COLUMN_RADIO_C+" REAL, "+
            COLUMN_LADOS+" INTEGER, " +
            COLUMN_ALMACENAMIENTO+" INTEGER, "+
            COLUMN_LARGO_ALM+" REAL, "+
            COLUMN_ANCHO_ALM+" REAL, "+
            COLUMN_ALTO_ALM+" REAL, "+
            COLUMN_CANTIDAD+" INTEGER, "+
            COLUMN_CANTIDAD_ALM+" INTEGER, "+
            COLUMN_SS+" REAL, "+
            COLUMN_SS_AL+" REAL, "+
            COLUMN_SG+" REAL, "+
            COLUMN_SE+" REAL, "+
            COLUMN_SE_AL+" REAL, "+
            COLUMN_SUMA+" REAL, "+
            COLUMN_SSN+" REAL, "+
            COLUMN_SSNH+" REAL, "+
            COLUMN_PORCENTAJE+" REAL, "+
            COLUMN_CF_AREA + " INTEGER, "+
            "FOREIGN KEY (" + COLUMN_CF_AREA + ") REFERENCES " + TABLE_AREAS + "(" + COLUMN_ID_AREAS + "))";

    public final static String CREAR_TABLA_ELEMENTO_MOVIL ="CREATE TABLE "+ TABLE_EM +" ( " +
            COLUMN_ID_EM+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOMBRE_AREA+" TEXT,"+
            COLUMN_NOMBRE_EM+" TEXT, "+
            COLUMN_TIPO+" INTEGER, "+
            COLUMN_LARGO_EA +" REAL, "+
            COLUMN_ANCHO_EA +" REAL, "+
            COLUMN_ALTO_EA+ " REAL, "+
            COLUMN_CANTIDAD_EA+" INTEGER, "+
            COLUMN_CANTIDAD_OP+" INTEGER, "+
            COLUMN_SS+" REAL, "+
            COLUMN_SSN+" REAL, "+
            COLUMN_SSNH+" REAL, "+
            COLUMN_CF_AREA + " INTEGER, "+
            "FOREIGN KEY (" + COLUMN_CF_AREA + ") REFERENCES " + TABLE_AREAS + "(" + COLUMN_ID_AREAS + "))";


    public final static String CREAR_TABLA_AREAS ="CREATE TABLE "+ TABLE_AREAS +" ( " +
            COLUMN_ID_AREAS+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOMBRE_AREA+" TEXT, "+
            COLUMN_AREA_DE_AREA+" REAL, "+
            COLUMN_CF_PLANTA + " INTEGER, "+
            "FOREIGN KEY (" + COLUMN_CF_PLANTA + ") REFERENCES " + TABLE_PLANTAS + "(" + COLUMN_ID_PLANTAS + "))";

    public final static String CREAR_TABLA_PLANTAS ="CREATE TABLE "+ TABLE_PLANTAS +" ( " +
            COLUMN_ID_PLANTAS+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOMBRE_PLANTA+" TEXT)";
}


