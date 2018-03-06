package com.example.angie.biblioanye;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Angie on 06/02/2018.
 * Clase encargada de añadir datos de LIBROS
 * y CLIENTES autogenerados para hacer pruebas
 */

public class Eliminar extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        botonAñadir(); // Añade datos de prueba a la base de datos

        botonEliminar(); // Elimina todos los datos de la base de datos
    }

    // Añade datos en tablas
    public void botonAñadir() {
        final Button b1 = (Button) findViewById(R.id.añadir);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                añadirClientes();
                añadirLibros();
                Toast.makeText(getApplicationContext(), "Añadiendo datos prueba ALQUILER, CLIENTES y LIBROS", Toast.LENGTH_LONG).show();

            }
        });
    }

    // Elimina datos de tablas
    public void botonEliminar() {
        final Button b1 = (Button) findViewById(R.id.eliminar);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseDatos asist = new BaseDatos(getApplicationContext());
                asist.eliminarAlquileres();
                asist.eliminarClientes();
                asist.eliminarLibros();
                Toast.makeText(getApplicationContext(), "Eliminando datos de tablas ALQUILER, CLIENTES y LIBROS", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Inserts para CLIENTES
    public void añadirClientes() {
        BaseDatos asist = new BaseDatos(getApplicationContext());
        Cliente c = new Cliente("Angie Castilla", "610381960", "44332211q");
        asist.añadirCliente(c);
        c = new Cliente("Yeray Blanco", "655768093", "12345678r");
        asist.añadirCliente(c);
        c = new Cliente("Nanis Matallana", "632845642", "87654321y");
        asist.añadirCliente(c);
        c = new Cliente("Joan Trian", "64722734", "53667718u");
        asist.añadirCliente(c);
        c = new Cliente("Laura Tuno", "621267213", "42384459t");
        asist.añadirCliente(c);
    }

    // Inserts para LIBROS
    public void añadirLibros() {
        BaseDatos asist = new BaseDatos(getApplicationContext());
        Libro lib = new Libro("Novela fantástica", "El Alquimista", "Paulo Coelho", "libro01", "alquimista", 7);
        asist.añadirLibro(lib);
        lib = new Libro("Novela Erótica", "Amores Perros", "Paul Julian Smith", "libro02", "amoresperros", 5);
        asist.añadirLibro(lib);
        lib = new Libro("Realismo mágico", "Cien Años de Soledad", "Gabriel García Márquez", "libro03", "cien", 8);
        asist.añadirLibro(lib);
        lib = new Libro("Literatura experimental", "La Colmena", "Camilo José Cela", "libro04", "colmena", 4);
        asist.añadirLibro(lib);
        lib = new Libro("Novela romántica", "No culpes al Karma", "Laura Norton", "libro05", "noculpes", 6);
        asist.añadirLibro(lib);
        lib = new Libro("Literatura fantástica", "Eclipse", "Stephenie Meyer", "libro06", "eclipse", 7);
        asist.añadirLibro(lib);
    }

}
