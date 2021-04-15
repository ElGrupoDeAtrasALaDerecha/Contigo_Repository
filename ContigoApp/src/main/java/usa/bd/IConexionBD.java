package usa.bd;

import java.sql.Connection;

/**
 *
 * @author Valeria Bermúdez y Santiago Pérez
 */
 public abstract class IConexionBD {
    protected static IConexionBD instance;
    public abstract Connection tomarConexion();
    public abstract void desconectar();
    public static IConexionBD getInstance(){
        return instance;
    }
}
