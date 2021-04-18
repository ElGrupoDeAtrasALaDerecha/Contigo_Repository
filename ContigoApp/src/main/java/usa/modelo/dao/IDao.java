package usa.modelo.dao;

import java.sql.Connection;
import java.util.LinkedList;
import usa.bd.IConexionBD;
import usa.factory.AbstractFactory;
import usa.factory.Producer;

/**
 *  Interfaz de objetos de acceso a datos
 * @author Santiago Pérez
 * @param <T>
 */
public interface IDao<T> {

    /**
     *
     */
    static AbstractFactory fabrica = Producer.getFabrica("BD");
    static IConexionBD conexion=(IConexionBD) fabrica.obtener("mysql");
    static Connection conn = conexion.tomarConexion();
    boolean crear(T t);
    T consultar(String id);
    boolean actualizar(T t);
    boolean eliminar(String id);
    LinkedList <T> listarTodos();
}
/**/
