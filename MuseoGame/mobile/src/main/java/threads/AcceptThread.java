package threads;

import android.app.Activity;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.museogame.com.museogame.MainActivity;
import com.museogame.com.museogame.R;

import utilidades.ControllerPreferences;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Sasu on 25/05/2017.
 */
public class AcceptThread extends Thread {
    BluetoothAdapter mBluetoothAdapter;
    public Boolean parar = false;
    MainActivity main;
    String nombreBalizas="";
    ControllerPreferences preferences;

    public AcceptThread(MainActivity m){
        preferences= ControllerPreferences.getInstance(m);
        main = m;
    }

    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //Toast.makeText(main.getApplicationContext(),"onReceive: " + device.getName() + ": " + device.getAddress(), Toast.LENGTH_SHORT).show();
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                //Toast.makeText(main.getApplicationContext(),"  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                if (device.getName().compareTo(nombreBalizas) ==0){

                    Vibrator v = (Vibrator) main.getSystemService(VIBRATOR_SERVICE);

                    int valor = (rssi*(-1))*20;
                    String mensaje = "";
                    String contenido ="";

                    rssi = rssi*(-1);

                    if(rssi<60 && rssi>=0){
                        valor = 5000;
                        mensaje = "¡Estás muy muy cerca de una obra!";
                        contenido = "Fijate bien a tu alrededor porque la tienes muy cerca.";
                    }else if(rssi<120 && rssi>=60){
                        valor = 1000;
                        mensaje = "¡Estás a una distancia media de una obra!";
                        contenido = "Cada vez te acercas más. \n ¡Vas muy bien sigue así!";
                    }else if(rssi>=120){
                        valor = 500;
                        mensaje = "¡Ups, tienes una obra relativamente cerca!";
                        contenido = "Fijate en tu alrededor porque te vas acercando un poquito.";
                    }



                    long vibrar = valor;

                    long [] array = new long[]{vibrar,vibrar};


                    v.vibrate(valor);

                    Notification notification = new NotificationCompat.Builder(main.getApplication())
                            .setSmallIcon(R.drawable.icon)
                            .setVibrate(array)
                            .setContentTitle(mensaje)
                            .setContentText(contenido)
                            .extend(
                                    new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                            .build();


                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(main.getApplication());

                    int notificationId = 1;
                    notificationManager.notify(notificationId, notification);
                }else{




                    Vibrator v = (Vibrator) main.getSystemService(VIBRATOR_SERVICE);

                    int valor = (rssi*(-1))*20;
                    String mensaje = "";
                    String contenido ="";

                    rssi = rssi*(-1);

                    if(rssi<60 && rssi>=0){
                        valor = 5000;
                        mensaje = "¡Estás muy muy cerca de una obra!";
                        contenido = "Fijate bien a tu alrededor porque la tienes muy cerca.";
                    }else if(rssi<120 && rssi>=60){
                        valor = 1000;
                        mensaje = "¡Estás a una distancia media de una obra!";
                        contenido = "Cada vez te acercas más. \n ¡Vas muy bien sigue así!";
                    }else if(rssi>=120){
                        valor = 500;
                        mensaje = "¡Ups, tienes una obra relativamente cerca!";
                        contenido = "Fijate en tu alrededor porque te vas acercando un poquito.";
                    }



                    long vibrar = valor;

                    long [] array = new long[]{vibrar,vibrar};



                    v.vibrate(valor);

                    Notification notification = new NotificationCompat.Builder(main.getApplication())
                            .setSmallIcon(R.drawable.icon)
                            .setVibrate(array)
                            .setContentTitle(mensaje)
                            .setContentText(contenido)
                            .extend(
                                    new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                            .build();

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(main.getApplication());

                    int notificationId = 1;
                    notificationManager.notify(notificationId, notification);
                }




            }
        }
    };


    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void run() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        while(preferences.getScaning())
        {
            if(mBluetoothAdapter.isDiscovering()){
                mBluetoothAdapter.cancelDiscovery();

                mBluetoothAdapter.startDiscovery();
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                main.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
            }
            if(!mBluetoothAdapter.isDiscovering()){

                mBluetoothAdapter.startDiscovery();
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                main.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
            }


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
