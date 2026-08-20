
package com.mycompany.corporatetalenthub.modelo;

/**
 * Las Sealed Classes ofrecen mayor seguridad en el diseño de APIs
 * porque permiten definir explícitamente qué clases pueden heredar
 * de una clase base.
 *
 * A diferencia de la herencia abierta de una clase abstracta, donde
 * cualquier clase puede extender Persona, una Sealed Class restringe
 * la jerarquía mediante permits.
 *
 * Esto permite al desarrollador de la API mantener un conjunto
 * controlado de subclases y evitar extensiones no previstas que
 * puedan modificar o romper el comportamiento esperado del diseño.
 */
public sealed class Persona permits Empleado, ConsultorExterno {
    
}


