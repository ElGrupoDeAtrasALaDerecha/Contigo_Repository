package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Historia;

/**
 * Interfaz de objeto de acceso a datos
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 * @version 1.1
 * @since 2021-06-03
 */
public interface IHistoriasDao extends IDao<Historia>{
    int crearClasi(String con, int resultado);
    int crearhistoria(Historia his);
    LinkedList<Historia> consultarHistoriasDeEstudiante(String documento);
    boolean tieneHistorias(String documento);
}
