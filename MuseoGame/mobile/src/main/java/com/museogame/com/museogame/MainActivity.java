package com.museogame.com.museogame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import modelos.Obra;

public class MainActivity extends AppCompatActivity implements Inicio.OnObraSelectedListener {

    private TextView mTextMessage;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment swicthTo=null;
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    swicthTo = new Inicio();
                    break;
                /*case R.id.navigation_obra:
                    return true;*/
                case R.id.navigation_puntos:
                    //swicthTo = new Puntos();
                    break;

            }
            if(swicthTo!=null){
                //Paso 1: Obtener la instancia del administrador de fragmentos
                FragmentManager fragmentManager = getFragmentManager();
                //Paso 2: Crear una nueva transacción
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,swicthTo);
                transaction.commit();
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onObraSeleccionada(Obra obra) {

    }
}
