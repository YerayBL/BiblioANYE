package com.example.angie.biblioanye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Angie on 06/02/2018.
 */

public class MenuPrincipal extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cargarMenu();
    }

    // Carga lista de itmes para menu principal
    private void cargarMenu() {
        ListView menuList = (ListView) findViewById(R.id.listView_menu);

        final String[] items = getResources().getStringArray(R.array.items);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);

        menuList.setAdapter(adapt);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();

                if (strText.equals(items[0].toString())) {
                    startActivity(new Intent(MenuPrincipal.this, Consultar.class));

                } else if (strText.equals(items[1].toString())) {
                    startActivity(new Intent(MenuPrincipal.this, Editar.class));

                } else if (strText.equals(items[2].toString())) {
                    startActivity(new Intent(MenuPrincipal.this, Alquilar.class));

                } else if (strText.equals(items[3].toString())) {
                    startActivity(new Intent(MenuPrincipal.this, Devolver.class));

                } else if (strText.equals(items[4].toString())) {
                    startActivity(new Intent(MenuPrincipal.this, Eliminar.class));
                }
            }
        });
    }

}
