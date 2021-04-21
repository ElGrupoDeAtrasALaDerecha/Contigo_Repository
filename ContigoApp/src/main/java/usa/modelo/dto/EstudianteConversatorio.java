/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dto;

/**
 *
 * @author migue
 */
public class EstudianteConversatorio {

    public int getIdConversatorio() {
        return idConversatorio;
    }

    public void setIdConversatorio(int idConversatorio) {
        this.idConversatorio = idConversatorio;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    private int idConversatorio;
    private String idEstudiante ;
}
