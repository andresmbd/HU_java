
package com.mycompany.corporatetalenthubsem2.modelo;


public class Empleado {
    private final int id;
    private final String nombre;
    private final byte edad;
    private final double salario;
    private double promedioDesempeno;

    public Empleado(int id, String nombre, byte edad, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public byte getEdad() {
        return edad;
    }

    public double getSalario() {
        return salario;
    }

    public double getPromedioDesempeno() {
        return promedioDesempeno;
    }

    public void setPromedioDesempeno(double promedioDesempeno) {
        this.promedioDesempeno = promedioDesempeno;
    }
    
}
