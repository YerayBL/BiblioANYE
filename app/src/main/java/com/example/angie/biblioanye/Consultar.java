package com.example.angie.biblioanye;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Angie on 06/02/2018.
 * Ventana encargada de recoger datos para crear CLIENTE
 * y LIBRO por el usuario e insertar datos en las tablas.
 */

public class Consultar extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView clientes, libros;
    private RadioGroup rlibros, rclientes;
    private String radioLibro = "", radioCliente = "";
    private RelativeLayout mostrar;
    private EditText introduce;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        rlibros = (RadioGroup) findViewById(R.id.radioLibros);
        rlibros.setOnCheckedChangeListener(this);

        rclientes = (RadioGroup) findViewById(R.id.radioClientes);
        rclientes.setOnCheckedChangeListener(this);

        mostrar = (RelativeLayout) findViewById(R.id.datosDeLibro);
        introduce = (EditText) findViewById(R.id.detalles);

        cargarPestañas(); //carga pestañas para consultar libros y clientes

        botonLibros(); // Botón para mostrar todos los libros en base de datos

        botonClientes(); // Botón para mostrar clientes en base de datos

        botonReset(); // Botón para eliminar consultas de la pantalla

        botonReset1();

        botonVolver();

        botonDetalles(); //Consultar libro al detalle
    }

    // Detecta filtro de búsqueda seleccionado
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.todosLibros:
                radioLibro = "todos";
                break;
            case R.id.disponibles:
                radioLibro = "disponibles";
                break;
            case R.id.todosClientes:
                radioCliente = "todos";
                break;
            case R.id.clientesAlquiler:
                radioCliente = "alquiler";
                break;
        }
    }

    // Crea pestañas con elementos separados
    private void cargarPestañas() {
        TabHost host = (TabHost) findViewById(R.id.TabHost1);
        host.setup();

        //Crea pestaña1
        TabHost.TabSpec allScoresTab = host.newTabSpec("allTab");
        allScoresTab.setIndicator("Consultar Libros", null);
        allScoresTab.setContent(R.id.consultarLibros);
        host.addTab(allScoresTab);

        //Crea pestaña2
        TabHost.TabSpec friendsScoresTab = host.newTabSpec("friendsTab");
        friendsScoresTab.setIndicator("Consultar Clientes", null);
        friendsScoresTab.setContent(R.id.consultarCLiente);
        host.addTab(friendsScoresTab);
    }

    // Muestra datos de tabla LIBROS según filtro de búsqueda
    private void botonLibros() {
        libros = (TextView) findViewById(R.id.tv_libros);
        final Button button1 = (Button) findViewById(R.id.button_libros);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                borrarConsultas(clientes);
                BaseDatos asist = new BaseDatos(getApplicationContext());
                String s;
                if (radioLibro.equals("todos")) {
                    s = asist.consultarLibros();
                    if (s != "") {
                        mostrarConsultas(libros, s);
                    } else {
                        mostrarConsultas(libros, "No hay Libros para mostrar");
                    }
                } else if (radioLibro.equals("disponibles")) {
                    s = asist.consultarDisponibles();
                    if (s != "") {
                        mostrarConsultas(libros, s);
                    } else {
                        mostrarConsultas(libros, "No hay Libros disponibles");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Selecciona un filtro de búsqueda para Libros", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Muestra datos de CLIENTES o ALQUILER según filtro de búsqueda
    private void botonClientes() {
        clientes = (TextView) findViewById(R.id.tv_clientes);
        final Button button3 = (Button) findViewById(R.id.button_clientes);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                borrarConsultas(libros);
                BaseDatos asist = new BaseDatos(getApplicationContext());
                String s;
                if (radioCliente.equals("todos")) {
                    s = asist.consultarClientes();
                    if (s != "") {
                        mostrarConsultas(clientes, s);
                    } else {
                        mostrarConsultas(clientes, "No hay clientes para mostrar");
                    }
                } else if (radioCliente.equals("alquiler")) {
                    s = asist.consultarAlquileres();
                    if (s != "") {
                        mostrarConsultas(clientes, s);
                    } else {
                        mostrarConsultas(clientes, "No hay alquileres para mostrar");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Selecciona un filtro de búsqueda para clientes", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Elimina datos y filtro CLIENTES o ALQUILER seleccionado por el usuario
    private void botonReset() {
        final Button button5 = (Button) findViewById(R.id.button_reset);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                borrarConsultas(clientes);
                rclientes.clearCheck();
                radioCliente = "";
            }
        });
    }

    // Elimina datos y filtro LIBROS seleccionado por el usuario
    private void botonReset1() {
        final Button button5 = (Button) findViewById(R.id.button_reset1);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                borrarConsultas(libros);
                rlibros.clearCheck();
                radioLibro = "";
            }
        });
    }

    // Devuelve al menú principal
    private void botonVolver() {
        final Button b = (Button) findViewById(R.id.volver);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Consultar.this, MenuPrincipal.class));
            }
        });

        final Button b1 = (Button) findViewById(R.id.volver1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Consultar.this, MenuPrincipal.class));
            }
        });

    }

    // Recoge datos detallados de LIBRO seleccinado
    public void botonDetalles() {
        final Button b = (Button) findViewById(R.id.button_detalles);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText detalles = (EditText) findViewById(R.id.detalles);
                BaseDatos asist = new BaseDatos(getApplicationContext());
                Libro lib = asist.filtrarLibro2(new String[]{detalles.getText().toString().toUpperCase()});
                mostrarDetalles(lib);
            }
        });
    }

    // Elimina datos devueltos de consulta
    public void borrarConsultas(TextView t) {
        t.setText("");
        t.setPadding(0, 0, 0, 0);
        t.setVisibility(View.INVISIBLE);
        t.setBackgroundColor(Color.parseColor("#000000"));
        mostrar.setVisibility(View.INVISIBLE);
        introduce.setText("");
    }

    // Muestra los datos devueltos de consulta y detalles
    public void mostrarConsultas(TextView t, String s) {
        t.setText(s);
        t.setPadding(5, 5, 5, 5);
        t.setBackgroundColor(Color.parseColor("#E0E0E0"));
        t.setVisibility(View.VISIBLE);
    }

    // Muestra datos devueltas de consulta detalles.
    public void mostrarDetalles(final Libro lib) {
        if (introduce.getText().toString().equals("") || introduce.getText().toString().length() < 7) {
            Toast.makeText(getApplicationContext(), "ERROR: Introduce Id válido", Toast.LENGTH_LONG).show();
            introduce.setText("");
        } else if (lib.getId().equals("")) {
            Toast.makeText(getApplicationContext(), "ERROR: Id no encontrado", Toast.LENGTH_LONG).show();
            introduce.setText("");
        } else {
            mostrar.setVisibility(View.VISIBLE);
            ImageView imagen = (ImageView) findViewById(R.id.imagenLibro);
            TextView datosLibro = (TextView) findViewById(R.id.descripcionLibro);
            TextView tituloLibro = (TextView) findViewById(R.id.tituloLibro);
            TextView totalDetalles = (TextView) findViewById(R.id.totalDetalles);

            datosLibro.setText(lib.getDescripcion() + " " + lib.getTitulo());
            tituloLibro.setText(lib.getId() + " " + lib.getAutor());
            totalDetalles.setText("Precio: " + lib.getPrecioDia() + "€/día ");

            int drawableId = getResources().getIdentifier(lib.getImagen(), "drawable", getPackageName());
            imagen.setImageResource(drawableId);
        }
    }

}
