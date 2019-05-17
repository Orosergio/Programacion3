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
public class fraccion {
//variables para la fraccion
int numerador, denominador;
public fraccion(int num, int den){
    numerador=num;
    denominador=den;
    
}
public fraccion sumar(fraccion f1){
    try {
    fraccion aux=new fraccion(numerador*f1.denominador+denominador*f1.numerador,denominador*f1.denominador);
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
    int num=numerador,den=denominador;
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

public void simplificar(){
    int mcd=mcd(); //guardar resultado de la funcion mcd en variable
    numerador=numerador/mcd;
    denominador=denominador/mcd;
    //If por ley de signos
    if(numerador<0&&denominador<0){
        numerador=numerador*-1;
        denominador=denominador*-1;
       }else if(numerador>0&&denominador<0){
        numerador=numerador*-1;
        denominador=denominador*-1;
       }
}


@Override
public String toString(){
    //Validacion del denominador
    if(denominador!=0){
        
        simplificar();
        
        return numerador+"/"+denominador;
       }else{
        return "El denominador debe ser diferente de cero";
    }
    
}

}


