/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

/**
 *
 * @author KAMINATOR
 */
public class Alinear {  
        private String alineado;	
	private int columna;
	public Alinear(String alineado, int columna) {
		this.alineado=alineado;		
		this.columna=columna;
	}
        //metodo para obtener el titulo del libro
	public String getAlineado() {
		return alineado;
	}
        public void setAlienado(String alineado) {
		this.alineado=alineado;
	}	
	//metodo para obtener el Isbn
	public int getColumna() {
		return columna;
	} 
    
}
