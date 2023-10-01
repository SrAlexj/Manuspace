package Entidades;

public class ElementoMovil {
    private String nombrearea;
    private String nombreem;
    private int tipo;
    private double largoea;
    private double anchoea;
    private double altoea;
    private int cantidadea;
    private int cantidadop;
    private double ss;
    private double ssn;
    private double ssh;

    public ElementoMovil() {
    }

    public ElementoMovil(String nombrearea, String nombreem, int tipo, double largoea, double anchoea, double altoea, int cantidadea, int cantidadop) {
        this.nombrearea = nombrearea;
        this.nombreem = nombreem;
        this.tipo = tipo;
        this.largoea = largoea;
        this.anchoea = anchoea;
        this.altoea = altoea;
        this.cantidadea = cantidadea;
        this.cantidadop = cantidadop;
    }

    public double getSs() {
        return ss;
    }

    public void setSs(double ss) {
        this.ss = ss;
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

    public String getNombrearea() {
        return nombrearea;
    }

    public void setNombrearea(String nombrearea) {
        this.nombrearea = nombrearea;
    }

    public String getNombreem() {
        return nombreem;
    }

    public void setNombreem(String nombreem) {
        this.nombreem = nombreem;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getLargoea() {
        return largoea;
    }

    public void setLargoea(double largoea) {
        this.largoea = largoea;
    }

    public double getAnchoea() {
        return anchoea;
    }

    public void setAnchoea(double anchoea) {
        this.anchoea = anchoea;
    }

    public double getAltoea() {
        return altoea;
    }

    public void setAltoea(double altoea) {
        this.altoea = altoea;
    }

    public int getCantidadea() {
        return cantidadea;
    }

    public void setCantidadea(int cantidadea) {
        this.cantidadea = cantidadea;
    }

    public int getCantidadop() {
        return cantidadop;
    }

    public void setCantidadop(int cantidadop) {
        this.cantidadop = cantidadop;
    }
}
