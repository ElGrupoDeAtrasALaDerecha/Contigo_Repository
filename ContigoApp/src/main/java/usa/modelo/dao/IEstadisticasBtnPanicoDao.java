package usa.modelo.dao;

import java.util.LinkedList;
import org.json.JSONArray;
import usa.modelo.dto.EstadisticasBtnPanico;

/**
 * Interfaz de objeto de acceso a datos de los clicks del botón de pánico
 * @author Andrés López, Santiago Pérez
 * @version 1.1
 * @since 2021-06-03
 */
public interface IEstadisticasBtnPanicoDao extends IDao<EstadisticasBtnPanico> {
    LinkedList<JSONArray> clicksPorestudiante(String documento);
}
