

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
                
        Empleado empleado = crearEmpleado();
        EmpresaRecord empresa = crearEmpresa();
        double extra = empleado.bonoExtra(56.5);
        String elegible = (empleado.validarElegibilidad())? "Sí":"No";
        
        System.out.println(encabezado);
        System.out.println("Empresa "+empresa.nombre());
        System.out.println("Empleado "+empleado.getNombre());
        System.out.println("Bono extra si id es par "+extra);
        System.out.println("Salario neto "+empleado.calcularSalarioFinal());
        System.out.println("Empleado elegible? "+elegible);
        

        
        laboratorioDeNullExeption(empleado);
        comparacionDeObjetos();
        
        
        
    }
     public static Empleado crearEmpleado()
        {
            return new Empleado(
                    (byte)4,
                    (short)5, 
                    6, 
                    123_456_789, 
                    97.2f, 
                    570_000, 
                    'I', 
                    true, 
                    "Andres Barrios", 
                    500, 
                    29, 
                    00045);
        }
     
     public static EmpresaRecord crearEmpresa()
     {
         return new EmpresaRecord("EduFlow", "123764499", 4);
     }
     
     public static void laboratorioDeNullExeption(Empleado empleado){
        // Java 8 normalmente informa que ocurrió una NullPointerException y señala
        // la línea mediante el stack trace, pero una expresión encadenada puede hacer
        // difícil reconocer cuál referencia era null.
        // Desde Java 14, Helpful NullPointerExceptions puede indicar que no se pudo
        // invocar length() porque el resultado de getNombre() era null.
        Empleado emp=crearEmpleado();
         try 
         {
             emp.setNombre(null);
             System.out.println(emp.getNombre().length());
         } catch (Exception e) 
         {
             System.out.println("Excepcion de null: " + e.getMessage());
         }
         // El try/catch es solo para que el laboratorio no detenga toda la aplicación; 
    }
     
     public static void comparacionDeObjetos()
     {
        // == no compara los atributos de los objetos: comprueba si ambas variables
        // se refieren exactamente al mismo objeto. empleado1 y empleado2 se crearon con
        // new por separado;
         
         Empleado empleado1 = crearEmpleado();
         Empleado empleado2 = crearEmpleado();
         Empleado empleado3 = empleado1;
         
         boolean igual = (empleado1 == empleado2);        
         
         System.out.println("Apuntan al mismo objeto en memoria empleado1 y empleado2? " +igual);
         System.out.println("Apuntan al mismo objeto en memoria empleado1 y empleado3? " +(empleado3==empleado1));
         
        // empleado3 recibió la misma referencia de primero.
        // Conceptualmente los objetos viven en el Heap, pero == no debe entenderse
        // como una comparación manual de direcciones físicas de memoria.
     }
            
}
