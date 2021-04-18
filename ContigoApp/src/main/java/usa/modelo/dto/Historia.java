package usa.modelo.dto;

/**
 * Clase de historias
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class Historia {
    private int id;
    private int idSituacion;
    private String titulo;
    private String descripcion;
    private String documentoCreador;
    private String urlImagen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSituacion() {
        return idSituacion;
    }

    public void setIdSituacion(int idSituacion) {
        this.idSituacion = idSituacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumentoCreador() {
        return documentoCreador;
    }

    public void setDocumentoCreador(String documentoCreador) {
        this.documentoCreador = documentoCreador;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    
}
