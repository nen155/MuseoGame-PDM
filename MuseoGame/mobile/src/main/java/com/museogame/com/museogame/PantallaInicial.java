package com.museogame.com.museogame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PantallaInicial extends AppCompatActivity {

    private Context context;

    private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Intent intent = new Intent(context,MainActivity.class);
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
