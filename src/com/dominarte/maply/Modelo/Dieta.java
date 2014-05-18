package com.dominarte.maply.Modelo;

/**
 * Created by Cristian on 7/04/14.
 */
public class Dieta {

	int cod_dieta;
	int tipo_comida;

	Alimento alimento;
	double cantidad;
	String comentario;
	int _consumida;

	public Dieta(int cod_dieta, int tipo_comida, Alimento alimento,
			double cantidad, String comentario, int esta) {
		this.cod_dieta = cod_dieta;
		this.tipo_comida = tipo_comida;

		this.alimento = alimento;
		this.cantidad = cantidad;
		this.comentario = comentario;
		this._consumida = esta;
	}

	public int getCod_dieta() {
		return cod_dieta;
	}

	public void setCod_dieta(int cod_dieta) {
		this.cod_dieta = cod_dieta;
	}

	public boolean es_consumida() {

		if (_consumida == 1)
			return true;
		return false;

	}

	public void setEstado(boolean estado) {
		if (estado) {
			this._consumida = 1;
		} else {

			this._consumida = 0;
		}

		;
	}



	public int get_consumida() {
		return _consumida;
	}

	public void set_consumida(int _consumida) {
		this._consumida = _consumida;
	}

	public int getTipo_comida() {
		return tipo_comida;
	}

	public void setTipo_comida(int tipo_comida) {
		this.tipo_comida = tipo_comida;
	}

	public Alimento getAlimento() {
		return alimento;
	}

	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
