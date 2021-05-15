package test.mail;

import usa.adapter.Contexto;
import usa.adapter.MailRecuperacionContraseñaPersonal;

/**
 *
 * @author Santiago Pérez
 */
public class TestRecuperacionMail {
    public static void main(String[] args) {
        MailRecuperacionContraseñaPersonal estrategia = new MailRecuperacionContraseñaPersonal("santipego0001@gmail.com");
        Contexto contexto = new Contexto(estrategia);
        estrategia.enviarCorreo();
    }
}
