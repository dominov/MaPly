package com.dominarte.maply.Alarma;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.dominarte.maply.Controlador.Actividad_login;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Gestor_Alarmas {

	
	private static final String TAG = "gestor Alarma";

	public static void crear_alarma( Context contexto) {
		Log.i(TAG, "crearAlarma");
		

		/**para esperar que se llene bn lalista*/
		try {Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		ArrayList<Comida> lista_comida = Usuario.getInstance().getList_comida();
		Date hora_hoy = new Date(); 
		Comida com = null;
		
		int hora = hora_hoy.getHours();
		Log.i(TAG, hora+" sssssss " + Usuario.getInstance().getList_comida().size() );
		for (int i = 0; i < Usuario.getInstance().getList_comida().size(); i++) {
			
			Log.i(TAG, hora+" : "+Usuario.getInstance().getList_comida().get(i).getFecha().getHours());
			
			if (hora < Usuario.getInstance().getList_comida().get(i).getFecha().getHours()) {
				com = Usuario.getInstance().getList_comida().get(i);
				break;
			}
			
		}
	    
		try {
			 Intent myIntent = new Intent(contexto, MyReceiver.class);
			    myIntent.putExtra("nombre", com.get_tipo());
			    PendingIntent pendingIntent = PendingIntent.getBroadcast(contexto, 0, myIntent,0);  
		    
			    AlarmManager alarmManager = (AlarmManager)contexto.getSystemService(contexto.ALARM_SERVICE);
			    alarmManager.set(AlarmManager.RTC, com.getFecha().getTime(), pendingIntent);
			    
		} catch (Exception e) {
			// TODO: handle exception
		}
	   
	}
	
	
	
	
}
