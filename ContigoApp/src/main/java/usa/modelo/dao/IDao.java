package usa.modelo.dao;

/**
 *
 * @author Santiago Pérez
 */
interface IDao<T> {
    boolean crear(T t);
    T consultar(String id);
    boolean actualizar(T t);
    boolean eliminar(String id);
}
