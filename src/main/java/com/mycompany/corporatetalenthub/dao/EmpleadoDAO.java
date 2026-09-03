
package com.mycompany.corporatetalenthub.dao;

import com.mycompany.corporatetalenthub.modelo.moderno.Empleado;
import java.util.List;


public interface EmpleadoDAO {
    boolean guardarEmpleado(Empleado empleado);
    List<Empleado> listarTodos();
    boolean actulizarEmpleado(Empleado empleado);
    boolean eliminarEmpleado(int id);
}
