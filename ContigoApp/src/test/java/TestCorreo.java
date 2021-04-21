
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.utils.Correo;

/**
 *
 * @author Santiago Pérez
 */
public class TestCorreo {
    public static void main(String[] args) {
        try {
            Correo.enviarCorreo("santipego0001@gmail.com", "Funciona", "Este es un mensaje de prueba. \nFuente: ContigoApp");
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(TestCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
