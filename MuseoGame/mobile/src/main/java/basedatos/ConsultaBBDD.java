package basedatos;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
*/

/**
 * Created by Rafa on 27/10/2015.
 */
public class ConsultaBBDD {

    //Servidor
    public static final String server ="http://s557211628.mialojamiento.es/guiaPueblo2.0/";

    //Ficheros php para consultas
    public static final String consultaObras = "datosObras.php";


    //Directorios de imagenes
    public static final String obraImagenes = "obraImgenes/";


    public static String resultado="";

    public static String realizarConsulta(String php, String parametros) {

            HttpURLConnection conexion = null;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {

                System.setProperty("http.keepAlive", "false");
            }

            try {

                URL url = new URL(server + php +"?" + parametros);

                conexion = (HttpURLConnection) url.openConnection();
                conexion.setConnectTimeout(20000);
                conexion.setReadTimeout(15000);
                conexion.setRequestMethod("GET");
                conexion.setDoInput(true);
                conexion.setDoOutput(true);

                conexion.connect();

                int responseCode = conexion.getResponseCode();

                Log.d(" reponseCode", String.valueOf(responseCode));

                if(responseCode == HttpURLConnection.HTTP_OK){

                    StringBuilder sb = new StringBuilder();
                    try{

                        BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        String linea;

                        while ((linea = br.readLine())!= null){

                            sb.append(linea);
                        }

                        return sb.toString();
                    }catch (Exception e){

                        e.printStackTrace();
                    }

                }else{

                    if(responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT){
                        Log.d(" reponseCode",  conexion.getErrorStream().toString());
                        //throw new Exception("Tempo maximo en la comunicacion: "+ conexion.getErrorStream());
                    }
                }

            } catch (MalformedURLException e) {

                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                    if(conexion!=null)
                        conexion.disconnect();
            }

            return null;
    }
    /*public static String realizarConsulta(String php,String parametros)
    {
        InputStream is = null;
        HttpClient cliente = new DefaultHttpClient();
        HttpGet peticionGet = new HttpGet(
                server + php +"?" + parametros);


        try {
            HttpResponse response = cliente.execute(peticionGet);
            HttpEntity contenido = response.getEntity();
            is = contenido.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buferlector = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String linea = null;
        try{
            while((linea = buferlector.readLine())!=null){
                sb.append(linea);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            is.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return sb.toString();

    }*/


    /*public static void insertaBD(String php, String parametros, ArrayList<NameValuePair> valueInsert){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(server + php );
            httppost.setEntity(new UrlEncodedFormEntity(valueInsert));
            HttpResponse response = httpclient.execute(httppost);
            Log.d("Http Post Response:", response.toString());


            BufferedReader buferlector = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String linea = null;
            try{
                while((linea = buferlector.readLine())!=null){
                    sb.append(linea);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            Log.d("resultado insert", sb.toString());
            resultado  =sb.toString();

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
    }*/


}
