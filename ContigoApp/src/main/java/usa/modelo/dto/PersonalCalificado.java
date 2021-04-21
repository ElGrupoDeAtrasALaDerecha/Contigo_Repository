package usa.modelo.dto;



/**
 * 
 */
public class PersonalCalificado extends Persona {

    
    private String correo;
    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    /**
     * Default constructor
     */
    public PersonalCalificado() {
    }

    public String getCorreo() {
        return correo;
    }
    /**
     * 
     * @param correo 
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

}