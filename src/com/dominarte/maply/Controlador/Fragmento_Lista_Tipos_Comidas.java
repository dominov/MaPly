package com.dominarte.maply.Controlador;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dominarte.maply.Herramientas;
import com.dominarte.maply.R;
import com.dominarte.maply.Adaptadores.Adaptador_dieta;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;
import com.dominarte.maply.Modelo.Dao.Dao_dieta;

/**
 * Created by Cristian on 14/04/14.
 */
public class Fragmento_Lista_Tipos_Comidas extends Fragment {

	private ListView _lista_dieta;
	private Comida_Listener _listener;
	private ProgressDialog _pDialog;

	public static int _ano, _mes, _dia;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.vista_fragmento_lista_comidas,
				container, false);
	}

	/**
	 * *Cuando la Actividad principal esta Cargada ****************
	 */
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		inicio();
		cargar_dietas();

	}

	public void cargar_dietas() {
		new Async_Cargar_Dietas().execute(""
				+ Usuario.getInstance().getCod_usuario(), _ano + "-" + _mes
				+ "-" + _dia);
	}

	// Inicializar Componentes
	private void inicio() {
		_lista_dieta = (ListView) getView().findViewById(R.id.lv_tipos_comida);

		_lista_dieta
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> list, View view,
							int pos, long id) {
						if (_listener != null) {
							_listener
									.onCorreoSeleccionado((Comida) _lista_dieta
											.getAdapter().getItem(pos));
						}
					}
				});

		_pDialog = new ProgressDialog(this.getActivity());
		_pDialog.setMessage("Cargargando dietas....");
		_pDialog.setIndeterminate(false);
		_pDialog.setCancelable(false);
		_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		_pDialog.setMax(100);

	}

	public void actualizar_lista_dieta() {
		Adaptador_dieta adaptador = new Adaptador_dieta(this.getActivity());
		_lista_dieta.setAdapter(adaptador);
	}

	public void setComidas_Listener(Comida_Listener listener) {
		this._listener = listener;
	}

	public interface Comida_Listener {
		void onCorreoSeleccionado(Comida comida);
	}

	class Async_Cargar_Dietas extends AsyncTask<String, Integer, String> {

		protected void onPreExecute() {
			// para el progress vista_dialog_nuevo_alimento
			_pDialog.setProgress(0);
			_pDialog.show();
		}

		protected String doInBackground(String... params) {

			// enviamos y recibimos y analizamos los datos en segundo plano.

			Dao_dieta dao_dieta = new Dao_dieta(_pDialog);

			return (String) dao_dieta.listar(params);

		}

		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();

			_pDialog.setProgress(progreso);
		}

		/*
		 * Una vez terminado doInBackground segun lo que halla ocurrido pasamos
		 * a la sig. activity o mostramos error
		 */
		protected void onPostExecute(String result) {
			_pDialog.setProgress(100);
			_pDialog.dismiss();// ocultamos progess vista_dialog_nuevo_alimento.

			actualizar_lista_dieta();

			Herramientas.mostrar_mensaje(result, getActivity());

			Log.i("onPostExecute=", "" + result);
		}

	}
}