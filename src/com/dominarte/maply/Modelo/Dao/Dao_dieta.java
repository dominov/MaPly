package com.dominarte.maply.Modelo.Dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;

import com.dominarte.maply.Modelo.Alimento;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Dieta;
import com.dominarte.maply.Modelo.Usuario;

/**
 * Created by Cristian on 6/04/14.
 */
public class Dao_dieta extends Dao_general implements Dao {
	/*
	 * private static Dao_dieta ourInstance = new Dao_dieta();
	 * 
	 * public static Dao_dieta getInstance() { return ourInstance; }
	 * 
	 * private Dao_dieta() { }
	 */

	private static String TAG = "Dao_dieta";

	public Dao_dieta(ProgressDialog progreso_dialog) {
		super(progreso_dialog);
	}

	/**
	 * *************************************************************************
	 * *******************Insertar
	 */

	public Object insertar(Object o) {

		Comida comida = (Comida) o;
		boolean resul = true;

		Comida comida_a_registrar = new Comida(comida.get_tipo(),
				comida.getFecha(), comida.get_tipo_numero());

		for (int i = 0; i < comida.size(); i++) {
			if (comida.get(i).es_consumida()) {
				comida_a_registrar.add(comida.get(i));
			}

		}
		if (!comida_a_registrar.isEmpty()) {

			HttpClient httpClient = new DefaultHttpClient();

			String url_completa = URL + URL_DIETA + "tipo=2";
			Log.i(TAG, "Url dieta: " + url_completa);

			pD.setProgress(+2);

			HttpPost post = new HttpPost(url_completa);
			post.setHeader("content-type", "application/json");

			try {
				// Construimos el objeto cliente en formato JSON

				JSONArray arry_datos = new JSONArray();

				for (int i = 0; i < comida_a_registrar.size(); i++) {
					JSONObject dato = new JSONObject();

					dato.put("codPaciente", Usuario.getInstance()
							.getCod_usuario());
					dato.put("codAlimeto", comida_a_registrar.get(i)
							.getAlimento().getCod());
					dato.put("cantidad", comida_a_registrar.get(i)
							.getCantidad());
					dato.put("tipo_comida",
							comida_a_registrar.get_tipo_numero());

					Date  fecha = new Date();
					
					SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
							"yyyy-MM-dd");
					dato.put("fecha_registro", formatoDeFecha
							.format(fecha));

					arry_datos.put(dato);
				} /**/

				StringEntity entity = new StringEntity(arry_datos.toString());
				post.setEntity(entity);

				HttpResponse resp = httpClient.execute(post);
				String respStr = EntityUtils.toString(resp.getEntity());

				 Log.i(TAG, "esta es a respueta de Insert :" + respStr);

				pD.setProgress(+20);

			} catch (Exception ex) {
				Log.e("ServicioRest", "Error!", ex);

			}
		}

		update(comida);

		return resul;

	}

	/**
	 * *************************************************************************
	 * *******************Actualizar
	 */
	@Override
	public Object update(Object o) {

		Comida comida = (Comida) o;

		boolean resul = true;

		HttpClient httpClient = new DefaultHttpClient();

		String url_completa = URL + URL_DIETA + "tipo=3";
		Log.i(TAG, "Url dieta: " + url_completa);

		pD.setProgress(+3);

		HttpPost post = new HttpPost(url_completa);
		post.setHeader("content-type", "application/json");

		try {
			// Construimos el objeto cliente en formato JSON

			JSONArray arry_datos = new JSONArray();

			for (int i = 0; i < comida.size(); i++) {
				JSONObject dato = new JSONObject();

				dato.put("codigo_dieta", comida.get(i).getCod_dieta());
				dato.put("estado", comida.get(i).es_consumida());

				arry_datos.put(dato);
			}

			/**/
			StringEntity entity = new StringEntity(arry_datos.toString());
			post.setEntity(entity);

			HttpResponse resp = httpClient.execute(post);
			String respStr = EntityUtils.toString(resp.getEntity());

			//Log.i(TAG + "222", "esta es a respuesta:" + respStr);

			pD.setProgress(+20);

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			resul = false;
		}

		return resul;
	}

	@Override
	public void delete(Object o) {

	}

	/**
	 * *************************************************************************
	 * *******************listar
	 */
	@Override
	public Object listar(Object o) {

		if (hay_dieta_hoy()) {
			return "OK";

		} else {

			String [] datos = (String [])o;
			

			String url_completa = URL + URL_DIETA + "tipo=1";
			JSONArray array = null;
			JSONObject json = null;

			url_completa += "&codigo=" + datos[0] + "&fecha="
					+ datos[1];

			Log.i(TAG, "Url dieta: " + url_completa);

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
										

					array = json.getJSONArray("dietas");
					if (array != null) {
						Usuario.getInstance().getList_comida().clear();

						int progreso = 50 / array.length();

						for (int i = 0; i < array.length(); i++) {
							int codigo_dieta = array.getJSONObject(i).getInt(
									"codigo_dieta");
							String fecha = array.getJSONObject(i).getString(
									"fecha");
							double cantidad = array.getJSONObject(i).getInt(
									"cantidad");
							String comentario = array.getJSONObject(i)
									.getString("comentario");
							int estado = array.getJSONObject(i)
									.getInt("estado");

							String nombre_tipo_comida = array.getJSONObject(i)
									.getString("nombre_tipo_comida");
							int cod_tipo_comida = array.getJSONObject(i)
									.getInt("codigo_tipo_comida");
							String hora_tipo_comida = array.getJSONObject(i)
									.getString("hora_tipo_comida");
							String nombre_alimento = array.getJSONObject(i)
									.getString("nombre_alimento");
							String medida = array.getJSONObject(i).getString(
									"medida");
							int proteinas = array.getJSONObject(i).getInt(
									"proteinas");
							int carbohidratos = array.getJSONObject(i).getInt(
									"carbohidratos");
							int grasas = array.getJSONObject(i)
									.getInt("grasas");
							int calorias = array.getJSONObject(i).getInt(
									"calorias");
							int colesterol = array.getJSONObject(i).getInt(
									"colesterol");
							int cod_alimento = array.getJSONObject(i).getInt(
									"cod_alimento");

							Alimento a = new Alimento(cod_alimento,
									nombre_alimento, medida, proteinas,
									carbohidratos, grasas, calorias, colesterol);
							Dieta d = new Dieta(codigo_dieta, cod_tipo_comida,
									a, cantidad, comentario, estado);

							SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
									"yyyy-MM-dd");

							Date fe = formatoDeFecha.parse(fecha);
							fe.setHours(Integer.parseInt(hora_tipo_comida));
							fe.setMinutes(0);
							fe.setSeconds(0);
							anadir_dieta_comida(d, nombre_tipo_comida, fe);

							pD.setProgress(+progreso);
						}
					
						return "ok";
					}
				} catch (Exception e) {
					Log.e(TAG, "Communication  JSon ERROr: " + e.getMessage());
					return e.getMessage();
				}
			} else {
				Log.e(TAG, "Communication  JSon NUll");
				return "No hay Dietas para este Dia";
			}
			/*************************************/

		}
		return "No hay Dietas para este Dia";
	}

	@Override
	public Object find(Object o) {
		return null;
	}


	private void anadir_dieta_comida(Dieta d, String nombre, Date fecha) {

		for (int i = 0; i < Usuario.getInstance().getList_comida().size(); i++) {

			if (Usuario.getInstance().getList_comida().get(i).get_tipo_numero() == d
					.getTipo_comida()) {

				Usuario.getInstance().getList_comida().get(i).add(d);

				return;
			}
			;
		}

		Comida c = new Comida(nombre, fecha, d.getTipo_comida());
		c.add(d);
		Usuario.getInstance().getList_comida().add(c);



		

	}

	private boolean hay_dieta_hoy() {
		return false;
	}
}
