
package com.riwi.talent.view;

import com.riwi.talent.controller.EmpleadoController;
import com.riwi.talent.modelo.Desarrollador;
import com.riwi.talent.modelo.record.DesempenoReport;
import com.riwi.talent.modelo.Empleado;
import com.riwi.talent.modelo.Gerente;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class EmpleadoView {
    private final EmpleadoController controller;
    private final Scanner scanner;

    public EmpleadoView() {
        this.controller = new EmpleadoController();
        this.scanner = new Scanner(System.in);
    }
    
    public void flujoInicial(){

        int opcion = -1;
        
        do {   
            mostrarMenu();
            System.out.print("Seleccione una opcion: ");
            try{
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> registrarEmpleado();
                    case 2 -> listarTodos();
                    case 3 -> generarReporte();
                    case 4 -> actulizarEmpleado();
                    case 5 -> eliminarEmpleado();
                    case 0 -> System.out.println("Saliendo del sistema... ");
                    default -> {
                        System.out.println("Opcion fuera de alcance");
                    }
                }
            } catch(InputMismatchException e){
                System.out.println("Ingrese un numero valido");
                scanner.nextLine();
            }
        } while (opcion != 0); 
    }
    
    private void mostrarMenu(){
        
        System.out.println("""
                ___________________________________________
                           
                           CORPORATE TALENT HUB
                ___________________________________________
                
                1. Registrar Empleado
                2. Listar Empleados
                3. Generar Reporte
                4. Actualizar Empleado
                5. Eliminar Empleado
                0. Salir
                           """);
    }
    
    private int validarEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0) return valor;
                System.out.println("El número debe ser mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private String validarString(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("El campo no puede estar vacío.");
        }
    }
    
    private double validarDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor > 0) return valor;
                System.out.println("El salario debe ser mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número decimal.");
            }
        }
    }
    
    private void registrarEmpleado(){
        int idEmpleado = validarEntero("Id empleado: ");
        String nombre = validarString("Nombre: ");
        int edad = validarEntero("Edad: ");

        if (edad < 18) {
            System.out.println("No se permite el registro de menores de edad (Mínimo 18 años).");
            return; // Corta el flujo de inmediato sin pedir el resto de datos
        }

        double salario = validarDouble("Salario: ");
        
        System.out.print("""
                   Selecciona el rol del Empleado:
                   1. Desarrollador
                   2. Gerente
                   Opción:  """);

        int tipo = validarEntero(" ");
        
        Empleado empleado = switch (tipo) {
            case 1 -> {
                String lenguaje = validarString("Lenguaje principal: ");
                yield new Desarrollador(idEmpleado, nombre, edad, salario, lenguaje);
            }
            case 2 -> {
                double presupuesto = validarDouble("Presupuesto mensual: ");
                yield new Gerente(idEmpleado, nombre, edad, salario, presupuesto);
            }
            default -> {
                System.out.println("Opción de rol inválida.");
                yield null;
            }
        };
        
        final int CANTIDAD_TRIMESTRES = 3;
        final double NOTA_MINIMA = 0.0;
        final double NOTA_MAXIMA = 100.0;
        
        for(int trimestre = 0; trimestre < CANTIDAD_TRIMESTRES; trimestre++){
            String txt = "\nCalificacion del trimestre %d : ".formatted(trimestre+1);
            double calificacion = validarDouble(txt);
            
            if (calificacion < NOTA_MINIMA || calificacion > NOTA_MAXIMA){
                System.out.println("La calificacion ingresada excede el rango permitido");
                return;
            }
            empleado.agregarCalificacion(calificacion);
        }
        System.out.print("\nFeedback: ");
        String feedback = scanner.nextLine().trim();
        
        if (feedback.isBlank()){
            feedback = "No hay comentarios de feedback";
        }
        empleado.setFeedback(feedback);
        empleado.setPromedioDesempeno(empleado.calcularPromedioDesempeno());

        if (empleado != null && controller.registrarEmpleado(empleado)) {
            System.out.println("Empleado creado y guardado exitosamente.");
            
        }else System.out.println("Ocurrio un problema al registrar empleado");
    }
    
    private void listarTodos(){
        List<Empleado> empleados = controller.listartodos();
        if (empleados != null && empleados.size() != 0){
            System.out.println("    LISTA DE EMPLEADOS    ");
            for(Empleado empleado: empleados){
                if(empleado instanceof Desarrollador dev)
                    System.out.println("Id empleado: "+dev.getIdEmpleado()+" | Rol: "+dev.getRol()+" | Nombre: "+dev.getNombre()+" | Edad: "+dev.getEdad()+" | Salario: "+dev.getSalario()+" | Lenguaje principal: "+dev.getLenguajePrincipal());
                else if (empleado instanceof Gerente ger)
                    System.out.println("Id empleado: "+ger.getIdEmpleado()+" | Rol: "+ger.getRol()+" | Nombre: "+ger.getNombre()+" | Edad: "+ger.getEdad()+" | Salario: "+ger.getSalario()+" | Presupuesto mensual: "+ger.getPresupuestoMensual());
            }
        }else System.out.println("\nNo hay empleados listados\n");
    }
    
    private void generarReporte(){
        List<DesempenoReport> reportes = controller.generarReporte();
        if (reportes != null){
            System.out.println("    REPORTE DE DESEMPEÑO    ");
            for (DesempenoReport r : reportes){
                String reporte = """

                                ID: %d
                                Promedio: %.2f
                                Feedback: %s

                                 """;
                System.out.printf(reporte, r.idEmpleado(), r.promedio(), r.feedback());
            }
            
        }else System.out.println("No hay reportes ingresados");
        
    }
    
    private void actulizarEmpleado(){
        int idEmpleado = validarEntero("Id del empleado a actualizar: ");

        List<Empleado> empleados = controller.listartodos();
        Empleado empleadoEncontrado = null;

        if (empleados != null){
            for (Empleado empleado : empleados){
                if (empleado.getIdEmpleado() == idEmpleado){
                    empleadoEncontrado = empleado;
                    break;
                }
            }
        }

        if (empleadoEncontrado == null){
            System.out.println("No existe un empleado con ese Id.");
            return;
        }

        String nuevoNombre = validarString("Nuevo nombre: ");
        double nuevoSalario = validarDouble("Nuevo salario: ");

        empleadoEncontrado.setNombre(nuevoNombre);
        empleadoEncontrado.setSalario(nuevoSalario);

        if (controller.actulizarEmpleado(empleadoEncontrado)) {
            System.out.println("Empleado actualizado exitosamente.");
        } else System.out.println("Ocurrio un problema al actualizar el empleado");
    }

    private void eliminarEmpleado(){
        int idEmpleado = validarEntero("Id del empleado a eliminar: ");

        System.out.print("¿Está seguro que desea eliminar el empleado con Id " + idEmpleado + "? (S/N): ");
        String confirmacion = scanner.nextLine().trim();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (controller.eliminarEmpleado(idEmpleado)) {
            System.out.println("Empleado eliminado exitosamente.");
        } else System.out.println("Ocurrio un problema al eliminar el empleado, verifique que el Id exista.");
    }

}
