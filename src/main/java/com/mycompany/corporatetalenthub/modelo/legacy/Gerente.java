
package com.mycompany.corporatetalenthub.modelo.legacy;


public class Gerente extends Persona{
    
    private double presupuestoMensual;
    
    public Gerente(String nombre, int edad, boolean esMayorDeEdad, String genero, double presupuestoMensual) {
        super(nombre, edad, esMayorDeEdad, genero);
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }
    
}
