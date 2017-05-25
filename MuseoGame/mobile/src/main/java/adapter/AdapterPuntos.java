package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.museogame.com.museogame.R;

import java.util.ArrayList;

import modelos.Obra;

/**
 * Created by Sasu on 22/05/2017.
 */
public class AdapterPuntos extends BaseAdapter {

    Context context;
    ArrayList<Obra> obras = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public AdapterPuntos(Context context, ArrayList<Obra> obras) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.obras = obras;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return obras.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return obras.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);

        TextView titulo = (TextView) vi.findViewById(R.id.tituloRow);
        titulo.setText(obras.get(position).getTitulo());

        TextView puntos = (TextView) vi.findViewById(R.id.puntosRow);
        puntos.setText(obras.get(position).getPuntos() + " puntos");
        return vi;
    }
}