package usa.modelo.dao;

import java.util.LinkedList;

/**
 *  Interfaz de objetos de acceso a datos
 * @author Santiago Pérez
 * @param <T>
 */
public interface IDao<T> {
    boolean crear(T t);
    T consultar(String id);
    boolean actualizar(T t);
    boolean eliminar(String id);
    LinkedList <T> listarTodos();
}
