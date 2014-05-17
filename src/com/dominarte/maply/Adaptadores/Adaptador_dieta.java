package com.dominarte.maply.Adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dominarte.maply.R;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;

/**
 * Created by Cristian on 7/04/14.
 */
public class Adaptador_dieta extends ArrayAdapter {

	Activity context;
	ArrayList<Comida> lc;

	ListView lista_porciones;

	public Adaptador_dieta(Activity context) {
		super(context, R.layout.vista_fragmento_lista_comidas, Usuario
				.getInstance().getList_comida());
		this.context = context;
		lc = Usuario.getInstance().getList_comida();

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;

		ViewHolder holder;
		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.item_lista_fragmento_comida, null);

			holder = new ViewHolder();
			holder.titulo = (TextView) item.findViewById(R.id.tvTipo);
			holder.porciones = (TextView) item.findViewById(R.id.tv_resumen);

			item.setTag(holder);
		} else {
			holder = (ViewHolder) item.getTag();
		}

		holder.titulo.setText(lc.get(position).get_tipo());
		holder.porciones
				.setText(lc.get(position).getFecha().getHours() + ":00");
		return (item);
	}

	static class ViewHolder {
		TextView titulo;
		TextView porciones;
	}

}
