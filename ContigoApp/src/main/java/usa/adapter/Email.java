package usa.adapter;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public abstract class Email {
    
    protected String cuerpo;
    
    public abstract void enviarCorreo(String receptor);
}
