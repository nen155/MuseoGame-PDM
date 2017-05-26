package com.museogame.com.museogame;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.AdapterPuntos;
import modelos.Obra;
import utilidades.ControllerPreferences;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Puntos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Puntos extends Fragment {
    //ArrayList de obras para cargar y pasar cuando se cambie de Fragment
    private ArrayList<Obra> obras = new ArrayList<>();
    private static final String ARG_OBRAS = "obras";

    ListView listview;


    public Puntos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment Puntos.
     */
    // TODO: Rename and change types and number of parameters
    public static Puntos newInstance() {
        Puntos fragment = new Puntos();
        Bundle args = new Bundle();
        //args.putParcelableArrayList(ARG_OBRAS, obras);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmen =inflater.inflate(R.layout.fragment_puntos, container, false);
        ControllerPreferences preferences= ControllerPreferences.getInstance(null);
        ListView lista = (ListView) fragmen.findViewById(R.id.lista);

        //lista.add
        obras = preferences.getObras();
        int contador = preferences.getPuntos();

        TextView puntos = (TextView) fragmen.findViewById(R.id.puntos);

        puntos.setText("¡Has conseguido: " + contador + " puntos en total!");

        ArrayList<Obra> obrasFiltradas = new ArrayList<>();

        for(int i = 0; i < obras.size(); i++){
            if(obras.get(i).getEncontrada().compareTo("ENCONTRADA!")==0){
                obrasFiltradas.add(obras.get(i));
            }
        }
        /*
        listview = (ListView) fragmen.findViewById(R.id.listview);
        listview.setAdapter(new AdapterPuntos(fragmen.getContext(), obrasFiltradas));*/


        return inflater.inflate(R.layout.fragment_puntos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Si tengo datos los asigno mediante el adapter

        ControllerPreferences preferences= ControllerPreferences.getInstance(null);
        ListView lista = (ListView) view.findViewById(R.id.lista);

        //lista.add
        obras = preferences.getObras();
        int contador = preferences.getPuntos();

        TextView puntos = (TextView) view.findViewById(R.id.puntos);

        puntos.setText("¡Has conseguido: " + contador + " puntos en total!");

        ArrayList<Obra> obrasFiltradas = new ArrayList<>();

        for(int i = 0; i < obras.size(); i++){
            if(obras.get(i).getEncontrada().compareTo("ENCONTRADA!")==0){
                obrasFiltradas.add(obras.get(i));
            }
        }

        listview = (ListView) view.findViewById(R.id.listview);
        listview.setAdapter(new AdapterPuntos(view.getContext(), obrasFiltradas));
    }

}
