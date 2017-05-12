package adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.museogame.com.museogame.Inicio;
import com.museogame.com.museogame.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import modelos.Obra;

public class AdapterObras extends BaseAdapter {
	private Context context;
	private List<Obra> obras;
	private LayoutInflater inflater;
	private Inicio.OnObraSelectedListener onObraSelectedListener;

	public AdapterObras(Context context, List<Obra> items, Inicio.OnObraSelectedListener onObraSelectedListener) {
		super();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.obras = items;
		this.onObraSelectedListener = onObraSelectedListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.obras.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.obras.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return obras.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;

		if (rowView == null) {
			rowView = inflater.inflate(R.layout.item_obra_cuadrada, parent, false);
		}

		ImageView imgItem = (ImageView) rowView.findViewById(R.id.imgItem);

		final Obra obra = this.obras.get(position);
		Uri imagen = obra.getUrlImagen();
		Picasso.with(context).load(imagen).into(imgItem);

		imgItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onObraSelectedListener.onObraSeleccionada(obra);
			}
		});

		return rowView;
	}

}