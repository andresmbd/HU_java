
package com.riwi.talent.modelo.dao;

import com.riwi.talent.conexionbd.ConexionBD;
import com.riwi.talent.modelo.dao.EmpleadoDAO;
import com.riwi.talent.modelo.Desarrollador;
import com.riwi.talent.modelo.record.DesempenoReport;
import com.riwi.talent.modelo.Empleado;
import com.riwi.talent.modelo.Gerente;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class EmpleadoDAOImpl implements EmpleadoDAO{

    @Override
    public boolean guardarEmpleado(Empleado empleado) {
        String query = """
                       INSERT INTO empleados(id_empleado, 
                                            nombre, 
                                            edad, 
                                            salario, 
                                            calificaciones,
                                            promedio_desempeno,
                                            feedback,
                                            tipo, 
                                            lenguaje_principal, 
                                            presupuesto_mensual
                                            ) 
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                       """;
        try(Connection conexion = ConexionBD.conectarBD();
            PreparedStatement ps = conexion.prepareStatement(query)){
            ps.setInt(1, empleado.getIdEmpleado());
            ps.setString(2, empleado.getNombre());
            ps.setInt(3, empleado.getEdad());
            ps.setDouble(4, empleado.getSalario());
            
            Double[] arrCalificaciones = empleado.getCalificaciones().toArray(new Double[0]); // es el tipo de array 
            Array arraysql = conexion.createArrayOf("numeric", arrCalificaciones);
            
            ps.setArray(5, arraysql);
            ps.setDouble(6, empleado.getPromedioDesempeno());
            ps.setString(7, empleado.getFeedback());
            
            if (empleado instanceof Desarrollador dev){
                ps.setString(8, "DESARROLLADOR");
                ps.setString(9, dev.getLenguajePrincipal());
                ps.setNull(10, Types.DOUBLE);
            }else if (empleado instanceof Gerente ger){
                ps.setString(8, "GERENTE");
                ps.setNull(9, Types.VARCHAR);
                ps.setDouble(10, ger.getPresupuestoMensual());
            }
            
            return ps.executeUpdate() > 0; // 1 si se insertó 1 fila (true)
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> lista = new ArrayList<>();
        String query = "SELECT * FROM empleados";
        try(Connection con = ConexionBD.conectarBD();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            
            Empleado empleado = null;
            
            while(rs.next()){
                int idEmpleado = rs.getInt("id_empleado");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                double salario = rs.getDouble("salario");
                
                String tipo = rs.getString("tipo");
                if(tipo.equalsIgnoreCase("DESARROLLADOR")){
                   String lenguajePrincipal = rs.getString("lenguaje_principal");
                   empleado = new Desarrollador(idEmpleado, nombre, edad, salario, lenguajePrincipal);
                }else if (tipo.equalsIgnoreCase("GERENTE")){
                    Double presupuestoMensual = rs.getDouble("presupuesto_mensual");
                    empleado = new Gerente(idEmpleado, nombre, edad, salario, presupuestoMensual);
                }
                
                if (empleado == null) continue; // tipo desconocido en BD, se salta el registro
                
                Array sqlArr = rs.getArray("calificaciones"); // Devuelve un objeto de tipo java.sql.Array.
                if (sqlArr != null){
                    Double[] array = (Double []) sqlArr.getArray(); // Devuelve un java.lang.Object que se debe castear al tipo de arreglo nativo
                    empleado.setCalificaciones(Arrays.asList(array)); // hace envolver el array y comporte como una List.
                }
                
                empleado.setPromedioDesempeno(rs.getDouble("promedio_desempeno")); 
                empleado.setFeedback(rs.getString("feedback"));
                
                lista.add(empleado);
            }
            
        } catch (SQLException ex) {
            return null;
            
        }
        return lista;
    }

    @Override
    public boolean actulizarEmpleado(Empleado empleado) {
        String query = """
                       UPDATE empleados 
                       SET nombre = ?, salario = ?
                       WHERE id_empleado = ? 
                       """;
        try(Connection con = ConexionBD.conectarBD();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, empleado.getNombre());
            ps.setDouble(2, empleado.getSalario());
            ps.setInt(3, empleado.getIdEmpleado());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            System.out.println("Error: " +ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarEmpleado(int idEmpleado) {
        String query = "DELETE FROM empleados WHERE id_empleado =?";
        try(Connection con = ConexionBD.conectarBD();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /*
    * Records + JDBC Moderno vs. POJO Tradicional (Java 8)
    * 
    * 1. Reducción de Boilerplate: En Java 8, una clase POJO requiere la creación manual de atributos,
    *    constructores, getters, setters, equals(), hashCode() y toString(). Con Java 17+, un Record
    *    reduce toda esta estructura a una sola línea de definición.
    * 
    * 2. Inmutabilidad por Diseño: Los Records son inmutables por defecto (sus campos son final y no
    *    poseen setters). Esto evita que los datos obtenidos desde la BD sufran modificaciones
    *    accidentales en otras capas de la arquitectura (MVC), aumentando la robustez del código.
    * 
    * 3. Integración con JDBC Moderno: Al combinar Records con try-with-resources, la extracción del
    *    ResultSet es limpia y declarativa. Se elimina el código verboso de cierre de conexiones
    *    en bloques finally y la manipulación de objetos mutables, mejorando la legibilidad y 
    *    facilitando el mantenimiento a largo plazo.
    */

    @Override
    public List<DesempenoReport> generarReporte() {
        List<DesempenoReport> reportes = new ArrayList<>();
        String query = "SELECT id_empleado, promedio_desempeno, feedback FROM empleados";
        try(Connection con = ConexionBD.conectarBD();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                DesempenoReport reporte = new DesempenoReport(rs.getInt("id_empleado"), 
                                                              rs.getDouble("promedio_desempeno"), 
                                                              rs.getString("feedback"));
                reportes.add(reporte);
            }
            return reportes; 
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
