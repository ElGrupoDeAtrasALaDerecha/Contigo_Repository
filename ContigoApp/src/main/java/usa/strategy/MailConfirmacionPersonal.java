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
public class MailConfirmacionPersonal extends Email implements IStrategy{

    public MailConfirmacionPersonal(String DESTINO) {
        super(DESTINO,"Verifique su cuenta como personal calificado de contigo","mailTemplates/mailConfirmacionPersonal.html");
    }


    
    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(DESTINO, ASUNTO, this.leerArchivo());
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(MailConfirmacionInstitucion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
