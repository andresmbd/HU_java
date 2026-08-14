package com.mycompany.corporatetalenthub.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmpleadoManager {
    private final ArrayList <Empleado> empleados; 
    private final HashMap <Integer, Empleado> empleadoMap;
    
    
    /*
    * List.of() crea una colección inmutable.
    * Es más segura que un ArrayList tradicional porque
    * evita modificaciones accidentales en los datos.
    * Una vez creada, no permite agregar, eliminar ni
    * reemplazar elementos mediante add(), remove() o set().
    */
    private final List<String> tegnologias;
    private final Map<String, String> sedes;

    public EmpleadoManager() {
        empleados = new ArrayList<>();
        empleadoMap = new HashMap<>();
        
        tegnologias = List.of("Java", "SpringBoot", "Docker", "PostgreSQL");
        sedes = Map.of(
        "BAQ", "Barranquilla",
        "BOG", "Bogota",
        "MED", "Medellin",
        "STM", "Santa Marta");
    }
    
    
        //Sem 3
    public  void agregarEmpleado (Empleado empleado){
        empleados.add(empleado);
        empleadoMap.put(empleado.getIdEmpleado(), empleado);
    }
    
    public ArrayList<Empleado> listarEmpleados(){
        return empleados;
    }
    
    public Empleado buscarEmpleado(int idEmpleado){
        return empleadoMap.get(idEmpleado);
    }
    
    public void eliminarEmpleado(int idEmpleado){
        var empleado = empleadoMap.get(idEmpleado);
        if(empleado != null){
            empleados.remove(empleado);
            empleadoMap.remove(idEmpleado);
        }
    }
    
        
    public int cantidadEmpleados(){
        return empleados.size();
    }
    
    public void removerEmpleadoBajoPuntaje(){
        empleados.removeIf( empleado -> !empleado.validarElegibilidad());
        empleadoMap.values().removeIf(empleado -> !empleado.validarElegibilidad());
    }
    
    
}
