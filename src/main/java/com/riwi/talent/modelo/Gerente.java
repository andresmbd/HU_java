
package com.riwi.talent.modelo;


public final class Gerente extends Empleado{
    private double presupuestoMensual;

    public Gerente(int idEmpleado, String nombre, int edad, double salario, double presupuestoMensual) {
        super(idEmpleado, nombre, edad, salario);
        if(presupuestoMensual <= 0)
            throw new IllegalArgumentException("El presupuesto no debe ser 0 ni menor");
        this.presupuestoMensual = presupuestoMensual;
        setRol("GERENTE");
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    public void setPresupuestoMensual(double presupuestoMensual) {
        this.presupuestoMensual = presupuestoMensual;
    }
    
}
