
package com.riwi.talent.modelo;

import java.util.ArrayList;
import java.util.List;


public sealed abstract class Empleado permits Gerente, Desarrollador{
    private  int idEmpleado;
    private  double salario;    
    private  String nombre;
    private  int edad;

    private double promedioDesempeno;
    private List<Double> calificaciones;
    private String feedback;
    private String rol;
    
    
    public Empleado(int idEmpleado, String nombre, int edad, double salario)
    {
        if(idEmpleado < 0) 
            throw new IllegalArgumentException("Id erroneo, debe ser positivo");
        this.idEmpleado = idEmpleado;
        
        if (salario <= 0)
            throw new IllegalArgumentException("el salario no puede ser 0 o menor");
        this.salario=salario;
        
        if(nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El campo debe estar llenado");
        this.nombre =nombre.trim();
        
        if(edad < 18)
            throw new IllegalArgumentException("No se permite edad menor a 18");
        this.edad = edad;
        
        calificaciones = new ArrayList<>();
    } 
    
    public void agregarCalificacion(double calificacion){
        calificaciones.add(calificacion);
    }
    
 
    public double calcularPromedioDesempeno(){
        var suma = 0.0;
        
        if (calificaciones.isEmpty()){
            return 0.0;
        }
        
        for (var calificacion : calificaciones){
            suma += calificacion;
        }
        return suma / calificaciones.size();
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public double getSalario() {
        return salario;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getPromedioDesempeno() {
        return promedioDesempeno;
    }

    public List<Double> getCalificaciones() {
        return calificaciones;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getRol() {
        return rol;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setPromedioDesempeno(double promedioDesempeno) {
        this.promedioDesempeno = promedioDesempeno;
    }

    public void setCalificaciones(List<Double> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
