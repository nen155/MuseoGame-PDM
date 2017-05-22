package com.museogame.com.museogame;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import modelos.Obra;
import utilidades.ControllerPreferences;

import java.lang.Object.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Inicio.OnObraSelectedListener {

    ArrayList<Obra> obras = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ControllerPreferences preferences= ControllerPreferences.getInstance();

        obras = preferences.getObras();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        FloatingActionButton floating = (FloatingActionButton) findViewById(R.id.action_button);
        /*floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.class,ActivityNFCScanner.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,Inicio.newInstance(obras));
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment swicthTo=null;
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    swicthTo = Inicio.newInstance(obras);
                    break;
                /*case R.id.navigation_obra:
                    return true;*/
                case R.id.navigation_puntos:
                    //ANTONIO TU PARTE!!!!!!!!!!!!!!!!!!!
                    swicthTo = new Puntos();
                    break;

            }
            if(swicthTo!=null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,swicthTo);
                transaction.commit();
            }
            return true;
        }

    };

    @Override
    public void onObraSeleccionada(Obra obra) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment vistaObra = VistaObra.newInstance(obra);
        transaction.add(R.id.fragment_container,vistaObra);
        transaction.addToBackStack("VistaObra");
        transaction.commit();
    }

    public void scanObra(View v){
        Intent intent = new Intent(this,ActivityNFCScanner.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //Toast.makeText(this,Integer.toString(fragmentManager.getBackStackEntryCount()),Toast.LENGTH_LONG).show();
        if(fragmentManager.getBackStackEntryCount()<1)
        {
            new AlertDialog.Builder(this).setMessage(R.string.exit_Message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            System.exit(0);

                        }
                    })
                    .setNegativeButton(R.string.No, null)
                    .show();
        }else{
            super.onBackPressed();
        }

        //Fragment test = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()).getClass()

        //String nombre = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()).getClass().toString();
        //Toast.makeText(this,nombre,Toast.LENGTH_LONG).show();

        /*
        if(taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            new AlertDialog.Builder(this).setMessage(R.string.exit_Message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            System.exit(0);

                        }
                    })
                    .setNegativeButton(R.string.No, null)
                    .show();
        }*/

    }
}
