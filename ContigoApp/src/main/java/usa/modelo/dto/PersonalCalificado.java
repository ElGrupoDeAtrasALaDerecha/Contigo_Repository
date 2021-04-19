package usa.modelo.dto;



/**
 * 
 */
public class PersonalCalificado extends Persona {

    
    private String correo;
    private String contraseña;
    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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