/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import usa.modelo.dto.PersonalCalificado;

/**
 *
 * @author Santiago Pérez
 */
interface IPersonalCalificadoDao extends IDao<PersonalCalificado>{
    PersonalCalificado consultarPorToken(String token);
    PersonalCalificado consultarPorCredenciales(String correo,String contraseña);
}
