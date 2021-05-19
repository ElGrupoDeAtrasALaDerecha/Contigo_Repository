package usa.modelo.dto;



/**
 * 
 */
public class PersonalCalificado extends Persona {

    

    private String imagen;
    
    private String biografia;
    
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

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

}