package com.dominarte.maply.Modelo.Dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;

import com.dominarte.maply.Modelo.Alimento;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;

/**
 * Created by Cristian on 27/04/2014.
 */
public class Dao_alimento extends Dao_general implements Dao {
	/*
	 * private static Dao_dieta ourInstance = new Dao_dieta();
	 * 
	 * public static Dao_dieta getInstance() { return ourInstance; }
	 * 
	 * private Dao_dieta() { }
	 */

	private static String TAG = "Dao_Alimento";

	public Dao_alimento(ProgressDialog progreso_dialog) {
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
	public Object listar(Object o) {

		if (hay_alimentos_hoy()) {
			return "OK";

		} else {

			String url_completa = URL + URL_ALIMETOS;
			JSONArray array = null;
			JSONObject json = null;

			url_completa += "tipo=" + 1;

			Log.i(TAG, "Url alimento: " + url_completa);

			pD.setProgress(+5);
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
					
					array = json.getJSONArray("alimentos");
					if (array != null) {
						ArrayList<String> c = new ArrayList<String>();
						ArrayList<String> n = new ArrayList<String>();

						Usuario.getInstance().getList_comida().clear();

						int progreso = 50 / array.length();

						for (int i = 0; i < array.length(); i++) {
							String codigo_dieta = array.getJSONObject(i)
									.getString("codigo_alimento");
							String nombre_alimento = array.getJSONObject(i)
									.getString("nombre_alimento");

							c.add(codigo_dieta);
							n.add(nombre_alimento);
							pD.setProgress(+progreso);
						}

						Usuario.getInstance().getLista_nombre_alimentos()
								.add(c);
						Usuario.getInstance().getLista_nombre_alimentos()
								.add(n);
						return "ok";
					}
				} catch (Exception e) {
					Log.e(TAG, "Communication  JSon ERROr: " + e.getMessage());
					return e.getMessage();
				}
			} else {
				Log.e(TAG, "Communication  JSon NUll");
				return "Vacio";
			}

		}
		return "Vacio";
	}

	private boolean hay_alimentos_hoy() {
		return false;
	}

	@Override
	public Object find(Object o) {

		int codigo_mandao = (Integer) o;

		Alimento ali = (Alimento) hay_alimento_local(codigo_mandao);
		if (ali != null) {
			return ali;

		} else {

			String url_completa = URL + URL_ALIMETOS;
			JSONArray array = null;
			JSONObject json = null;
			// int resp = 0;

			url_completa += "tipo=" + 2 + "&codigo_alimento=" + codigo_mandao;

			Log.i(TAG, "Url Alimento: " + url_completa);

			pD.setProgress(+10);
			json = getJson(url_completa);
			pD.setProgress(+40);

			if (json != null) {
				try {
					if (json.get("error") != null) {

						return json.get("error");
					}
					array = json.getJSONArray("alimento");

					if (array != null) {

						String nombre_alimento = array.getJSONObject(0)
								.getString("nombre_alimento");
						String medida = array.getJSONObject(0).getString(
								"medida");
						int proteinas = array.getJSONObject(0).getInt(
								"proteinas");
						int carbohidratos = array.getJSONObject(0).getInt(
								"carbohidratos");
						int grasas = array.getJSONObject(0).getInt("grasas");
						int calorias = array.getJSONObject(0)
								.getInt("calorias");
						int colesterol = array.getJSONObject(0).getInt(
								"colesterol");
						int cod_alimento = array.getJSONObject(0).getInt(
								"cod_alimento");

						ali = new Alimento(cod_alimento, nombre_alimento,
								medida, proteinas, carbohidratos, grasas,
								calorias, colesterol);

						pD.setProgress(+40);
						/*******************************************************/

						return ali;
					} else {
						return null;
					}

				} catch (Exception e) {
					Log.e(TAG,
							"Communication User JSon ERROr: " + e.getMessage());
					return null;
				}
			} else {
				Log.e(TAG, "Communication User JSon NUll");
				return null;
			}

		}
	}

	private Object hay_alimento_local(int cod) {

		for (int i = 0; i < Usuario.getInstance().getList_comida().size(); i++) {

			Comida c = Usuario.getInstance().getList_comida().get(i);
			for (int j = 0; j < c.size(); j++) {
				if (cod == c.get(j).getAlimento().getCod()) {
					return c.get(j).getAlimento();
				}

			}

		}
		return null;
	}
}
