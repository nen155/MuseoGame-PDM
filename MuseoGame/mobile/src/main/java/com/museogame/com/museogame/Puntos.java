package com.museogame.com.museogame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import modelos.Obra;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Puntos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Puntos extends Fragment {
    //ArrayList de obras para cargar y pasar cuando se cambie de Fragment
    private ArrayList<Obra> obras = new ArrayList<>();
    private static final String ARG_OBRAS = "obras";


    public Puntos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param obras Parameter 1.
     * @return A new instance of fragment Puntos.
     */
    // TODO: Rename and change types and number of parameters
    public static Puntos newInstance(ArrayList<Obra> obras) {
        Puntos fragment = new Puntos();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_OBRAS, obras);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            obras = getArguments().getParcelableArrayList(ARG_OBRAS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_puntos, container, false);
    }

}
