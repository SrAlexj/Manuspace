package Entidades;


import java.io.Serializable;

public class ElementoFijo implements Serializable {

    private String nombrearea;
    private String nombreef;
    private int dimensionamiento;
    private double largor;
    private double anchor;
    private double altor;

    private double altoc;
    private double radioc;

    private int ladosn;
    private int almacenamiento;
    private double largoal;
    private double anchoal;
    private double altoal;
    private int cantidadef;
    private int cantidadalm;
    private double ss;
    private double sg;
    private double se;
    private double suma;
    private double ssn;
    private double ssh;
    private double porcentaje;


    public ElementoFijo() {

    }

    public ElementoFijo(String nombrearea, String nombreef, int dimensionamiento, double largor, double anchor, double altor, double altoc, double radioc, int ladosn, int almacenamiento, double largoal, double anchoal, int cantidadef) {
        this.nombrearea = nombrearea;
        this.nombreef = nombreef;
        this.dimensionamiento = dimensionamiento;
        this.largor = largor;
        this.anchor = anchor;
        this.altor = altor;
        this.altoc = altoc;
        this.radioc = radioc;
        this.ladosn = ladosn;
        this.almacenamiento = almacenamiento;
        this.largoal = largoal;
        this.anchoal = anchoal;
        this.cantidadef = cantidadef;
    }

    public double getAltoal() {
        return altoal;
    }

    public void setAltoal(double altoal) {
        this.altoal = altoal;
    }

    public int getCantidadalm() {
        return cantidadalm;
    }

    public void setCantidadalm(int cantidadalm) {
        this.cantidadalm = cantidadalm;
    }

    public double getSe() {
        return se;
    }

    public void setSe(double se) {
        this.se = se;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public double getSsn() {
        return ssn;
    }

    public void setSsn(double ssn) {
        this.ssn = ssn;
    }

    public double getSsh() {
        return ssh;
    }

    public void setSsh(double ssh) {
        this.ssh = ssh;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getSs() {
        return ss;
    }

    public void setSs(double ss) {
        this.ss = ss;
    }

    public double getSg() {
        return sg;
    }

    public void setSg(double sg) {
        this.sg = sg;
    }

    public String getNombrearea() {
        return nombrearea;
    }

    public void setNombrearea(String nombrearea) {
        this.nombrearea = nombrearea;
    }

    public String getNombreef() {
        return nombreef;
    }

    public void setNombreef(String nombreef) {
        this.nombreef = nombreef;
    }

    public int getDimensionamiento() {
        return dimensionamiento;
    }

    public void setDimensionamiento(int dimensionamiento) {
        this.dimensionamiento = dimensionamiento;
    }

    public double getLargor() {
        return largor;
    }

    public void setLargor(double largor) {
        this.largor = largor;
    }

    public double getAnchor() {
        return anchor;
    }

    public void setAnchor(double anchor) {
        this.anchor = anchor;
    }

    public double getAltor() {
        return altor;
    }

    public void setAltor(double altor) {
        this.altor = altor;
    }

    public double getAltoc() {
        return altoc;
    }

    public void setAltoc(double altoc) {
        this.altoc = altoc;
    }

    public double getRadioc() {
        return radioc;
    }

    public void setRadioc(double radioc) {
        this.radioc = radioc;
    }

    public int getLadosn() {
        return ladosn;
    }

    public void setLadosn(int ladosn) {
        this.ladosn = ladosn;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public double getLargoal() {
        return largoal;
    }

    public void setLargoal(double largoal) {
        this.largoal = largoal;
    }

    public double getAnchoal() {
        return anchoal;
    }

    public void setAnchoal(double anchoal) {
        this.anchoal = anchoal;
    }

    public int getCantidadef() {
        return cantidadef;
    }

    public void setCantidadef(int cantidadef) {
        this.cantidadef = cantidadef;
    }




}
