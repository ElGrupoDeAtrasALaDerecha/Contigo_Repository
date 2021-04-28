package usa.modelo.dto;

import java.util.LinkedList;

/**
 * Clase de situaciones
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class Situacion {
    private int id;
    private int idHistoria;
    private int predecesor;
    private String titulo;
    private String texto;
    private String textoOpcion;
    private LinkedList<Situacion> opciones;
 
    public void agregarOpcion(Situacion st){
        if(opciones == null){
            opciones = new LinkedList();  
        }
        opciones.add(st);
    }
    
    public int getId() {
        return id;
    }

    public Situacion() {
        opciones = new LinkedList();
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

    public int getPredecesor() {
        return predecesor;
    }

    public void setPredecesor(int predecesor) {
        this.predecesor = predecesor;
    }
    
}
