package com.example.angie.biblioanye;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Angie on 06/02/2018.
 * Ventana encargada de recoger datos para eliminar ALQUILER
 * seleccionado por el usuario y modificar tabla LIBROS.
 */

public class Devolver extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private EditText idLibro;
    private CheckBox cb;
    private RelativeLayout calendario, atiempo;
    static final int DATE_DIALOG_ID = 0;
    private long fechaEntrega, fechaAlquiler;
    private int diasRetraso, diasAlquiler;
    private TextView dev;
    private boolean fechado = false, fechado2 = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devolver);

        initiVariables();

        botonBorrar();

        botonDevolver();

        botonFecha();
    }

    // Inicia variables del programa
    private void initiVariables() {
        idLibro = (EditText) findViewById(R.id.idET);
        cb = (CheckBox) findViewById(R.id.check);
        cb.setOnCheckedChangeListener(this);
        calendario = (RelativeLayout) findViewById(R.id.calendario);
        atiempo = (RelativeLayout) findViewById(R.id.atiempo);
        dev = (TextView) findViewById(R.id.dias);
    }

    // Elimina datos introducidos por el usuario
    private void botonBorrar() {
        final Button b2 = (Button) findViewById(R.id.borrarDevolucion);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idLibro.setText("");
                cb.setChecked(false);
                dev.setVisibility(View.INVISIBLE);
                fechado = false;
                fechado2 = false;
            }
        });

    }

    // Elimina entrada seleccionada por usuario de tabla ALQUILER
    private void botonDevolver() {

        final Button b2 = (Button) findViewById(R.id.boton_devolver);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String m = idLibro.getText().toString().toUpperCase();
                if (m.equals("") || m.length() < 7) {
                    Toast.makeText(getApplicationContext(), "Introduce id válida", Toast.LENGTH_LONG).show();
                    idLibro.setText("");
                } else if (!fechado) {
                    Toast.makeText(getApplicationContext(), "Clica devolución", Toast.LENGTH_LONG).show();
                } else if (!fechado2) {
                    Toast.makeText(getApplicationContext(), "Seleccióna fecha de devolución", Toast.LENGTH_LONG).show();

                } else {
                    BaseDatos bd = new BaseDatos(getApplicationContext());
                    bd.eliminarAlquiler(new String[]{m});
                    bd.modificarLibro(bd.filtrarLibro2(new String[]{m}), 1);
                    System.out.println("MODIFICANDO LIBRO     !!" + m);
                    Toast.makeText(getApplicationContext(), "Libro devuelto correctamente", Toast.LENGTH_LONG).show();
                    idLibro.setText("");
                    startActivity(new Intent(Devolver.this, MenuPrincipal.class));
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            calendario.setVisibility(View.VISIBLE);
            atiempo.setVisibility(View.INVISIBLE);
            fechado = true;
        } else {
            atiempo.setVisibility(View.VISIBLE);
            calendario.setVisibility(View.INVISIBLE);
            fechado = false;
            fechado2 = false;
        }
    }

    // Crea diálogo con calendario
    public void botonFecha() {
        Button pickDate = (Button) findViewById(R.id.botonFecha);
        pickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idLibro.getText().toString().equals("") || idLibro.getText().length() < 7) {
                    Toast.makeText(getApplicationContext(), "Introduce id del Libro", Toast.LENGTH_LONG).show();
                } else {
                    DatePickerDialog datePicker;
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });
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

    // Calcula diferencia entre fecha entrega prevista y real
    protected Dialog onCreateDialog(int id) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Time entrega = new Time();
                entrega.set(dayOfMonth, monthOfYear, year);
                fechaEntrega = entrega.toMillis(true);
                BaseDatos bd = new BaseDatos(getApplicationContext());
                fechaAlquiler = bd.consultarFecha(new String[]{idLibro.getText().toString().toUpperCase()});
                diasAlquiler = bd.consultarDias(new String[]{idLibro.getText().toString().toUpperCase()});
                if (diasAlquiler == 0) {
                    Toast.makeText(getApplicationContext(), "Introduce id de Libro alquilado", Toast.LENGTH_LONG).show();
                    idLibro.setText("");
                    cb.setChecked(false);
                } else {
                    long dias = fechaEntrega - fechaAlquiler;
                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;
                    long elapsedDays = dias / daysInMilli;
                    int diasTotales = (int) elapsedDays;
                    fechado2 = true;
                    diasRetraso = diasTotales - diasAlquiler;
                    if (diasRetraso < 0) {
                        dev.setText(diasRetraso + " días antes");
                        dev.setVisibility(View.VISIBLE);
                        dev.setTextColor(Color.GREEN);
                    } else {
                        dev.setText(diasRetraso + " días de retraso");
                        dev.setVisibility(View.VISIBLE);
                        dev.setTextColor(Color.RED);
                    }

                }

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        return dateDialog;
    }

}
