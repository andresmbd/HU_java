
package com.riwi.talent.modelo;

public final class Desarrollador extends Empleado{
    private String lenguajePrincipal;

    public Desarrollador(int idEmpleado, String nombre, int edad, double salario, String lenguajePrincipal) {
        super(idEmpleado, nombre, edad, salario);
        if (lenguajePrincipal == null || lenguajePrincipal.isBlank())
            throw new IllegalArgumentException("El campo deber llenado correctamente");
        this.lenguajePrincipal = lenguajePrincipal.trim();
        setRol("DESARROLLADOR");
    }
    
    public String getLenguajePrincipal(){
        return lenguajePrincipal;
    }

    public void setLenguajePrincipal(String lenguajePrincipal) {
        this.lenguajePrincipal = lenguajePrincipal;
    }


}
