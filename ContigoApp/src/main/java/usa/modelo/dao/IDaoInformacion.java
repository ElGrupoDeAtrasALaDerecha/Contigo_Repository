package usa.modelo.dao;

import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.decorador.IInformacion;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public interface IDaoInformacion extends IDao<IInformacion>{
    IInformacion consultarPorPersonal(PersonalCalificado p);
}
