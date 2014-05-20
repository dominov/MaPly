package com.dominarte.maply.Alarma;

import com.dominarte.maply.Controlador.Actividad_Principal_Lista;
import com.dominarte.maply.Controlador.Actividad_login;
import com.dominarte.maply.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver
{ private NotificationManager mManager;
	 
	@Override
	 public void onReceive(Context context, Intent intent)
	{
		   String nombre = intent.getStringExtra("nombre");

		   
	       mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		   Intent intent1 = new Intent(context,Actividad_Principal_Lista.class);
		
		   Notification notification = new Notification(R.drawable.ic_launcher,"Hora de comer!", System.currentTimeMillis());
		
		   intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		   PendingIntent pendingNotificationIntent = PendingIntent.getActivity( context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
	       notification.flags |= Notification.FLAG_AUTO_CANCEL;
	       notification.setLatestEventInfo(context, "Maply", "Ahora toca comer tu " + nombre, pendingNotificationIntent);

	       mManager.notify(0, notification);
		
	 }
	
}
