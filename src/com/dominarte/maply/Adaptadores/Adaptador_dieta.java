package com.dominarte.maply.Adaptadores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dominarte.maply.R;
import com.dominarte.maply.Controlador.Fragmento_Lista_Tipos_Comidas;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;

/**
 * Created by Cristian on 7/04/14.
 */
public class Adaptador_dieta extends ArrayAdapter {

	Activity _contexto;
	ArrayList<Comida> _comdidalc;

	ListView lista_porciones;

	final public static int PROXIMO = 0;
	final private static int ACTUAL = 1;
	final private static int PASADO_COMPLETO = 2;
	final private static int PASODO_MEDIO_COMPLETO = 3;
	final private static int PASODO_INCOMPLETO = 4;
	private static final String TAG = "Adaptador_dieta";

	@SuppressWarnings("unchecked")
	public Adaptador_dieta(Activity context) {
		super(context, R.layout.vista_fragmento_lista_comidas, Usuario
				.getInstance().getList_comida());
		this._contexto = context;
		_comdidalc = Usuario.getInstance().getList_comida();
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;

		Titular titular;
		if (item == null) {
			LayoutInflater inflater = _contexto.getLayoutInflater();
			item = inflater.inflate(R.layout.item_lista_fragmento_comida, null);

			titular = new Titular();
			titular.titulo = (TextView) item.findViewById(R.id.tvTipo);
			titular.hora = (TextView) item.findViewById(R.id.tv_hora);

			item.setTag(titular);
		} else {
			titular = (Titular) item.getTag();
		}

		titular.titulo.setText(_comdidalc.get(position).get_tipo());
		int hora = _comdidalc.get(position).getFecha().getHours();
		if (hora <10) {
			titular.hora.setText( "0"+hora+ ":00");
		}else {
			titular.hora.setText( hora+ ":00");	
		}
		

		titular.colorear(ver_estado(_comdidalc.get(position)));
		return (item);
	}

	private int ver_estado(Comida comida) {

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh");
		int hora_actual = Integer.parseInt(simpleDateFormat.format(date));

		int hora_comida = Integer.parseInt(simpleDateFormat.format(comida
				.getFecha()));


		if (hora_actual < hora_comida
				&& date.getTime() < comida.getFecha().getTime()) {
			return PROXIMO;
			

		} else if (hora_actual == hora_comida
				&& date.getTime() < comida.getFecha().getTime()) {
			return ACTUAL;
		} else {
			int n = 0;

			for (int i = 0; i < comida.size(); i++) {
				if (comida.get(i).es_consumida()) {
					n++;
				}
			}

			if (n == 0) {

				return PASODO_INCOMPLETO;
			}
			if (n > 0 && n < comida.size()) {
				return PASODO_MEDIO_COMPLETO;
			}
			if (n == comida.size()) {
				
				return PASADO_COMPLETO;
			}
		}

		return 0;
	}

	class Titular {
		TextView titulo;
		TextView hora;

	
		public void colorear(int i) {

			switch (i) {
			case PROXIMO:
				//titulo.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Proximo));
				hora.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Proximo));
				break;
			case ACTUAL:
				//titulo.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Actual));
				hora.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Actual));
				break;
			case PASADO_COMPLETO:
				//titulo.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Pasado_CompletoNegro));
				hora
						.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Pasado_CompletoNegro));
				break;
			case PASODO_MEDIO_COMPLETO:
				//titulo.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Pasado_medio));
				hora.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Pasado_medio));
				break;
			case PASODO_INCOMPLETO:
				//titulo.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Pasado_incompleto));
				hora.setBackgroundColor(_contexto.getResources().getColor(R.color.Color_Pasado_incompleto));
				break;
			default:
				break;
			}

		}
	}

}
