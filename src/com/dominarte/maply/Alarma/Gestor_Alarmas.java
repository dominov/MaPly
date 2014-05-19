package com.dominarte.maply.Alarma;

import java.util.ArrayList;
import java.util.Calendar;

import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Usuario;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Gestor_Alarmas {

	
	public static void crear_alarma( Context contexto) {
	    
		int  horas=0,min=0,segundos=0;
		
		
		ArrayList<Comida> lista_comida = Usuario.getInstance().getList_comida();
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < lista_comida.size(); i++) {
			if (cal.getTime().getTime() < lista_comida.get(i).getFecha().getTime()) {
				//Calendar cale_com = lista_comida.get(i).getFecha();
				//cal.add(Calendar.HOUR, lista_comida.get(i).getFecha().get);
				cal.add(Calendar.MINUTE, min);
				cal.add(Calendar.SECOND, segundos);
			}
			
		}
		
		
		

	    
	    Intent myIntent = new Intent(contexto, MyReceiver.class);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(contexto, 0, myIntent,0);
	    
	    AlarmManager alarmManager = (AlarmManager)contexto.getSystemService(contexto.ALARM_SERVICE);
	    alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
	}
	
	
	
	
}
