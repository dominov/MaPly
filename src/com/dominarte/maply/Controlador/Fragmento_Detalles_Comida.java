package com.dominarte.maply.Controlador;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dominarte.maply.Herramientas;
import com.dominarte.maply.R;
import com.dominarte.maply.Adaptadores.Adaptador_expandible_porciones;
import com.dominarte.maply.Modelo.Alimento;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;
import com.dominarte.maply.Modelo.Dao.Dao_alimento;
import com.dominarte.maply.Modelo.Dao.Dao_dieta;

/**
 * Created by Cristian on 14/04/14.
 */
public class Fragmento_Detalles_Comida extends Fragment {

	private static final String TAG = "Fragmento_Detalles_Comida";

	ExpandableListView lista_expandible;
	TextView tipo, fecha, hora;
	CheckBox _cbx_consumir_todo;
	private HashMap<String, String> _datos_capturados;
	TextView txt;
	private Button _btn_nuevo_alimento, _btn_registrar_comida;
	private Comida _comida;
	private ProgressDialog pDialog;

	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.vista_fragmento_detalles_comida,
				container, false);

	}

	public void mostrarDetalle(Comida comida) {

		_comida = comida;

		inicio();

		tipo.setText(_comida.get_tipo());
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");

		fecha.setText(formatoDeFecha.format(_comida.getFecha()));
		hora.setText(_comida.getFecha().getHours() + ":00");

		Adaptador_expandible_porciones adaptador_expandible_porciones = new Adaptador_expandible_porciones(
				this.getActivity(), comida);
		lista_expandible.setAdapter(adaptador_expandible_porciones);

	}

	private void inicio() {

		lista_expandible = (ExpandableListView) getView().findViewById(
				R.id.lst_exp_porciones);
		tipo = (TextView) getView().findViewById(R.id.txt_tipo);
		fecha = (TextView) getView().findViewById(R.id.txt_fecha);
		hora = (TextView) getView().findViewById(R.id.lbl_hora);
		_btn_nuevo_alimento = (Button) getView().findViewById(
				R.id.btn_agregar_porcion);
		_cbx_consumir_todo = (CheckBox) getView().findViewById(
				R.id.cbox_registrar_todo);
		_btn_registrar_comida = (Button) getView().findViewById(
				R.id.btn_registrar_comida);

		pDialog = new ProgressDialog(this.getActivity());
		pDialog.setMessage("Descargando Alimentos....");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMax(100);

		/************************* Listeners ****************************/

		_btn_nuevo_alimento.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				evento_btn_nuevo_alimento();
			}
		});

		_cbx_consumir_todo
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						evento_cbx_registrar_todo(isChecked);
					}
				});

		_btn_registrar_comida.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				evento_btn_registrar_comida();
			}
		});

	}

	/********************************************************************************************* evento registrar */

	private void evento_btn_registrar_comida() {

		new Async_Registrar_Comida().execute(_comida);

	}

	private void evento_cbx_registrar_todo(boolean estado) {

		for (int i = 0; i < _comida.size(); i++) {
			_comida.get(i).setEstado(estado);
		}

		Adaptador_expandible_porciones adaptador_expandible_porciones = new Adaptador_expandible_porciones(
				this.getActivity(), _comida);
		lista_expandible.setAdapter(adaptador_expandible_porciones);
	}

	/***************************************************************************************** Evento nuevo ali */
	private void evento_btn_nuevo_alimento() {

		final Dialog custom;
		final TextView cantida, comentario;
		final Spinner nombre;
		final Button btn_guardar;
		final Button btn_cancelar;

		custom = new Dialog(getActivity());
		custom.setContentView(R.layout.vista_dialog_nuevo_alimento);

		nombre = (Spinner) custom.findViewById(R.id.spr_nombres);
		cantida = (TextView) custom.findViewById(R.id.txt_cantidad);
		comentario = (TextView) custom.findViewById(R.id.txt_comentario);
		btn_guardar = (Button) custom.findViewById(R.id.btn_guardar);
		btn_cancelar = (Button) custom.findViewById(R.id.btn_cancelar);
		custom.setTitle("Nueva Porción");

		// *******************************/

		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_spinner_item,
				Usuario.getInstance().getLista_nombre_alimentos().get(1));
		adaptador
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		nombre.setAdapter(adaptador);
		/********************************/

		btn_guardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				_datos_capturados = new HashMap<String, String>();

				_datos_capturados.put("codigo_alimento",
						Usuario.getInstance().getLista_nombre_alimentos()
								.get(0).get(nombre.getSelectedItemPosition()));
				_datos_capturados.put("cantidad", ""
						+ (String) cantida.getText().toString());
				_datos_capturados.put("Comentario", ""
						+ (String) comentario.getText().toString());
				new Async_Buscar_Alimento().execute(Integer
						.parseInt(_datos_capturados.get("codigo_alimento")));

				custom.dismiss();
			}

		});
		btn_cancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				custom.dismiss();

			}
		});
		custom.show();

	}

	class Async_Registrar_Comida extends AsyncTask<Comida, Integer, Boolean> {

		protected void onPreExecute() {
			// para el progress vista_dialog_nuevo_alimento
			pDialog.setProgress(0);
			pDialog.setMessage("Sincronizando...");
			pDialog.show();
		}

		protected Boolean doInBackground(Comida... params) {

			Comida aaa = params[0];
			// enviamos y recibimos y analizamos los datos en segundo plano.
			Dao_dieta dao_dieta = new Dao_dieta(pDialog);
			return (Boolean) dao_dieta.insertar(aaa);

		}

		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();

			pDialog.setProgress(progreso);
		}

		/*
		 * Una vez terminado doInBackground segun lo que halla ocurrido pasamos
		 * a la sig. activity o mostramos error
		 */
		protected void onPostExecute(Boolean result) {
			pDialog.setProgress(100);
			pDialog.dismiss();// ocultamos progess vista_dialog_nuevo_alimento.

			Log.i("onPostExecute=", "" + result);

			if (result) {

				Herramientas.mostrar_mensaje( "Comida Registrada",
						getActivity());

			} else {
				Herramientas.mostrar_mensaje(
						"Error Al registrar Comida", getActivity());
			}

		}

	}

	class Async_Buscar_Alimento extends AsyncTask<Integer, Integer, Alimento> {

		protected void onPreExecute() {
			// para el progress vista_dialog_nuevo_alimento
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected Alimento doInBackground(Integer... params) {

			int aaa = params[0];
			// enviamos y recibimos y analizamos los datos en segundo plano.

			Dao_alimento dao_usuario = new Dao_alimento(pDialog);

			return (Alimento) dao_usuario.find(aaa);

		}

		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();

			pDialog.setProgress(progreso);
		}

		/*
		 * Una vez terminado doInBackground segun lo que halla ocurrido pasamos
		 * a la sig. activity o mostramos error
		 */
		protected void onPostExecute(Alimento result) {
			pDialog.setProgress(100);
			pDialog.dismiss();// ocultamos progess vista_dialog_nuevo_alimento.

			Log.i("onPostExecute=", "" + result);

			if (result != null) {

				_comida.agregar_dieta_por_alimento(result,
						Integer.parseInt(_datos_capturados.get("cantidad")),
						_datos_capturados.get("comentario"));
				Adaptador_expandible_porciones adaptador_expandible_porciones = new Adaptador_expandible_porciones(
						getActivity(), _comida);
				lista_expandible.setAdapter(adaptador_expandible_porciones);

			} else {
				Herramientas.mostrar_mensaje(
						"Error Cargar nuevo  alimento", getActivity());
			}

		}

	}

}