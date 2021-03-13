package usa.modelo.dto;


/**
 * 
 */
public class Grado {

    /**
     * Default constructor
     */
    public Grado() {
    }

    /**
     * 
     */
    private String codigo;

    /**
     * 
     */
    private Institucion institucion;

    /**
     * 
     */
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}