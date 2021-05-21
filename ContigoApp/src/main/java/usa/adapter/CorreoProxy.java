package usa.adapter;

/**
 *
 * @author Santiago Pérez
 */
public class CorreoProxy extends Email{

    private final Email EMAIL;

    public CorreoProxy(Email email) {
        this.EMAIL= email;
    }
    
    @Override
    public void enviarCorreo(String receptor) {
        EMAIL.enviarCorreo(receptor);
    }
    
}
