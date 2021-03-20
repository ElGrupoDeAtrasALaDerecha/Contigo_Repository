package usa.modelo.dto;

/**
 *
 */
public class Conversatorio {

    /**
     * Default constructor
     */
    public Conversatorio() {
    }

    private int id;
    private String titulo;
    private String cronograma;
    private String orador;
    private String infografia;
    private String lugar;
    private String imagen;
    private String descripcion;
    
    public String getInfografia() {
        return infografia;
    }

    public void setInfografia(String infografia) {
        this.infografia = infografia;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCronograma() {
        return cronograma;
    }

    public void setCronograma(String cronograma) {
        this.cronograma = cronograma;
    }

    public String getOrador() {
        return orador;
    }

    public void setOrador(String orador) {
        this.orador = orador;
    }

}
