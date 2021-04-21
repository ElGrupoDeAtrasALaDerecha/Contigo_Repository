/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dto;

import java.util.LinkedList;

/**
 *
 * @author migue
 */
public class Arbol {

    Situacion primerNodo;

    public void agregarNodo(Situacion situacion) {
        if (primerNodo == null) {
            primerNodo = situacion;
        } else {
            if (primerNodo.getOpciones().isEmpty() || situacion.getPredecesor() == primerNodo.getId()) {
                primerNodo.agregarOpcion(situacion);
                return;
            }
            Situacion situacionPadre = buscarPadre(situacion.getPredecesor(), primerNodo);
            situacionPadre.agregarOpcion(situacion);

        }
    }

    public Situacion buscarPadre(int id, Situacion situacion) {
        if(situacion.getId() == id ){
            return situacion;
        }
        LinkedList<Situacion> situaciones = situacion.getOpciones();
        for (int i = 0; i < situaciones.size(); i++) {
            if (situaciones.get(i).getId() == id) {
                return situaciones.get(i);
            } else {

                return buscarPadre(id, situaciones.get(i));

            }
        }
        return null;
    }
}
