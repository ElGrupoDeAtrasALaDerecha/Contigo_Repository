/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.util.LinkedList;
import org.json.JSONArray;
import usa.modelo.dto.Conversatorio;
import usa.modelo.dto.EstudianteConversatorio;



/**
 *
 * @author migue
 */
public interface IDaoConversatorios extends IDao<Conversatorio> {
    int crearConver(Conversatorio conver);
    int crearClasi(String con, int resultado);
    boolean consultarEstuConver(String idEstudiante);
    boolean registrarEstuConver(EstudianteConversatorio estu);
    boolean eliminarRegistroEstu(String idConversatorio, String idEstudiante);
    EstudianteConversatorio consultarEstConversatorio(String idConversatorio, String idEstudiante);
    LinkedList <JSONArray> consultarPorGrado(String grado);
    LinkedList <JSONArray> consultarPorInstitucion(String institucion);
}
