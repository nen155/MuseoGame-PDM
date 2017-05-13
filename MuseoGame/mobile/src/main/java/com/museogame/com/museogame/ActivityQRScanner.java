package com.museogame.com.museogame;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import modelos.Obra;

public class ActivityQRScanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{

    private static final int CAMERA_REQUEST = 1;
    private QRCodeReaderView qrCodeReaderView;
    private TextView resultTextView;
    private Obra ObraABuscar =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        //Obra a buscar
        ObraABuscar = (Obra) getIntent().getExtras().getParcelable("obra");

        resultTextView = (TextView) findViewById(R.id.textView_Prueba);

        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        qrCodeReaderView.setQRDecodingEnabled(true);

        qrCodeReaderView.setAutofocusInterval(2000L);

        qrCodeReaderView.setBackCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        try {
            int id_result = Integer.parseInt(text);
            if(id_result!= ObraABuscar.getId()){
                Toast toast = Toast.makeText(this,"ESTA NO ES LA OBRA!! SIGUE BUSCANDO...", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else {
                Intent mainIntent = new Intent(this, VistaObra.class);
                mainIntent.putExtra("obra", ObraABuscar);
                startActivity(mainIntent);
            }

        }catch (Exception e){

            Vibrator v = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);

            v.vibrate(50);
            Toast.makeText(this,getString(R.string.QR_Error), Toast.LENGTH_LONG).show();

        }


    }
}
