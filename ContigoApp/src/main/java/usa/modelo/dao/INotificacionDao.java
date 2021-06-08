package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Notificacion;

/**
 * Interfaz de objeto de acceso a datos de las notificaciones
 * @author Santiago Pérez
 * @version 1.0
 * @since 2021-06-07
 */
public interface INotificacionDao extends IDao<Notificacion> {
    /**
     * Método que permite consultar las notificaciones de una persona específica
     * @param documento que es el documento de una persona
     * @return una lista con las notificaciones de una persona
     */
    public LinkedList<Notificacion> consultarNotificacionDe(String documento);
}
