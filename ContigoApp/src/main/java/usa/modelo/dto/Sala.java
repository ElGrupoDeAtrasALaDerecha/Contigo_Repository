package usa.modelo.dto;

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


    

}