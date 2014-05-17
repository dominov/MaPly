package com.dominarte.maply.Modelo.Dao;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;

/**
 * Created by Cristian on 6/04/14.
 */
public class Dao_general {

	public static String TAG = "Dao_general";
	public String SERVIDOR = "http://192.168.1.4/";
	public String URL = SERVIDOR + "Servicio_MaPly/servicios/";
	public String URL_USUARIO = "ServicioUsuario.php?";
	public String URL_DIETA = "Servicio_dieta.php?";
	public String URL_ALIMETOS = "Servicio_alimentos.php?";
	ProgressDialog pD;
	private JSONObject jsonObject;

	public Dao_general(ProgressDialog pD) {
		this.pD = pD;
	}

	public JSONObject getJson(String url) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet del = new HttpGet(url);
		del.setHeader("content-type", "application/json");

		JSONObject json = null;

		try {
			HttpResponse resp = httpClient.execute(del);
			String respStr = EntityUtils.toString(resp.getEntity());
			json = new JSONObject(respStr);
		} catch (HttpHostConnectException ex) {
			Log.e(TAG, ex.toString());
			try {
				json = new JSONObject();
				json.put("error", "Servidor no responde");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception ex) {
			Log.e(TAG, ex.toString());

		}

		return json;

	}
}
