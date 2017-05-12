package hebras;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;

import android.view.Gravity;

import android.widget.GridView;
import android.widget.Toast;

import com.museogame.com.museogame.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapter.AdapterObras;
import basedatos.ConsultaBBDD;
import modelos.Obra;


/**
 * Created by NeN on 31/10/2015.
 */
public class HObras extends AsyncTask<Void, Integer, List<Obra>> {

    private Context context;
    private List<Obra> obras = null;
    private ProgressDialog pDialog;

    public HObras(Context context,List<Obra> obras) {
        this.context = context;
        this.obras = obras;
    }

    @Override
    protected List<Obra> doInBackground(Void... params) {
        String resultado  = ConsultaBBDD.realizarConsulta(ConsultaBBDD.consultaObras, "");
       /* JSONArray arrayJson = null;
        try {
            arrayJson = new JSONArray(resultado);

            JSONArray jsonArrayEmp = arrayJson.getJSONArray(0);
            for (int i = 0; i < jsonArrayEmp.length(); ++i) {
                JSONObject objetoJson = jsonArrayEmp.getJSONObject(i);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyy", Locale.getDefault());
                Date fecha = null;
                try {
                    fecha = simpleDateFormat.parse(objetoJson.getString("fecha"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Obra obra = new Obra(objetoJson.getInt("id"), objetoJson.getString("titulo"), fecha, objetoJson.getString("descricpion"), objetoJson.getString("imagen"));
                obras.add(obra);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return obras;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando Datos");
        pDialog.setCancelable(false);
        pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(List<Obra> result) {
        //Si no tengo datos muestro un mensaje
        if (result.isEmpty()) {
            Toast toast = Toast.makeText(context,
                    "Lo sentimos, pero no hay datos disponibles",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            ((Activity) context).finish();
        } else {
            //Si tengo datos los asigno mediante el adapter
            GridView gridObras = (GridView) ((Activity) context).findViewById(R.id.obrasACapurar);
            //gridObras.setAdapter(new AdapterObras(context,result));
            pDialog.dismiss();
        }
    }

}

