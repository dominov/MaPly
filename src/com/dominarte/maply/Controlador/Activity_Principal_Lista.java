package com.dominarte.maply.Controlador;

import java.util.Calendar;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dominarte.maply.R;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;

public class Activity_Principal_Lista extends FragmentActivity {

	private static final int PERIOD = 2000;
	private static final String TAG = "Activity_Principal_Lista";
	TextView _txt_nombre_completo;
	Button _btn_actualizar, _btn_salir;
	private PendingIntent pendingIntent;
	/* Metod que se ejecuta cuando se selecciona un correo */
	/**
	 * *************************************************************************
	 * *****************
	 */

	private long lastPressedTime;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_activity_principal_lista);

		inicio();
		guardar_preferncias();

		//crearAlarmas();
		Log.i(TAG, "crearAlarma");

	}

	/*private void crearAlarmas() {
		Log.i(TAG, "crearAlarma");

		Intent myIntent = new Intent(this, Servicio_Alarma.class);
		pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.HOUR_OF_DAY, 22);
		calendar.add(Calendar.MINUTE, 14);
		calendar.add(Calendar.SECOND, 0);

		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);

		for (int i = 0; i < Usuario.getInstance().getList_comida().size(); i++) {

		}
	}*/

	private HashMap<String, String> _daotos_login;

	private void guardar_preferncias() {

		SharedPreferences prefs = getSharedPreferences("MAPLY_preferencias",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("codigo_usuario", Usuario.getInstance().getCod_usuario());
		editor.putString("nombre_usuario", Usuario.getInstance()
				.getNombre_usuario());
		editor.putInt("password", Usuario.getInstance().getPassword());
		editor.putString("nombre_completo", Usuario.getInstance()
				.getNombre_Completo());
		editor.putInt("edad", Usuario.getInstance().getEdad());
		editor.putInt("codigo_admin", Usuario.getInstance().getCod_admin());
		editor.commit();

	}

	private void event_btn_logout() {
		SharedPreferences prefs = getSharedPreferences("MAPLY_preferencias",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("codigo_usuario", -1);
		editor.commit();
		Intent intent = new Intent(this, Activity_login.class);
		startActivity(intent);
		finish();
	}

	/**
	 * ***************************Para cuando pulse atras 2 veces se salga de la
	 * APP**************
	 */

	private void inicio() {

		Fragmento_Lista_Tipos_Comidas frgListado;

		frgListado = (Fragmento_Lista_Tipos_Comidas) getSupportFragmentManager()
				.findFragmentById(R.id.FrgListado);

		frgListado
				.setComidas_Listener(new Fragmento_Lista_Tipos_Comidas.Comida_Listener() {
					@Override
					public void onCorreoSeleccionado(Comida comida) {
						correo_seleccionado(comida);
					}
				});

		_btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
		_btn_actualizar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				evento_btn_actalizar();
			}
		});

		_btn_salir = (Button) findViewById(R.id.btn_logout);
		_btn_salir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				event_btn_logout();
			}
		});

		_txt_nombre_completo = (TextView) findViewById(R.id.txt_nombre_completo);
		_txt_nombre_completo
				.setText(Usuario.getInstance().getNombre_Completo());

	}

	private void correo_seleccionado(Comida comida) {
		boolean hayDetalle = (getSupportFragmentManager().findFragmentById(
				R.id.FrgDetalle) != null);

		Log.i(TAG, "hay detalles es :" + hayDetalle);

		if (hayDetalle) {
			((Fragmento_Detalles_Comida) getSupportFragmentManager()
					.findFragmentById(R.id.FrgDetalle)).mostrarDetalle(comida);
		} else {
			Intent i = new Intent(this, Activity_Principal_detalles.class);
			i.putExtra("codigo_tipo_comida", comida.get_tipo_numero());
			// i.putExtra(Activity_Principal_detalles.EXTRA_TEXTO,
			// comida.get_tipo());
			startActivity(i);
		}
	}

	private void evento_btn_actalizar() {
		((Fragmento_Lista_Tipos_Comidas) getSupportFragmentManager()
				.findFragmentById(R.id.FrgListado)).cargar_dietas();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				if (event.getDownTime() - lastPressedTime < PERIOD) {
					/*
					 * Intent intent = new Intent(Intent.ACTION_MAIN);
					 * startActivity(intent);
					 */
					finish();

				} else {

					Toast.makeText(getApplicationContext(),
							"Presionar nuevamente para Salir.",
							Toast.LENGTH_SHORT).show();
					lastPressedTime = event.getEventTime();
				}
				return true;
			}
		}
		return false;
	}

}
