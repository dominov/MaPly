package com.dominarte.maply.Modelo.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;

import com.dominarte.maply.Modelo.Usuario;

/**
 * Created by Cristian on 6/04/14.
 */
public class Dao_usuario extends Dao_general implements Dao {
	/*
	 * private static Dao_usuario ourInstance = new Dao_usuario();
	 * 
	 * public static Dao_usuario getInstance() { return ourInstance; }
	 */

	private static String TAG = "Dao_Usuario";

	public Dao_usuario(ProgressDialog progreso_dialog) {
		super(progreso_dialog);

	}

	@Override
	public Object update(Object o) {
		return null;
	}

	@Override
	public void delete(Object o) {

	}

	@Override
	public Object find(Object o) {

		if (hay_usuario_local()) {
			return true;

		} else {

			String[] s = (String[]) o;

			String url_completa = URL + URL_USUARIO;
			JSONArray array = null;
			JSONObject json = null;
			// int resp = 0;

			url_completa += "tipo=" + 2 + "&nombre_usuario=" + s[0]
					+ "&password=" + s[1];

			Log.i(TAG, "Url user: " + url_completa);

			pD.setProgress(+10);
			json = getJson(url_completa);
			pD.setProgress(+40);

			if (json != null) {

				try {
					if (json.get("error") != null) {

						return json.get("error");
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {


					array = json.getJSONArray("usuario");

					if (array != null) {

						int codigo_usuario = array.getJSONObject(0).getInt(
								"codigo_usuario");
						int codigo_admin = array.getJSONObject(0).getInt(
								"codigo_admin");
						String nombre_completo = array.getJSONObject(0)
								.getString("nombre_completo");
						String nombre_usuario = array.getJSONObject(0)
								.getString("nombre_usuario");
						int password = array.getJSONObject(0)
								.getInt("password");
						// String fecha =
						// array.getJSONObject(0).getString("fecha");

						Usuario.getInstance().llebar_usuario(nombre_usuario,
								nombre_completo, password, codigo_usuario,
								codigo_admin, 10);
						pD.setProgress(+40);
						/*******************************************************/

						return "ok";
					} else {
						return "Usuario o Contraseña incorrectos";
					}

				} catch (Exception e) {
					Log.e(TAG,
							"Communication User JSon ERROr: " + e.getMessage());
					//return e.getMessage();
				}
			} else {
				Log.e(TAG, "Communication User JSon NUll");
				// Herramientas.mostrar_mensaje("",
				// "No hay conexion con el servidorm", "", );

				return "Usuario o Contraseña incorrectos";
			}

		}
		return "Usuario o Contraseña incorrectos";
	}

	@Override
	public Object listar(Object o) {
		return null;
	}

	private boolean hay_usuario_local() {
		return false;
	}
}
