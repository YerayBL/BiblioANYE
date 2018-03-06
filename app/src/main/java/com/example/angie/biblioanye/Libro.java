package com.example.angie.biblioanye;

/**
 * Created by Angie on 06/02/2018.
 * Objeto encargado de recoger los datos de la tabla Libro
 */

public class Libro {

    private String descripcion;
    private String titulo;
    private String autor;
    private String id;
    private String imagen;
    private int precioDia = 0;

    public Libro(String descripcion, String titulo, String autor, String id, String img, int precioDia) {
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.autor = autor;
        this.id = id;
        this.imagen = img;
        this.precioDia = precioDia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getId() {
        return id.toUpperCase();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(int precioDia) {
        this.precioDia = precioDia;
    }

}
