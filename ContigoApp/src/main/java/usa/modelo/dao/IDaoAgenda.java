/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import usa.modelo.dto.Agenda;

/**
 *
 * @author Valeria
 */
public interface IDaoAgenda extends IDao<Agenda> {

    int crearAgenda(Agenda ag);

}
