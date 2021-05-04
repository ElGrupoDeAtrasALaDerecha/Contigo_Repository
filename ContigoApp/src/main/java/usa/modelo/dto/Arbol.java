package usa.modelo.dto;

import java.util.LinkedList;

/**
 *
 * @author migue
 */
public class Arbol {

    Situacion primerNodo;
    /**
     * Método que agrega una situación a un árbol (o un final). 
     * Se agregan nodos con necesidad de encontrar un nodo padre para componer la historia.
     * @param situacion que es la situación de decisión que se agregará al árbol
     */
    public void agregarNodo(Componente situacion) {
        if (primerNodo == null) {
            primerNodo = (Situacion)situacion;
        } else {
            if (primerNodo.getOpciones().isEmpty() || situacion.getPredecesor() == primerNodo.getId()) {
                primerNodo.agregarOpcion(situacion);
                return;
            }
            Situacion situacionPadre = (Situacion)buscarPadre(situacion.getPredecesor(), primerNodo);
            situacionPadre.agregarOpcion(situacion);
        }
    }
    /**
     * Método que busca un nodo padre
     * @param id que es el id del nodo que se busca
     * @param situacion que es una situación que posiblemente sea el padre o que contenga al padre
     * @return una instancia de situación que puede ser el padre.
     */
    public Componente buscarPadre(int id, Situacion situacion) {
        if(situacion.getId() == id){
            return situacion;
        }
        LinkedList<Componente> situaciones = situacion.getOpciones();
        for (int i = 0; i < situaciones.size(); i++) {
            if (situaciones.get(i).getId() == id) {
                return situaciones.get(i);
            } else {
                if(situaciones.get(i) instanceof Situacion){
                    Situacion posiblePadre=(Situacion)buscarPadre(id, (Situacion)situaciones.get(i));
                    if(posiblePadre!=null){
                        return posiblePadre;
                    }
                } 
            }
        }
        return null;
    }
}
