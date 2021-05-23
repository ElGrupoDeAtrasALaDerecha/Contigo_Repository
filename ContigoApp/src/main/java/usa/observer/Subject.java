package usa.observer;

import java.util.LinkedList;

/**
 * Clase subject del patrón observador
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public abstract class Subject {

    private LinkedList<Observer> observadores = new LinkedList();
    private int estado;

    public LinkedList<Observer> getObservadores() {
        return observadores;
        
    }

    public void setObservadores(LinkedList<Observer> observadores) {
        this.observadores = observadores;
    }

    public void suscribir(Observer o) {
        this.observadores.add(o);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        
        if(!observadores.isEmpty()){
            this.notificarAObservadores();
        }
        
    }

    public void notificarAObservadores() {
        for (Observer o : observadores) {
            o.actualizar();
        }
    }
}
