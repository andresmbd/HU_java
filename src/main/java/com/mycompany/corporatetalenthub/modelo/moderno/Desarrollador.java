
package com.mycompany.corporatetalenthub.modelo.moderno;
import com.mycompany.corporatetalenthub.interfaces.Promocionable;

public final class Desarrollador extends Empleado implements Promocionable{
    private String lenguajePrincipal;

    public Desarrollador(int idEmpleado, String nombre, int edad, double salario, String lenguajePrincipal) {
        super(idEmpleado, nombre, edad, salario);
        this.lenguajePrincipal = lenguajePrincipal;
    }
    
    public String getLenguajePrincipal(){
        return lenguajePrincipal;
    }

    public void setLenguajePrincipal(String lenguajePrincipal) {
        this.lenguajePrincipal = lenguajePrincipal;
    }

    @Override
    public double calcularBonoAscenso() {
        return getSalario() * 0.15;
    }
}
