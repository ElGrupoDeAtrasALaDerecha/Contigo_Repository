package usa.strategy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.utils.Correo;

/**
 *
 * @author Santiago Pérez
 */
public class MailConfirmacionInstitucion implements IStrategy{
    private final String destino; 

    public MailConfirmacionInstitucion(String destino) {
        this.destino = destino;
    }
    
    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(destino, "Verifique su cuenta", "¡Cuenta verificada!");
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(MailConfirmacionInstitucion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
