package com.museogame.com.museogame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import modelos.Obra;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PantallaInicial extends AppCompatActivity {

    private Context context;
    //ArrayList de obras para cargar y pasar cuando se cambie de Fragment
    ArrayList<Obra> obras = new ArrayList<>();
    String[] imagenes ={"noche_estrellada.jpg","david_ma.jpg","guernica_piccasso.jpg","hilanderas.jpg","mona_lisa.jpg","velazquez.jpg"};

    private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ///CARGAMOS LAS OBRAS ESTO DEBERÍA VENIR DEL SERVIDOR
            for(int i=0;i<6;++i){
                Random r = new Random();
                int factor = r.nextInt(6);
                Date fecha = new Date();
                Uri imagenUri=Uri.parse("file:///android_asset/" +imagenes[i]);
                Obra obra = new Obra(i+1, "VistaObra"+i, fecha, "Descripcion obra "+i+" Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, ", imagenUri,200*factor,"Tipo "+i);
                //Un ejemplo de carga como si hubiese encontrado ya algunas de las obras
                //Lo hacemos aleatoriamente
                if(factor%2==0)
                    obra.setEncontrada("CAPTURAME!");
                else
                    obra.setEncontrada("ENCONTRADA!");
                obras.add(obra);
            }
            //LO HACEMOS TODO EN LOCAL POR LO QUE NO LO NECESITAMOS
            //Ejecuto la hebra para que rellene el GridView
            /*HObras hObras = new HObras(getContext(),obras);
            hObras.execute();*/
            Intent intent = new Intent(context,MainActivity.class);
            intent.putParcelableArrayListExtra("obras",obras);
            startActivity(intent);

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantalla_inicial);
        context=this;

        findViewById(R.id.captura).setOnTouchListener(onTouchListener);
    }

}
