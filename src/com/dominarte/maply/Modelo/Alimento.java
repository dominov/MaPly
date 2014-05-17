package com.dominarte.maply.Modelo;

import java.util.ArrayList;

/**
 * Created by Cristian on 7/04/14.
 */
public class Alimento {

	private int cod;
	private String nombre;
	private String medida;

	private ArrayList<String[]> valor_nutri;

	public Alimento(int cod, String nombre, String medida, int proteinas,
			int carbohidratos, int grasas, int calorias, int colesterol) {
		this.cod = cod;
		this.nombre = nombre;
		this.medida = medida;
		valor_nutri = new ArrayList<String[]>();

		valor_nutri.add(new String[] { "proteinas", "" + proteinas });
		valor_nutri.add(new String[] { "carbohidratos", "" + carbohidratos });
		valor_nutri.add(new String[] { "grasas", "" + grasas });
		valor_nutri.add(new String[] { "calorias", "" + calorias });
		valor_nutri.add(new String[] { "colesterol", "" + colesterol });

	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public ArrayList<String[]> getValor_nutri() {
		return valor_nutri;
	}

	public void setValor_nutri(ArrayList<String[]> valor_nutri) {
		this.valor_nutri = valor_nutri;
	}
}
