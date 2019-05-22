/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;



/**
 *
 * @author Sergio Orozco
 */
public class ControllerGeneral {
   
int numerador, denominador;
public ControllerGeneral(double decimal){
        
}    
    

public String toFraccion(double decimal){
     fraccion newFraccion;
     String partInt=getPartInt(String.valueOf(decimal));
     String partDecimal=getPartDecimal(String.valueOf(decimal));
     
     int n=Integer.parseInt(partInt);
     int d=Integer.parseInt(partDecimal);
     int factorMultiplicador=factorMultiplicador(partDecimal.length());
     //Si la parte entera es cero
     if(n==0){
         newFraccion=new fraccion(d,factorMultiplicador);
     }else{//Si la parte entera es diferente de cero
         fraccion f1=new fraccion(n,1);
         fraccion f2=new fraccion(d,factorMultiplicador);
         newFraccion=f1.sumar(f2);
     }     
 
     return newFraccion.toString();
}
//Metodo para obtener la parte entera del numero decimal
    private String getPartInt(String val){
        int i=getPuntoDecimal(val);
        return val.substring(0, i);
        
    }
    //Metodo para obtener la parte decimal
    private String getPartDecimal(String val){
        int i=getPuntoDecimal(val);
        return val.substring(i+1,val.length());
    }
    
    
    //Devuelve la posicion en que se encuentra el punto decimal
    private int getPuntoDecimal(String val){//String para poder recorrer las posiciones
        int i=0;
        while(val.charAt(i)!='.'){
            i++;
            //Validacion por si no tuviera punto decimal y no se quede en bucle
            if(i==val.length()){
                break;
            }
        }
        return i;
    }

    
    private int factorMultiplicador(int longitud){
        String factorMultiplicador  ="1";
        
        for (int i = 0; i < longitud; i++) {
            factorMultiplicador=factorMultiplicador+"0";
        }
        return Integer.parseInt(factorMultiplicador);
    }
    



}
