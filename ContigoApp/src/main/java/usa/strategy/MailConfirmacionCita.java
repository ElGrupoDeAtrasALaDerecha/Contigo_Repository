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
public class MailConfirmacionCita extends Email implements IStrategy{

    public MailConfirmacionCita(String DESTINO, String ASUNTO, String TEXTO) {
        super(DESTINO, ASUNTO, TEXTO);
    }

    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(DESTINO, ASUNTO, NOMBRE_ARCHIVO);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(MailConfirmacionCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}