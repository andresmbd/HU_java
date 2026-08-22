package com.mycompany.corporatetalenthub;
import com.mycompany.corporatetalenthub.modelo.moderno.Empleado;
import com.mycompany.corporatetalenthub.modelo.EmpresaRecord;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    
    private final static ArrayList <Empleado> empleados = new ArrayList<>();
    private final static HashMap <String, Empleado> empleadosId = new HashMap<>();
    
    /*
    * List.of() y Map.of crean una colección inmutable.
    * Son apropiadas para datos de configuración porque evitan cambios
    * accidentales durante la ejecución.
    
    * Es más segura que un ArrayList tradicional porque
    * evita modificaciones accidentales en los datos.
    * Una vez creada, no permite agregar, eliminar ni
    * reemplazar elementos mediante add(), remove() o set().
    */
    
    private final static List <String> tecnologias = List.of(
            "Java",
            "Spring boot",
            "PostgreSQL",
            "Git");
    
    private final static Map <String,String> sedes = Map.of(
            "BAQ", "Barranquilla",
            "BOG", "Bogota",
            "MED", "Medellin");
    
    private static final int CANTIDAD_TRIMESTRES = 3;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 100.0;
    

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


        try (var scanner = new Scanner(System.in)) {

            var sistemaActivo = true;

            do {
                mostrarMenu();

                try{
                    System.out.print("Seleccione una opción: ");
                    var opcion = scanner.nextInt();
                    scanner.nextLine(); // Consume el salto de línea pendiente.

                    /*
                     * Switch tradicional, compatible con Java 8.
                     * Cada case necesita break para impedir el fall-through. Si se
                     * olvida, Java continúa ejecutando el siguiente case. La Switch
                     * Expression moderna con -> no tiene ese riesgo por defecto y,
                     * además, puede producir directamente un valor.
                     */
                    switch (opcion) {
                        case 1:
                            registrarEmpleado(
                                    scanner,
                                    empleados,
                                    empleadosId);
                            break;
                        
                        case 2:
                            eliminarEmpleado(scanner, empleados, empleadosId);
                            break;
                            
                        case 3:
                            listarEmpleados(empleados);
                            break;
                            
                        case 4:
                            buscarEmpleado(scanner, empleadosId);
                            break;
                            
                        case 5:
                            mostrarConfiguracion(tecnologias, sedes);
                            break;
                        
                        case 6:
                            mostrarOrdenEmpleados(empleados);
                            break;
                            
                        case 7:
                            filtrarDesempeno(scanner, empleados, empleadosId);
                            break;
                            
                        case 8:
                            mostrarReporteFinal(empleados);
                            break;

                        case 0:
                            sistemaActivo = false;
                            System.out.println("Sesión finalizada.");
                            break;

                        default:
                            System.out.println("Opción fuera del menú.");
                            break;
                    }
                } catch (InputMismatchException excepcion) {
                    System.out.println(
                            "Entrada inválida. Debe escribir un valor numérico "
                                    + "del tipo solicitado.");

                    // Descarta la entrada que provocó la excepción. Sin esta línea,
                    // Scanner intentaría leer el mismo dato inválido nuevamente.
                    scanner.nextLine();

                    /*
                     * Java 8 ya entrega el tipo de excepción y el stack trace. Las
                     * versiones modernas mejoraron especialmente algunos diagnósticos,
                     * como Helpful NullPointerExceptions desde Java 14, indicando qué
                     * referencia era null en una expresión. Esto no significa que el
                     * mensaje de toda InputMismatchException sea siempre más detallado;
                     * por eso la aplicación muestra un mensaje comprensible al usuario.
                     */
                }
            } while (sistemaActivo);
        }
        
        
    } 
    
    private static double promediarSalarioEmpleados(List<Empleado> empleados){
        if(empleados.isEmpty()){
            return 0.0;
        }
        var suma = 0.0;
        for(Empleado empleado: empleados)
        {
            suma += empleado.getSalario();
        }
        return suma / empleados.size();
    }
    
    private static void mostrarReporteFinal(List<Empleado> empleados){
        if (empleados.isEmpty()){
            System.out.println("No hay empleados para generar reporte");
            return;
        }
        var totalEmpleados = empleados.size();
        var promedioSalario = promediarSalarioEmpleados(empleados);
        
        System.out.println(""
                + "_____________________"
                + ""
                + "    REPORTE FINAL    "
                + "_____________________"
            + "\nTotal Empleados: "+ totalEmpleados);
        System.out.printf("Promedio Salarial: %.2f%n", promedioSalario);
    }
    
    private static void filtrarDesempeno(
            Scanner scanner,
            List <Empleado> empleados,
            Map <String, Empleado> empleadosId){
        
        System.out.print("Establecer puntaje minimo: ");
        var puntajeMinimo = scanner.nextDouble();
        scanner.nextLine();
        
        if (puntajeMinimo < NOTA_MINIMA || puntajeMinimo > NOTA_MAXIMA){
            System.out.println("El puntaje excede el rango permitido");
            return;
        }
        
        var empleadosAntes= empleados.size();
        empleados.removeIf(empleado -> empleado.getPromedioDesempeno() < puntajeMinimo);
        
        var empleadosAhora = empleados.size();
        var empleadosEliminados = empleadosAntes - empleadosAhora;
        
        empleadosId.clear();
        for (var empleado : empleados){
            var clave = String.valueOf(empleado.getIdEmpleado());
            empleadosId.put(clave, empleado);
        }
        System.out.println("Empleados eliminados: "+empleadosEliminados);
    }
    
    /*
    * Java 21 incorpora Sequenced Collections.
    * Los métodos getFirst() y getLast() permiten
    * acceder al primer y último elemento de forma
    * más legible que get(0) y get(size()-1).
    *
    * Además, reversed() permite obtener una vista
    * invertida de la colección sin recorrerla
    * manualmente ni calcular índices.
    *
    * Esto reduce errores de IndexOutOfBoundsException
    * y mejora la claridad del código.
    */
    
    public static void mostrarOrdenEmpleados(List<Empleado> empleados){
        if(empleados.isEmpty()){
            System.out.println("No hay empleados registrados.");
            return;
        }
        
        var primero = empleados.getFirst();
        var ultimo = empleados.getLast();
        System.out.println("Primer Empleado: "+primero.getNombre()+
                         "\nUltimo Empleado: "+ultimo.getNombre());
        
        System.out.println("\nOrden Normal");
        for(var empleado : empleados){
            System.out.println(empleado.getNombre());
        }
        
        System.out.println("\nOrden Inverso");
        for(var empleado : empleados.reversed()){
            System.out.println(empleado.getNombre());
        }
    }
        
        public static void listarEmpleados(List<Empleado> empleados)
        {
            if(empleados.isEmpty())
            {
                System.out.println("No hay empleados registrados");
                return;
            }
           
            for(var empleado: empleados){
                System.out.printf("ID: " + empleado.getIdEmpleado()+
                                   " | Nombre: "+empleado.getNombre()+
                                   " | Salario: "+empleado.getSalario()+
                                   " | Promedio Desempeño: %.2f%n", empleado.getPromedioDesempeno());
            }

        }  

        public static void buscarEmpleado(Scanner scanner,
                                          Map<String, Empleado>empleadosId){
            System.out.print("Id del empleado: ");
            var id = scanner.nextInt();
            scanner.nextLine();

            var clave = String.valueOf(id);
            var empleado = empleadosId.get(clave);

            if(empleado == null){
                System.out.println("Empleado no encontrado");
                return;
            }
            System.out.println("ID: " + empleado.getIdEmpleado()+
                                   " | Nombre: "+empleado.getNombre()+
                                   " | Salario: "+empleado.getSalario());

        }
    
        public static void eliminarEmpleado(
                Scanner scanner, 
                List<Empleado>empleados, 
                Map<String,Empleado> empleadoId)
        {
            System.out.print("Id empleado para eliminar: ");
            var id = scanner.nextInt();
            scanner.nextLine();
            
            var clave = String.valueOf(id);
            var empleado = empleadoId.get(clave);
            
            if(empleado == null){
                System.out.println("Empleado no encontrado");
                return;
            }
            
            empleados.remove(empleado);
            empleadoId.remove(clave);
        }
    

    
    

        // Sem 2
        private static void mostrarMenu() {
            System.out.println("""
                    _____________________________________

                            CORPORATE TALENT HUB
                    _____________________________________
                    1. Registrar empleado
                    2. Eliminar Empleado
                    3. Listar Empleados
                    4. Buscar Empleado
                    5. Consultar tegnologias y sedes
                    6. Consultar Orden Empleados
                    7. Filtrar empleados con bajo puntaje
                    8. Mostrar reporte final
                    0. Salir
                    """);
        }
    
        private static void mostrarConfiguracion(
        List<String> tegnologias,
        Map<String, String> sedes){
            System.out.println("---TEGNOLOGIAS---");
            for(var tecnologia: tecnologias){
                System.out.println("- "+ tecnologia);
            }
            System.out.println("---SEDES---");
            for(var sede : sedes.entrySet()){
                String key = sede.getKey();
                String value = sede.getValue();
                System.out.println(key+" - "+value);
            }
        }

    private static boolean registrarEmpleado(
            Scanner scanner,
            List<Empleado> empleados,
            Map<String, Empleado> empleadosId) {

        System.out.print("ID positivo: ");
        var idEmpleado = scanner.nextInt();
        scanner.nextLine();

        if (idEmpleado <= 0) {
            System.out.println("El ID debe ser mayor que cero.");
            return false;
        } else if (idRepetido(idEmpleado)) {
            System.out.println("Ya existe un empleado con ese ID.");
            return false;
        }

        System.out.print("Nombre: ");
        var nombre = scanner.nextLine().trim();

        if (nombre.isBlank()) {
            System.out.println("El nombre no puede estar vacío.");
            return false;
        }

        System.out.print("Edad : ");
        var edadIngresada = scanner.nextInt();

        if (edadIngresada < 18 || edadIngresada > 100) {
            System.out.println("La edad está fuera del rango permitido.");
            scanner.nextLine();
            return false;
        }

        // Scanner entrega un int; después de validar el rango se convierte a byte.
        var edad = (byte) edadIngresada;

        System.out.print("Salario: ");
        var salario = scanner.nextDouble();

        if (salario <= 0) {
            System.out.println("El salario debe ser mayor que cero.");
            scanner.nextLine();
            return false;
        }
        
        var empleado = new Empleado(idEmpleado, nombre, edad, salario);
        
        for(var trimestre = 0; trimestre < CANTIDAD_TRIMESTRES; trimestre++){
            System.out.printf("Calificacion del trimestre %d : ", trimestre+1);
            var calificacion = scanner.nextDouble();
            
            if (calificacion < NOTA_MINIMA || calificacion > NOTA_MAXIMA){
                System.out.println("La calificacion ingresada excede el rango permitido");
                scanner.nextLine();
                return false;
            }
            empleado.agregarCalificacion(calificacion);
            
        }
        
        empleado.setPromedioDesempeno(empleado.calcularPromedioDesempeno());
        
        empleados.add(empleado);
        empleadosId.put(String.valueOf(idEmpleado), empleado);
        
        System.out.println("\nEmpleado registrado correctamente.");
        return true;
    }

    private static boolean idRepetido(int idBuscado){
        var clave = String.valueOf(idBuscado);
        
        if(empleadosId.containsKey(clave)){
            System.out.println("Ya existe un empleado con el id "+ clave);
            return true;
        }
        return false;
    }
    
   
 
//    private static void mostrarReporte(List<Empleado> empleados) {
//
//        if (empleados.size() == 0) {
//            System.out.println("Todavía no hay empleados registrados.");
//            return;
//        }
//
//        System.out.println("\n   REPORTE DE DESEMPEÑO   ");
//
//        for (var empleado : empleados){
//            var promedio = empleado.calcularPromedioDesempeno();
//
//            /*
//             * Casting explícito de double a int. Se elimina la parte decimal, no
//             * se redondea: 89.99 se convierte en 89. Esto implica pérdida de precisión.
//             */
//            var puntajeSimplificado = (int) promedio;
//
//            // Operador ternario: condición ? resultadoSiTrue : resultadoSiFalse.
//            var estadoPromocion = promedio >= PROMEDIO_PARA_PROMOCION
//                    ? "PROMOVIDO"
//                    : "NO PROMOVIDO";
//
//            var categoria = obtenerCategoriaSalarial(
//                    empleados[fila].getSalario());
//
//            System.out.printf(
//                    "ID: %d | Nombre: %s | Promedio: %.2f | "
//                            + "Simplificado: %d | Estado: %s | Categoría: %s%n",
//                    empleados.get(),
//                    empleados[fila].getNombre(),
//                    promedio,
//                    puntajeSimplificado,
//                    estadoPromocion,
//                    categoria);
//        }
//    }


    public static String obtenerCategoriaSalarial(double salario) {
        var rango = determinarRangoSalarial(salario);

        /*
         * Switch Expression moderna. La flecha evita el fall-through y el switch
         * devuelve un valor, por lo que no se necesita asignar y usar break en cada case.
         */
        return switch (rango) {
            case 1 -> "JUNIOR";
            case 2 -> "SEMISENIOR";
            case 3 -> "SENIOR";
            case 4 -> "LÍDER";
            default -> throw new IllegalArgumentException(
                    "Rango salarial no reconocido: " + rango);
        };
    }



    private static int determinarRangoSalarial(double salario) {
        if (salario < 2_000_000.0) {
            return 1;
        } else if (salario < 4_000_000.0) {
            return 2;
        } else if (salario < 7_000_000.0) {
            return 3;
        } else {
            return 4;
        }
    }
     

    private static void mostrarCategoriasSalariales() {
        System.out.println("""
                Categorías:
                - Menos de $2.000.000: JUNIOR
                - Desde $2.000.000 y menos de $4.000.000: SEMISENIOR
                - Desde $4.000.000 y menos de $7.000.000: SENIOR
                - Desde $7.000.000: LÍDER
                """);
    }
    

    
    // Sem 1

     public static Empleado crearEmpleado()
        {
            return new Empleado(
                    3, 
                    "Andres Barrios", 
                    (byte)4, 
                    570_000);
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
