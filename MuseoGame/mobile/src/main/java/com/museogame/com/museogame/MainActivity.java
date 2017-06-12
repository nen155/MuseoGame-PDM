package com.museogame.com.museogame;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;


import modelos.Obra;
import threads.AcceptThread;
import utilidades.ControllerPreferences;

import java.lang.Object.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Inicio.OnObraSelectedListener {
    ArrayList<Obra> obras = null;
    BluetoothAdapter mBluetoothAdapter;
    FloatingActionButton bluetooth;
    TextView tBluetooth;
    AcceptThread thread;
    ControllerPreferences preferences;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(preferences.getScaning()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.enable)));
            tBluetooth.setText("on");


        }else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.disable)));
            tBluetooth.setText("off");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        preferences= ControllerPreferences.getInstance(this);
        preferences.crearThread();

        obras = preferences.getObras();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        bluetooth = (FloatingActionButton) findViewById(R.id.botonBluetooh);
        tBluetooth = (TextView) findViewById(R.id.textBluetooth);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        thread = preferences.thread;

        if(!preferences.getScaning()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.enable)));
            tBluetooth.setText("on");


        }else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.disable)));
            tBluetooth.setText("off");

        }


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,Inicio.newInstance(obras));
        transaction.commit();
    }


    public void scan(View view) throws Throwable {

        if(preferences.getScaning()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.enable)));
            tBluetooth.setText("on");
            preferences.setScaning(false);

        }else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.disable)));
            tBluetooth.setText("off");
            preferences.setScaning(true);
        }
        buscar();

    }


    private void buscar() throws Throwable {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        checkBTPermissions();

        if(preferences.getScaning())
            thread.start();
        else{
            thread.interrupt();
            preferences.thread = new AcceptThread(this);
            thread = preferences.thread;
        }

    }

    private void checkBTPermissions() {

        //Toast.makeText(getApplicationContext(),"Pedimos permisos", Toast.LENGTH_SHORT).show();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }

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
        if(NfcAdapter.getDefaultAdapter(this)==null){
            Intent intent = new Intent(this, ActivityQRScanner.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, ActivityNFCScanner.class);
            startActivity(intent);
        }
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
