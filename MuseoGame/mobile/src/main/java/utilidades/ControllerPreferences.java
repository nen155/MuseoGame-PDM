package utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import modelos.Obra;


public class ControllerPreferences {
    private static ControllerPreferences instance = new ControllerPreferences();
    private int puntos;
    ArrayList<Obra> obras = new ArrayList<>();
    String[] imagenes ={"noche_estrellada.jpg","david_ma.jpg","guernica_piccasso.jpg","hilanderas.jpg","mona_lisa.jpg","velazquez.jpg"};
    String[] titulos = {"Noche estrellada","David de Miguel Angel","Guernica","Hilanderas","Mona Lisa","Velazquez"};

    public static ControllerPreferences getInstance() {
        return instance;
    }

    //No se si este metodo es util
    private ControllerPreferences() {
        puntos = 0;
        for(int i=0;i<6;++i){
            Random r = new Random();
            int factor = r.nextInt(6);
            Date fecha = new Date();
            Uri imagenUri=Uri.parse("file:///android_asset/" +imagenes[i]);

            Obra obra = new Obra(i+1, titulos[i], fecha, "Descripcion obra "+i+" Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, ", imagenUri,200*factor,"Tipo "+i);
            //Un ejemplo de carga como si hubiese encontrado ya algunas de las obras
            //Lo hacemos aleatoriamente
           // if(factor%2==0)
                obra.setEncontrada("CAPTURAME!");
           /* else
                obra.setEncontrada("ENCONTRADA!");*/
            obras.add(obra);
        }
    }

    public void setEncontrada(int index){
        if(obras.get(index).getEncontrada().compareTo("CAPTURAME!")==0)
        {
            obras.get(index).setEncontrada("ENCONTRADA!");
            puntos+= obras.get(index).getPuntos();
        }
        obras.get(index).setEncontrada("ENCONTRADA!");
    }

    public ArrayList<Obra> getObras(){
        return this.obras;
    }

    public void ganarPuntos(int indice){
        int puntosGanados = obras.get(indice).getPuntos();
        puntos += puntosGanados;
    }

    public int getPuntos(){
        return puntos;
    }



}
