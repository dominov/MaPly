package com.dominarte.maply.Controlador;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.dominarte.maply.Herramientas;
import com.dominarte.maply.R;
import com.dominarte.maply.Modelo.Usuario;
import com.dominarte.maply.Modelo.Dao.Dao_alimento;
import com.dominarte.maply.Modelo.Dao.Dao_usuario;

public class Activity_login extends Activity {

	private static final int PERIOD = 2000;
	private long lastPressedTime;
	private static final String TAG = "Activity_login";
	private EditText usuario, password;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_activity_login);

		inicio();

		SharedPreferences prefs = getSharedPreferences("MAPLY_preferencias",
				Context.MODE_PRIVATE);

		int password = prefs.getInt("password", -1);
		String nombre = prefs.getString("nombre_usuario", "");
		int cod = prefs.getInt("codigo_usuario", -1);
		String nombre_completo = prefs.getString("nombre_completo", "");
		int edad = prefs.getInt("edad", -1);
		int cod_admin = prefs.getInt("codigo_admin", -1);

		if (cod > -1) {
			Log.i(TAG, "cod: " + cod);
			Usuario.getInstance().llebar_usuario(nombre, nombre_completo,
					password, cod, cod_admin, edad);
			new Async_Cargar_Nombre_Alimento().execute();
		}

	}

	private void inicio() {
		usuario = (EditText) findViewById(R.id.etUsuario);
		password = (EditText) findViewById(R.id.etPassword);

		pDialog = new ProgressDialog(Activity_login.this);
		pDialog.setMessage("Autenticando....");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMax(100);

	}

	// Evento del Boton Iniciar Sesion
	public void login(View v) {

		if(!hay_conexion()){			
			Herramientas.mostrar_mensaje("No hay conexion intentelo mas tarde", this);
			return;
		}
		
		if (usuario.getText().toString() != null
				&& password.getText().toString() != null) {

			new Asynclogin().execute(usuario.getText().toString(), password
					.getText().toString());
		} else {

			Herramientas.mostrar_mensaje(
					"Digite Usuario y Contraseña",
					getApplicationContext());

		}
	}

	public void llamar_activity_principal() {

		Intent intent = new Intent(this, Activity_Principal_Lista.class);
		startActivity(intent);
		finish();
	}

	public boolean hay_conexion() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			/*
			 * switch (event.getAction()) { case KeyEvent.ACTION_DOWN: if
			 * (event.getDownTime() - lastPressedTime < PERIOD) { Intent intent
			 * = new Intent(Intent.ACTION_MAIN); finish();
			 * 
			 * } else {
			 * 
			 * finish(); } return true; }
			 */
			finish();
		}
		return false;
	}

	/*******************************************************************************************
	 * 
	 * @author Cristian
	 *	Tareas Asincronas
	 ***********************************************************************************************/
	
	class Asynclogin extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			// para el progress vista_dialog_nuevo_alimento
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(String... params) {

			String[] aaa = new String[] { params[0], params[1] };
			// enviamos y recibimos y analizamos los datos en segundo plano.

			Dao_usuario dao_usuario = new Dao_usuario(pDialog);

			return (String) dao_usuario.find(aaa);

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

			Log.i("onPostExecute=", "" + result);

			if (result.equals("ok")) {

				new Async_Cargar_Nombre_Alimento().execute();

			} else {
				Herramientas.mostrar_mensaje( result,
						getApplicationContext());
			}

		}

	}

	class Async_Cargar_Nombre_Alimento extends AsyncTask<Void, Integer, String> {

		protected void onPreExecute() {
			// para el progress vista_dialog_nuevo_alimento
			pDialog.setMessage("Cargando Nombres de Alimentos");
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {

			// enviamos y recibimos y analizamos los datos en segundo plano.

			Dao_alimento dao_alimento = new Dao_alimento(pDialog);
			return (String) dao_alimento.listar(null);

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

			llamar_activity_principal();
			// Herramientas.mostrar_mensaje("Dietas Descargada", result, "OK",
			// getApplicationContext());

			Log.i("onPostExecute=", "" + result);
		}

	}
}
