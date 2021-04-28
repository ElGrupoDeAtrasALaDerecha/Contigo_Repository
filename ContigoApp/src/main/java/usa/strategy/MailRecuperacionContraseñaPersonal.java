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
public class MailRecuperacionContraseñaPersonal extends Email implements IStrategy{

    
    public MailRecuperacionContraseñaPersonal(String destino) {
        super(destino,"Recuperación de contraseña","mailTemplates/mailRecuperacionPersonal.html");
    }
    
    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(DESTINO, ASUNTO, this.leerArchivo());
        } catch (IOException | MessagingException ex) {
            System.out.println("Error interno al enviar el correo: "+ex.getMessage());
            Logger.getLogger(MailConfirmacionInstitucion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
