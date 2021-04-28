package usa.modelo.dto;

/**
 *
 */
public class Estudiante extends Persona {

    /**
     * Default constructor
     */
    public Estudiante() {
    }

    private String correo;
    //a
    /**
     *
     */
    private String grado;



    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
