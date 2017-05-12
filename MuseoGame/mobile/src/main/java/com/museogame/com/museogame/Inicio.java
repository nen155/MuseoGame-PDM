package com.museogame.com.museogame;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapter.AdapterObras;
import hebras.HObras;
import modelos.Obra;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnObraSelectedListener} interface
 * to handle interaction events.
 * Use the {@link Inicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inicio extends Fragment {

    //ArrayList de obras para cargar y pasar cuando se cambie de Fragment
    List<Obra> obras = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnObraSelectedListener mListener;

    public Inicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static Inicio newInstance(String param1, String param2) {
        Inicio fragment = new Inicio();
        Bundle args = new Bundle();
       // args.putSerializable("lista-obras",);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmen =inflater.inflate(R.layout.fragment_inicio, container, false);

        GridView gridObras = (GridView) ((Activity) getContext()).findViewById(R.id.obrasACapurar);

        for(int i=0;i<6;++i){

            Date fecha = new Date();
            Uri imagenUri=Uri.parse("R.mipmap.ic_launcher");
            Obra obra = new Obra(i+1, "Obra"+i, fecha, "Descripcion obra "+i+" Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, ", imagenUri);
            obras.add(obra);
        }
        //Si tengo datos los asigno mediante el adapter
        gridObras.setAdapter(new AdapterObras(getContext(),obras,mListener));

        //LO HACEMOS TODO EN LOCAL POR LO QUE NO LO NECESITAMOS
        //Ejecuto la hebra para que rellene el GridView
        /*HObras hObras = new HObras(getContext(),obras);
        hObras.execute();*/

        return fragmen;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Obra obra) {
        if (mListener != null) {
            mListener.onObraSeleccionada(obra);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnObraSelectedListener) {
            mListener = (OnObraSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnObraSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnObraSelectedListener {
        // TODO: Update argument type and name
       public void onObraSeleccionada(Obra obra);
    }
}
