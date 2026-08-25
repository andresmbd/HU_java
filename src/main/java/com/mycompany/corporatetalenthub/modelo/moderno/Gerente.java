
package com.mycompany.corporatetalenthub.modelo.moderno;
import com.mycompany.corporatetalenthub.interfaces.Promocionable;


public final class Gerente extends Empleado implements Promocionable{
    private double presupuestoMensual;

    public Gerente(int idEmpleado, String nombre, int edad, double salario, double presupuestoMensual) {
        super(idEmpleado, nombre, edad, salario);
        this.presupuestoMensual = presupuestoMensual;
    }

    @Override
    public double calcularBonoAscenso() {
        return getSalario() * 0.20;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }
}
