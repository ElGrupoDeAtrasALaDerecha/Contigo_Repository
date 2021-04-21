package usa.strategy;

/**
 * 
 * @author Santiago Pérez
 */
public class Contexto {
    IStrategy estrategia;

    public Contexto(IStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void enviarCorreo(){
        this.estrategia.enviarCorreo();
    }
    
}
