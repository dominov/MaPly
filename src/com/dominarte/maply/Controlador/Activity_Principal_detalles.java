package com.dominarte.maply.Controlador;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dominarte.maply.R;
import com.dominarte.maply.Modelo.Usuario;

/**
 * Created by Cristian on 19/04/14.
 */
public class Activity_Principal_detalles extends FragmentActivity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_activity_principal_detalles);

		Fragmento_Detalles_Comida detalle = (Fragmento_Detalles_Comida) getSupportFragmentManager()
				.findFragmentById(R.id.FrgDetalle);
		int numero_tipo = getIntent().getIntExtra("codigo_tipo_comida", 0);

		for (int i = 0; i < Usuario.getInstance().getList_comida().size(); i++) {
			if (Usuario.getInstance().getList_comida().get(i).get_tipo_numero() == numero_tipo) {

				detalle.mostrarDetalle(Usuario.getInstance().getList_comida()
						.get(i));
				break;
			}
		}

	}
}
