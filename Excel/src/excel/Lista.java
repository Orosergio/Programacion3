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
        private NodoAlinear inicio=null;
	private int longitud=0;
        private int largo=0;
        //crear clase de nodo
	private class Nodo {
		public Celda celda;
                //se crea puntero de siguiente 
		public Nodo siguiente=null;
		public Nodo(Celda celda) {
			this.celda=celda;
		}
	}        
                ///crear clase para el alineado por columna        
        private class NodoAlinear{
                public Alinear alinear;
                 public NodoAlinear Siguiente=null;
                 public NodoAlinear(Alinear alinear){
                     this.alinear=alinear;
                 }
         }        
        ///
        //metodo para ingresar el nodo en posicion vacia
	public void insertarDato(Celda celda) {
		Nodo nodo=new Nodo(celda);
		if (cabeza==null) {
			cabeza=nodo;
		} else {
			Nodo puntero=cabeza;
			while (puntero.siguiente!=null) {
				puntero=puntero.siguiente;
			}
			nodo.siguiente=puntero.siguiente;
			puntero.siguiente=nodo;
		}
		longitud++;
	}
        //metodo para obtener la posición de lo buscado
	public int obtenerPos(int fila, int columna) {
            int intPos=-1;
            Nodo aux = cabeza;
            boolean encontrado = false;
            // Recorre la lista hasta encontrar el elemento o al final 
            while(aux != null && encontrado != true){
                intPos++;
                if (fila == aux.celda.getFila() && columna == aux.celda.getColumna()){
                    encontrado = true;
                }
                else{
                    aux = aux.siguiente;
                }
                if (aux==null) {
                    intPos=-1;
                }
            }
            return intPos;
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
        public String obtenerNodo(int pos)
	{
		String Dato="";
		Nodo aux=cabeza;
                int intContador=0;
                //ciclo para obtener los datos que contiene la lista
		while (aux!=null)
		{
                    if (intContador==pos) {
                        Dato=aux.celda.getDato()+";"+aux.celda.getFila()+";"+aux.celda.getColumna() + ";";
                    }
                    intContador++;
                    aux=aux.siguiente;
		}
		return(Dato);
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
         //
        //
        ///Apartir de aqui es para la alineacion
        public void insertarAlineado(Alinear alinear) {
		NodoAlinear nodo=new NodoAlinear(alinear);
		if (inicio==null) {
			inicio=nodo;
		} else {
			NodoAlinear puntero=inicio;
			while (puntero.Siguiente!=null) {
				puntero=puntero.Siguiente;
			}
			nodo.Siguiente=puntero.Siguiente;
			puntero.Siguiente=nodo;
		}
		largo++;
	}
        
       public int obtenerColumna(int columna) {
            int intPos=-1;
            NodoAlinear aux = inicio;
            boolean encontrado = false;
            // Recorre la lista hasta encontrar el elemento o al final 
            while(aux != null && encontrado != true){
                intPos++;
                if (columna == aux.alinear.getColumna()){
                    encontrado = true;
                }
                else{
                    aux = aux.Siguiente;
                }
                if (aux==null) {
                    intPos=-1;
                }
            }
            return intPos;
	}
       
             //metodo para modificar dato de la celda buscándolo por la fila y la columna
        public void modificarPorColumna(int columna, String Alineado){
            NodoAlinear aux = inicio;
            boolean encontrado = false;
            // Recorre la lista hasta encontrar el elemento o al final 
            while(aux != null && encontrado != true){
                if (columna == aux.alinear.getColumna()){
                    encontrado = true;
                    aux.alinear.setAlienado(Alineado);                                      
                }
                else{
                    aux = aux.Siguiente;
                }
            }
        }
        //
            //metodo para obtener la longitud de la lista
	public int Tamaño() {
		return largo;
	}
        
            //metodo para ver la lista guardada
        public String obtenerNodoAlienar(int pos)
	{
		String Dato="";
		NodoAlinear aux=inicio;
                int intContador=0;
                //ciclo para obtener los datos que contiene la lista
		while (aux!=null)
		{
                    if (intContador==pos) {
                        Dato=aux.alinear.getAlineado()+";"+aux.alinear.getColumna()+ ";";
                    }
                    intContador++;
                    aux=aux.Siguiente;
		}
		return(Dato);
	}
        //       
             //metodo para ver la lista guardada
        public String ListarAlineado()
	{
		String Dato=" ";
		NodoAlinear aux=inicio;
                //ciclo para obtener los datos que contiene la lista
		while (aux!=null)
		{
			Dato+="{Titulo: " + aux.alinear.getAlineado()+"\n  Isbn: "+aux.alinear.getColumna() + "}\n\n";
			aux=aux.Siguiente;
		}
		return(Dato);
	}
        public void vaciarLista() {
            if (cabeza!=null){ //Si el nodo caeza no está vacío
                Nodo primer = cabeza;
                cabeza=cabeza.siguiente=null; //el nodo cabeza se igual al siguiente que se igual como vacío
                longitud=0; //se resta uno a la longitud de la lista
            }
        }
}
