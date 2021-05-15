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

    private String contenido="<h1>Cuenta confirmada</h1>\n" +
                                "¡Muchas gracias por registrarte! <br>\n" +
                                "Ahora cuentas con acceso a:\n" +
                                "\n" +
                                "<ol>\n" +
                                "    <li>\n" +
                                "        Conversatorios: nuestro personal calificado brinda su servicio<br>\n" +
                                "        dando conversatorios acerca de temas particulares\n" +
                                "    </li>\n" +
                                "    <li>\n" +
                                "        Historias de situaciones de decisión: puedes verte inmerso en distintas <br>\n" +
                                "        situaciones en las cuales debes tomar decisiones que te permitirán tener <br>\n" +
                                "        realimentación de ellas.\n" +
                                "    </li>\n" +
                                "    <li>\n" +
                                "        Chat privado: si tienes problemas con algún tema particular, <br>\n" +
                                "        puedes contar con nuestro personal\n" +
                                "    </li>\n" +
                                "</ol>";
    public MailConfirmacionEstudiante(String DESTINO) {
        super(DESTINO, "¡Bienvenido como estudiante a ContigoApp!", "./mailTemplates/mailConfirmacionEstudiante.html");
    }

    @Override
    public void enviarCorreo() {
        try {
            Correo.enviarCorreo(DESTINO, ASUNTO, contenido);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(MailConfirmacionInstitucion.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception ex){
            System.out.println("Error inesperado: "+ex.getMessage());
        }
    }
    
}
