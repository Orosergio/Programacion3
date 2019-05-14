/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

/**
 *
 * @author Bryan
 */
public class Celda {
   private String dato;
	private int fila;
	private int columna;
	public Celda(String dato, int fila, int columna) {
		this.dato=dato;
		this.fila=fila;
		this.columna=columna;
	}
        //metodo para obtener el titulo del libro
	public String getDato() {
		return dato;
	}
        public void setDato(String dato) {
		this.dato=dato;
	}
	//metodo para obtener el autor
	public int getFila() {
		return fila;	
	}
	//metodo para obtener el Isbn
	public int getColumna() {
		return columna;
	} 
}
