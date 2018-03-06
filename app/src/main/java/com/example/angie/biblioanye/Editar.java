package com.example.angie.biblioanye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Created by Angie on 06/02/2018.
 * Ventana encargada de recoger datos para crear CLIENTE
 * o LIBRO seleccionado por el usuario e insertar en las tablas.
 */

public class Editar extends AppCompatActivity {

    private EditText nom, num, dni, des, tit, aut, idL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        cargarPestañas();

        botonCliente();

        botonLibro();

        botonVolver();
    }

    // Crea contenedor de pestañas
    private void cargarPestañas() {
        TabHost host = (TabHost) findViewById(R.id.TabHost1);
        host.setup();

        //Crea pestaña1
        TabHost.TabSpec allScoresTab = host.newTabSpec("allTab");
        allScoresTab.setIndicator("Añadir cliente", null);
        allScoresTab.setContent(R.id.añadirCliente);
        host.addTab(allScoresTab);

        //Crea pestaña2
        TabHost.TabSpec friendsScoresTab = host.newTabSpec("friendsTab");
        friendsScoresTab.setIndicator("Añadir Libro", null);
        friendsScoresTab.setContent(R.id.añadirLibro);
        host.addTab(friendsScoresTab);
    }

    // Listener para añadir CLIENTE a base de datos al pulsar botón
    private void botonCliente() {
        final Button button = (Button) findViewById(R.id.boton_cliente);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nom = (EditText) findViewById(R.id.EditText_Nombre);
                String nomb = nom.getText().toString();
                num = (EditText) findViewById(R.id.EditText_Numero);
                String nume = num.getText().toString();
                dni = (EditText) findViewById(R.id.EditText_DNI);
                String dn = dni.getText().toString();

                if (nom.getText().toString().equals("") || nom.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce nombre de cliente", Toast.LENGTH_LONG).show();
                } else if (num.getText().toString().length() < 9 || num.getText().toString().length() > 9) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce teléfono válido", Toast.LENGTH_LONG).show();
                } else if (dni.getText().toString().length() < 9 || dni.getText().toString().length() > 9) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce DNI válido", Toast.LENGTH_LONG).show();
                } else {
                    Cliente c = new Cliente(nomb, nume, dn);
                    BaseDatos asist = new BaseDatos(getApplicationContext());
                    asist.añadirCliente(c);
                    nom.setText(" ");
                    num.setText(" ");
                    dni.setText(" ");
                    Toast.makeText(getApplicationContext(), "Añadido: " + nomb + ", " + dn, Toast.LENGTH_LONG).show();
                    System.out.println("Añadido: " + nomb + " " + dn + " " + nume);
                }
            }
        });
    }

    // Listener para añadir LIBRO a base de datos al pulsar botón
    private void botonLibro() {
        final Button button2 = (Button) findViewById(R.id.button_libro);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                des = (EditText) findViewById(R.id.EditText_Descripcion);
                String desc = des.getText().toString();
                tit = (EditText) findViewById(R.id.EditText_Titulo);
                String titu = tit.getText().toString();
                aut = (EditText) findViewById(R.id.EditText_Autor);
                String autor = aut.getText().toString();
                idL = (EditText) findViewById(R.id.EditText_IdLibro);
                String idLibro = idL.getText().toString();

                if (des.getText().toString().equals("") || des.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce Descripcion del Libro", Toast.LENGTH_LONG).show();
                } else if (tit.getText().toString().equals("") || tit.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce Titulo del Libro", Toast.LENGTH_LONG).show();
                } else if (aut.getText().toString().equals("") || aut.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce Autor del Libro   ", Toast.LENGTH_LONG).show();
                } else if (idL.getText().toString().equals("") || idL.getText().toString().length() < 7) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce Id Libro valido", Toast.LENGTH_LONG).show();
                } else {
                    Libro lib = new Libro(desc, titu, autor, idLibro, "", 20);
                    BaseDatos asist = new BaseDatos(getApplicationContext());
                    asist.añadirLibro(lib);
                    des.setText(" ");
                    tit.setText(" ");
                    aut.setText(" ");
                    idL.setText(" ");
                    Toast.makeText(getApplicationContext(), "Añadido: " + desc + " " + titu + ", " + autor, Toast.LENGTH_LONG).show();
                    System.out.println("Añadido: " + desc + " " + titu + " " + autor + " " + idLibro);
                }
            }
        });
    }

    // Vuelve al menú principal
    private void botonVolver() {
        final Button b = (Button) findViewById(R.id.volver);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Editar.this, MenuPrincipal.class));
            }
        });

        final Button b1 = (Button) findViewById(R.id.volver1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Editar.this, MenuPrincipal.class));
            }
        });

    }

}
