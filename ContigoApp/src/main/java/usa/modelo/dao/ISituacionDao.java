package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Arbol;
import usa.modelo.dto.Situacion;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public interface ISituacionDao extends IDao<Situacion>{

    /**
     *
     * @param idHistoria
     * @return
     */
    public Arbol consultarPorHistoria(int idHistoria);
}
