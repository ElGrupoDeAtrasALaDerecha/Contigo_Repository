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
public class MailConfirmacionEstudiante extends Email implements IStrategy{

    public MailConfirmacionEstudiante(String DESTINO) {
        super(DESTINO, "Verifique su cuenta como estudiante", "mailTemplates/mailConfirmacionEstudiante.html");
    }

    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(DESTINO, ASUNTO, this.leerArchivo());
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(MailConfirmacionInstitucion.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception ex){
            System.out.println("Error inesperado: "+ex.getMessage());
        }
    }
    
}
