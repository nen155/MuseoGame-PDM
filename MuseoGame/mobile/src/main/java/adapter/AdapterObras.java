package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.museogame.com.museogame.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import modelos.Obra;

public class AdapterObras extends BaseAdapter {
	private Context context;
	private List<Obra> obras;
	private LayoutInflater inflater;

	public AdapterObras(Context context, List<Obra> items) {
		super();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.obras = items;
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

		Obra item = this.obras.get(position);
		String imagen = item.getUrlImagen();
		Picasso.with(context).load(imagen).into(imgItem);

		return rowView;
	}

}