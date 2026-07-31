

package com.mycompany.corporatetalenthub;
import com.mycompany.corporatetalenthub.modelo.Empleado;
import com.mycompany.corporatetalenthub.modelo.EmpresaRecord;

public class App {

    public static void main(String[] args) {
        String encabezado = """
                    _____________________________________
                            
                            Corporate Talent Hub
                         Gestion del talento humano
                    _____________________________________
                            """;
        System.out.println(encabezado);
        
        
        Empleado empleado = instanciarEmpleado();
        
        
    }
     public static Empleado instanciarEmpleado()
        {
            return new Empleado(
                    (byte)4,
                    (short)5, 
                    6, 
                    123_456_789, 
                    7.2f, 
                    570_000, 
                    'I', 
                    true, 
                    "Andres Barrios", 
                    500, 
                    45, 
                    00045);
        }
            
}
