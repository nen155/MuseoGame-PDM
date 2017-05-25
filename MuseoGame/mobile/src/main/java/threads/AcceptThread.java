package threads;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;
import android.widget.Toast;

import com.museogame.com.museogame.MainActivity;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Sasu on 25/05/2017.
 */
public class AcceptThread extends Thread {
    BluetoothAdapter mBluetoothAdapter;
    public Boolean parar = false;
    MainActivity main;

    public AcceptThread(MainActivity m){
        main = m;
    }

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

                Toast.makeText(main.getApplicationContext(),"onReceive: " + device.getName() + ": " + device.getAddress(), Toast.LENGTH_SHORT).show();
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                Toast.makeText(main.getApplicationContext(),"  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                if (device.getName().compareTo("DESKTOP-UBSF7CJ") ==0){
                    Toast.makeText(main.getApplicationContext(),"Encontrada la pecera", Toast.LENGTH_SHORT).show();

                    Vibrator v = (Vibrator) main.getSystemService(VIBRATOR_SERVICE);

                    int valor = (rssi*(-1))*20;

                    rssi = rssi*(-1);

                    if(rssi<60 && rssi>=0){
                        valor = 5000;
                    }else if(rssi<120 && rssi>=60){
                        valor = 1000;
                    }else if(rssi>=120){
                        valor = 500;
                    }

                    Toast.makeText(main.getApplicationContext(),Integer.toString(valor), Toast.LENGTH_SHORT).show();

                    v.vibrate(valor);
                }else{
                    Vibrator v = (Vibrator) main.getSystemService(VIBRATOR_SERVICE);

                    int valor = (rssi*(-1))*20;

                    rssi = rssi*(-1);

                    if(rssi<60 && rssi>=0){
                        valor = 5000;
                    }else if(rssi<120 && rssi>=60){
                        valor = 1000;
                    }else if(rssi>=120){
                        valor = 500;
                    }

                    Toast.makeText(main.getApplicationContext(),Integer.toString(valor), Toast.LENGTH_SHORT).show();

                    v.vibrate(valor);
                }



                //mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                //lvNewDevices.setAdapter(mDeviceListAdapter);

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(main.getApplicationContext(),"Buscamos de nuevo", Toast.LENGTH_SHORT).show();
                mBluetoothAdapter.startDiscovery();
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                main.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
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

        while(main.searching)
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
