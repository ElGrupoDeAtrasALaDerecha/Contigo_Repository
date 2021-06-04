package usa.modelo.dao;

import usa.modelo.dto.Persona;

/**
 *
 * @author Santiago Pérez
 */
public interface IPersonaDao extends IDao <Persona>{
    Persona consultarPorCorreo(String correo);
}
