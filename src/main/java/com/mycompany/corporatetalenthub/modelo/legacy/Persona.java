
package com.mycompany.corporatetalenthub.modelo.legacy;

public abstract class Persona {
    private String nombre;
    private int edad; 
    private boolean esMayorDeEdad;
    private String genero;

    public Persona(String nombre, int edad, boolean esMayorDeEdad, String genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.esMayorDeEdad = esMayorDeEdad;
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isEsMayorDeEdad() {
        return esMayorDeEdad;
    }

    public String getGenero() {
        return genero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEsMayorDeEdad(boolean esMayorDeEdad) {
        this.esMayorDeEdad = esMayorDeEdad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", edad=" + edad + ", esMayorDeEdad=" + esMayorDeEdad + ", genero=" + genero + '}';
    }
    
    
}
