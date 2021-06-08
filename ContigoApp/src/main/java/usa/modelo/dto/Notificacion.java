package usa.modelo.dto;

/**
 * Clase modelo de notificación
 * @author Santiago Pérez
 * @version 1.0
 * @since 2021-06-07
 */
public class Notificacion {
    /**
     * Id de notificación
     */
    private String id;
    /**
     * Título de la notificación
     */
    private String titulo;
    /**
     * Texto de la notificación
     */
    private String texto;
    /**
     * Se ve o no la notificación
     */
    private boolean vista;
    /**
     * Documento de la persona que tiene la notificación 
     */
    private String documento;
    
    /**
     * Fecha de la notificación
     */
    private String fecha;
    
    /**
     * Tipo de notificación
     */
    private String tipo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
