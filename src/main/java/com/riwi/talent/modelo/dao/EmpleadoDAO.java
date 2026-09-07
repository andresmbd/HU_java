
package com.riwi.talent.modelo.dao;

import com.riwi.talent.modelo.record.DesempenoReport;
import com.riwi.talent.modelo.Empleado;
import java.util.List;


public interface EmpleadoDAO {
    boolean guardarEmpleado(Empleado empleado);
    List<Empleado> listarTodos();
    boolean actulizarEmpleado(Empleado empleado);
    boolean eliminarEmpleado(int idEmpleado);
    List<DesempenoReport> generarReporte();
}
