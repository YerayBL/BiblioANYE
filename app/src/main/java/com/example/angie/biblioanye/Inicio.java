package com.example.angie.biblioanye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Angie on 06/02/2018.
 * Ventana principal del programa que muestra varias animaciones
 * solo durantes unos segundos antes de pasar a menú principal
 */
public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Animación del logo1
        ImageView logo1 = (ImageView) findViewById(R.id.logo);
        Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo1.startAnimation(fade1);

        //Animación del icono2
        ImageView logo2 = (ImageView) findViewById(R.id.logo1);
        Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
        logo2.startAnimation(fade2);

        //Animación del icon2
        ImageView logo3 = (ImageView) findViewById(R.id.logo2);
        Animation fade3 = AnimationUtils.loadAnimation(this, R.anim.fade_in3);
        logo3.startAnimation(fade3);

        //Abre ventana Menu principal al terminar las animaciones
        fade3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Inicio.this, MenuPrincipal.class));
                Inicio.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        //Animación del logo2
        ImageView logo4 = (ImageView) findViewById(R.id.logo3);
        Animation fade4 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        logo4.startAnimation(fade4);
    }

    protected void onPause() {
        super.onPause();
        // Para animación de logo1
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.clearAnimation();

        // Para animación de icono2
        ImageView logo2 = (ImageView) findViewById(R.id.logo1);
        logo2.clearAnimation();

        // Para animación de logo1
        ImageView logo3 = (ImageView) findViewById(R.id.logo2);
        logo3.clearAnimation();

        // Para animación de logo1
        ImageView logo4 = (ImageView) findViewById(R.id.logo3);
        logo4.clearAnimation();
    }

}
