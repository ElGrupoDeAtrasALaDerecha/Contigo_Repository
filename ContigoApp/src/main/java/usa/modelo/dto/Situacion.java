package usa.modelo.dto;

import java.util.LinkedList;

/**
 * Clase de situaciones
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class Situacion extends Componente{
    
    private LinkedList<Componente> opciones;
 
    public void agregarOpcion(Componente componente){
        opciones.add(componente);
    }
    
    public Situacion() {
        opciones = new LinkedList();
    }
    
    public LinkedList<Componente> getOpciones() {
        return opciones;
    }

    public void setOpciones(LinkedList<Componente> opciones) {
        this.opciones = opciones;
    }
}
