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
import android.net.Uri;
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

import modelos.Obra;
import threads.AcceptThread;
import utilidades.ControllerPreferences;

import java.lang.Object.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Inicio.OnObraSelectedListener {
    private static final String TAG = "MainActivity";
    ArrayList<Obra> obras = null;
    BluetoothAdapter mBluetoothAdapter;
    public Boolean searching = false;
    FloatingActionButton bluetooth;
    TextView tBluetooth;
    AcceptThread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        ControllerPreferences preferences= ControllerPreferences.getInstance();

        obras = preferences.getObras();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        FloatingActionButton floating = (FloatingActionButton) findViewById(R.id.action_button);

        bluetooth = (FloatingActionButton) findViewById(R.id.botonBluetooh);
        tBluetooth = (TextView) findViewById(R.id.textBluetooth);

        /*floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.class,ActivityNFCScanner.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        thread = new AcceptThread(this);

/*

        thread = new Thread() {
            BluetoothAdapter mBluetoothAdapter;
            public Boolean parar = false;

            public void setParar(Boolean b){
                parar = b;
            }
            private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    // When discovery finds a device
                    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        // Get the BluetoothDevice object from the Intent
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        Toast.makeText(getApplicationContext(),"onReceive: " + device.getName() + ": " + device.getAddress(), Toast.LENGTH_SHORT).show();
                        int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                        Toast.makeText(getApplicationContext(),"  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                        if (device.getName().compareTo("DESKTOP-UBSF7CJ") ==0){
                            Toast.makeText(getApplicationContext(),"Encontrada la pecera", Toast.LENGTH_SHORT).show();

                            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                            int valor = (rssi*(-1))*20;

                            rssi = rssi*(-1);

                            if(rssi<60 && rssi>=0){
                                valor = 5000;
                            }else if(rssi<120 && rssi>=60){
                                valor = 1000;
                            }else if(rssi>=120){
                                valor = 500;
                            }

                            Toast.makeText(getApplicationContext(),Integer.toString(valor), Toast.LENGTH_SHORT).show();

                            v.vibrate(valor);
                        }else{
                            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                            int valor = (rssi*(-1))*20;

                            rssi = rssi*(-1);

                            if(rssi<60 && rssi>=0){
                                valor = 5000;
                            }else if(rssi<120 && rssi>=60){
                                valor = 1000;
                            }else if(rssi>=120){
                                valor = 500;
                            }

                            Toast.makeText(getApplicationContext(),Integer.toString(valor), Toast.LENGTH_SHORT).show();

                            v.vibrate(valor);
                        }



                        //mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                        //lvNewDevices.setAdapter(mDeviceListAdapter);

                    } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                        Toast.makeText(getApplicationContext(),"Buscamos de nuevo", Toast.LENGTH_SHORT).show();
                        mBluetoothAdapter.startDiscovery();
                        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                    }
                }
            };


            @Override
            public void run() {

                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                while(parar)
                {
                    if(mBluetoothAdapter.isDiscovering()){
                        mBluetoothAdapter.cancelDiscovery();

                        mBluetoothAdapter.startDiscovery();
                        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                    }
                    if(!mBluetoothAdapter.isDiscovering()){

                        mBluetoothAdapter.startDiscovery();
                        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                    }


                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };*/

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,Inicio.newInstance(obras));
        transaction.commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void scan(View view) throws Throwable {

        if(searching){
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.enable)));
            tBluetooth.setText("on");
            searching = false;

        }else
        {
            bluetooth.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.disable)));
            tBluetooth.setText("off");
            searching = true;
        }
        Toast.makeText(getApplicationContext(),"Buscamos", Toast.LENGTH_SHORT).show();
        buscar();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void buscar() throws Throwable {
        checkBTPermissions();

        Toast.makeText(getApplicationContext(),"Dentro de buscar", Toast.LENGTH_SHORT).show();


        if(searching)
            thread.start();
        else{
            thread.interrupt();
            thread = new AcceptThread(this);
        }

/*

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }*/

    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions() {

        Toast.makeText(getApplicationContext(),"Pedimos permisos", Toast.LENGTH_SHORT).show();
        //if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        //}else{
         //   Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
       // }
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

    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Toast.makeText(getApplicationContext(),"onReceive: " + device.getName() + ": " + device.getAddress(), Toast.LENGTH_SHORT).show();
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                Toast.makeText(getApplicationContext(),"  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                if (device.getName().compareTo("DESKTOP-UBSF7CJ") ==0){
                    Toast.makeText(getApplicationContext(),"Encontrada la pecera", Toast.LENGTH_SHORT).show();

                    Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                    int valor = (rssi*(-1))*20;

                    rssi = rssi*(-1);

                    if(rssi<60 && rssi>=0){
                        valor = 5000;
                    }else if(rssi<120 && rssi>=60){
                        valor = 1000;
                    }else if(rssi>=120){
                        valor = 500;
                    }

                    Toast.makeText(getApplicationContext(),Integer.toString(valor), Toast.LENGTH_SHORT).show();

                    v.vibrate(valor);
                }else{
                    Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                    int valor = (rssi*(-1))*20;

                    rssi = rssi*(-1);

                    if(rssi<60 && rssi>=0){
                        valor = 5000;
                    }else if(rssi<120 && rssi>=60){
                        valor = 1000;
                    }else if(rssi>=120){
                        valor = 500;
                    }

                    Toast.makeText(getApplicationContext(),Integer.toString(valor), Toast.LENGTH_SHORT).show();

                    v.vibrate(valor);
                }



                //mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                //lvNewDevices.setAdapter(mDeviceListAdapter);

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(getApplicationContext(),"Buscamos de nuevo", Toast.LENGTH_SHORT).show();
                mBluetoothAdapter.startDiscovery();
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
            }
        }
    };


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
