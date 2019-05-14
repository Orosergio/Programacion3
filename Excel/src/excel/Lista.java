/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

/**
 *
 * @author Bryan Orlando
 * Aguirre Sagastume 
 * 0901-17-646
 */
public class Lista {
	private Nodo cabeza=null;
	private int longitud=0;
        //crear clase de nodo
	private class Nodo {
		public Celda celda;
                //se crea puntero de siguiente 
		public Nodo siguiente=null;
		public Nodo(Celda celda) {
			this.celda=celda;
		}
	}
        //metodo para ingresar el nodo al principio
	public void insertarPrincipio(Celda celda) {
		Nodo nodo=new Nodo(celda);
		nodo.siguiente=cabeza;
		cabeza=nodo;
		longitud++;	
	}
        //metodo para ingresar el nodo al final
	public void insertarFinal(Celda celda) {
		Nodo nodo=new Nodo(celda);
		if (cabeza==null) {
			cabeza=nodo;
		} else {
			Nodo puntero=cabeza;
			while (puntero.siguiente!=null) {
				puntero=puntero.siguiente;
			}
			puntero.siguiente=nodo;
		}
		longitud++;
	}
        //metodo para ingresar el nodo en una posicion especificada
	public void insertarDespues(int n, Celda celda) {
		Nodo nodo=new Nodo(celda);
		if (cabeza==null) {
			cabeza=nodo;
		} else {
			Nodo puntero=cabeza;
			int contador=0;
			while (contador<n && puntero.siguiente!=null) {
				puntero=puntero.siguiente;
				contador++;
			}
			nodo.siguiente=puntero.siguiente;
			puntero.siguiente=nodo;
		}
		longitud++;
	}
        //metodo para obtener la direccion de la posicion ingresada
	public Celda obtener(int n) {
		if (cabeza==null) {
			return null;
		} else {
			Nodo puntero=cabeza;
			int contador=0;
			while (contador<n && puntero.siguiente!=null) {
				puntero=puntero.siguiente;
				contador++;
			}
			if (contador!=n) {
				return null;
			} else {
				return puntero.celda;
			}
		}
	}
        //metodo para modificar dato de la celda buscándolo por la fila y la columna
        public void modifyPorFilaColumna(int fila, int columna, String dat){
            Nodo aux = cabeza;
            boolean encontrado = false;
            // Recorre la lista hasta encontrar el elemento o al final 
            while(aux != null && encontrado != true){
                if (fila == aux.celda.getFila() && columna == aux.celda.getColumna()){
                    encontrado = true;
                    aux.celda.setDato(dat);
                }
                else{
                    aux = aux.siguiente;
                }
            }
        }
        //metodo para obtener la longitud de la lista
	public int contar() {
		return longitud;
	}
        public boolean estaVacia() {
            return cabeza==null;
        }
        //metodo para eliminar el primer nodo
        public void eliminaPrincipio() {
            if (cabeza!=null){
                Nodo primer = cabeza;
                cabeza=cabeza.siguiente;
                longitud--;
            }
        }
        //metodo para eliminar el ultimo nodo
        public void eliminarUltimo() {
            if (cabeza!= null) {
                if (cabeza.siguiente==null) {
                    cabeza=null;  
                    longitud--;
                } else {
                    Nodo puntero=cabeza;
                    while (puntero.siguiente.siguiente!=null) {                    
                        puntero=puntero.siguiente;
                    }
                    puntero.siguiente=null;
                    longitud--;
                }
            }
        }
        //metodo para eliminar una celda elegida
        public void eliminarCelda(int n) {
            //si la lista no es vacia
            if (cabeza!=null){
                if (n==0){
                    Nodo primer=cabeza;
                    cabeza=cabeza.siguiente;
                    primer.siguiente=null;
                    longitud--;
                } else if (n<longitud) {
                    Nodo puntero=cabeza;
                    int contador=0;
                    while (contador<(n-1)){
                        puntero=puntero.siguiente;
                        contador++;
                    }
                    Nodo temp=puntero.siguiente;
                    puntero.siguiente=temp.siguiente;
                    temp.siguiente=null;
                    longitud--;
                }
            }
        }
        //metodo para ver la lista guardada
        public String Listar()
	{
		String Dato=" ";
		Nodo aux=cabeza;
                //ciclo para obtener los datos que contiene la lista
		while (aux!=null)
		{
			Dato+="{Titulo: " + aux.celda.getDato()+"\n  Autor:"+aux.celda.getFila()+"\n  Isbn: "+aux.celda.getColumna() + "}\n\n";
			aux=aux.siguiente;
		}
		return(Dato);
	}
}
