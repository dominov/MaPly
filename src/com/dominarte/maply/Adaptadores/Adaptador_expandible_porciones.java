package com.dominarte.maply.Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dominarte.maply.R;
import com.dominarte.maply.Modelo.Comida;
import com.dominarte.maply.Modelo.Dieta;

/**
 * Created by Cristian on 23/04/2014.
 */
public class Adaptador_expandible_porciones extends BaseExpandableListAdapter {

	private static final String TAG = "Adaptador_expandible_porciones";
	private Context _context;
	// private List<Dieta> _listDataHeader; // header titles
	// child data in format of header title, child title
	// private HashMap<String, List<String>> _listDataChild;

	private Comida _comida;

	public Adaptador_expandible_porciones(Context context, Comida c) {
		this._context = context;
		this._comida = c;

	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._comida.get(groupPosition).getAlimento().getValor_nutri()
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String[] hijo_seleccionado = (String[]) getChild(groupPosition,
				childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.item_lista_porciones_hijo, null);
		}

		TextView txthijo = (TextView) convertView.findViewById(R.id.lbl_key);
		TextView lbl_valor = (TextView) convertView
				.findViewById(R.id.lbl_valor);

		txthijo.setText(hijo_seleccionado[0]);
		lbl_valor.setText(hijo_seleccionado[1]);
		Log.i(TAG, hijo_seleccionado[1]);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return _comida.get(groupPosition).getAlimento().getValor_nutri().size();
	}

	@Override
	public Object getGroup(int groupPosition) {

		return this._comida.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (_comida == null) {
			return 0;
		}
		// Log.i(TAG,"Tamaño comida:"+this._comida.size());
		return this._comida.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		final Dieta padre = (Dieta) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.item_lista_porciones_padre, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		TextView lbl_cantidad = (TextView) convertView
				.findViewById(R.id.lbl_cantidad);
		TextView lbl_medida = (TextView) convertView
				.findViewById(R.id.lbl_medida);

		CheckBox cbx_porcion_consumida = (CheckBox) convertView
				.findViewById(R.id.cbx_porcion_consumida);
		cbx_porcion_consumida.setChecked(padre.es_consumida());

		cbx_porcion_consumida
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						padre.setEstado(isChecked);
						// revisar_si_comida_esta_consumida();
					}
				});
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(padre.getAlimento().getNombre());
		lbl_cantidad.setText("" + padre.getCantidad());
		lbl_medida.setText(padre.getAlimento().getMedida());

		return convertView;
	}

	/*
	 * private void revisar_si_comida_esta_consumida() {
	 * 
	 * for (int i = 0; i < _comida.size(); i++) {
	 * _comida.setConsumida(_comida.get(i).isEstado()); if
	 * (_comida.get(i).isEstado()) { return; } } }
	 */

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
