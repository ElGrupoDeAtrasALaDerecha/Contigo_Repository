package usa.modelo.dao;

import usa.modelo.dto.Historia;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public interface IHistoriasDao extends IDao<Historia>{
    int crearClasi(String con, int resultado);
    int crearhistoria(Historia his);
}
