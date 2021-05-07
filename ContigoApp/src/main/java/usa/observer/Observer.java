package usa.observer;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippez
 */
public abstract class Observer {
    protected Subject sujeto;
    public abstract void actualizar();
    public abstract void mostrar(String params);
    public Observer(Subject sujeto) {
        this.sujeto = sujeto;
        sujeto.suscribir(this);
    }
    
}
