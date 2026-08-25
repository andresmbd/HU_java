package com.mycompany.corporatetalenthub.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class EmpleadoManager {
    private final ArrayList <Empleado> empleados; 
    private final HashMap <Integer, Empleado> empleadosId;
    
    
    
    
    private final Map<String, String> sedes;

    public EmpleadoManager() {
        empleados = new ArrayList<>();
        empleadosId = new HashMap<>();
        
        //tegnologias = List.of("Java", "SpringBoot", "Docker", "PostgreSQL");
        
        sedes = Map.of(
        "BAQ", "Barranquilla",
        "BOG", "Bogota",
        "MED", "Medellin",
        "STM", "Santa Marta");
    }
    
    
        //Sem 3

    
    
        
    
//    public void reporteFinal(){
//        
//        System.out.println("     REPORTE FINAL     "
//                       + "\nTotal empleados: " + cantidadEmpleados()
//                        +"\nPromedio salarios: "+ promediarSalarioEmpleado());
//    }
    
    
    
    
}
