/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Estudiante;
/**/
/**
 *
 * @author marif
 */
public interface IDaoEstudiante extends IDao<Estudiante>{
    public LinkedList<Estudiante> listarGradosEstudiante(String id);
    public Estudiante consultarPorTokenGrado(String id);
    public Estudiante consultarPorToken(String token);
}
