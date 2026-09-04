
package com.mycompany.corporatetalenthub.dao.impl;

import com.mycompany.corporatetalenthub.conexionbd.ConexionBD;
import com.mycompany.corporatetalenthub.dao.EmpleadoDAO;
import com.mycompany.corporatetalenthub.modelo.moderno.Desarrollador;
import com.mycompany.corporatetalenthub.modelo.moderno.Empleado;
import com.mycompany.corporatetalenthub.modelo.moderno.Gerente;
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
                                            tipo, 
                                            lenguaje_principal, 
                                            presupuesto_mensual, 
                                            calificaciones) 
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                       """;
        try(Connection conexion = ConexionBD.conectarBD();
            PreparedStatement ps = conexion.prepareStatement(query)){
            ps.setInt(1, empleado.getIdEmpleado());
            ps.setString(2, empleado.getNombre());
            ps.setInt(3, empleado.getEdad());
            ps.setDouble(4, empleado.getSalario());
            
            Double[] arrCalificaciones = empleado.getCalificaciones().toArray(new Double[0]); // es el tipo de array 
            Array arraysql = conexion.createArrayOf("numeric", arrCalificaciones);
            
            ps.setArray(8, arraysql);
            
            if (empleado instanceof Desarrollador dev){
                ps.setString(5, "DESARROLLADOR");
                ps.setString(6, dev.getLenguajePrincipal());
                ps.setNull(7, Types.DOUBLE);
            }else if (empleado instanceof Gerente ger){
                ps.setString(5, "GERENTE");
                ps.setNull(6, Types.VARCHAR);
                ps.setDouble(7, ger.getPresupuestoMensual());
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
            
            Empleado empleado;
            
            while(rs.next()){
                int idEmpleado = rs.getInt("id_empleado");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                double salario = rs.getDouble("salario");
                String tipo = rs.getString("tipo");
                
                Array sqlArr = rs.getArray("calificaciones"); // Devuelve un objeto de tipo java.sql.Array.
                if (sqlArr != null){
                    Double[] array = (Double []) sqlArr.getArray(); // Devuelve un java.lang.Object que se debe castear al tipo de arreglo nativo
                    List<Double> calificaciones = Arrays.asList(array); // hace envolver el array y comporte como una List.
                    
                }
                if(tipo.equalsIgnoreCase("DESARROLLADOR")){
                   String lenguajePrincipal = rs.getString("lenguaje_principal");
                   empleado = new Desarrollador(idEmpleado, nombre, edad, salario, lenguajePrincipal);
                }else if (tipo.equalsIgnoreCase("GERENTE")){
                    Double presupuestoMensual = rs.getDouble("presupuesto_mensual");
                    empleado = new Gerente(idEmpleado, nombre, edad, salario, presupuestoMensual);
                }else{
                    continue;
                }
                lista.add(empleado);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error sql "+ex.getMessage());
            
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
            System.out.println("Error "+ex.getMessage());
            return false;
        }
    }
    
}
