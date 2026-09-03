
package com.mycompany.corporatetalenthub.conexionbd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* SINTAXIS LEGACY (Java 8 hacia atrás) 
 * Anteriormente, para cerrar conexiones era obligatorio usar el bloque 'finally' y llamar
 * manualmente al método .close() de cada objeto (Connection, PreparedStatement, ResultSet).
 * Como .close() podía lanzar una SQLException, se debían anidar bloques try-catch dentro
 * del 'finally', lo que producía un código extenso, propenso a errores y difícil de mantener.
 * 
 * SINTAXIS MODERNA (Java 17/21) Y PREVENCIÓN DE MEMORY LEAKS 
 * Con 'try-with-resources', Java gestiona el ciclo de vida de los recursos automáticamente.
 * Cualquier objeto que implemente AutoCloseable se cierra en orden inverso a su apertura
 * al finalizar el bloque try, incluso si ocurren excepciones.
 * Esto previene las Fugas de Memoria (Memory Leaks) porque garantiza que los sockets de red
 * y los recursos asignados en el servidor de PostgreSQL/JVM sean liberados de inmediato,
 * evitando el agotamiento de memoria en la JVM o el bloqueo del pool de conexiones.
 */

public class ConexionBD {
    public static final String URL = "jdbc:postgresql://localhost:5432/corporate_db";
    public static final String USER = "coder";
    public static final String PASSWORD = "riwi";
    
    public static Connection conectarBD() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
