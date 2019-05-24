/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

/**
 *
 * Hecho por Yavhé Sergio Enrique Orozco Valle
 * 0901-17-6739
 */
public class fraccion {
//variables para la fraccion
int intNumerador, intDenominador;
public fraccion(int num, int den){
    intNumerador=num;
    intDenominador=den;
    
}
//Obtecion de los valores
public fraccion sumar(fraccion f1){
    try {
         fraccion aux=new fraccion(intNumerador*f1.intDenominador+intDenominador*f1.intNumerador,intDenominador*f1.intDenominador);
         aux.simplificar();
         return aux;    
     } catch (Exception ex) {
        System.out.println(ex);
        return null; 
    }
    
    
}
//funcion para el mcd
public int mcd(){
    //Creacion de variables auxiliares
    int num=intNumerador,den=intDenominador;
    if(num==0){
        return 1;
    }else{
        if(num<0){
            num=num*-1; //-2 --> -2*-1=2
        }
        if(den<0){
            den=den*-1; //-2 --> -2*-1=2
        }
        if(den>num){
            //Si hay este caso se hace un intercambio
            int aux=num;
            num=den;
            den=aux;
        }
        int mcd=1;
        while(den!=0){
            mcd=den;
            den=num%den; 
            num=mcd;
        }
        return mcd;
    }
}
//Se obtiene el mcd para simplificar fraccion
public void simplificar(){
    int mcd=mcd(); //guardar resultado de la funcion mcd en variable
    intNumerador=intNumerador/mcd;
    intDenominador=intDenominador/mcd;
    //If por ley de signos
    if(intNumerador<0&&intDenominador<0){
        intNumerador=intNumerador*-1;
        intDenominador=intDenominador*-1;
       }else if(intNumerador>0&&intDenominador<0){
        intNumerador=intNumerador*-1;
        intDenominador=intDenominador*-1;
       }
}


@Override
public String toString(){
    //Validacion del intDenominador
    if(intDenominador!=0){
        simplificar();
        return intNumerador+"/"+intDenominador;
       }else{
        return "El denominador debe ser diferente de cero";
    }
    
}

}


