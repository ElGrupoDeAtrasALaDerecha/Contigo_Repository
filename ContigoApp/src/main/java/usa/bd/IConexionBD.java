package usa.bd;

import java.sql.Connection;

/**
 * Interfaz de conexión con la base de datos
 * @author Valeria Bermúdez y Santiago Pérez
 */
 public abstract class IConexionBD {
    protected static IConexionBD instance;
    public abstract Connection tomarConexion();
    public abstract void desconectar();
}
