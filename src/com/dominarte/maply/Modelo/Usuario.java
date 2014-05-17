package com.dominarte.maply.Modelo;

import java.util.ArrayList;

/**
 * Created by Cristian on 6/04/14.
 */
public class Usuario {
	private static Usuario ourInstance = new Usuario();
	private String nombre_usuario;
	private String nombre_Completo;
	private int password;
	private int cod_usuario;
	private int cod_admin;
	private int edad;
	private ArrayList<Comida> list_comida;
	private ArrayList<ArrayList<String>> lista_nombre_alimentos;

	private Usuario() {
		this.nombre_usuario = null;
		this.nombre_Completo = null;
		this.password = 0;
		this.cod_usuario = 0;
		this.cod_admin = 0;
		this.edad = 0;
		this.list_comida = new ArrayList<Comida>();
		this.lista_nombre_alimentos = new ArrayList<ArrayList<String>>();

		/*
		 * lista_nombre-alimeto : posicion 0: Codigo posicion 1: nombre;
		 */
	}

	public static Usuario getInstance() {
		return ourInstance;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public void llebar_usuario(String nombre_usuario, String nombre_Completo,
			int password, int cod_usuario, int cod_admin, int edad) {
		this.nombre_usuario = nombre_usuario;
		this.nombre_Completo = nombre_Completo;
		this.password = password;
		this.cod_usuario = cod_usuario;
		this.cod_admin = cod_admin;
		this.edad = edad;
		this.lista_nombre_alimentos = new ArrayList<ArrayList<String>>();
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getNombre_Completo() {
		return nombre_Completo;
	}

	public void setNombre_Completo(String nombre_Completo) {
		this.nombre_Completo = nombre_Completo;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public int getCod_usuario() {
		return cod_usuario;
	}

	public void setCod_usuario(int cod_usuario) {
		this.cod_usuario = cod_usuario;
	}

	public int getCod_admin() {
		return cod_admin;
	}

	public void setCod_admin(int cod_admin) {
		this.cod_admin = cod_admin;
	}

	public ArrayList<Comida> getList_comida() {
		return list_comida;
	}

	public void setList_comida(ArrayList<Comida> list_comida) {
		this.list_comida = list_comida;
	}

	@Override
	public String toString() {
		return "Usuario{" + "nombre_usuario='" + nombre_usuario + '\''
				+ ", nombre_Completo='" + nombre_Completo + '\''
				+ ", password=" + password + ", cod_usuario=" + cod_usuario
				+ ", cod_admin=" + cod_admin + ", edad=" + edad + '}';
	}

	public ArrayList<ArrayList<String>> getLista_nombre_alimentos() {
		return lista_nombre_alimentos;
	}

	public void setLista_nombre_alimentos(
			ArrayList<ArrayList<String>> lista_nombre_alimentos) {
		this.lista_nombre_alimentos = lista_nombre_alimentos;
	}
}