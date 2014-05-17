package com.dominarte.maply.Modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cristian on 7/04/14.
 */
public class Comida extends ArrayList<Dieta> {

	private String _tipo;
	private int _tipo_numero;
	private Date fecha;
	private boolean consumida;

	public Comida(String tipo, Date hora, int tipo_num) {
		this._tipo = tipo;
		this.fecha = hora;
		this._tipo_numero = tipo_num;
	}

	public Comida(Dieta d) {
		this._tipo_numero = d.getTipo_comida();
		add(d);

	}

	public void agregar_dieta_por_alimento(Alimento alimento, int cantidad,
			String comentario) {

		Dieta dieta = new Dieta(-1, _tipo_numero, alimento, cantidad,
				comentario, 0);
		add(dieta);
	}

	public String toString_lista_alimentos() {

		String l = "";

		for (int i = 0; i < size(); i++) {

			l += get(i).getAlimento().getNombre() + "---" + get(i).cantidad
					+ "!!!";
		}

		return l;

	}

	public String get_tipo() {
		return _tipo;
	}

	public void set_tipo(String _tipo) {
		this._tipo = _tipo;
	}

	public int get_tipo_numero() {
		return _tipo_numero;
	}

	public void set_tipo_numero(int _tipo_numero) {
		this._tipo_numero = _tipo_numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isConsumida() {
		return consumida;
	}

	public void setConsumida(boolean consumida) {
		this.consumida = consumida;
	}

}