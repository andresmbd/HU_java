
package com.riwi.talent.controller;

import com.riwi.talent.modelo.record.DesempenoReport;
import com.riwi.talent.modelo.Empleado;
import com.riwi.talent.modelo.dao.EmpleadoDAO;
import com.riwi.talent.modelo.dao.EmpleadoDAOImpl;
import java.util.List;


public class EmpleadoController {
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    public boolean registrarEmpleado(Empleado empleado){
        return empleadoDAO.guardarEmpleado(empleado);
    }
    
    public List<Empleado> listartodos(){
        return empleadoDAO.listarTodos();
    } 
    
    public boolean actulizarEmpleado(Empleado empleado){
        return empleadoDAO.actulizarEmpleado(empleado);
    }
    
    public boolean eliminarEmpleado(int idEmpleado){
        return empleadoDAO.eliminarEmpleado(idEmpleado);
    }
    
    public List<DesempenoReport> generarReporte(){
        return empleadoDAO.generarReporte();
    }
    
}

