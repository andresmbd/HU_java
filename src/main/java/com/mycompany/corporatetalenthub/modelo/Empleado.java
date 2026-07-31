
package com.mycompany.corporatetalenthub.modelo;

/***
 * Modelo tradicional Java 8
 * 
 * Esta clase es más verbosa que un Record porque declara campos, constructor,
 * getters, setter y métodos explícitamente. Esa verbosidad es útil cuando el
 * objeto necesita estado mutable, como bonoMensual o nombre.
 */
public class Empleado {
    //
    private byte nivelAcceso;
    private short anioIngreso;
    private int idEmpleado;
    private long numeroDocumento;
    private float puntajeTest;
    private double salario;
    private char tipoContrato;
    private boolean esActivo;
    
    String nombre;
    private double bonusMensual;
    private int edad;
    private int idSede;
    
    
    public Empleado(
        byte nivelAcceso,
        short anioIngreso,
        int idEmpleado,
        long numeroDocumento,
        float puntajeTest,
        double salario,
        char tipoContrato,
        boolean esActivo,
        
        String nombre,
        double bonusMensual,
        int edad,
        int idSede
    )
    {
        this.nivelAcceso = nivelAcceso;
        this.anioIngreso = anioIngreso;
        this.idEmpleado = idEmpleado;
        this.numeroDocumento=numeroDocumento;
        this.puntajeTest=puntajeTest;
        this.salario=salario;
        this.tipoContrato=tipoContrato;
        this.esActivo=esActivo;
        
        this.nombre =nombre;
        this.bonusMensual=bonusMensual;
        this.edad = edad;
        this.idSede = idSede;
    }
    
    
    public String getNombre(String nombre) {
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public double getBonusMensual(double bonusMensual) {
        return bonusMensual;
    }
    
    public void setBonusMensual(double bonusMensual){
        this.bonusMensual=bonusMensual;
    }
    
    public int getEdad(int edad) {
        return edad;
    }
    
    public void setEdad(int edad){
        this.edad=edad;
    }
    
    public int getIdSede(int idSede) {
        return idSede;
    }
    
    public void setIdSede(int idSede){
        this.idSede=idSede;
    }
    
    
    public byte getNivelAcceso(byte nivelEmpleado) {
        return nivelEmpleado;
    }
    
    public void setNivelEmpleado(byte nivelAcceso){
        this.nivelAcceso = nivelAcceso;
    }
    
    public short getAnioIngreso(short anioIngreso) {
        return anioIngreso;
    }
    
    public void setAnioIngreso(byte nivelAcceso){
        this.nivelAcceso = nivelAcceso;
    }
    
     public int getIdEmpleado(int idEmpleado) {
        return idEmpleado;
    }
    
    public void setIdEmpleado(byte idEmpleado){
        this.idEmpleado = idEmpleado;
    }
    
    public long getNumeroDocumento(long numeroDocumento) {
        return numeroDocumento;
    }
    
    public void setNumeroDocumento(long numeroDocumento){
        this.numeroDocumento = numeroDocumento;
    }
    
     public float getPuntajeTest(float puntajeTest) {
        return puntajeTest;
    }
     
     public void setNumeroDocumento(float puntajeTest){
        this.puntajeTest = puntajeTest;
    }
    
    public double getSalario(double salario) {
        return salario;
    }
    
    public void setSalario(double salario){
        this.salario = salario;
    }
    
    public char getTipoContrato(char tipoContrato) {
        return tipoContrato;
    }
    
    public void setTipoContrato(char tipoContrato){
        this.tipoContrato = tipoContrato;
    }
    
    public boolean getTipoContrato(boolean esActivo) {
        return esActivo;
    }
    
    public void setTipoContrato(boolean esActivo){
        this.esActivo = esActivo;
    }
    
    public double calcularSalarioFinal()
    {
        /***
         * Orden de ejecucion: Parentesis -> Multiplicacion -> suma -> resta
         * 1. bonusMensual * 1.10
         * 2. salario + resultado
         * 3. salario * 0.05
         * 3. resultado suma - resultado multiplicacion
         */
        
        return (salario + (bonusMensual * 1.10))-(salario * 0.05);
    }
    
    public void bonoExtra(double extra){
        if(idEmpleado % 2 == 0)
        {
            bonusMensual += extra;
        }
    }
    
    public boolean validarElegibilidad(){
        /***
         * Precedencia logica: primero se ejecuta !esActivo
         * Despues && evaluandose de izquierda a derecha, o sea primero (puntajeTest > 85 && edad < 30) luego (idSede == 1 && [resultado de !])
         * Y por ultimo || conecta los dos bloques validando si solo uno de ellos es true
         */
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }
   
}
