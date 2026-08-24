
package com.mycompany.corporatetalenthub.modelo.legacy;


public class Desarrollador extends Persona{
    
    private String lenguajePrincipal;
    
    public Desarrollador(String nombre, int edad, boolean esMayorDeEdad, String genero, String lenguajePrincipal) {
        super(nombre, edad, esMayorDeEdad, genero);
        this.lenguajePrincipal = lenguajePrincipal;
    }
    
    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }
    
    
}
