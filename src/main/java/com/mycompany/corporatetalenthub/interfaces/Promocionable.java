
package com.mycompany.corporatetalenthub.interfaces;

public interface Promocionable {
    double calcularBonoAscenso();
    
    default void registrarLog(){
        System.out.println("[LOG]: Se ha calculado un bono para ascenso.");
    }
}
