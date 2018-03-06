package com.example.angie.biblioanye;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Angie on 06/02/2018.
 * Clase encargada de ejecutar las acciones sobre base de datos,
 * mostrar, actualizar, eliminar y devolver datos seleccionados
 */

public class BaseDatos extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "myDB";

    public BaseDatos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists CLIENTES("
                + "dniCliente TEXT primary key,"
                + "nombreCliente TEXT,"
                + "tlfCliente TEXT,"
                + "libroEnAlquiler INTEGER);");

        db.execSQL("CREATE TABLE if not exists LIBROS  (" +
                "idLibro TEXT primary key," +
                "descripcion TEXT," +
                "titulo TEXT," +
                "autor TEXT," +
                "disponible INTEGER," +
                "imagen TEXT," +
                "precioDia INTEGER);");

        db.execSQL("CREATE TABLE if not exists ALQUILER( " +
                "dni_Cliente TEXT not null," +
                "idLibro TEXT not null," +
                "diasAlquiler INTEGER not null," +
                "precioDia INTEGER," +
                "fecha INTEGER," +
                "CONSTRAINT fk_Cliente FOREIGN KEY (dni_Cliente) REFERENCES CLIENTES (dniCliente)," +
                "CONSTRAINT fk_idLibro FOREIGN KEY (idLibro) REFERENCES LIBROS (idLibro));");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Inserts de distintas tablas en base de datos
     */
    public void añadirCliente(Cliente c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombreCliente", c.getNom());
        contentValues.put("tlfCliente", c.getTelefon());
        contentValues.put("dniCliente", c.getDni().toUpperCase());
        contentValues.put("libroEnAlquiler", 0);
        db.insert("CLIENTES", null, contentValues);
        db.close();
    }

    public void añadirLibro(Libro lib) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("descripcion", lib.getDescripcion());
        contentValues.put("titulo", lib.getTitulo());
        contentValues.put("autor", lib.getAutor());
        contentValues.put("idLibro", lib.getId());
        contentValues.put("disponible", 1);
        String imagen;
        if (lib.getImagen().equals("")) {
            imagen = "no_disponible";
        } else {
            imagen = lib.getImagen();
        }
        contentValues.put("precioDia", lib.getPrecioDia());
        contentValues.put("imagen", imagen);
        db.insert("LIBROS", null, contentValues);
        db.close();
    }

    public void añadirAlquiler(Alquiler a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dni_Cliente", a.getDniCliente());
        contentValues.put("idLibro", a.getIdLibro());
        contentValues.put("diasAlquiler", a.getDiasAlquiler());
        contentValues.put("precioDia", a.getPrecioDia());
        contentValues.put("fecha", a.getFechaAlquiler());
        db.insert("ALQUILER", null, contentValues);
        db.close();
    }

    /**
     * Consultas de todos los elementos de tablas en base de datos
     */
    public String consultarClientes() {
        String s = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from CLIENTES", null);
        //startManagingCursor(cursor);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String nombre = cursor.getString(cursor.getColumnIndex("nombreCliente"));
                    String numero = cursor.getString(cursor.getColumnIndex("tlfCliente"));
                    String dni = cursor.getString(cursor.getColumnIndex("dniCliente"));
                    s += nombre + "\t-\t" + numero + "\t(" + dni + ")\n";
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return s;
    }

    public String consultarLibros() {
        String s = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from LIBROS", null);
        //startManagingCursor(cursor);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    //String descripcion = cursor.getString(cursor.getColumnIndex("descripcion")); descripcion + " " +
                    String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                    String autor = cursor.getString(cursor.getColumnIndex("autor"));
                    String disp = "";
                    if (cursor.getInt(cursor.getColumnIndex("disponible")) == 1) {
                        disp = "Disponible";
                    } else if (cursor.getInt(cursor.getColumnIndex("disponible")) == 0) {
                        disp = "No disponible";
                    }
                    //System.out.println("LIBRO CONSULTADO !!: " + descripcion + " " + titulo);
                    s +=  titulo + "\t-\t" + autor + "\t(" + disp + ")\n";
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return s;
    }

    public String consultarDisponibles() {
        String s = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from LIBROS where disponible = ?", new String[]{"1"});
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    //String descripcion = cursor.getString(cursor.getColumnIndex("descripcion")); descripcion + " " +
                    String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                    String autor = cursor.getString(cursor.getColumnIndex("autor"));
                    String idLibro = cursor.getString(cursor.getColumnIndex("idLibro"));
                    s +=  titulo + "\t-\t" + autor + "\t(" + idLibro + ")\n";
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return s;
    }

    public String consultarAlquileres() {
        String s = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ALQUILER", null);
        //startManagingCursor(cursor);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String dni = cursor.getString(cursor.getColumnIndex("dni_Cliente"));
                    String idLibro = cursor.getString(cursor.getColumnIndex("idLibro"));
                    int dias = cursor.getInt(cursor.getColumnIndex("diasAlquiler"));
                    int precio = cursor.getInt(cursor.getColumnIndex("precioDia"));
                    long fecha = cursor.getLong(cursor.getColumnIndex("fecha"));

                    Alquiler a = new Alquiler(dni, idLibro, dias, precio, fecha);
                    Cliente c = filtrarCliente(new String[]{a.getDniCliente().toUpperCase()});
                    Libro lib = filtrarLibro2(new String[]{a.getIdLibro()});

                    String nombre = c.getNom();
                    //String descripcion = lib.getDescripcion();+ descripcion + " "
                    String titulo = lib.getTitulo();
                    int total = a.getPrecioDia() * a.getDiasAlquiler();

                    //System.out.println("MOSTRANDO ALQUILERES !!: " + nombre + " " + descripcion + " " + titulo);
                    s += nombre + "\t-\t"  + titulo + ",\t" + dias + " días\t(" + total + "€)\n";
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return s;
    }

    public long consultarFecha(String[] idLibro) {
        long fecha = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select fecha from ALQUILER where idLibro = ?", idLibro);
        //startManagingCursor(cursor);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    fecha = cursor.getLong(cursor.getColumnIndex("fecha"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return fecha;
    }

    public int consultarDias(String[] idLibro) {
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select diasAlquiler from ALQUILER where idLibro = ?", idLibro);
        //startManagingCursor(cursor);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    i = cursor.getInt(cursor.getColumnIndex("diasAlquiler"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return i;
    }

    /**
     * Consulta detallada de las distintas tablas
     */
    public Cliente filtrarCliente(String[] dni) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from CLIENTES where dniCliente = ?", dni);
        Cliente c = new Cliente("", "", "");
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String nombre = cursor.getString(cursor.getColumnIndex("nombreCliente"));
                    String numero = cursor.getString(cursor.getColumnIndex("tlfCliente"));
                    String dn = cursor.getString(cursor.getColumnIndex("dniCliente"));
                    c = new Cliente(nombre, numero, dn);
                    System.out.println("CLIENTE FILTRADO: " + c.getNom() + " DNI:" + c.getDni() + " TLF:" + c.getTelefon() + "!!!");
                } while (cursor.moveToNext());
            }
        }
        return c;
    }

    public Libro filtrarLibro(String[] idLibro) {

        Libro lib = new Libro("", "", "", "", "", 0);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from LIBROS where idLibro = ?", idLibro);
        String s = idLibro.toString() + " ";
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    //System.out.println("Entra cursor!!!!!!!!    "+cursor.getInt(cursor.getColumnIndex("disponible")));
                    if (cursor.getInt(cursor.getColumnIndex("disponible")) == 1) {
                        System.out.println("Entra disponible!!");
                        String IdLibro = cursor.getString(cursor.getColumnIndex("idLibro"));
                        String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                        String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                        String autor = cursor.getString(cursor.getColumnIndex("autor"));
                        String imagen = cursor.getString(cursor.getColumnIndex("imagen"));
                        int precio = cursor.getInt(cursor.getColumnIndex("precioDia"));
                        System.out.println(descripcion + " " + titulo + " " + autor + " " + idLibro + " " + imagen + " !!");
                        lib = new Libro(descripcion, titulo, autor, IdLibro, imagen, precio);
                    }
                } while (cursor.moveToNext());
            }
        }
        return lib;
    }

    public Libro filtrarLibro2(String[] idLibro) {
        Libro lib = new Libro("", "", "", "", "", 0);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from LIBROS where idLibro = ?", idLibro);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("idLibro"));
                    String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                    String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                    String autor = cursor.getString(cursor.getColumnIndex("autor"));
                    String imagen = cursor.getString(cursor.getColumnIndex("imagen"));
                    int precio = cursor.getInt(cursor.getColumnIndex("precioDia"));
                    lib = new Libro(descripcion, titulo, autor, id, imagen, precio);
                } while (cursor.moveToNext());
            }
        }
        return lib;
    }

    /**
     * Actualizaciones en la base de datos
     */
    public void modificarLibro(Libro lib, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("disponible", i);
        try {
            String[] args = {lib.getId()};
            db.update("LIBROS", contentValues, "idLibro=?", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    /**
     * Eliminar datos de las tablas
     */
    public void eliminarAlquileres() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from ALQUILER");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarClientes() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from CLIENTES");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarLibros() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from LIBROS");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarAlquiler(String[] lib) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from ALQUILER where IdLibro = ?", lib);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}