package com.example.angie.biblioanye;

/**
 * Created by Angie on 06/02/2018.
 * Objeto encargado de recoger los datos de tabla CLIENTES
 */

public class Cliente {

    private String nom;
    private String telefon;
    private String dniCliente;

    public Cliente(String nom, String tel, String dni) {
        this.nom = nom;
        this.telefon = tel;
        this.dniCliente = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getDni() {
        return dniCliente;
    }

    public void setDni(String dni) {
        this.dniCliente = dni;
    }

}
