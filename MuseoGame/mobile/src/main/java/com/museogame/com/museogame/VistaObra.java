package com.museogame.com.museogame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import modelos.Obra;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VistaObra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VistaObra extends Fragment {

    private static final String ARG_OBRA = "obra";


    private Obra obra;


    public VistaObra() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param obra
     * @return A new instance of fragment VistaObra.
     */
    // TODO: Rename and change types and number of parameters
    public static VistaObra newInstance(Obra obra) {
        VistaObra fragment = new VistaObra();
        Bundle args = new Bundle();
        args.putParcelable(ARG_OBRA,obra);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            obra = getArguments().getParcelable(ARG_OBRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewObra = inflater.inflate(R.layout.fragment_obra, container, false);;
        ImageView imagenObra = (ImageView)viewObra.findViewById(R.id.imagenObra);
        TextView puntos = (TextView)viewObra.findViewById(R.id.puntos);
        TextView tipo = (TextView)viewObra.findViewById(R.id.tipo);
        TextView fecha = (TextView)viewObra.findViewById(R.id.fecha);
        TextView titulo = (TextView)viewObra.findViewById(R.id.titulo);
        TextView descripcion = (TextView)viewObra.findViewById(R.id.descripcion);

        Picasso.with(getActivity()).load(obra.getUrlImagen()).into(imagenObra);
        puntos.setText("Con esta obra has obtenido: "+obra.getPuntos()+"!");
        tipo.setText(obra.getTipo());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());
        fecha.setText(dateFormat.format(obra.getFecha()));
        titulo.setText(obra.getTitulo());
        descripcion.setText(obra.getDescripcion());

        return viewObra;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}
