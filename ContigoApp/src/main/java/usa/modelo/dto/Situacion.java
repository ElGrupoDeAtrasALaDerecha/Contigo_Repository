package usa.modelo.dto;

import java.util.LinkedList;
import usa.modelo.dto.decorador.media.IComponente;

/**
 * Clase de situaciones
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class Situacion extends Componente{
    
    private LinkedList<IComponente> opciones;
 
    public void agregarOpcion(IComponente componente){
        opciones.add(componente);
    }
    
    public Situacion() {
        opciones = new LinkedList();
    }
    
    public LinkedList<IComponente> getOpciones() {
        return opciones;
    }

    public void setOpciones(LinkedList<IComponente> opciones) {
        this.opciones = opciones;
    }
}
