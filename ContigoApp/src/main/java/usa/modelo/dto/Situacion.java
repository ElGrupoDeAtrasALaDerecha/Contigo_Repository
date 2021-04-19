package usa.modelo.dto;

import java.util.LinkedList;

/**
 * Clase de situaciones
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class Situacion {
    private int id;
    private int idHistoria;
    private String titulo;
    private String texto;
    private String textoOpcion;
    private LinkedList<Situacion> opciones;

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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }

    public LinkedList<Situacion> getOpciones() {
        return opciones;
    }

    public void setOpciones(LinkedList<Situacion> opciones) {
        this.opciones = opciones;
    }

    public int getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }
    
}
