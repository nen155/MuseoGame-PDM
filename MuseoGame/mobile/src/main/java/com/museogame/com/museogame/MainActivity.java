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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,new Inicio());
        transaction.commit();
    }

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
                    //ANTONIO TU PARTE!!!!!!!!!!!!!!!!!!!
                    swicthTo = new Puntos();
                    break;

            }
            if(swicthTo!=null){
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,swicthTo);
                transaction.commit();
            }
            return true;
        }

    };

    @Override
    public void onObraSeleccionada(Obra obra) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment vistaObra = VistaObra.newInstance(obra);
        transaction.replace(R.id.fragment_container,vistaObra);
        transaction.commit();
    }
}
