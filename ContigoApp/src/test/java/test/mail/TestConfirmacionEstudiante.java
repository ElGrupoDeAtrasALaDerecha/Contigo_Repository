package test.mail;

import usa.strategy.Contexto;
import usa.strategy.MailConfirmacionEstudiante;
import usa.strategy.MailRecuperacionContraseñaPersonal;

/**
 *
 * @author Santiago Pérez
 */
public class TestConfirmacionEstudiante {
    public static void main(String[] args) {
        MailConfirmacionEstudiante estrategia = new MailConfirmacionEstudiante("santiago.perez01@correo.usa.edu.co");
        Contexto contexto = new Contexto(estrategia);
        estrategia.enviarCorreo();
    }
}
