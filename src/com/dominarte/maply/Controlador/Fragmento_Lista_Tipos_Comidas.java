package com.dominarte.maply.Controlador;

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

	private ListView lista_dieta;
	private Comida_Listener listener;
	private ProgressDialog pDialog;

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
		new Async_Cargar_Dietas().execute();
	}

	// Inicializar Componentes
	private void inicio() {
		lista_dieta = (ListView) getView().findViewById(R.id.lv_tipos_comida);

		lista_dieta
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> list, View view,
							int pos, long id) {
						if (listener != null) {
							listener.onCorreoSeleccionado((Comida) lista_dieta
									.getAdapter().getItem(pos));
						}
					}
				});

		pDialog = new ProgressDialog(this.getActivity());
		pDialog.setMessage("Cargargando dietas....");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMax(100);

	}

	public void actualizar_lista_dieta() {
		Adaptador_dieta adaptador = new Adaptador_dieta(this.getActivity());
		lista_dieta.setAdapter(adaptador);
	}

	public void setComidas_Listener(Comida_Listener listener) {
		this.listener = listener;
	}

	public interface Comida_Listener {
		void onCorreoSeleccionado(Comida comida);
	}

	class Async_Cargar_Dietas extends AsyncTask<Void, Integer, String> {

		protected void onPreExecute() {
			// para el progress vista_dialog_nuevo_alimento
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {

			// enviamos y recibimos y analizamos los datos en segundo plano.

			Dao_dieta dao_dieta = new Dao_dieta(pDialog);
			return (String) dao_dieta.listar(Usuario.getInstance()
					.getCod_usuario());

		}

		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();

			pDialog.setProgress(progreso);
		}

		/*
		 * Una vez terminado doInBackground segun lo que halla ocurrido pasamos
		 * a la sig. activity o mostramos error
		 */
		protected void onPostExecute(String result) {
			pDialog.setProgress(100);
			pDialog.dismiss();// ocultamos progess vista_dialog_nuevo_alimento.

			actualizar_lista_dieta();

			Herramientas.mostrar_mensaje( result, 
					getActivity());

			Log.i("onPostExecute=", "" + result);
		}

	}
}