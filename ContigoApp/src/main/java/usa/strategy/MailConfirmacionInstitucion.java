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
public class MailConfirmacionInstitucion extends Email implements IStrategy{

    public MailConfirmacionInstitucion(String DESTINO) {
        super(DESTINO,"Verifique su cuenta","mailTemplates/mailRecuperacionPersonal.html");
    }
    
    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(DESTINO,ASUNTO ,leerArchivo());
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(MailConfirmacionInstitucion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
