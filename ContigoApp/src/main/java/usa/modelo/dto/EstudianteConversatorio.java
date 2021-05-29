package usa.modelo.dto;

import usa.observer.Subject;

/**
 *
 * @author migue
 */
public class EstudianteConversatorio extends Subject{

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
