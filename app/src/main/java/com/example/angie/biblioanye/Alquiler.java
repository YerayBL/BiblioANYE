package com.example.angie.biblioanye;

/**
 * Created by Angie on 06/02/2018.
 * Objeto encargado de recoger los datos de la tabla ALQUILER
 */

public class Alquiler {

    private String dniCliente;
    private String idLibro;
    private int diasAlquiler;
    private int precioDia;
    private long fechaAlquiler;

    public Alquiler(String dni, String lib, int dias, int precio, long fecha) {
        this.dniCliente = dni;
        this.idLibro = lib;
        this.diasAlquiler = dias;
        this.precioDia = precio;
        this.fechaAlquiler = fecha;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public int getDiasAlquiler() {
        return diasAlquiler;
    }

    public void setDiasAlquiler(int diasAlquiler) {
        this.diasAlquiler = diasAlquiler;
    }

    public int getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(int precioDia) {
        this.precioDia = precioDia;
    }

    public long getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(long fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

}
