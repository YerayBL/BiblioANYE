package com.example.angie.biblioanye;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Angie on 06/02/2018.
 * Ventana encargada de recoger datos para crear ALQUILER
 * seleccionado por el usuario e insertar datos en las tablas.
 */

public class Alquilar extends AppCompatActivity {

    private EditText dni, idLibro;
    private ImageView imagen;
    private TextView datosLibro, datosCliente, totalAlquiler, diasD;
    private RelativeLayout alquiler;
    private boolean calculo = false;
    private Libro lib;
    private Cliente c;
    private long fechaActual, fechaEntrega;
    private int diasAlquiler = 0;
    static final int DATE_DIALOG_ID = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquilar);

        botonCalcular();

        botonBorrar();

        botonAlquilar();

        botonFecha();

        detectarFecha();
    }

    // Calcula precio y días para alquiler seleccionado
    private void botonCalcular() {
        final Button b1 = (Button) findViewById(R.id.boton_calcular);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idLibro = (EditText) findViewById(R.id.IdET);
                dni = (EditText) findViewById(R.id.EditText_DNI);
                diasD = (TextView) findViewById(R.id.dias);

                if (dni.getText().toString().length() < 9 || dni.getText().toString().length() > 9) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce DNI válido", Toast.LENGTH_LONG).show();
                    dni.setText("");

                } else if (idLibro.getText().toString().equals("") || idLibro.getText().toString().length() < 7) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce id de Libro válido", Toast.LENGTH_LONG).show();
                    idLibro.setText("");

                } else if (diasAlquiler == 0) {
                    Toast.makeText(getApplicationContext(), "ERROR: Introduce fecha de devolución", Toast.LENGTH_LONG).show();

                } else {
                    final String[] ide = {idLibro.getText().toString().toUpperCase()};
                    final String[] dn = {dni.getText().toString().toUpperCase()};

                    BaseDatos asist = new BaseDatos(getApplicationContext());
                    lib = asist.filtrarLibro(ide);
                    c = asist.filtrarCliente(dn);
                    if (c.getDni().equals("")) {
                        Toast.makeText(getApplicationContext(), "ERROR: DNI no encontrado", Toast.LENGTH_LONG).show();
                        dni.setText("");

                    } else if (lib.getId().equals("")) {
                        Toast.makeText(getApplicationContext(), "ERROR: Id no encontrado", Toast.LENGTH_LONG).show();
                        idLibro.setText("");

                    } else {
                        mostrarAlquiler(c, lib);
                    }
                }
            }
        });
    }

    // Muestra datos de alquiler seleccionado
    public void mostrarAlquiler(Cliente c, Libro lib) {
        calculo = true;
        alquiler = (RelativeLayout) findViewById(R.id.datosAlquiler);
        alquiler.setVisibility(View.VISIBLE);
        datosLibro = (TextView) findViewById(R.id.datosLibro);
        datosCliente = (TextView) findViewById(R.id.datosCliente);
        imagen = (ImageView) findViewById(R.id.imagenLibro);
        totalAlquiler = (TextView) findViewById(R.id.totalAlquiler);

        int precioTotal = diasAlquiler * lib.getPrecioDia();
        totalAlquiler.setText(diasAlquiler + " días\nPrecio total: " + precioTotal + "€ ");
        datosCliente.setText(c.getNom());
        datosLibro.setText(lib.getDescripcion() + " " + lib.getTitulo());
        int drawableId = getResources().getIdentifier(lib.getImagen(), "drawable", getPackageName());
        imagen.setImageResource(drawableId);
    }

    // Elimina datos introducidos
    public void botonBorrar() {
        final Button b2 = (Button) findViewById(R.id.borrarAlquiler);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                borrarAqluiler();
            }
        });
    }

    // Elimina cálculo del aquiler
    public void borrarAqluiler() {
        calculo = false;
        alquiler = (RelativeLayout) findViewById(R.id.datosAlquiler);
        alquiler.setVisibility(View.INVISIBLE);

        idLibro = (EditText) findViewById(R.id.IdET);
        dni = (EditText) findViewById(R.id.EditText_DNI);
        diasD = (TextView) findViewById(R.id.dias);

        idLibro.setText("");
        dni.setText("");
        diasAlquiler = 0;
        diasD.setVisibility(View.INVISIBLE);
    }

    // Añade fila a tabla alquileres y cambia libro a no disponible
    public void botonAlquilar() {
        final Button b3 = (Button) findViewById(R.id.aceptarAlquiler);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!calculo) {
                    Toast.makeText(getApplicationContext(),
                            "ERROR: Introduce datos válidos y a continuación pulsa calcular", Toast.LENGTH_LONG).show();
                } else {
                    //Alquiler a = new Alquiler(c.getDni(), lib.getId(), diasAlquiler, lib.getPrecioDia(), fechaActual);
                    Alquiler a = new Alquiler(c.getDni(), lib.getId(), diasAlquiler, lib.getPrecioDia(), fechaActual);
                    BaseDatos asist = new BaseDatos(getApplicationContext());

                    asist.añadirAlquiler(a);
                    asist.modificarLibro(lib, 0);

                    Toast.makeText(getApplicationContext(), "Procesando alquiler", Toast.LENGTH_LONG).show();
                    borrarAqluiler();
                    startActivity(new Intent(Alquilar.this, MenuPrincipal.class));
                }
            }
        });
    }

    // Crea diálogo con calendario
    public void botonFecha() {
        Button pickDate = (Button) findViewById(R.id.botonFecha);
        pickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog datePicker;
                showDialog(DATE_DIALOG_ID);

            }
        });
    }

    // Fija fecha actual
    public void detectarFecha() {
        int iDay, iMonth, iYear;
        Calendar cal = Calendar.getInstance();
        // Today's date fields
        iDay = cal.get(Calendar.DAY_OF_MONTH);
        iMonth = cal.get(Calendar.MONTH);
        iYear = cal.get(Calendar.YEAR);

        Time today = new Time();
        today.set(iDay, iMonth, iYear);
        fechaActual = today.toMillis(true);
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);

        // Handle any DatePickerDialog initialization here
        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        int iDay, iMonth, iYear;
        Calendar cal = Calendar.getInstance();

        // Today's date fields
        iDay = cal.get(Calendar.DAY_OF_MONTH);
        iMonth = cal.get(Calendar.MONTH);
        iYear = cal.get(Calendar.YEAR);

        dateDialog.updateDate(iYear, iMonth, iDay); // Set the date in the DatePicker to the current date
    }

    // Procesa fecha de alquiler seleccionado
    protected Dialog onCreateDialog(int id) {
        final TextView dob = (TextView) findViewById(R.id.dias);
        Calendar now = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Time entrega = new Time();
                entrega.set(dayOfMonth, monthOfYear, year);
                fechaEntrega = entrega.toMillis(true);
                long dias = fechaEntrega - fechaActual;

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;
                long elapsedDays = dias / daysInMilli;
                diasAlquiler = (int) elapsedDays;

                dob.setText(DateFormat.format("dd MMMM, yyyy", fechaEntrega));
                dob.setVisibility(View.VISIBLE);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        return dateDialog;
    }

}
