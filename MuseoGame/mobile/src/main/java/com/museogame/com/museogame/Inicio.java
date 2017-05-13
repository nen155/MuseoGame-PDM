package com.museogame.com.museogame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import adapter.AdapterObras;
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
    private ArrayList<Obra> obras = new ArrayList<>();
    private static final String ARG_OBRAS = "obras";
    private GridView gridObras;

    private OnObraSelectedListener mListener;

    public Inicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param obras Parameter 1.
     * @return A new instance of fragment Inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static Inicio newInstance(ArrayList<Obra> obras) {
        Inicio fragment = new Inicio();
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
        View fragmen =inflater.inflate(R.layout.fragment_inicio, container, false);

        gridObras = (GridView) fragmen.findViewById(R.id.obrasACapurar);


        return fragmen;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Si tengo datos los asigno mediante el adapter
        gridObras.setAdapter(new AdapterObras(getActivity(),obras,mListener));
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
