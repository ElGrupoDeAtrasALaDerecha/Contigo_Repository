package usa.modelo.dao;

import usa.modelo.dto.PersonalCalificado;

/**
 * Interfaz de la clase de objeto de acceso a datos del personal calificado
 *
 * @author Valeria Bermúdez y Santiago Pérez
 */
public interface IPersonalCalificadoDao extends IDao<PersonalCalificado> {

    PersonalCalificado consultarPorToken(String token);
    PersonalCalificado consultarPorCredenciales(String correo, String contraseña);
}
/**/