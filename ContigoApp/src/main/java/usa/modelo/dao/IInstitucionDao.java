package usa.modelo.dao;

import usa.modelo.dto.Institucion;

/**
 *
 * @author Santiago Pérez
 */
public interface IInstitucionDao extends IDao<Institucion>{
    public Institucion consultarPorCorreo(String correo);
    public Institucion consultarPorId(String id);
}
