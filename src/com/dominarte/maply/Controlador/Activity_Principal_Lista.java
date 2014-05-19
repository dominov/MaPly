package com.dominarte.maply.Controlador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dominarte.maply.Herramientas;
import com.dominarte.maply.R;
import com.dominarte.maply.Alarma.Gestor_Alarmas;
import com.dominarte.maply.Controlador.Fragmento_Detalles_Comida.Async_Buscar_Alimento;
import com.dominarte.maply.Controlador.Fragmento_Lista_Tipos_Comidas.Async_Cargar_Dietas;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;

public class Activity_Principal_Lista extends FragmentActivity {

	private static final int PERIOD = 2000;
	private static final String TAG = "Activity_Principal_Lista";
	TextView _txt_nombre_completo;
	Button _btn_actualizar, _btn_salir, _btn_select_fecha;
	 private PendingIntent pendingIntent;

	static final int DIALOGO_SELECCIONAR_FECHA = 0;

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


		Log.i(TAG, "crearAlarma");

	}

	protected void onResume() {
		super.onResume();
		((Fragmento_Lista_Tipos_Comidas) getSupportFragmentManager()
				.findFragmentById(R.id.FrgListado)).actualizar_lista_dieta();

	}
	/****************************************************/

	protected void onStart(){
		super.onStart();
		
		Gestor_Alarmas.crear_alarma(this);
	}

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

		_btn_select_fecha = (Button) findViewById(R.id.btn_seleccionar_fecha);
		_btn_select_fecha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOGO_SELECCIONAR_FECHA);
			}
		});

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		Fragmento_Lista_Tipos_Comidas._ano = Integer.parseInt(simpleDateFormat
				.format(date));

		simpleDateFormat = new SimpleDateFormat("MM");
		Fragmento_Lista_Tipos_Comidas._mes = Integer.parseInt(simpleDateFormat
				.format(date));

		simpleDateFormat = new SimpleDateFormat("dd");
		Fragmento_Lista_Tipos_Comidas._dia = Integer.parseInt(simpleDateFormat
				.format(date));

		_btn_select_fecha.setText(Fragmento_Lista_Tipos_Comidas._ano + "-"
				+ Fragmento_Lista_Tipos_Comidas._mes + "-"
				+ Fragmento_Lista_Tipos_Comidas._dia);

		Fragmento_Lista_Tipos_Comidas frgListado;

		frgListado = (Fragmento_Lista_Tipos_Comidas) getSupportFragmentManager()
				.findFragmentById(R.id.FrgListado);

		frgListado
				.setComidas_Listener(new Fragmento_Lista_Tipos_Comidas.Comida_Listener() {
					@Override
					public void onCorreoSeleccionado(Comida comida) {
						comida_seleccionada(comida);
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

	private void comida_seleccionada(Comida comida) {
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

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOGO_SELECCIONAR_FECHA:

			OnDateSetListener onDateSetListener = new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					Fragmento_Lista_Tipos_Comidas._ano = year;
					Fragmento_Lista_Tipos_Comidas._mes = monthOfYear;
					Fragmento_Lista_Tipos_Comidas._dia = dayOfMonth;
					_btn_select_fecha
							.setText(Fragmento_Lista_Tipos_Comidas._ano + "-"
									+ Fragmento_Lista_Tipos_Comidas._mes + "-"
									+ Fragmento_Lista_Tipos_Comidas._dia);
					evento_btn_actalizar();

				}
			};

			return new DatePickerDialog(this, onDateSetListener,
					Fragmento_Lista_Tipos_Comidas._ano,
					Fragmento_Lista_Tipos_Comidas._mes,
					Fragmento_Lista_Tipos_Comidas._dia);
		}
		return null;
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
