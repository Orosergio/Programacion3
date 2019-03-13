/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solucionparcial1;

import javax.swing.JOptionPane;

/**
 *
 * @author Sergio Orozco
 * 0901-17-6739
 * Variante A
 */

public class SolucionParcial1 {

    public  static void main(String[] args) {
        
String matrizProductos[][]=new String[11][5];

    recoleccionDatos(matrizProductos);
    Operaciones(matrizProductos);
    }
    
    
  
    public static void recoleccionDatos(String [][] matrizProductos){
        matrizProductos[0][0]="Código";
        matrizProductos[0][1]="Nombre";
        matrizProductos[0][2]="Precio";
        matrizProductos[0][3]="Cantidad";
        matrizProductos[0][4]="Total";
            for(int i=1;i<11;i++){
        matrizProductos[i][0]=String.valueOf(i);
        matrizProductos[i][1]=JOptionPane.showInputDialog(null, "Ingrese Nombre del Producto");   
        //Asignando Precio con rango
        int randomPrecio=(int)(Math.random()*500)+1;
        matrizProductos[i][2]=String.valueOf(randomPrecio);
        //Asignacion de cantidad en stock
        int randomCantidad=(int)(Math.random()*5000)+100;
        matrizProductos[i][3]=String.valueOf(randomCantidad);
        }
            
      }

    
    public static void Operaciones(String [][] matrizProductos){
        //Total Asociado
        for(int i=1;i<11;i++){
        matrizProductos[i][4]=String.valueOf(Integer.parseInt(matrizProductos[i][2])*Integer.parseInt(matrizProductos[i][3]));    
        }
        
        mostrarMatriz(matrizProductos);
    }
    
    public static void mostrarMatriz(String [][] matrizProductos){
        for(int j=0;j<11;j++){
            System.out.println(matrizProductos[j][0]+"\t"+matrizProductos[j][1]+"\t"+matrizProductos[j][2]+"\t"+matrizProductos[j][3]+"\t   "+matrizProductos[j][4]+"\n");
        }
    }
    
    
}
