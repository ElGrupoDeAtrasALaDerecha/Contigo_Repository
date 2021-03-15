package usa.modelo.dto;
import javax.websocket.Session;
/**
 * 
 */
public class Sala extends Thread{

    /**
     *  Constructor
     */

    private int codigo;

    /**
     * 
     */
    private Estudiante estudiante;

    /**
     * 
     */
    private PersonalCalificado personaCalificada;
    
    private Session sesionEstudiante,sesionPersonal;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public PersonalCalificado getPersonaCalificada() {
        return personaCalificada;
    }

    public void setPersonaCalificada(PersonalCalificado personaCalificada) {
        this.personaCalificada = personaCalificada;
    }

    public Session getSesionEstudiante() {
        return sesionEstudiante;
    }

    public void setSesionEstudiante(Session sesionEstudiante) {
        this.sesionEstudiante = sesionEstudiante;
    }

    public Session getSesionPersonal() {
        return sesionPersonal;
    }

    public void setSesionPersonal(Session sesionPersonal) {
        this.sesionPersonal = sesionPersonal;
    }


    

}