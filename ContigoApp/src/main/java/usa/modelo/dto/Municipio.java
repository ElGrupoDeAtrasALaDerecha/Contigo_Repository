package usa.modelo.dto;


/**
 * 
 */
public class Municipio {

    /**
     * Default constructor
     */
    public Municipio() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private int departamentoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

 



}