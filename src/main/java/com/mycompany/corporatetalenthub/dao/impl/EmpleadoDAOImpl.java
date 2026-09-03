
package com.mycompany.corporatetalenthub.dao.impl;

import com.mycompany.corporatetalenthub.conexionbd.ConexionBD;
import com.mycompany.corporatetalenthub.dao.EmpleadoDAO;
import com.mycompany.corporatetalenthub.modelo.moderno.Desarrollador;
import com.mycompany.corporatetalenthub.modelo.moderno.Empleado;
import com.mycompany.corporatetalenthub.modelo.moderno.Gerente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EmpleadoDAOImpl implements EmpleadoDAO{

    @Override
    public boolean guardarEmpleado(Empleado empleado) {
        String query = "INSERT INTO empleados(id_empleado, nombre, edad, salario, tipo, lenguaje_principal, presupuesto_mensual) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conexion = ConexionBD.conectarBD();
            PreparedStatement ps = conexion.prepareStatement(query)){
            ps.setInt(1, empleado.getIdEmpleado());
            ps.setString(2, empleado.getNombre());
            ps.setInt(3, empleado.getEdad());
            ps.setDouble(4, empleado.getSalario());
            
            if (empleado instanceof Desarrollador dev){
                ps.setString(5, "DESARROLLADOR");
                ps.setString(6, dev.getLenguajePrincipal());
                ps.setNull(7, java.sql.Types.DOUBLE);
            }else if (empleado instanceof Gerente ger){
                ps.setString(5, "GERENTE");
                ps.setNull(6, java.sql.Types.VARCHAR);
                ps.setDouble(7, ger.getPresupuestoMensual());
            }
            
            return ps.executeUpdate() > 0; // retorna un boolean
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
                       UPDATE empleados SET
                       """;
        
    }

    @Override
    public boolean eliminarEmpleado(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
