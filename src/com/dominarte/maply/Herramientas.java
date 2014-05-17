package com.dominarte.maply;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Cristian on 6/04/14.
 */
public class Herramientas {

	public static void mostrar_mensaje(String m, 
			Context context) {

		Toast toast1 = Toast.makeText(context, m, Toast.LENGTH_SHORT);

		toast1.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);

		toast1.show();

	}
}
